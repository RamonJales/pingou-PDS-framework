package com.pds.pingou.framework.core.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * Configuração de CORS do framework.
 * 
 * Esta classe fornece a configuração padrão de CORS para a aplicação,
 * permitindo requisições de diferentes origens durante o desenvolvimento.
 * 
 * @author Pingou Framework Team
 * @version 1.0
 * @since 1.0
 */
@Configuration
public class CorsConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Permite requisições de qualquer origem durante desenvolvimento
        configuration.setAllowedOriginPatterns(List.of("*"));

        // Permite todos os métodos HTTP
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));

        // Permite todos os headers
        configuration.setAllowedHeaders(List.of("*"));

        // Permite credenciais (cookies, authorization headers)
        configuration.setAllowCredentials(true);

        // Expõe headers na resposta
        configuration.setExposedHeaders(List.of("Authorization", "Content-Type"));

        // Tempo de cache do preflight request (1 hora)
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
