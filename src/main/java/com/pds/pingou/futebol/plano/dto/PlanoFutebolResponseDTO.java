package com.pds.pingou.futebol.plano.dto;

import com.pds.pingou.futebol.enums.TipoPlanoFutebol;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * DTO para respostas de plano de futebol.
 */
@Getter
@Setter
public class PlanoFutebolResponseDTO {

    private Long id;
    private String nome;
    private String descricao;
    private BigDecimal precoBase;
    private BigDecimal precoFinal;
    private BigDecimal precoPorMembro;
    private TipoPlanoFutebol tipoPlano;
    private String tipoPlanoNome;
    private String tipoPlanoDescricao;
    private Integer maxMembros;
    private Integer camisasPorMembro;
    private Integer maxProdutosPorPeriodo;
    private String frequenciaEntrega;
    private Boolean personalizacaoInclusa;
    private Boolean prioridadeEdicaoLimitada;
    private Boolean incluiSelecoes;
    private Integer trocasTamanhoAno;
    private Boolean permiteEscolhaTime;
    private BigDecimal descontoCamisaAdicional;
    private Boolean freteGratis;
    private Boolean ativo;
    private Boolean familiar;
    private String descricaoCompleta;
}
