package com.pds.pingou.camisa.assinatura.dto;

import com.pds.pingou.camisa.assinatura.AssinaturaCamisa;

/**
 * Mapper para conversão entre AssinaturaCamisa e DTOs.
 */
public class AssinaturaCamisaMapper {
    
    public static AssinaturaCamisaResponseDTO toDTO(AssinaturaCamisa assinatura) {
        if (assinatura == null) return null;
        
        AssinaturaCamisaResponseDTO dto = new AssinaturaCamisaResponseDTO();
        dto.setId(assinatura.getId());
        dto.setUserId(assinatura.getUser() != null ? assinatura.getUser().getId() : null);
        dto.setUserName(assinatura.getUser() != null ? assinatura.getUser().getUsername() : null);
        dto.setPlanoId(assinatura.getPlano() != null ? assinatura.getPlano().getId() : null);
        dto.setPlanoNome(assinatura.getPlano() != null ? assinatura.getPlano().getNome() : null);
        dto.setStatus(assinatura.getStatus() != null ? assinatura.getStatus().name() : null);
        dto.setDataInicio(assinatura.getDataInicio());
        dto.setDataExpiracao(assinatura.getDataExpiracao());
        dto.setTamanhoPreferido(assinatura.getTamanhoPreferido());
        dto.setTimesFavoritos(assinatura.getTimesFavoritos());
        dto.setLigasPreferidas(assinatura.getLigasPreferidas());
        dto.setAceitaPersonalizacao(assinatura.getAceitaPersonalizacao());
        return dto;
    }
    
    public static AssinaturaCamisaResponseDTO toResponseDTO(AssinaturaCamisa assinatura) {
        return toDTO(assinatura);
    }
}
