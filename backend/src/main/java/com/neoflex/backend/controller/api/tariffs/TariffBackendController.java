package com.neoflex.backend.controller.api.tariffs;

import com.neoflex.backend.dto.TariffDto;
import com.neoflex.backend.dto.tariffs.TariffCreateDto;
import com.neoflex.backend.dto.tariffs.TariffUpdateDto;
import com.neoflex.backend.integration.tariffs.TariffClient;
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
import java.util.List;
import java.util.UUID;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/tariffs")
@Tag(name = "Tariff Controller",
        description = "Создает, изменяет и удаляет тариф, устанавливает тариф продукту")
public class TariffBackendController {

    private final TariffClient tariffClient;

    @Operation(summary = "Создание тарифа",
            description = "Создает тариф для банковского продукта на основе введенных данных")
    @PostMapping("/create")
    public TariffDto createTariff(@RequestHeader("Authorization") String accessToken,
                                  @Valid @RequestBody TariffCreateDto tariffCreateDto) {
        log.info("Got the request for creating tariff {}", tariffCreateDto);
        return tariffClient.createTariff(accessToken, tariffCreateDto);
    }

    @Operation(summary = "Обновление тарифа",
            description = "Обновляет тариф для банковского продукта на основе введенных данных")
    @PutMapping("/update/{tariffId}")
    public TariffDto updateTariff(
            @RequestHeader("Authorization") String accessToken,
            @PathVariable @Parameter(description = "Идентификатор тарифа",
                    example = "123e4567-e89b-42d3-a456-556642440000", required = true) UUID tariffId,
            @Valid @RequestBody TariffUpdateDto tariffUpdateDto) {
        log.info("Got the request for updating the tariff {}", tariffUpdateDto);
        return tariffClient.updateTariff(accessToken, tariffId, tariffUpdateDto);
    }

    @Operation(summary = "Удаление тарифа",
            description = "Удаляет тариф по идентификатору")
    @DeleteMapping("/remove/{tariffId}")
    public void removeTariff(@RequestHeader("Authorization") String accessToken,
                             @PathVariable @Parameter(description = "Идентификатор тарифа",
            example = "123e4567-e89b-42d3-a456-556642440000", required = true) UUID tariffId) {
        log.info("Got the request for deleting the tariff with id {}", tariffId);
        tariffClient.removeTariff(accessToken, tariffId);

    }

    @Operation(summary = "Установка тарифа продукту",
            description = "Устанавливает тариф продукту по их идентификаторам")
    @PutMapping("/install/{productId}/tariff/{tariffId}")
    public void installTariff(@RequestHeader("Authorization") String accessToken,
                              @PathVariable @Parameter(description = "Идентификатор продукта",
            example = "123e4567-e89b-42d3-a456-556642440000", required = true) UUID productId,
                              @PathVariable @Parameter(description = "Идентификатор тарифа",
                                      example = "123e4567-e89b-42d3-a456-556642440000", required = true) UUID tariffId) {
        log.info("Got the request to install the tariff with id {} on the product with id {}", tariffId, productId);
        tariffClient.installTariff(accessToken, productId, tariffId);
    }

    @Operation(summary = "Получение тарифа",
            description = "Возвращает тариф по id")
    @GetMapping("/{tariffId}")
    public TariffDto getTariff(@RequestHeader("Authorization") String accessToken,
                               @PathVariable @Parameter(description = "Идентификатор тарифа",
            example = "123e4567-e89b-42d3-a456-556642440000", required = true) UUID tariffId) {
        log.info("Got the request to get a tariff with {}", tariffId);
        return tariffClient.getTariff(accessToken, tariffId);
    }

    @Operation(summary = "Получение списка тарифов",
            description = "Возвращает список тарифов в зависимости от поисковой фразы и страниц выдачи результата")
    @GetMapping()
    public List<TariffDto> getAll(
            @RequestHeader("Authorization") String accessToken,
            @Parameter(description = "Текст для поиска по названию и описанию", example = "новогодний")
            @RequestParam(required = false) String searchPhrase,
            @PositiveOrZero @Parameter(description = "Начальная страница для поиска", example = "0")
            @RequestParam(defaultValue = "0", required = false) int fromPage,
            @Parameter(description = "Последняя страница для поиска", example = "10")
            @Positive @RequestParam(defaultValue = "10", required = false) int toPage) {
        log.info("Got the request to get tariffs with phrase [{}], start page [{}] and finish page [{}] for searching",
                searchPhrase, fromPage, toPage);
        return tariffClient.getAll(accessToken, searchPhrase, fromPage, toPage);
    }
}
