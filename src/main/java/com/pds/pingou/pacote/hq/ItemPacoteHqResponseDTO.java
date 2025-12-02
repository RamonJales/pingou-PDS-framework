// src/main/java/com/pds/pingou/pacote/hq/ItemPacoteHqResponseDTO.java
package com.pds.pingou.pacote.hq;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
public class ItemPacoteHqResponseDTO {
    private Long id;
    private Long hqId;
    private String hqNome;
    private BigDecimal hqPreco;
    private String hqImagem;
    private Integer quantidade;
    private String observacoes;
    private Boolean edicaoEspecial;
}