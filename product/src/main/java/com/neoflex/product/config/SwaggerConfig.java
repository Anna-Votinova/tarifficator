package com.neoflex.product.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(info = @Info(title = "Product", description = "Create, modify and audit debit and credit cards",
        version = "1.0.0",
        contact = @Contact(name = "Anna Ponomareva (Votinova)", email = "anyvotinova@yandex.ru",
                url = "https://github.com/Anna-Votinova")))
public class SwaggerConfig {
}
