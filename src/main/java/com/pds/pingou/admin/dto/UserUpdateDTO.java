package com.pds.pingou.admin.dto;

import com.pds.pingou.framework.core.security.user.UserRole;
import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class UserUpdateDTO {
    
    @Email(message = "Email inválido")
    private String email;
    
    private String nome;
    
    private String sobrenome;
    
    private String password;
    
    private UserRole role;
}

