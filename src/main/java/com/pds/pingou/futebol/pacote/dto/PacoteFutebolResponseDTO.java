package com.pds.pingou.futebol.pacote.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

/**
 * DTO para respostas de pacote de futebol.
 */
@Getter
@Setter
public class PacoteFutebolResponseDTO {

    private Long id;
    private String nome;
    private String descricao;
    private LocalDate dataEntrega;
    private Integer mes;
    private Integer ano;
    private String periodo; // "Janeiro 2024", "Fevereiro 2024", etc.
    private Long planoId;
    private String planoNome;
    private List<ItemPacoteFutebolResponseDTO> itens;
    private Integer totalItens;
    private Integer quantidadeCamisasDistintas;
    private String tematica;
    private String eventoRelacionado;
    private Boolean edicaoLimitada;
    private Integer quantidadeMaxima;
    private Integer quantidadeVendida;
    private Integer quantidadeDisponivel;
    private Boolean temDisponibilidade;
    private Boolean ativo;
    private Boolean atrasado;
    private String descricaoFormatada;
}
