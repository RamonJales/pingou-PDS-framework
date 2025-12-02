package com.pds.pingou.camisa.assinatura.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * DTO para respostas de assinaturas de camisas.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssinaturaCamisaResponseDTO {
    private Long id;
    private Long userId;
    private String userName;
    private Long planoId;
    private String planoNome;
    private String status;
    private LocalDate dataInicio;
    private LocalDate dataExpiracao;
    private String tamanhoPreferido;
    private String timesFavoritos;
    private String ligasPreferidas;
    private Boolean aceitaPersonalizacao;
}
