package com.pds.pingou.pontuacao.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PontuacaoResponseDTO {
    private Long id;
    private Long userId;
    private String userEmail;
    private Integer pontosTotais;
    private Integer pontosDisponiveis;
    private Integer pontosUtilizados;
    private LocalDateTime ultimaAtualizacao;
}