package com.neoflex.product.service;

import com.neoflex.product.dto.ProductDto;
import com.neoflex.product.entity.Product;
import com.neoflex.product.exception.RevisionException;
import com.neoflex.product.exception.ValidationException;
import com.neoflex.product.integration.feign.AuthClient;
import com.neoflex.product.mapper.ProductMapper;
import com.neoflex.product.repository.ProductRepository;
import com.neoflex.product.repository.ProductRevisionRepository;
import com.neoflex.product.service.util.CheckDateUtil;
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

    private static final String SERVICE_NAME = "product";

    private final ProductRevisionRepository productRevisionRepository;
    private final ProductRepository productRepository;
    private final AuditReader auditReader;
    private final EntityManager entityManager;
    private final AuthClient authClient;

    /**
     * <p> Returns actual version of a product from audit tables
     * </p>
     * @param productId - a product id
     * @return the product with info about its author and version
     */
    @Transactional(readOnly = true)
    public ProductDto getActualVersion(String accessToken, UUID productId) {
        verifyUser(accessToken);
        Product actualProduct = getRevision(productId).getEntity();
        log.info("Actual version of the product {}", actualProduct);
        return ProductMapper.toProductDto(actualProduct);
    }

    /**
     * <p> Returns a list of previous product versions. The latest version is not taken into account
     * </p>
     * @param productId - a product id
     * @return a list of previous product versions
     * @throws com.neoflex.product.exception.RevisionException if the revision for the product is not found
     */
    @Transactional(readOnly = true)
    public List<ProductDto> getPreviousVersions(String accessToken, UUID productId) {
        verifyUser(accessToken);
        Product actualProduct = getRevision(productId).getEntity();

        List<Product> previousProducts = productRevisionRepository.findRevisions(productId).stream()
                .map(Revision::getEntity)
                .filter(p -> p.getVersion() < actualProduct.getVersion())
                .toList();
        log.info("Product list size {}", previousProducts.size());
        return ProductMapper.toListProductDto(previousProducts);
    }

    /**
     * <p> Returns a list of product versions chosen by a period given by a client
     * </p>
     * @param fromDate - a start date for searching audit information
     * @param toDate - an end date for searching audit information
     * @param productId - a product id
     * @return - list of product versions
     * @throws com.neoflex.product.exception.ValidationException if the start date is after the end date
     */
    @Transactional(readOnly = true)
    public List<ProductDto> getVersionsByPeriod(String accessToken, LocalDate fromDate, LocalDate toDate, UUID productId) {
        verifyUser(accessToken);
        if (CheckDateUtil.isDateBefore(toDate, fromDate)) {
            throw new ValidationException("Дата окончания поиска не должна быть раньше даты начала поиска");
        }

        Long periodStart = convertTime(fromDate);
        Long periodEnd = convertTime(toDate);

        AuditQuery auditQuery = auditReader.createQuery().forRevisionsOfEntity(Product.class, true, true)
                .add(AuditEntity.property("id").eq(productId))
                .add(AuditEntity.revisionProperty("timestamp").between(periodStart, periodEnd));

        List<Product> products = auditQuery.getResultList();
        log.info("Product list {}", products);
        return ProductMapper.toListProductDto(products);
    }

    /**
     * <p> Changes a product version on given one. Note: the version number of the product is not changed because of
     * annotation @Version. Instead of this the version number increases on one point, but the product has another
     * information that a client needs
     * </p>
     * @see <a href="https://stackoverflow.com/questions/2572566/java-jpa-version-annotation">Annotation Version</a>
     * @param productId - a product id
     * @param version - a version of the product to change to
     * @return the product with a proper version
     */
    @Transactional
    public ProductDto revertVersion(String accessToken, UUID productId, long version) {
        verifyUser(accessToken);
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

    private void verifyUser(String accessToken) {
        String login = authClient.verify(accessToken, SERVICE_NAME);
        log.info("Token {} verified. Username {}", accessToken, login);
    }
}
