package com.pds.pingou.pontuacao.dto;

import lombok.Data;

@Data
public class TrocaPontosRequestDTO {
    private Long userId;
    private Long hqId;
    private Integer pontosUtilizar;
}