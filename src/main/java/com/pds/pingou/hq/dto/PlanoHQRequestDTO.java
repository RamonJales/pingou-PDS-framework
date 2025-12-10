package com.pds.pingou.hq.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * DTO para criar/atualizar Plano de HQ
 */
@Data
public class PlanoHQRequestDTO {
    
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private Integer maxProdutosPorPeriodo;
    private String frequenciaEntrega;
    private Integer percentualClassicas;
    private Integer percentualModernas;
    private Integer pontosBonusMensal;
    private Boolean incluiEdicoesColecionador;
    private String nivelCuradoria;
}
