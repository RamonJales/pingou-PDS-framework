package com.pds.pingou.framework.core.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

/**
 * Configuração principal de segurança do framework.
 * 
 * Esta classe configura o Spring Security para usar autenticação JWT,
 * define endpoints públicos e protegidos, e configura CORS.
 * 
 * @author Pingou Framework Team
 * @version 1.0
 * @since 1.0
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final CorsConfigurationSource corsConfigurationSource;

    public SecurityConfiguration(
            JwtAuthenticationFilter jwtAuthFilter,
            AuthenticationProvider authenticationProvider,
            CorsConfigurationSource corsConfigurationSource) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.authenticationProvider = authenticationProvider;
        this.corsConfigurationSource = corsConfigurationSource;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/v1/auth/**").permitAll()
                        .requestMatchers("/api/v1/auth/**").permitAll()
                        .requestMatchers("/v1/ai/**").permitAll()
                        .requestMatchers("/api/v1/ai/**").permitAll()
                        .requestMatchers("/v3/**", "/swagger-ui/**", "/actuator/**").permitAll()
                        .requestMatchers(HttpMethod.OPTIONS).permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
