package com.pds.pingou.camisa.mapper;

import com.pds.pingou.camisa.dto.CamisaRequestDTO;
import com.pds.pingou.camisa.dto.CamisaResponseDTO;
import com.pds.pingou.camisa.entity.Camisa;

public class CamisaMapper {
    
    public static CamisaResponseDTO toDTO(Camisa camisa) {
        if (camisa == null) {
            return null;
        }
        
        CamisaResponseDTO dto = new CamisaResponseDTO();
        dto.setId(camisa.getId());
        dto.setNome(camisa.getNome());
        dto.setDescricao(camisa.getDescricao());
        dto.setPreco(camisa.getPreco());
        dto.setTime(camisa.getTime());
        dto.setTimeRival(camisa.getTimeRival());
        dto.setAno(camisa.getAno());
        dto.setTamanho(camisa.getTamanho());
        dto.setTipo(camisa.getTipo());
        dto.setMaterial(camisa.getMaterial());
        dto.setPersonalizacao(camisa.getPersonalizacao());
        dto.setEstoque(camisa.getEstoque());
        dto.setDisponivelEmEstoque(camisa.isDisponivelEmEstoque());
        dto.setDeJogador(camisa.isDeJogador());
        dto.setDeTorcedor(camisa.isDeTorcedor());
        
        return dto;
    }
    
    public static Camisa toEntity(CamisaRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Camisa camisa = new Camisa();
        camisa.setNome(dto.getNome());
        camisa.setDescricao(dto.getDescricao());
        camisa.setPreco(dto.getPreco());
        camisa.setTime(dto.getTime());
        camisa.setTimeRival(dto.getTimeRival());
        camisa.setAno(dto.getAno());
        camisa.setTamanho(dto.getTamanho());
        camisa.setTipo(dto.getTipo());
        camisa.setMaterial(dto.getMaterial());
        camisa.setPersonalizacao(dto.getPersonalizacao());
        camisa.setEstoque(dto.getEstoque() != null ? dto.getEstoque() : 0);
        
        return camisa;
    }
    
    public static void updateEntity(Camisa camisa, CamisaRequestDTO dto) {
        if (dto.getNome() != null) camisa.setNome(dto.getNome());
        if (dto.getDescricao() != null) camisa.setDescricao(dto.getDescricao());
        if (dto.getPreco() != null) camisa.setPreco(dto.getPreco());
        if (dto.getTime() != null) camisa.setTime(dto.getTime());
        if (dto.getTimeRival() != null) camisa.setTimeRival(dto.getTimeRival());
        if (dto.getAno() != null) camisa.setAno(dto.getAno());
        if (dto.getTamanho() != null) camisa.setTamanho(dto.getTamanho());
        if (dto.getTipo() != null) camisa.setTipo(dto.getTipo());
        if (dto.getMaterial() != null) camisa.setMaterial(dto.getMaterial());
        if (dto.getPersonalizacao() != null) camisa.setPersonalizacao(dto.getPersonalizacao());
        if (dto.getEstoque() != null) camisa.setEstoque(dto.getEstoque());
    }
}
