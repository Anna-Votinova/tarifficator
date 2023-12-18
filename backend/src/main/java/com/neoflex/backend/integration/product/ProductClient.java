package com.neoflex.backend.integration.product;

import com.neoflex.backend.dto.product.CreateProductDto;
import com.neoflex.backend.dto.product.ProductDto;
import com.neoflex.backend.dto.product.UpdateProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@FeignClient(value = "product", url = "${applications.url.product}",
        configuration = ProductClientConfiguration.class)
public interface ProductClient {

    @PostMapping("/create")
    ProductDto createProduct(@RequestHeader("Authorization") String accessToken,
                             @RequestBody CreateProductDto productDto);

    @PutMapping("/update/{productId}")
    ProductDto updateProduct(@RequestHeader("Authorization") String accessToken,
                             @PathVariable UUID productId, @RequestBody UpdateProductDto updateProductDto);

    @DeleteMapping("/remove/{productId}")
    void removeProduct(@RequestHeader("Authorization") String accessToken, @PathVariable UUID productId);
}
