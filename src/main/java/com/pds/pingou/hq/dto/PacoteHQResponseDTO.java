package com.pds.pingou.hq.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

/**
 * DTO para resposta de Pacote de HQ
 */
@Data
public class PacoteHQResponseDTO {
    
    private Long id;
    private String nome;
    private String descricao;
    private LocalDate dataEntrega;
    private Integer periodo;
    private Integer ano;
    private String temaMes;
    private Integer pontosTotais;
    private Integer quantidadeClassicas;
    private Integer quantidadeModernas;
    private Boolean completo;
    private Boolean respeitaPercentuais;
    private List<ItemPacoteHQResponseDTO> itens;
}
