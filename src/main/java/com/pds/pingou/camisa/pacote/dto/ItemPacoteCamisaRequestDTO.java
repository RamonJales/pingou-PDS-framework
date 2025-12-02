package com.pds.pingou.camisa.pacote.dto;

import lombok.Data;

@Data
public class ItemPacoteCamisaRequestDTO {
    private Long camisaId;
    private Integer quantidade;
    private String tamanhoSelecionado;
    private Integer numeroPersonalizacao;
    private String nomePersonalizacao;
    private String observacoes;
}
