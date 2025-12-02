// src/main/java/com/pds/pingou/pacote/hq/PacoteHqRequestDTO.java
package com.pds.pingou.pacote.hq;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class PacoteHqRequestDTO {
    private String nome;
    private String descricao;
    private LocalDate dataEntrega;
    private Integer mes;
    private Integer ano;
    private Long planoId;
    private String temaMes;
    private List<ItemPacoteHqRequestDTO> itens;
}