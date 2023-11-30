package com.neoflex.backend.dto.tariffs;

import com.fasterxml.jackson.annotation.JsonFormat;
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
@Schema(description = "Обновляемый тариф")
public class TariffUpdateDto {

    @NotBlank
    @Size(min = 2, max = 250, message = "Название тарифа должно быть в пределах 2-250 знаков")
    @Schema(description = "Название тарифа", example = "Новогодний тариф для держателей золотых карт")
    private String name;

    @NotNull
    @FutureOrPresent
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Schema(description = "Дата начала действия тарифа", example = "2024-01-12")
    private LocalDate startDate;

    @NotNull
    @FutureOrPresent
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Schema(description = "Дата окончания действия тарифа", example = "2026-01-12")
    private LocalDate endDate;

    @NotBlank
    @Size(min = 2, message = "Описание тарифа не может меньше 2 знаков")
    @Schema(description = "Описание тарифа", example = "Специальный тариф на золотые карты на 2 года")
    private String description;

    @NotNull
    @Schema(description = "Стоимость тарифа", example = "2000.00")
    private Double rate;
}
