package com.neoflex.tariffs.repository;

import com.neoflex.tariffs.entity.Tariff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;

import java.util.UUID;

public interface TariffRepository extends JpaRepository<Tariff, UUID>, RevisionRepository<Tariff, UUID, Long> {
}
