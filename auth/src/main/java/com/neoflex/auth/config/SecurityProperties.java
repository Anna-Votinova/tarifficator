package com.neoflex.auth.config;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Getter
@Setter
@Validated
@Configuration
@ConfigurationProperties(prefix = "application.security")
public class SecurityProperties {

    @NotNull
    private String secretKey;
    @NotNull
    private long expirationTimeMillis;
    private List<String> services;
}
