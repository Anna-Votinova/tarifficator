package com.neoflex.product.dto;

import com.neoflex.product.dto.enums.ProductType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@Schema(description = "Продукт")
public class ProductDto {

    @Schema(description = "Идентификатор продукта", example = "123e4567-e89b-42d3-a456-556642440000")
    private UUID id;

    @Schema(description = "Название продукта", example = "Золотая карта клиента")
    private String name;

    @Schema(description = "Тип продукта", example = "CARD")
    private ProductType productType;

    @Schema(description = "Дата начала действия продукта", example = "2024-01-12")
    private LocalDate startDate;

    @Schema(description = "Дата окончания действия продукта", example = "2029-01-12")
    private LocalDate endDate;

    @Schema(description = "Описание продукта",
            example = "Карта для постоянных клиентов с рассрочкой в магазинах-партнерах на 10 месяцев")
    private String description;

    private TariffDto tariffDto;

    @Schema(description = "Идентификатор создателя продукта", example = "1")
    private Long author;

    @Schema(description = "Номер версии продукта", example = "0")
    private long version;
}
