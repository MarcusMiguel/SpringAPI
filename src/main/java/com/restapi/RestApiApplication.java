package com.restapi;

import com.restapi.models.Product;
import com.restapi.services.ProductService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.time.format.DateTimeFormatter;
import java.util.Date;

@SpringBootApplication
@EnableJpaRepositories
@EntityScan(basePackages = {"com.restapi.models"})
public class RestApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestApiApplication.class, args);
	}

}

