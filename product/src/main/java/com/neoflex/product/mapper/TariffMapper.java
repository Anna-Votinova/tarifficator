package com.neoflex.product.mapper;

import com.neoflex.product.dto.TariffDto;
import com.neoflex.product.dto.TariffKafkaMessage;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TariffMapper {

    public static TariffDto toTariffDto(TariffKafkaMessage tariffKafkaMessage) {
        return TariffDto.builder()
                .id(tariffKafkaMessage.getId())
                .name(tariffKafkaMessage.getName())
                .startDate(tariffKafkaMessage.getStartDate())
                .endDate(tariffKafkaMessage.getEndDate())
                .description(tariffKafkaMessage.getDescription())
                .rate(tariffKafkaMessage.getRate())
                .author(tariffKafkaMessage.getAuthor())
                .version(tariffKafkaMessage.getVersion())
                .build();
    }
}
