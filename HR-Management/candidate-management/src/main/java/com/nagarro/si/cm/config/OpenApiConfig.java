package com.nagarro.si.cm.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Nagarro HR Candidate Management")
                        .description("Candidate HR Management for new employee")
                        .version("1.0")
                        .termsOfService("Terms of Service")
                        .contact(new Contact()
                                .name("Diana Hategan, Daniel Cirnaru, Gabriel Grecu")
                                .url("localhost:")
                                .email("nagarro@gmail.com"))
                        .license(new License()
                                .name("Apache License Version 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0")));
    }
}

