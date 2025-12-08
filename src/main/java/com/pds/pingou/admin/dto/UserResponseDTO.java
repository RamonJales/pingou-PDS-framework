package com.pds.pingou.admin.dto;

import com.pds.pingou.assinatura.AssinaturaResponseDTO;
import com.pds.pingou.framework.core.security.user.UserRole;
import lombok.Data;

@Data
public class UserResponseDTO {
    private Long id;
    private String email;
    private String nome;
    private String sobrenome;
    private UserRole role;
    private AssinaturaResponseDTO assinatura;
}

