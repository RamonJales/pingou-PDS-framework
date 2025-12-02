// src/main/java/com/pds/pingou/pacote/hq/ItemPacoteHqRequestDTO.java
package com.pds.pingou.pacote.hq;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemPacoteHqRequestDTO {
    private Long hqId;
    private Integer quantidade;
    private String observacoes;
    private Boolean edicaoEspecial;
}