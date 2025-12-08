package com.pds.pingou.framework.core.security.auth;

import com.pds.pingou.framework.core.security.dto.AuthenticationResponseDTO;
import com.pds.pingou.framework.core.security.dto.LoginRequestDTO;
import com.pds.pingou.framework.core.security.dto.RegisterRequestDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

/**
 * Controller abstrato base para endpoints de autenticação.
 * 
 * Esta classe fornece os endpoints padrão de autenticação (registro, login e refresh token).
 * Deve ser estendida por implementações específicas de domínio.
 * 
 * @param <S> Tipo do serviço de autenticação
 * 
 * @author Pingou Framework Team
 * @version 1.0
 * @since 1.0
 */
@RequestMapping("/api/v1/auth")
public abstract class BaseAuthenticationController<S extends BaseAuthenticationService<?>> {

    protected abstract S getAuthService();

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponseDTO> register(@RequestBody @Valid RegisterRequestDTO request) {
        return ResponseEntity.ok(getAuthService().register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDTO> login(@RequestBody @Valid LoginRequestDTO request) {
        return ResponseEntity.ok(getAuthService().login(request));
    }

    @PostMapping("/refresh-token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        getAuthService().refreshToken(request, response);
    }
}
