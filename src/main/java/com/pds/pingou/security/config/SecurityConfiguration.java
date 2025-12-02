package com.pds.pingou.security.config;

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

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {

    private final JwtAuthFiltro jwtAuthFiltro;
    private final AuthenticationProvider authenticationProvider;
    private final CorsConfigurationSource corsConfigurationSource;


    public SecurityConfiguration(JwtAuthFiltro jwtAuthFiltro, 
                                 AuthenticationProvider authenticationProvider,
                                 CorsConfigurationSource corsConfigurationSource) {
        this.jwtAuthFiltro = jwtAuthFiltro;
        this.authenticationProvider = authenticationProvider;
        this.corsConfigurationSource = corsConfigurationSource;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        // Autenticação
                        .requestMatchers("/v1/auth/**").permitAll()
                        .requestMatchers("/api/v1/auth/**").permitAll()
                        
                        // IA
                        .requestMatchers("/v1/ai/**").permitAll()
                        .requestMatchers("/api/v1/ai/**").permitAll()
                        
                        // Documentação e Actuator
                        .requestMatchers("/v3/**", "/swagger-ui/**", "/swagger-ui.html", "/actuator/**").permitAll()
                        
                        // Endpoints públicos - GET para listagem e visualização
                        .requestMatchers(HttpMethod.GET, "/api/camisas/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/planos/**").permitAll()
                        
                        // OPTIONS para CORS
                        .requestMatchers(HttpMethod.OPTIONS).permitAll()
                        
                        // Todos os outros endpoints requerem autenticação
                        .anyRequest()
                        .authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFiltro, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}