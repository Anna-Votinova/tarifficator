package com.neoflex.product.entity.listeners;

import com.neoflex.product.entity.Product;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

@Slf4j
public class OperationProductListener {
    @PostLoad
    public void postLoad(Product product) {
        log.info("From database loaded product with id [{}]", product.getId());
    }

    @PrePersist
    public void prePersist(Product product) {
        log.info("Try to save product with id [{}]", product.getId());
    }

    @PostPersist
    public void postPersist(Product product) {
        log.info("Saved product {}", product);
    }

    @PreRemove
    public void preRemove(Product product) {
        log.info("Try to delete product with id [{}]", product.getId());
    }

    @PostRemove
    public void postRemove(Product product) {
        log.info("Deleted product {}", product);
    }

    @PreUpdate
    public void preUpdate(Product product) {
        log.info("Try to update product with id [{}]", product.getId());
    }

    @PostUpdate
    public void postUpdate(Product product) {
        log.info("Updated product {}", product);
    }
}
