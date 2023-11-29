package com.neoflex.tariffs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.envers.repository.config.EnableEnversRepositories;

@EnableEnversRepositories
@SpringBootApplication
public class TariffsApplication {

	public static void main(String[] args) {
		SpringApplication.run(TariffsApplication.class, args);
	}

}
