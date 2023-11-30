package com.neoflex.product.integration.mail;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Validated
@ConfigurationProperties(prefix = "application.mail")
public class MailProperties {

    @NotBlank
    private String host;
    @NotNull
    private Integer port;
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String transportProtocol;
    @NotBlank
    private String smtpAuthEnabled;
    @NotBlank
    private String smtpStartTlsEnabled;
    @NotBlank
    private String debugEnabled;
}
