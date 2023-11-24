package com.neoflex.product.controller.api;


import com.neoflex.product.dto.ProductDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.UUID;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/product")
@Tag(name = "Product Controller",
        description = "Создает, изменяет и удаляет продукт")
public class ProductController {

    @Operation(summary = "Создание продукта",
            description = "Создает продукт без возможности создать тариф сразу")
    @PostMapping("/create")
    public ProductDto createProduct(@Valid @RequestBody ProductDto productDto) {
        return null;
    }

    @Operation(summary = "Обновление продукта",
            description = "Обновляет продукт без возможности обновления тарифа")
    @PutMapping("/update")
    public ProductDto updateProduct(@Valid @RequestBody ProductDto productDto) {
        return null;
    }

    @Operation(summary = "Удаление продукта",
            description = "Удаляет продукт по идентификатору")
    @DeleteMapping("/remove/{productId}")
    public void removeProduct(@Positive @PathVariable @Parameter(description = "Идентификатор продукта",
            example = "123e4567-e89b-42d3-a456-556642440000", required = true) UUID productId) {
        log.info("delete method");
    }
}
