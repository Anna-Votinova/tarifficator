package com.neoflex.product.config;

import lombok.RequiredArgsConstructor;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

@Configuration
@RequiredArgsConstructor
public class AuditReaderConfiguration {

    private final EntityManagerFactory entityManagerFactory;
    @Bean
    public EntityManager entityManager() {
        return entityManagerFactory.createEntityManager();
    }

    @Bean
    public AuditReader auditReader() {
        return AuditReaderFactory.get(entityManager());
    }
}
