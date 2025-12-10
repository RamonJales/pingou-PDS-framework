package com.pds.pingou.camisa.mapper;

import com.pds.pingou.camisa.dto.PlanoCamisaRequestDTO;
import com.pds.pingou.camisa.dto.PlanoCamisaResponseDTO;
import com.pds.pingou.camisa.entity.PlanoCamisa;

public class PlanoCamisaMapper {
    
    public static PlanoCamisaResponseDTO toDTO(PlanoCamisa plano) {
        if (plano == null) {
            return null;
        }
        
        PlanoCamisaResponseDTO dto = new PlanoCamisaResponseDTO();
        dto.setId(plano.getId());
        dto.setNome(plano.getNome());
        dto.setDescricao(plano.getDescricao());
        dto.setPreco(plano.getPreco());
        dto.setDuracaoMeses(plano.getDuracaoMeses());
        dto.setAtivo(plano.getAtivo());
        dto.setPermiteCompartilhamento(plano.getPermiteCompartilhamento());
        dto.setMaxParticipantes(plano.getMaxParticipantes());
        dto.setCamisasPorMes(plano.getCamisasPorMes());
        dto.setPlanoFamilia(plano.isPlanoFamilia());
        
        return dto;
    }
    
    public static PlanoCamisa toEntity(PlanoCamisaRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        
        PlanoCamisa plano = new PlanoCamisa();
        plano.setNome(dto.getNome());
        plano.setDescricao(dto.getDescricao());
        plano.setPreco(dto.getPreco());
        plano.setDuracaoMeses(dto.getDuracaoMeses());
        plano.setAtivo(dto.getAtivo() != null ? dto.getAtivo() : true);
        plano.setPermiteCompartilhamento(dto.getPermiteCompartilhamento() != null ? dto.getPermiteCompartilhamento() : false);
        plano.setMaxParticipantes(dto.getMaxParticipantes() != null ? dto.getMaxParticipantes() : 1);
        plano.setCamisasPorMes(dto.getCamisasPorMes() != null ? dto.getCamisasPorMes() : 1);
        
        return plano;
    }
    
    public static void updateEntity(PlanoCamisa plano, PlanoCamisaRequestDTO dto) {
        if (dto.getNome() != null) plano.setNome(dto.getNome());
        if (dto.getDescricao() != null) plano.setDescricao(dto.getDescricao());
        if (dto.getPreco() != null) plano.setPreco(dto.getPreco());
        if (dto.getDuracaoMeses() != null) plano.setDuracaoMeses(dto.getDuracaoMeses());
        if (dto.getAtivo() != null) plano.setAtivo(dto.getAtivo());
        if (dto.getPermiteCompartilhamento() != null) plano.setPermiteCompartilhamento(dto.getPermiteCompartilhamento());
        if (dto.getMaxParticipantes() != null) plano.setMaxParticipantes(dto.getMaxParticipantes());
        if (dto.getCamisasPorMes() != null) plano.setCamisasPorMes(dto.getCamisasPorMes());
    }
}
