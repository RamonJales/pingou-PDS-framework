package com.pds.pingou.framework.core.security.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuração do Swagger/OpenAPI para documentação da API.
 * 
 * Esta classe configura a documentação OpenAPI com suporte a autenticação JWT.
 * 
 * @author Pingou Framework Team
 * @version 1.0
 * @since 1.0
 */
@Configuration
public class SwaggerConfig {

    @Value("${spring.application.name:API}")
    private String applicationName;

    @Value("${api.version:v1}")
    private String apiVersion;

    @Bean
    public OpenAPI customOpenAPI() {
        final String securitySchemeName = "bearerAuth";
        return new OpenAPI()
                .info(new Info()
                        .title(applicationName)
                        .version(apiVersion)
                        .description("API Documentation"))
                .addServersItem(new Server().url("/"))
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName,
                                new SecurityScheme()
                                        .name(securitySchemeName)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")));
    }
}
