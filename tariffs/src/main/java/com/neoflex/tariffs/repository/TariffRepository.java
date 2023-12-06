package com.neoflex.tariffs.repository;

import com.neoflex.tariffs.entity.Tariff;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface TariffRepository extends JpaRepository<Tariff, UUID>, RevisionRepository<Tariff, UUID, Long> {

    @Query("select t from Tariff t where lower(t.name) like lower(concat('%', :phrase,'%')) " +
            "or lower(t.description) like lower(concat('%', :phrase,'%'))")
    Page<Tariff> findByNameOrDescription(@Param("phrase") String searchPhrase, Pageable pageable);

}
