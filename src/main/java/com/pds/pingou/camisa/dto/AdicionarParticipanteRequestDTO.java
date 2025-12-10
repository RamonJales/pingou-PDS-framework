package com.pds.pingou.camisa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdicionarParticipanteRequestDTO {
    private Long novoParticipanteUserId;
    private String timeFavorito;
    private String timeRival;
}
