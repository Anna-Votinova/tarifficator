package com.neoflex.tariffs.integration.kafka;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Validated
@ConfigurationProperties(prefix = "product.kafka")
public class ProductKafkaConfig {

    @NotBlank
    private String bootstrapAddress;
    @NotBlank
    private String installTariffTopic;
}
