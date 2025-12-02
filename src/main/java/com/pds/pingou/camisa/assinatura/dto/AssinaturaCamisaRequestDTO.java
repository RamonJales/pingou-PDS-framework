package com.pds.pingou.camisa.assinatura.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para requisições de criação/atualização de assinaturas de camisas.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssinaturaCamisaRequestDTO {
    private Long userId;
    private Long planoId;
    private String tamanhoPreferido;
    private String timesFavoritos;
    private String ligasPreferidas;
    private Boolean aceitaPersonalizacao;
}
