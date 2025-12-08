package com.pds.pingou.admin.dto;

import com.pds.pingou.framework.core.security.user.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserRequestDTO {
    
    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email inválido")
    private String email;
    
    @NotBlank(message = "Nome é obrigatório")
    private String nome;
    
    @NotBlank(message = "Sobrenome é obrigatório")
    private String sobrenome;
    
    private String password;
    
    @NotNull(message = "Role é obrigatória")
    private UserRole role;
}

