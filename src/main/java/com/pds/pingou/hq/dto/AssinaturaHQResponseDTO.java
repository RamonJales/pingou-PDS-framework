package com.pds.pingou.hq.dto;

import com.pds.pingou.framework.core.enums.SubscriptionStatus;
import lombok.Data;

import java.time.LocalDate;

/**
 * DTO para resposta de Assinatura de HQ
 */
@Data
public class AssinaturaHQResponseDTO {
    
    private Long id;
    private Long userId;
    private String username;
    private PlanoHQResponseDTO plano;
    private SubscriptionStatus status;
    private LocalDate dataInicio;
    private LocalDate dataExpiracao;
    private Integer pontosAcumulados;
    private Integer pontosResgatados;
    private Integer pontosDisponiveis;
    private String nivelColecionador;
    private Integer pacotesRecebidos;
    private Integer hqsRecebidas;
    private LocalDate ultimoPacoteData;
    private Double mediaHQsPorPacote;
    private Boolean elegivelParaUpgrade;
    private String estatisticas;
}
