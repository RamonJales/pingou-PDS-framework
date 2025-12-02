package com.pds.pingou.admin.dto;

import com.pds.pingou.camisa.assinatura.dto.AssinaturaCamisaResponseDTO;
import com.pds.pingou.security.user.UserRole;
import lombok.Data;

@Data
public class UserResponseDTO {
    private Long id;
    private String email;
    private String nome;
    private String sobrenome;
    private UserRole role;
    private AssinaturaCamisaResponseDTO assinaturaCamisa;
}

