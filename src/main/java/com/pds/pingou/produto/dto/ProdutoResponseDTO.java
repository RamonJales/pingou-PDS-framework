package com.pds.pingou.produto.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO para respostas com dados de produtos.
 * 
 * @author Pingou Framework Team
 * @version 2.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoResponseDTO {
    
    private Long id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private String categoria;
    private String marca;
    private String sku;
    private String unidadeMedida;
    private Integer quantidadeUnidade;
    private Boolean ativo;
    private String shortDescription;
}
