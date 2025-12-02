// src/main/java/com/pds/pingou/assinatura/hq/AssinaturaHqRequestDTO.java
package com.pds.pingou.assinatura.hq;

import lombok.Data;

@Data
public class AssinaturaHqRequestDTO {
    private Long userId;
    private Long planoId;
    private String preferenciasCategorias;
    private String preferenciasEditoras;
}