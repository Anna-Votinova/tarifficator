package com.neoflex.product.integration.kafka;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Validated
@ConfigurationProperties(prefix = "spring.kafka.consumer")
public class KafkaProductProperties {

    @NotBlank
    private String bootstrapServers;
}
