package com.gamestore.compra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().info(new Info()
                .title("Compra Service")
                .version("1.0.0")
                .description("Proceso de compra: valida comprador, juego y stock, y registra el juego en la biblioteca"));
    }
}
