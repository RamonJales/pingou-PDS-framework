package com.pds.pingou.camisa.dto;

import com.pds.pingou.framework.core.enums.SubscriptionStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssinaturaCamisaResponseDTO {
    private Long id;
    private Long userId;
    private String userName;
    private PlanoCamisaResponseDTO plano;
    private String timeFavorito;
    private String timeRival;
    private SubscriptionStatus status;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private Boolean assinaturaPrincipal;
    private Boolean assinaturaCompartilhada;
    private Integer totalParticipantes;
    private List<ParticipanteResponseDTO> participantes;
}
