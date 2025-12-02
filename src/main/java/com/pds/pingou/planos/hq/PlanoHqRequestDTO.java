package com.pds.pingou.planos.hq;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PlanoHqRequestDTO {
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private Integer maxHqsPorMes;
    private String frequenciaEntrega = "MENSAL";
    private TipoPlanoHq tipoPlano;
    private Integer percentualClassicas;
    private Integer percentualModernas;
    private BigDecimal multiplicadorPontos;
}