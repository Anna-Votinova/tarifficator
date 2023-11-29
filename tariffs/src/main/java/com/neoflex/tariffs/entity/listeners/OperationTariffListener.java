package com.neoflex.tariffs.entity.listeners;

import com.neoflex.tariffs.entity.Tariff;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

@Slf4j
public class OperationTariffListener {

    @PostLoad
    public void postLoad(Tariff tariff) {
        log.info("From database loaded tariff with id [{}]", tariff.getId());
    }

    @PrePersist
    public void prePersist(Tariff tariff) {
        log.info("Try to save tariff {}", tariff);
    }

    @PostPersist
    public void postPersist(Tariff tariff) {
        log.info("Saved tariff {}", tariff);
    }

    @PreRemove
    public void preRemove(Tariff tariff) {
        log.info("Try to delete tariff with id [{}]", tariff.getId());
    }

    @PostRemove
    public void postRemove(Tariff tariff) {
        log.info("Deleted tariff {}", tariff);
    }

    @PreUpdate
    public void preUpdate(Tariff tariff) {
        log.info("Try to update tariff with id [{}]", tariff.getId());
    }

    @PostUpdate
    public void postUpdate(Tariff tariff) {
        log.info("Updated tariff {}", tariff);
    }
}
