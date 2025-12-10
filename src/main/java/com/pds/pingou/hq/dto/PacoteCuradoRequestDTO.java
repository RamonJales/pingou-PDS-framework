package com.pds.pingou.hq.dto;

import lombok.Data;

/**
 * DTO para solicitar criação de pacote curado
 */
@Data
public class PacoteCuradoRequestDTO {
    
    private Long planoId;
    private Integer periodo; // Mês (1-12)
    private Integer ano;
    private String temaMes;
}
