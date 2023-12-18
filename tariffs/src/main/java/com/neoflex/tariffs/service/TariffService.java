package com.neoflex.tariffs.service;

import com.neoflex.tariffs.dto.*;
import com.neoflex.tariffs.entity.Tariff;
import com.neoflex.tariffs.exception.TariffNotFoundException;
import com.neoflex.tariffs.exception.ValidationException;
import com.neoflex.tariffs.integration.feign.AuthClient;
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

    private static final String SERVICE_NAME = "tariffs";

    private final TariffRepository tariffRepository;
    private final TariffMessageProducer tariffMessageProducer;
    private final ProductKafkaConfig productKafkaConfig;
    private final AuthClient authClient;

    /**
     * <p> Creates tariff
     * </p>
     * @param tariffCreateDto - tariff info to save
     * @return a new tariff with info about itd author and version
     */
    public TariffDto createTariff(String accessToken, TariffCreateDto tariffCreateDto) {
        verifyUser(accessToken);
        Tariff newTariff = TariffMapper.toTariff(tariffCreateDto);
        Tariff createdTariff = tariffRepository.save(newTariff);
        return TariffMapper.toTariffDto(createdTariff);
    }

    /**
     * <p> Updates tariff fields except an author and a version
     * </p>
     * @param tariffId - a tariff id
     * @param tariffUpdateDto - info about a tariff to update existing entity
     * @return an updated tariff
     * @throws com.neoflex.tariffs.exception.TariffNotFoundException if the tariff with the given id does not exist
     */
    public TariffDto updateTariff(String accessToken, UUID tariffId, TariffUpdateDto tariffUpdateDto) {
        verifyUser(accessToken);

        Tariff exixtingTariff = findTariff(tariffId);
        exixtingTariff.setName(tariffUpdateDto.getName());
        exixtingTariff.setStartDate(tariffUpdateDto.getStartDate());
        exixtingTariff.setEndDate(tariffUpdateDto.getEndDate());
        exixtingTariff.setDescription(tariffUpdateDto.getDescription());
        exixtingTariff.setRate(tariffUpdateDto.getRate());
        tariffRepository.save(exixtingTariff);

        return TariffMapper.toTariffDto(exixtingTariff);
    }

    /**
     * <p> Removes a tariff by id
     * </p>
     * @param tariffId - a tariff id
     */
    public void removeTariff(String accessToken, UUID tariffId) {
        verifyUser(accessToken);
        tariffRepository.deleteById(tariffId);
    }

    /**
     * <p> Returns a tariff by id
     * </p>
     * @param tariffId - a tariff id
     * @throws com.neoflex.tariffs.exception.TariffNotFoundException if the tariff with the given id does not exist
     */
    public TariffDto getTariff(String accessToken, UUID tariffId) {
        verifyUser(accessToken);
        Tariff tariff = findTariff(tariffId);
        return TariffMapper.toTariffDto(tariff);
    }

    /**
     * <p> Returns a list of tariffs by given parameters
     * </p>
     * @param searchPhrase - text for searching in the name or in the description of a tariff. Optional.
     * @param fromPage - the start page for the searching. Default value: 0.
     * @param toPage - the finish page for the searching. Default value: 10.
     * @return the list of tariffs
     * @throws com.neoflex.tariffs.exception.ValidationException if the start page is greater than the finish page
     */
    @Transactional(readOnly = true)
    public List<TariffDto> getAll(String accessToken, String searchPhrase, int fromPage, int toPage) {
        verifyUser(accessToken);
        checkPages(fromPage, toPage);
        Pageable pageable = PageRequest.of(fromPage, toPage);
        List<Tariff> tariffs;

        if (Objects.isNull(searchPhrase) || searchPhrase.isBlank()) {
            tariffs = tariffRepository.findAll(pageable).stream().toList();
        } else {
            tariffs = tariffRepository
                    .findByNameOrDescription(searchPhrase, pageable)
                    .stream().toList();
        }
        log.info("List size of tariffs equals {}", tariffs.size());

        if (tariffs.isEmpty()) {
            return Collections.emptyList();
        }
        return TariffMapper.mapToTariffDtoList(tariffs);
    }

    /**
     * <p> Installs a tariff to a product through Kafka and Product Service
     * </p>
     * @param productId - a product id
     * @param tariffId - a tariff id
     * @throws com.neoflex.tariffs.exception.TariffNotFoundException if the tariff with the given id does not exist
     */
    @Transactional
    public void installTariff(String accessToken, UUID productId, UUID tariffId) {
        String login = authClient.verify(accessToken, SERVICE_NAME);
        log.info("Token {} verified. Username {}", accessToken, login);

        Tariff tariff = findTariff(tariffId);
        TariffKafkaMessage tariffKafkaMessage = TariffMapper.toTariffKafkaMessage(productId, tariff, login);
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

    private void verifyUser(String accessToken) {
        String login = authClient.verify(accessToken, SERVICE_NAME);
        log.info("Token {} verified. Username {}", accessToken, login);
    }
}
