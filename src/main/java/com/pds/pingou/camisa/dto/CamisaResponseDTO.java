package com.pds.pingou.camisa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO para respostas de camisas.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CamisaResponseDTO {
    private Long id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private String time;
    private String liga;
    private Integer anoTemporada;
    private String tipoCamisa;
    private String marca;
    private String tamanhosDisponiveis;
    private String paisTime;
    private String material;
    private Boolean numeracaoDisponivel;
    private Boolean nomePersonalizacaoDisponivel;
    private Boolean edicaoLimitada;
    private Integer estoqueQuantidade;
    private String urlImagem;
    private Boolean ativo;
    private String shortDescription;
}
