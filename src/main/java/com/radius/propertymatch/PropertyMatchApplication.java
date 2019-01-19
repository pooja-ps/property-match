package com.radius.propertymatch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaAuditing
@EnableJpaRepositories("com.radius.propertymatch.repository")
@EnableAutoConfiguration
public class PropertyMatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(PropertyMatchApplication.class, args);
	}

}

