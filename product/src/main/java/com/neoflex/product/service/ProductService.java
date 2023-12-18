package com.neoflex.product.service;

import com.neoflex.product.dto.*;
import com.neoflex.product.entity.Product;
import com.neoflex.product.exception.ProductNotFoundException;
import com.neoflex.product.integration.feign.AuthClient;
import com.neoflex.product.integration.mail.EmailSender;
import com.neoflex.product.mapper.ProductMapper;
import com.neoflex.product.mapper.TariffMapper;
import com.neoflex.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.OptimisticLockException;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private static final String SERVICE_NAME = "product";
    private static final String THEME = "Install tariff";
    private static final String ERROR_MESSAGE_TEXT = "Installation of a tariff failed. Try to install a tariff once " +
            "again. Product id: ";
    private static final String SENDING_EMAIL_SUCCESS_LOG_MESSAGE = "Email product with id {} and tariff {} has been " +
            "successfully delivered";
    private static final String SENDING_EMAIL_ERROR_LOG_MESSAGE = "Error with sending product with id {} and tariff {} " +
            "has happened. The reason is: ";

    private final ProductRepository productRepository;
    private final EmailSender emailSender;
    private final AuthClient authClient;

    /**
     * <p> Creates a product without a tariff
     * </p>
     * @param productDto - client's info about new product
     * @return a product with info about its version
     */
    public ProductDto create(String accessToken, CreateProductDto productDto) {
        verifyUser(accessToken);
        Product product = ProductMapper.toShortProduct(productDto);
        productRepository.save(product);
        return ProductMapper.toProductDto(product);
    }

    /**
     * <p> Updates product fields except a tariff, an author and a version
     * </p>
     * @param productId - an id of a product
     * @param newProduct - info about a product to update existing entity
     * @return updated product
     * @throws com.neoflex.product.exception.ProductNotFoundException if the product with the given id does not exist
     */
    public ProductDto update(String accessToken, UUID productId, UpdateProductDto newProduct) {
        verifyUser(accessToken);
        Product oldProduct = findProductById(productId);

        oldProduct.setName(newProduct.getName());
        oldProduct.setProductType(newProduct.getProductType());
        oldProduct.setStartDate(newProduct.getStartDate());
        oldProduct.setEndDate(newProduct.getEndDate());
        oldProduct.setDescription(newProduct.getDescription());

        productRepository.save(oldProduct);
        return ProductMapper.toProductDto(oldProduct);
    }

    /**
     * <p> Removes a product by id
     * </p>
     * @param productId - a product id
     */
    public void remove(String accessToken, UUID productId) {
        verifyUser(accessToken);
        productRepository.deleteById(productId);
    }

    /**
     * <p> Receives message from Kafka, installs a tariff to a product and sends a message to the client's email
     * </p>
     * @param tariffKafkaMessage - a tariff for installation
     */
    public void installTariff(TariffKafkaMessage tariffKafkaMessage) {
        log.info("Try to install tariff with id {} to product with id {}",
                tariffKafkaMessage.getId(), tariffKafkaMessage.getProductId());
        Product product = findProductById(tariffKafkaMessage.getProductId());
        TariffDto tariffDto = TariffMapper.toTariffDto(tariffKafkaMessage);

        product.setTariffDto(tariffDto);
        Product savedProduct = saveProduct(product);
        if (Objects.isNull(savedProduct)) {
            sendFailedResultMessage(tariffKafkaMessage.getEmail(), tariffKafkaMessage.getId(), tariffKafkaMessage.getProductId());
        } else {
            sendSuccessfulResultMessage(tariffKafkaMessage.getEmail(), savedProduct);
        }
    }

    private Product findProductById(UUID productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Продукт с id " + productId + " не существует"));
    }

    private Product saveProduct(Product product) {
        Product savedProduct = new Product();
        log.info("Try to save product with id {} or catch Optimistic lock exception", product.getId());
        try {
            savedProduct = productRepository.save(product);
        } catch (OptimisticLockException e) {
            log.error("Optimistic lock exception happened, while trying to save product with id {}", product.getId(), e);
        }
        return savedProduct;
    }

    private void sendSuccessfulResultMessage(String email, Product product) {
        log.info("Try to send message with product id {} and tariff {} on email {}",
                product.getId(), product.getTariffDto(), email);
        ProductDto productDto = ProductMapper.toProductDto(product);
        try {
            emailSender.sendComplexMessage(email, THEME, productDto);
            log.info(SENDING_EMAIL_SUCCESS_LOG_MESSAGE, product.getId(), product.getTariffDto());
        } catch (Exception e) {
            log.error(SENDING_EMAIL_ERROR_LOG_MESSAGE, product.getId(), product.getTariffDto(), e);
        }
    }

    private void sendFailedResultMessage(String email, UUID tariffId, UUID productId) {
        log.info("Try to send message for product id {} and tariff id {} on email {}", productId, tariffId, email);
        try {
            emailSender.sendSimpleMessage(email, THEME, ERROR_MESSAGE_TEXT + productId);
            log.info(SENDING_EMAIL_SUCCESS_LOG_MESSAGE, productId, tariffId);
        } catch (Exception e) {
            log.error(SENDING_EMAIL_ERROR_LOG_MESSAGE, productId, tariffId, e);
        }
    }

    private void verifyUser(String accessToken) {
        String login = authClient.verify(accessToken, SERVICE_NAME);
        log.info("Token {} verified. Username {}", accessToken, login);
    }
}
