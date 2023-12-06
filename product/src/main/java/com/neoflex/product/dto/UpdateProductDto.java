package com.neoflex.product.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.neoflex.product.dto.enums.ProductType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@Builder
@Schema(description = "Обновляемый продукт")
public class UpdateProductDto {

    @NotBlank
    @Size(min = 2, max = 250, message = "Название продукта должно быть в пределах 2-250 знаков")
    @Schema(description = "Название продукта", example = "Золотая карта клиента")
    private String name;

    @NotNull
    @Schema(description = "Тип продукта", example = "CARD")
    private ProductType productType;

    @NotNull
    @FutureOrPresent
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Schema(description = "Дата начала действия продукта", example = "2024-01-12")
    private LocalDate startDate;

    @NotNull
    @FutureOrPresent
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Schema(description = "Дата окончания действия продукта", example = "2029-01-12")
    private LocalDate endDate;

    @NotBlank
    @Size(min = 2, message = "Описание продукта не может меньше 2 знаков")
    @Schema(description = "Описание продукта",
            example = "Карта для постоянных клиентов с рассрочкой в магазинах-партнерах на 10 месяцев")
    private String description;
}
