package com.pds.pingou.camisa.planos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO para requisições de criação/atualização de planos de camisas.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanoCamisaRequestDTO {
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private Integer maxProdutosPorPeriodo;
    private String frequenciaEntrega;
    private String categoriaPlano;
    private String ligaFoco;
    private Boolean permitePersonalizacao;
    private Boolean prioridadeLancamentos;
    private BigDecimal descontoLoja;
}
