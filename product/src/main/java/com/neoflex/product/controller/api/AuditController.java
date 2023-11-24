package com.neoflex.product.controller.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.neoflex.product.dto.ProductDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/audit")
@Tag(name = "Audit Controller",
        description = "Предоставляет версии продукта")
public class AuditController {

    @Operation(summary = "Получение продукта",
            description = "Возвращает текущую версию продукта по его идентификатору")
    @GetMapping("/find/actual/{productId}")
    public ProductDto getActualProductVersion(@Positive @PathVariable @Parameter(description = "Идентификатор продукта",
            example = "123e4567-e89b-42d3-a456-556642440000", required = true) UUID productId) {
        return null;
    }

    @Operation(summary = "Получение старых версий продукта",
            description = "Возвращает предыдущие версии продукта по его идентификатору")
    @GetMapping("/find/previous/{productId}")
    public List<ProductDto> getPreviousProductVersions(
            @Positive @PathVariable @Parameter(description = "Идентификатор продукта",
            example = "123e4567-e89b-42d3-a456-556642440000", required = true) UUID productId) {
        return null;
    }

    @Operation(summary = "Получение версий продукта на определенный период",
            description = "Возвращает версии продукта по его идентификатору с учетом указанного периода")
    @GetMapping("/find/period/{productId}")
    public List<ProductDto> getProductVersionsByPeriod(
            @Parameter(description = "Дата начала периода", example = "2024-01-12") @RequestParam(required = false)
            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") LocalDate formDate,
            @Parameter(description = "Дата окончания периода", example = "2029-01-12") @RequestParam(required = false)
            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") LocalDate toDate,
            @Positive @PathVariable @Parameter(description = "Идентификатор продукта",
                    example = "123e4567-e89b-42d3-a456-556642440000", required = true) UUID productId) {
        return null;
    }

    @Operation(summary = "Откат версии продукта",
            description = "Откатывает версию продукта на предыдущую")
    @PutMapping("/revert/{productId}")
    public ProductDto revertProductVersion(@Positive @PathVariable @Parameter(description = "Идентификатор продукта",
            example = "123e4567-e89b-42d3-a456-556642440000", required = true) UUID productId) {
        return null;
    }
}
