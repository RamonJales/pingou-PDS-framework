package com.pds.pingou.camisa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParticipanteResponseDTO {
    private Long assinaturaId;
    private Long userId;
    private String userName;
    private String timeFavorito;
    private String timeRival;
}
