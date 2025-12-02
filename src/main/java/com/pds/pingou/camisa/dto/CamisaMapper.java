package com.pds.pingou.camisa.dto;

import com.pds.pingou.camisa.Camisa;
import com.pds.pingou.camisa.enums.LigaCamisa;
import com.pds.pingou.camisa.enums.TipoCamisa;

/**
 * Mapper para conversão entre Camisa e DTOs.
 */
public class CamisaMapper {
    
    public static CamisaResponseDTO toDTO(Camisa camisa) {
        if (camisa == null) return null;
        
        CamisaResponseDTO dto = new CamisaResponseDTO();
        dto.setId(camisa.getId());
        dto.setNome(camisa.getNome());
        dto.setDescricao(camisa.getDescricao());
        dto.setPreco(camisa.getPreco());
        dto.setTime(camisa.getTime());
        dto.setLiga(camisa.getLiga() != null ? camisa.getLiga().name() : null);
        dto.setAnoTemporada(camisa.getAnoTemporada());
        dto.setTipoCamisa(camisa.getTipoCamisa() != null ? camisa.getTipoCamisa().name() : null);
        dto.setMarca(camisa.getMarca());
        dto.setTamanhosDisponiveis(camisa.getTamanhosDisponiveis());
        dto.setPaisTime(camisa.getPaisTime());
        dto.setMaterial(camisa.getMaterial());
        dto.setNumeracaoDisponivel(camisa.getNumeracaoDisponivel());
        dto.setNomePersonalizacaoDisponivel(camisa.getNomePersonalizacaoDisponivel());
        dto.setEdicaoLimitada(camisa.getEdicaoLimitada());
        dto.setEstoqueQuantidade(camisa.getEstoqueQuantidade());
        dto.setUrlImagem(camisa.getUrlImagem());
        dto.setAtivo(camisa.getAtivo());
        dto.setShortDescription(camisa.getShortDescription());
        return dto;
    }
    
    public static Camisa toEntity(CamisaRequestDTO dto) {
        if (dto == null) return null;
        
        Camisa camisa = new Camisa();
        updateEntity(camisa, dto);
        return camisa;
    }
    
    public static void updateEntity(Camisa camisa, CamisaRequestDTO dto) {
        if (dto == null || camisa == null) return;
        
        camisa.setNome(dto.getNome());
        camisa.setDescricao(dto.getDescricao());
        camisa.setPreco(dto.getPreco());
        camisa.setTime(dto.getTime());
        if (dto.getLiga() != null) {
            camisa.setLiga(LigaCamisa.valueOf(dto.getLiga()));
        }
        camisa.setAnoTemporada(dto.getAnoTemporada());
        if (dto.getTipoCamisa() != null) {
            camisa.setTipoCamisa(TipoCamisa.valueOf(dto.getTipoCamisa()));
        }
        camisa.setMarca(dto.getMarca());
        camisa.setTamanhosDisponiveis(dto.getTamanhosDisponiveis());
        camisa.setPaisTime(dto.getPaisTime());
        camisa.setMaterial(dto.getMaterial());
        camisa.setNumeracaoDisponivel(dto.getNumeracaoDisponivel());
        camisa.setNomePersonalizacaoDisponivel(dto.getNomePersonalizacaoDisponivel());
        camisa.setEdicaoLimitada(dto.getEdicaoLimitada());
        camisa.setEstoqueQuantidade(dto.getEstoqueQuantidade());
        camisa.setUrlImagem(dto.getUrlImagem());
    }
}
