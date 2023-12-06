package com.neoflex.backend.integration.tariffs;

import com.neoflex.backend.dto.TariffDto;
import com.neoflex.backend.dto.tariffs.TariffCreateDto;
import com.neoflex.backend.dto.tariffs.TariffUpdateDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@FeignClient(value = "tariffs", url = "${applications.url.tariffs}",
        configuration = TariffClientConfiguration.class)
public interface TariffClient {

    @PostMapping("/create")
    TariffDto createTariff(@RequestBody TariffCreateDto tariffCreateDto);

    @PutMapping("/update/{tariffId}")
    TariffDto updateTariff(@PathVariable UUID tariffId, @RequestBody TariffUpdateDto tariffUpdateDto);

    @DeleteMapping("/remove/{tariffId}")
    void removeTariff(@PathVariable UUID tariffId);

    @PutMapping("/install/{productId}/tariff/{tariffId}")
    void installTariff(@PathVariable UUID productId, @PathVariable UUID tariffId);

    @GetMapping("/{tariffId}")
    TariffDto getTariff(@PathVariable UUID tariffId);

    @GetMapping()
    List<TariffDto> getAll(@RequestParam String searchPhrase, @RequestParam int fromPage, @RequestParam int toPage);
}
