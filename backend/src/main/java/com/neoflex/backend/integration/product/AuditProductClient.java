package com.neoflex.backend.integration.product;

import com.neoflex.backend.dto.product.ProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


@FeignClient(value = "audit-product", url = "${applications.url.product}",
        configuration = ProductClientConfiguration.class)
public interface AuditProductClient {

    @GetMapping("/audit/find/actual/{productId}")
    ProductDto getActualProductVersion(@PathVariable UUID productId);

    @GetMapping("/audit/find/previous/{productId}")
    List<ProductDto> getPreviousProductVersions(@PathVariable UUID productId);
    @GetMapping("/audit/find/period/{productId}")
    List<ProductDto> getProductVersionsByPeriod(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fromDate,
                                                @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate toDate,
                                                @PathVariable UUID productId);

    @PutMapping("/audit/revert/{productId}")
    ProductDto revertProductVersion(@PathVariable UUID productId, @RequestParam long version);
}
