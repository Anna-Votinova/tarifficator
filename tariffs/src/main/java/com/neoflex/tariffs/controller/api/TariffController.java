package com.neoflex.tariffs.controller.api;

import com.neoflex.tariffs.dto.TariffCreateDto;
import com.neoflex.tariffs.dto.TariffDto;
import com.neoflex.tariffs.dto.TariffUpdateDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/tariffs")
@Tag(name = "Tariff Controller",
        description = "Создает, изменяет и удаляет тариф, устанавливает тариф продукту")
public class TariffController {

    @Operation(summary = "Создание тарифа",
            description = "Создает тариф для банковского продукта на основе введенных данных")
    @PostMapping("/create")
    public TariffDto createTariff(@Valid @RequestBody TariffCreateDto tariffCreateDto) {
        log.info("Got the request for creating tariff {}", tariffCreateDto);
        return null;
    }

    @Operation(summary = "Обновление тарифа",
            description = "Обновляет тариф для банковского продукта на основе введенных данных")
    @PutMapping("/update/{tariffId}")
    public TariffDto updateTariff(
            @Positive @PathVariable @Parameter(description = "Идентификатор тарифа",
                    example = "123e4567-e89b-42d3-a456-556642440000", required = true) UUID tariffId,
            @Valid @RequestBody TariffUpdateDto tariffUpdateDto) {
        log.info("Got the request for updating the tariff {}", tariffUpdateDto);
        return null;
    }

    @Operation(summary = "Удаление тарифа",
            description = "Удаляет тариф по идентификатору")
    @DeleteMapping("/remove/{tariffId}")
    public void removeTariff(@Positive @PathVariable @Parameter(description = "Идентификатор тарифа",
            example = "123e4567-e89b-42d3-a456-556642440000", required = true) UUID tariffId) {
        log.info("Got the request for deleting the tariff with id {}", tariffId);
    }

    @Operation(summary = "Установка тарифа продукту",
            description = "Устанавливает тариф продукту по их идентификаторам")
    @PutMapping("/install/{productId}/tariff/{tariffId}")
    public void installTariff(@Positive @PathVariable @Parameter(description = "Идентификатор продукта",
            example = "123e4567-e89b-42d3-a456-556642440000", required = true) UUID productId,
                              @Positive @PathVariable @Parameter(description = "Идентификатор тарифа",
            example = "123e4567-e89b-42d3-a456-556642440000", required = true) UUID tariffId) {
        log.info("Got the request to install the tariff with id {} on the product with id {}", tariffId, productId);
    }

    @Operation(summary = "Получение тарифа",
            description = "Возвращает тариф по id")
    @GetMapping("/{tariffId}")
    public TariffDto getTariff(@Positive @PathVariable @Parameter(description = "Идентификатор тарифа",
            example = "123e4567-e89b-42d3-a456-556642440000", required = true) UUID tariffId) {
        log.info("Got the request to get a tariff with {}", tariffId);
        return null;
    }

    @Operation(summary = "Получение списка тарифов",
            description = "Возвращает список всех тарифов")
    @GetMapping()
    public List<TariffDto> getAll(
            @PositiveOrZero @Parameter(description = "Начальная страница для поиска", example = "0")
            @RequestParam(defaultValue = "0", required = false) int fromPage,
            @Parameter(description = "Последняя страница для поиска", example = "10")
            @Positive @RequestParam(defaultValue = "10", required = false) int toPage) {
        log.info("Got the request to get all tariffs with start page [{}] and finish page [{}] for search",
                fromPage, toPage);
        return Collections.emptyList();
    }
}
