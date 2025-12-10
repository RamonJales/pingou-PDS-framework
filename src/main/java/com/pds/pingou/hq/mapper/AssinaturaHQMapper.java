package com.pds.pingou.hq.mapper;

import com.pds.pingou.hq.dto.AssinaturaHQResponseDTO;
import com.pds.pingou.hq.entity.AssinaturaHQ;

/**
 * Mapper para converter entre AssinaturaHQ e DTOs
 */
public class AssinaturaHQMapper {

    public static AssinaturaHQResponseDTO toDTO(AssinaturaHQ assinatura) {
        if (assinatura == null) {
            return null;
        }

        AssinaturaHQResponseDTO dto = new AssinaturaHQResponseDTO();
        dto.setId(assinatura.getId());
        dto.setUserId(assinatura.getUser().getId());
        dto.setUsername(assinatura.getUser().getUsername());
        dto.setPlano(PlanoHQMapper.toDTO(assinatura.getPlano()));
        dto.setStatus(assinatura.getStatus());
        dto.setDataInicio(assinatura.getDataInicio());
        dto.setDataExpiracao(assinatura.getDataExpiracao());
        dto.setPontosAcumulados(assinatura.getPontosAcumulados());
        dto.setPontosResgatados(assinatura.getPontosResgatados());
        dto.setPontosDisponiveis(assinatura.getPontosDisponiveis());
        dto.setNivelColecionador(assinatura.getNivelColecionador());
        dto.setPacotesRecebidos(assinatura.getPacotesRecebidos());
        dto.setHqsRecebidas(assinatura.getHqsRecebidas());
        dto.setUltimoPacoteData(assinatura.getUltimoPacoteData());
        dto.setMediaHQsPorPacote(assinatura.getMediaHQsPorPacote());
        dto.setElegivelParaUpgrade(assinatura.isElegivelParaUpgrade());
        dto.setEstatisticas(assinatura.getEstatisticas());

        return dto;
    }
}
