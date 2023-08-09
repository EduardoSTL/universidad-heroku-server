package com.springsimplespasos.universidad.universidadbackend.configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("com.springsimplespasos")
                .pathsToMatch("/**")
                .build();
    }
    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Universidad API rest")
                        .description("Registros univerisdad")
                        .version("v2")
                        .license(new License().name("GitHub Repository").url("https://github.com/EduardoSTL/universidad-heroku-server.git"))
                        .contact(new Contact()
                        .name("Eduardo Melgar")
                    .email("eduardojosemelgar2004@gmail.com"))
        );
    }
}