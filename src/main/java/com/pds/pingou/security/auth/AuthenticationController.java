package com.pds.pingou.security.auth;

import com.pds.pingou.framework.core.security.auth.BaseAuthenticationController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller de autenticação específico da aplicação Pingou.
 * 
 * Estende o controller base do framework e fornece os endpoints
 * padrão de autenticação.
 */
@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController extends BaseAuthenticationController<AuthenticationService> {

    private final AuthenticationService authService;

    public AuthenticationController(AuthenticationService authService) {
        this.authService = authService;
    }

    @Override
    protected AuthenticationService getAuthService() {
        return authService;
    }
}
