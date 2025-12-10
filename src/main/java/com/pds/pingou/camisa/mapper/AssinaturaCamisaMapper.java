package com.pds.pingou.camisa.mapper;

import com.pds.pingou.camisa.dto.AssinaturaCamisaResponseDTO;
import com.pds.pingou.camisa.dto.ParticipanteResponseDTO;
import com.pds.pingou.camisa.entity.AssinaturaCamisa;

import java.util.stream.Collectors;

public class AssinaturaCamisaMapper {
    
    public static AssinaturaCamisaResponseDTO toDTO(AssinaturaCamisa assinatura) {
        if (assinatura == null) {
            return null;
        }
        
        AssinaturaCamisaResponseDTO dto = new AssinaturaCamisaResponseDTO();
        dto.setId(assinatura.getId());
        dto.setUserId(assinatura.getUser().getId());
        dto.setUserName(assinatura.getUser().getNome());
        dto.setPlano(PlanoCamisaMapper.toDTO(assinatura.getPlan()));
        dto.setTimeFavorito(assinatura.getTimeFavorito());
        dto.setTimeRival(assinatura.getTimeRival());
        dto.setStatus(assinatura.getStatus());
        dto.setDataInicio(assinatura.getDataInicio());
        dto.setDataFim(assinatura.getDataFim());
        dto.setAssinaturaPrincipal(assinatura.isAssinaturaPrincipal());
        dto.setAssinaturaCompartilhada(assinatura.isAssinaturaCompartilhada());
        
        if (assinatura.isAssinaturaPrincipal()) {
            dto.setTotalParticipantes(assinatura.getTotalParticipantes());
            dto.setParticipantes(
                assinatura.getParticipantesCompartilhados().stream()
                    .map(AssinaturaCamisaMapper::toParticipanteDTO)
                    .collect(Collectors.toList())
            );
        }
        
        return dto;
    }
    
    public static ParticipanteResponseDTO toParticipanteDTO(AssinaturaCamisa assinatura) {
        if (assinatura == null) {
            return null;
        }
        
        ParticipanteResponseDTO dto = new ParticipanteResponseDTO();
        dto.setAssinaturaId(assinatura.getId());
        dto.setUserId(assinatura.getUser().getId());
        dto.setUserName(assinatura.getUser().getNome());
        dto.setTimeFavorito(assinatura.getTimeFavorito());
        dto.setTimeRival(assinatura.getTimeRival());
        
        return dto;
    }
}
