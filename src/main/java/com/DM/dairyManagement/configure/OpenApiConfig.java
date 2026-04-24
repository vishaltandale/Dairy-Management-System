package com.DM.dairyManagement.configure;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    
    @Bean
    public OpenAPI milkMateOpenAPI() {
        final String securitySchemeName = "sessionAuth";
        
        return new OpenAPI()
            .info(new Info()
                .title("MilkMate Dairy Management System API")
                .description("Comprehensive REST API for managing dairy business operations including inventory, billing, payments, customers, and reporting.")
                .version("1.0.0")
                .contact(new Contact()
                    .name("MilkMate Support")
                    .email("support@milkmate.com")
                    .url("https://milkmate.com"))
                .license(new License()
                    .name("Proprietary License")
                    .url("https://milkmate.com/license")))
            .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
            .components(new Components()
                .addSecuritySchemes(securitySchemeName,
                    new SecurityScheme()
                        .name(securitySchemeName)
                        .type(SecurityScheme.Type.APIKEY)
                        .in(SecurityScheme.In.COOKIE)
                        .scheme("http")
                        .name("JSESSIONID")
                        .description("Session-based authentication using JSESSIONID cookie")));
    }
}
