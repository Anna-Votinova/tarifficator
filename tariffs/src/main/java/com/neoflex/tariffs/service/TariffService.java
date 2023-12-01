package com.neoflex.tariffs.service;

import com.neoflex.tariffs.dto.TariffCreateDto;
import com.neoflex.tariffs.dto.TariffDto;
import com.neoflex.tariffs.dto.TariffKafkaMessage;
import com.neoflex.tariffs.dto.TariffUpdateDto;
import com.neoflex.tariffs.entity.Tariff;
import com.neoflex.tariffs.exception.TariffNotFoundException;
import com.neoflex.tariffs.exception.ValidationException;
import com.neoflex.tariffs.integration.kafka.ProductKafkaConfig;
import com.neoflex.tariffs.integration.kafka.TariffMessageProducer;
import com.neoflex.tariffs.mapper.TariffMapper;
import com.neoflex.tariffs.repository.TariffRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class TariffService {

    private final TariffRepository tariffRepository;
    private final TariffMessageProducer tariffMessageProducer;
    private final ProductKafkaConfig productKafkaConfig;

    public TariffDto createTariff(TariffCreateDto tariffCreateDto) {
        Tariff newTariff = TariffMapper.toTariff(tariffCreateDto);
        Tariff createdTariff = tariffRepository.save(newTariff);
        return TariffMapper.toTariffDto(createdTariff);
    }

    public TariffDto updateTariff(UUID tariffId, TariffUpdateDto tariffUpdateDto) {
        Tariff exixtingTariff = findTariff(tariffId);

        exixtingTariff.setName(tariffUpdateDto.getName());
        exixtingTariff.setStartDate(tariffUpdateDto.getStartDate());
        exixtingTariff.setEndDate(tariffUpdateDto.getEndDate());
        exixtingTariff.setDescription(tariffUpdateDto.getDescription());
        exixtingTariff.setRate(tariffUpdateDto.getRate());

        tariffRepository.save(exixtingTariff);
        return TariffMapper.toTariffDto(exixtingTariff);
    }

    public void removeTariff(UUID tariffId) {
        tariffRepository.deleteById(tariffId);
    }

    public TariffDto getTariff(UUID tariffId) {
        Tariff tariff = findTariff(tariffId);
        return TariffMapper.toTariffDto(tariff);
    }

    @Transactional(readOnly = true)
    public List<TariffDto> getAll(String searchPhrase, int fromPage, int toPage) {
        checkPages(fromPage, toPage);
        Pageable pageable = PageRequest.of(fromPage, toPage);
        List<Tariff> tariffs;

        if (Objects.isNull(searchPhrase) || searchPhrase.isBlank()) {
            tariffs = tariffRepository.findAll(pageable).stream().toList();
        } else {
            tariffs = tariffRepository
                    .findByNameOrDescription(searchPhrase, searchPhrase, pageable)
                    .stream().toList();
        }
        log.info("List size of tariffs equals {}", tariffs.size());

        if (tariffs.isEmpty()) {
            return Collections.emptyList();
        }
        return TariffMapper.mapToTariffDtoList(tariffs);
    }

    @Transactional
    public void installTariff(UUID productId, UUID tariffId) {
        Tariff tariff = findTariff(tariffId);
        TariffKafkaMessage tariffKafkaMessage = TariffMapper.toTariffKafkaMessage(productId, tariff);
        tariffMessageProducer.sendTariffMessage(productKafkaConfig.getInstallTariffTopic(), tariffKafkaMessage);
    }

    private Tariff findTariff(UUID tariffId) {
        return tariffRepository.findById(tariffId)
                .orElseThrow(() -> new TariffNotFoundException("Тариф с id " + tariffId + " не существует"));
    }

    private void checkPages(int fromPage, int toPage) {
        if (fromPage > toPage) {
            throw new ValidationException("Начальная страница не должна быть больше окончательной");
        }
    }
}
