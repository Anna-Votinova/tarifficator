package com.neoflex.product.mapper;

import com.neoflex.product.dto.CreateProductDto;
import com.neoflex.product.dto.ProductDto;
import com.neoflex.product.entity.Product;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ProductMapper {

    public static Product toShortProduct(CreateProductDto productDto) {
        return Product.builder()
                .name(productDto.getName())
                .productType(productDto.getProductType())
                .startDate(productDto.getStartDate())
                .endDate(productDto.getEndDate())
                .description(productDto.getDescription())
                .authorId(productDto.getAuthor())
                .build();
    }

    public static ProductDto toProductDto(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .productType(product.getProductType())
                .startDate(product.getStartDate())
                .endDate(product.getEndDate())
                .description(product.getDescription())
                .tariffDto(product.getTariffDto())
                .author(product.getAuthorId())
                .version(product.getVersion())
                .build();
    }

    public static List<ProductDto> toListProductDto(List<Product> products) {
        return products.stream().map(ProductMapper::toProductDto).toList();
    }
}
