package com.neoflex.product.service;

import com.neoflex.product.dto.CreateProductDto;
import com.neoflex.product.dto.ProductDto;
import com.neoflex.product.dto.UpdateProductDto;
import com.neoflex.product.entity.Product;
import com.neoflex.product.exception.ProductNotFoundException;
import com.neoflex.product.mapper.ProductMapper;
import com.neoflex.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ProductDto create(CreateProductDto productDto) {
        Product product = ProductMapper.toShortProduct(productDto);
        productRepository.save(product);
        return ProductMapper.toProductDto(product);
    }

    public ProductDto update(UUID productId, UpdateProductDto newProduct) {
        Product oldProduct = findProductById(productId);

        oldProduct.setName(newProduct.getName());
        oldProduct.setProductType(newProduct.getProductType());
        oldProduct.setStartDate(newProduct.getStartDate());
        oldProduct.setEndDate(newProduct.getEndDate());
        oldProduct.setDescription(newProduct.getDescription());

        productRepository.save(oldProduct);
        return ProductMapper.toProductDto(oldProduct);
    }

    public void remove(UUID productId) {
        productRepository.deleteById(productId);
    }

    private Product findProductById(UUID productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Продукт с id " + productId + " не существует"));
    }
}
