package com.api.superhero.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        
        return new OpenAPI()
                .info(new Info().title("Superheroes API").description(
                        "API to do CRUD operations on superheroes.")
                        .version("v0.0.1"));
    }

}
