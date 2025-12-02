// src/main/java/com/pds/pingou/assinatura/hq/AssinaturaHqResponseDTO.java
package com.pds.pingou.assinatura.hq;

import com.pds.pingou.framework.core.enums.SubscriptionStatus;
import lombok.Data;
import java.time.LocalDate;

@Data
public class AssinaturaHqResponseDTO {
    private Long id;
    private Long userId;
    private String userEmail;
    private Long planoId;
    private String planoNome;
    private SubscriptionStatus status;
    private LocalDate dataInicio;
    private LocalDate dataExpiracao;
    private String preferenciasCategorias;
    private String preferenciasEditoras;
}