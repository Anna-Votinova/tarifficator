package com.neoflex.product.repository;

import com.neoflex.product.entity.Product;
import org.springframework.data.repository.history.RevisionRepository;

import java.util.UUID;

public interface ProductRevisionRepository extends RevisionRepository<Product, UUID, Long> {
}
