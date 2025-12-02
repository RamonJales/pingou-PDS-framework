package com.pds.pingou.camisa.planos.dto;

import com.pds.pingou.camisa.planos.PlanoCamisa;

/**
 * Mapper para conversão entre PlanoCamisa e DTOs.
 */
public class PlanoCamisaMapper {
    
    public static PlanoCamisaResponseDTO toDTO(PlanoCamisa plano) {
        if (plano == null) return null;
        
        PlanoCamisaResponseDTO dto = new PlanoCamisaResponseDTO();
        dto.setId(plano.getId());
        dto.setNome(plano.getNome());
        dto.setDescricao(plano.getDescricao());
        dto.setPreco(plano.getPreco());
        dto.setMaxProdutosPorPeriodo(plano.getMaxProdutosPorPeriodo());
        dto.setFrequenciaEntrega(plano.getFrequenciaEntrega());
        dto.setCategoriaPlano(plano.getCategoriaPlano());
        dto.setLigaFoco(plano.getLigaFoco());
        dto.setPermitePersonalizacao(plano.getPermitePersonalizacao());
        dto.setPrioridadeLancamentos(plano.getPrioridadeLancamentos());
        dto.setDescontoLoja(plano.getDescontoLoja());
        dto.setAtivo(plano.getAtivo());
        return dto;
    }
    
    public static PlanoCamisa toEntity(PlanoCamisaRequestDTO dto) {
        if (dto == null) return null;
        
        PlanoCamisa plano = new PlanoCamisa();
        updateEntity(plano, dto);
        return plano;
    }
    
    public static void updateEntity(PlanoCamisa plano, PlanoCamisaRequestDTO dto) {
        if (dto == null || plano == null) return;
        
        plano.setNome(dto.getNome());
        plano.setDescricao(dto.getDescricao());
        plano.setPreco(dto.getPreco());
        plano.setMaxProdutosPorPeriodo(dto.getMaxProdutosPorPeriodo());
        plano.setFrequenciaEntrega(dto.getFrequenciaEntrega());
        plano.setCategoriaPlano(dto.getCategoriaPlano());
        plano.setLigaFoco(dto.getLigaFoco());
        plano.setPermitePersonalizacao(dto.getPermitePersonalizacao());
        plano.setPrioridadeLancamentos(dto.getPrioridadeLancamentos());
        plano.setDescontoLoja(dto.getDescontoLoja());
    }
}
