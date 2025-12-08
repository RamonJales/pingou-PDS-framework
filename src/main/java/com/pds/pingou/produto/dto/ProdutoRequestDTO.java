package com.pds.pingou.produto.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO para requisições de criação e atualização de produtos.
 * 
 * @author Pingou Framework Team
 * @version 2.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoRequestDTO {
    
    @NotBlank(message = "Nome é obrigatório")
    private String nome;
    
    private String descricao;
    
    @NotNull(message = "Preço é obrigatório")
    @Positive(message = "Preço deve ser positivo")
    private BigDecimal preco;
    
    private String categoria;
    
    private String marca;
    
    private String sku;
    
    private String unidadeMedida;
    
    private Integer quantidadeUnidade;
    
    @Builder.Default
    private Boolean ativo = true;
}
