package com.pds.pingou.pontuacao.dto;

import com.pds.pingou.pontuacao.TipoMovimentacao;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class HistoricoPontosResponseDTO {
    private Long id;
    private Long userId;
    private String userEmail;
    private TipoMovimentacao tipo;
    private Integer pontos;
    private String descricao;
    private Long hqId;
    private String hqNome;
    private LocalDateTime dataHora;
}