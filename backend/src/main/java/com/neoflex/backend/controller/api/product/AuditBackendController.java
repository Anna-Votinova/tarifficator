package com.neoflex.backend.controller.api.product;

import com.neoflex.backend.dto.product.ProductDto;
import com.neoflex.backend.integration.product.AuditProductClient;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/product/audit")
@Tag(name = "Product Audit Controller",
        description = "Предоставляет версии продукта")
public class AuditBackendController {

    private final AuditProductClient auditProductClient;

    @Operation(summary = "Получение текущей версии продукта",
            description = "Возвращает текущую версию продукта по его идентификатору")
    @GetMapping("/find/actual/{productId}")
    public ProductDto getActualProductVersion(@RequestHeader("Authorization") String accessToken,
                                              @PathVariable @Parameter(description = "Идентификатор продукта",
            example = "123e4567-e89b-42d3-a456-556642440000", required = true) UUID productId) {
        log.info("Got the request for retrieving current version of the product with id {}", productId);
        return auditProductClient.getActualProductVersion(accessToken, productId);
    }

    @Operation(summary = "Получение старых версий продукта",
            description = "Возвращает предыдущие версии продукта по его идентификатору. Последняя версия не " +
                    "возвращается")
    @GetMapping("/find/previous/{productId}")
    public List<ProductDto> getPreviousProductVersions(
            @RequestHeader("Authorization") String accessToken,
            @PathVariable @Parameter(description = "Идентификатор продукта",
            example = "123e4567-e89b-42d3-a456-556642440000", required = true) UUID productId) {
        log.info("Got the request for retrieving previous versions of the product with id {}", productId);
        return auditProductClient.getPreviousProductVersions(accessToken, productId);
    }

    @Operation(summary = "Получение версий продукта на определенный период",
            description = "Возвращает версии продукта по его идентификатору с учетом указанного периода. Указывать " +
                    "обе даты обязательно.")
    @GetMapping("/find/period/{productId}")
    public List<ProductDto> getProductVersionsByPeriod(
            @RequestHeader("Authorization") String accessToken,
            @Parameter(description = "Дата начала периода", example = "2024-01-12")
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fromDate,
            @Parameter(description = "Дата окончания периода", example = "2029-01-12")
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate toDate,
            @PathVariable @Parameter(description = "Идентификатор продукта",
                    example = "123e4567-e89b-42d3-a456-556642440000", required = true) UUID productId) {
        log.info("Got the request for retrieving versions of the product with id {} by period with start date {} " +
                "and end date {}", productId, fromDate, toDate);
        return auditProductClient.getProductVersionsByPeriod(accessToken, fromDate, toDate, productId);
    }

    @Operation(summary = "Откат версии продукта",
            description = "Откатывает версию продукта на одну из предыдущих. Возвращает продукт с необходимыми " +
                    "значениями. Номер версии при этом возрастает.")
    @PutMapping("/revert/{productId}")
    public ProductDto revertProductVersion(
            @RequestHeader("Authorization") String accessToken,
            @PathVariable @Parameter(description = "Идентификатор продукта",
            example = "123e4567-e89b-42d3-a456-556642440000", required = true) UUID productId,
            @PositiveOrZero @Parameter(description = "Версия продукта", example = "1") @RequestParam long version) {
        log.info("Got the request for changing existing version of the product with id {} on one of the previous " +
                "versions: {}", productId, version);
        return auditProductClient.revertProductVersion(accessToken, productId, version);
    }
}
