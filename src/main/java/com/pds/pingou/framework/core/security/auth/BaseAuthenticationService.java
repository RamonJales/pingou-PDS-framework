package com.pds.pingou.framework.core.security.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pds.pingou.framework.core.security.config.JwtService;
import com.pds.pingou.framework.core.security.dto.AuthenticationResponseDTO;
import com.pds.pingou.framework.core.security.dto.LoginRequestDTO;
import com.pds.pingou.framework.core.security.dto.RegisterRequestDTO;
import com.pds.pingou.framework.core.security.exception.UserDuplicatedException;
import com.pds.pingou.framework.core.security.exception.UserNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;
import java.util.Optional;

/**
 * Serviço abstrato base para autenticação de usuários.
 * 
 * Esta classe fornece a lógica comum de autenticação, registro e
 * refresh de tokens. Deve ser estendida por implementações específicas
 * de domínio.
 * 
 * @param <U> Tipo do usuário que implementa UserDetails
 * 
 * @author Pingou Framework Team
 * @version 1.0
 * @since 1.0
 */
public abstract class BaseAuthenticationService<U extends UserDetails> {

    protected final JwtService jwtService;
    protected final AuthenticationManager authenticationManager;

    protected BaseAuthenticationService(JwtService jwtService, AuthenticationManager authenticationManager) {
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    /**
     * Busca um usuário pelo email.
     */
    protected abstract Optional<U> findUserByEmail(String email);

    /**
     * Verifica se um usuário já existe com o email fornecido.
     */
    protected abstract boolean userExistsByEmail(String email);

    /**
     * Cria e salva um novo usuário a partir do DTO de registro.
     */
    protected abstract U createAndSaveUser(RegisterRequestDTO request);

    /**
     * Registra um novo usuário no sistema.
     */
    public AuthenticationResponseDTO register(RegisterRequestDTO request) {
        if (userExistsByEmail(request.email())) {
            throw new UserDuplicatedException(request.email());
        }

        U user = createAndSaveUser(request);

        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return new AuthenticationResponseDTO(accessToken, refreshToken);
    }

    /**
     * Autentica um usuário existente.
     */
    public AuthenticationResponseDTO login(LoginRequestDTO request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.email(),
                            request.password()
                    )
            );
        } catch (AuthenticationException ex) {
            throw new BadCredentialsException("Email ou senha inválidos");
        }

        U user = findUserByEmail(request.email())
                .orElseThrow(() -> new UserNotFoundException(request.email()));

        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return new AuthenticationResponseDTO(accessToken, refreshToken);
    }

    /**
     * Atualiza o token de acesso usando o refresh token.
     */
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        final String refreshToken = authHeader.substring(7);
        final String userEmail = jwtService.extractUsernameFromRefreshToken(refreshToken);

        if (userEmail == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        Optional<U> userOptional = findUserByEmail(userEmail);
        
        if (userOptional.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        U user = userOptional.get();
        
        if (jwtService.isRefreshTokenValid(refreshToken, user)) {
            String accessToken = jwtService.generateToken(user);
            AuthenticationResponseDTO authResponse = new AuthenticationResponseDTO(accessToken, refreshToken);

            response.setContentType("application/json");
            new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}
