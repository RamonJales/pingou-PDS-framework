package com.pds.pingou.framework.core.security.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DTO de resposta contendo os tokens de autenticação.
 * 
 * @author Pingou Framework Team
 * @version 1.0
 * @since 1.0
 */
public record AuthenticationResponseDTO(
        @JsonProperty("access_token")
        String accessToken,
        
        @JsonProperty("refresh_token")
        String refreshToken
) {
}
