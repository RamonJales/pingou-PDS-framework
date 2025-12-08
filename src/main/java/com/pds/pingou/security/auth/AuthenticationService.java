package com.pds.pingou.security.auth;

import com.pds.pingou.framework.core.security.auth.BaseAuthenticationService;
import com.pds.pingou.framework.core.security.config.JwtService;
import com.pds.pingou.framework.core.security.dto.RegisterRequestDTO;
import com.pds.pingou.framework.core.security.user.UserRole;
import com.pds.pingou.security.user.User;
import com.pds.pingou.security.user.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Serviço de autenticação específico da aplicação Pingou.
 * 
 * Estende o serviço base do framework e fornece a implementação
 * específica para criação e busca de usuários.
 */
@Service
public class AuthenticationService extends BaseAuthenticationService<User> {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            AuthenticationManager authenticationManager) {
        super(jwtService, authenticationManager);
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    protected boolean userExistsByEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    @Override
    protected User createAndSaveUser(RegisterRequestDTO request) {
        User user = new User(
                request.email(),
                request.nome(),
                request.sobrenome(),
                passwordEncoder.encode(request.password()),
                UserRole.USER
        );
        return userRepository.save(user);
    }
}
