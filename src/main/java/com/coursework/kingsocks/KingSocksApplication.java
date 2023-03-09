package com.coursework.kingsocks;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition
public class KingSocksApplication {

    public static void main(String[] args) {
        SpringApplication.run(KingSocksApplication.class, args);
    }

}
