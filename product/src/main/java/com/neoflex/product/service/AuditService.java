package com.neoflex.product.service;

import com.neoflex.product.dto.ProductDto;
import com.neoflex.product.entity.Product;
import com.neoflex.product.exception.RevisionException;
import com.neoflex.product.mapper.ProductMapper;
import com.neoflex.product.repository.ProductRepository;
import com.neoflex.product.repository.ProductRevisionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.springframework.data.history.Revision;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.EntityManager;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuditService {

    private final ProductRevisionRepository productRevisionRepository;
    private final ProductRepository productRepository;
    private final AuditReader auditReader;
    private final EntityManager entityManager;

    @Transactional(readOnly = true)
    public ProductDto getActualVersion(UUID productId) {
        Product actualProduct = getRevision(productId).getEntity();
        log.info("Actual version of the product {}", actualProduct);
        return ProductMapper.toProductDto(actualProduct);
    }

    @Transactional(readOnly = true)
    public List<ProductDto> getPreviousVersions(UUID productId) {
        Product actualProduct = getRevision(productId).getEntity();

        List<Product> previousProducts = productRevisionRepository.findRevisions(productId).stream()
                .map(Revision::getEntity)
                .filter(p -> p.getVersion() < actualProduct.getVersion())
                .toList();
        log.info("Product list size {}", previousProducts.size());
        return ProductMapper.toListProductDto(previousProducts);
    }

    @Transactional(readOnly = true)
    public List<ProductDto> getVersionsByPeriod(LocalDate fromDate, LocalDate toDate, UUID productId) {
        Long periodStart = convertTime(fromDate);
        Long periodEnd = convertTime(toDate);

        AuditQuery auditQuery = auditReader.createQuery().forRevisionsOfEntity(Product.class, true, true)
                .add(AuditEntity.property("id").eq(productId))
                .add(AuditEntity.revisionProperty("timestamp").between(periodStart, periodEnd));

        List<Product> products = auditQuery.getResultList();
        log.info("Product list {}", products);
        return ProductMapper.toListProductDto(products);
    }

    @Transactional
    public ProductDto revertVersion(UUID productId, long version) {
        Revision<Long,Product> lastRevision = getRevision(productId);

        if (lastRevision.getEntity().getVersion() < version) {
            throw new RevisionException("Версия [ " + version + " ] для продукта с id [ " + productId +
                    " ] больше существующей версии продукта [ " + lastRevision.getEntity().getVersion() + " ]");
        }

        AuditQuery auditQuery = auditReader.createQuery().forRevisionsOfEntity(Product.class, true, true)
                .add(AuditEntity.property("id").eq(productId))
                .add(AuditEntity.property("version").eq(version));

        Product previousProductVersion = (Product) auditQuery.getSingleResult();
        log.info("Previous product retrieved from the database {}", previousProductVersion);

        Product updatingProduct = updateProduct(lastRevision.getEntity(), previousProductVersion);
        log.info("Updating product {}", updatingProduct);
        Product savedProduct = productRepository.save(updatingProduct);
        entityManager.flush();

        return ProductMapper.toProductDto(savedProduct);
    }

    private Revision<Long,Product> getRevision(UUID productId) {
        return productRevisionRepository.findLastChangeRevision(productId)
                .orElseThrow(() -> new RevisionException("Ревизия для продукта с id " + productId + " не существует"));
    }

    private Long convertTime(LocalDate date) {
        Timestamp timestamp = Timestamp.valueOf(date.atStartOfDay());
        log.debug("Timestamp of date {} equals {}", date, timestamp);
        Long longDate = timestamp.getTime();
        log.debug("Long value of timestamp date {} equals {}", timestamp, longDate);
        return longDate;
    }
    private Product updateProduct(Product updatingProduct, Product previousProductVersion) {

        updatingProduct.setName(previousProductVersion.getName());
        updatingProduct.setProductType(previousProductVersion.getProductType());
        updatingProduct.setStartDate(previousProductVersion.getStartDate());
        updatingProduct.setEndDate(previousProductVersion.getEndDate());
        updatingProduct.setDescription(previousProductVersion.getDescription());
        updatingProduct.setTariffDto(previousProductVersion.getTariffDto());

        return updatingProduct;
    }
}
