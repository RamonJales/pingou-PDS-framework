package com.pds.pingou.hq.dto;

import lombok.Data;

/**
 * DTO para resposta de Item de Pacote
 */
@Data
public class ItemPacoteHQResponseDTO {
    
    private Long id;
    private QuadrinhoResponseDTO quadrinho;
    private Integer quantidade;
    private Boolean destaque;
    private String motivoEscolha;
    private String observacoes;
    private Integer pontos;
}
