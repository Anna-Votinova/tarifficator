package com.neoflex.backend.controller.api.product;

import com.neoflex.backend.dto.product.CreateProductDto;
import com.neoflex.backend.dto.product.ProductDto;
import com.neoflex.backend.dto.product.UpdateProductDto;
import com.neoflex.backend.integration.product.ProductClient;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/product")
@Tag(name = "Product Controller",
        description = "Создает, изменяет и удаляет продукт")
public class ProductBackendController {

    private final ProductClient productClient;

    @Operation(summary = "Создание продукта",
            description = "Создает продукт без возможности создать тариф сразу")
    @PostMapping("/create")
    public ProductDto createProduct(@RequestHeader("Authorization") String accessToken,
                                    @Valid @RequestBody CreateProductDto productDto) {
        log.info("Got the request to create product {}", productDto);
        return productClient.createProduct(accessToken, productDto);
    }

    @Operation(summary = "Обновление продукта",
            description = "Обновляет продукт без возможности обновления тарифа")
    @PutMapping("/update/{productId}")
    public ProductDto updateProduct(
            @RequestHeader("Authorization") String accessToken,
            @PathVariable @Parameter(description = "Идентификатор продукта",
            example = "123e4567-e89b-42d3-a456-556642440000", required = true) UUID productId,
            @Valid @RequestBody UpdateProductDto updateProductDto) {
        log.info("Got the request to update product {}", updateProductDto);
        return productClient.updateProduct(accessToken, productId, updateProductDto);
    }

    @Operation(summary = "Удаление продукта",
            description = "Удаляет продукт по идентификатору")
    @DeleteMapping("/remove/{productId}")
    public void removeProduct(@RequestHeader("Authorization") String accessToken,
                              @PathVariable @Parameter(description = "Идентификатор продукта",
            example = "123e4567-e89b-42d3-a456-556642440000", required = true) UUID productId) {
        log.info("Got the request to delete product with id {}", productId);
        productClient.removeProduct(accessToken, productId);
    }
}
