package com.pds.pingou.futebol.produto.dto;

import com.pds.pingou.futebol.enums.Competicao;
import com.pds.pingou.futebol.enums.TipoCamisa;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * DTO para respostas de camisa de futebol.
 */
@Getter
@Setter
public class CamisaFutebolResponseDTO {

    private Long id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private String time;
    private String pais;
    private String temporada;
    private TipoCamisa tipoCamisa;
    private String tipoCamisaNome;
    private Competicao competicao;
    private String competicaoNome;
    private String marca;
    private Integer numeroJogador;
    private String nomeJogador;
    private Boolean permitePersonalizacao;
    private BigDecimal custoPersonalizacao;
    private String material;
    private Boolean versaoJogador;
    private Boolean oficial;
    private Integer estoqueTotal;
    private String urlImagem;
    private Boolean ativo;
    private String descricaoCurta;
    private String categoria;
    private BigDecimal precoFinal;
}
