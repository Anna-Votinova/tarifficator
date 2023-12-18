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
    TariffDto createTariff(@RequestHeader("Authorization") String accessToken,
                           @RequestBody TariffCreateDto tariffCreateDto);

    @PutMapping("/update/{tariffId}")
    TariffDto updateTariff(@RequestHeader("Authorization") String accessToken,
                           @PathVariable UUID tariffId, @RequestBody TariffUpdateDto tariffUpdateDto);

    @DeleteMapping("/remove/{tariffId}")
    void removeTariff(@RequestHeader("Authorization") String accessToken, @PathVariable UUID tariffId);

    @PutMapping("/install/{productId}/tariff/{tariffId}")
    void installTariff(@RequestHeader("Authorization") String accessToken,
                       @PathVariable UUID productId, @PathVariable UUID tariffId);

    @GetMapping("/{tariffId}")
    TariffDto getTariff(@RequestHeader("Authorization") String accessToken, @PathVariable UUID tariffId);

    @GetMapping()
    List<TariffDto> getAll(@RequestHeader("Authorization") String accessToken,
                           @RequestParam String searchPhrase, @RequestParam int fromPage, @RequestParam int toPage);
}
