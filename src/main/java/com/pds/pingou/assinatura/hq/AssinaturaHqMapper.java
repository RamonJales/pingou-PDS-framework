// src/main/java/com/pds/pingou/assinatura/hq/AssinaturaHqMapper.java
package com.pds.pingou.assinatura.hq;

import com.pds.pingou.planos.hq.PlanoHq;
import com.pds.pingou.security.user.User;

public class AssinaturaHqMapper {

    public static AssinaturaHqResponseDTO toDTO(AssinaturaHq assinatura) {
        AssinaturaHqResponseDTO dto = new AssinaturaHqResponseDTO();
        dto.setId(assinatura.getId());
        dto.setUserId(assinatura.getUser().getId());
        dto.setUserEmail(assinatura.getUser().getEmail());
        dto.setPlanoId(assinatura.getPlano().getId());
        dto.setPlanoNome(assinatura.getPlano().getNome());
        dto.setStatus(assinatura.getStatus());
        dto.setDataInicio(assinatura.getDataInicio());
        dto.setDataExpiracao(assinatura.getDataExpiracao());
        dto.setPreferenciasCategorias(assinatura.getPreferenciasCategorias());
        dto.setPreferenciasEditoras(assinatura.getPreferenciasEditoras());
        return dto;
    }

    public static AssinaturaHq toEntity(AssinaturaHqRequestDTO dto, User user, PlanoHq plano) {
        AssinaturaHq assinatura = new AssinaturaHq(user, plano);
        assinatura.setPreferenciasCategorias(dto.getPreferenciasCategorias());
        assinatura.setPreferenciasEditoras(dto.getPreferenciasEditoras());
        return assinatura;
    }
}