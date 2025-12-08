package com.pds.pingou.framework.core.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

/**
 * Configuração de autenticação do framework.
 * 
 * Esta classe abstrata fornece a configuração base para autenticação,
 * incluindo beans para UserDetailsService, AuthenticationProvider,
 * AuthenticationManager e PasswordEncoder.
 * 
 * @param <U> Tipo do usuário que implementa UserDetails
 * @param <R> Tipo do repositório do usuário
 * 
 * @author Pingou Framework Team
 * @version 1.0
 * @since 1.0
 */
@Configuration
public abstract class ApplicationConfig<U extends UserDetails, R extends JpaRepository<U, Long>> {

    /**
     * Retorna o repositório de usuários.
     * Deve ser implementado pelas subclasses.
     */
    protected abstract R getUserRepository();

    /**
     * Busca um usuário pelo email.
     * Deve ser implementado pelas subclasses.
     */
    protected abstract Optional<U> findUserByEmail(String email);

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> findUserByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
