package com.rodrigues.restapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("RESTful API with Java 18 and SpringBoot 3")
                        .version("1.0")
                        .description("Description of the RESTful API with Java 18 and SpringBoot 3")
                        .termsOfService("https://rodrigues.com/terms/")
                        .license(
                                new License()
                                        .name("Apache 2.0")
                                        .url("https://rodrigues.com.br/license"))
                );
    }
}
