package com.pds.pingou.futebol.produto.dto;

import com.pds.pingou.futebol.enums.Competicao;
import com.pds.pingou.futebol.enums.TipoCamisa;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * DTO para requisições de criação/atualização de camisa de futebol.
 */
@Getter
@Setter
public class CamisaFutebolRequestDTO {

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    private String descricao;

    @NotNull(message = "Preço é obrigatório")
    @Positive(message = "Preço deve ser positivo")
    private BigDecimal preco;

    @NotBlank(message = "Time é obrigatório")
    private String time;

    private String pais;

    @NotBlank(message = "Temporada é obrigatória")
    private String temporada;

    @NotNull(message = "Tipo de camisa é obrigatório")
    private TipoCamisa tipoCamisa;

    private Competicao competicao;

    @NotBlank(message = "Marca é obrigatória")
    private String marca;

    private Integer numeroJogador;
    private String nomeJogador;
    private Boolean permitePersonalizacao = true;
    private BigDecimal custoPersonalizacao;
    private String material;
    private Boolean versaoJogador = false;
    private Boolean oficial = true;
    private Integer estoqueTotal;
    private String urlImagem;
}
