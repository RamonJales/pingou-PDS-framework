// src/main/java/com/pds/pingou/pacote/hq/PacoteHqResponseDTO.java
package com.pds.pingou.pacote.hq;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class PacoteHqResponseDTO {
    private Long id;
    private String nome;
    private String descricao;
    private LocalDate dataEntrega;
    private Integer mes;
    private Integer ano;
    private Long planoId;
    private String planoNome;
    private String temaMes;
    private List<ItemPacoteHqResponseDTO> itens;
}