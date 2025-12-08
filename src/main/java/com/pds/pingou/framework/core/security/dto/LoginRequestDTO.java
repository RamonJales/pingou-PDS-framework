package com.pds.pingou.framework.core.security.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO para requisição de login.
 * 
 * @author Pingou Framework Team
 * @version 1.0
 * @since 1.0
 */
public record LoginRequestDTO(
        @Email(message = "O formato do email é inválido.")
        @NotBlank(message = "O email não pode estar em branco.")
        @Schema(example = "usuario@mail.com")
        String email,

        @NotBlank(message = "A senha não pode estar em branco.")
        @Schema(example = "12345678")
        String password
) {
}
