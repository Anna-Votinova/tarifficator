package com.neoflex.tariffs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.envers.repository.config.EnableEnversRepositories;

@EnableEnversRepositories
@ConfigurationPropertiesScan
@SpringBootApplication
public class TariffsApplication {

	public static void main(String[] args) {
		SpringApplication.run(TariffsApplication.class, args);
	}

}
