package com.neoflex.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@Schema(description = "Тариф")
public class TariffDto {

    @Schema(description = "Идентификатор тарифа", example = "123e4567-e89b-42d3-a456-556642440001")
    private UUID id;

    @Schema(description = "Название тарифа", example = "Новогодний тариф для держателей золотых карт")
    private String name;

    @Schema(description = "Дата начала действия тарифа", example = "2024-01-12")
    private LocalDate startDate;

    @Schema(description = "Дата окончания действия тарифа", example = "2026-01-12")
    private LocalDate endDate;

    @Schema(description = "Описание тарифа", example = "Специальный тариф на золотые карты на 2 года")
    private String description;

    @Schema(description = "Стоимость тарифа", example = "2000.00")
    private Double rate;

    @Schema(description = "Автор тарифа", example = "ADMIN")
    private Long author;

    @Schema(description = "Версия тарифа", example = "0")
    private long version;
}
