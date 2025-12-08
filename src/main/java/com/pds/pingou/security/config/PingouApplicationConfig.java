package com.pds.pingou.security.config;

import com.pds.pingou.framework.core.security.config.ApplicationConfig;
import com.pds.pingou.security.user.User;
import com.pds.pingou.security.user.UserRepository;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

/**
 * Configuração de autenticação específica da aplicação Pingou.
 * 
 * Estende a configuração base do framework e fornece a implementação
 * específica para o repositório de usuários.
 */
@Configuration
public class PingouApplicationConfig extends ApplicationConfig<User, UserRepository> {

    private final UserRepository userRepository;

    public PingouApplicationConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    protected UserRepository getUserRepository() {
        return userRepository;
    }

    @Override
    protected Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
