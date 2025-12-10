package com.pds.pingou.camisa.mapper;

import com.pds.pingou.camisa.dto.PerfilMorfologicoRequestDTO;
import com.pds.pingou.camisa.dto.PerfilMorfologicoResponseDTO;
import com.pds.pingou.camisa.entity.PerfilMorfologico;

public class PerfilMorfologicoMapper {
    
    public static PerfilMorfologicoResponseDTO toDTO(PerfilMorfologico perfil) {
        if (perfil == null) {
            return null;
        }
        
        PerfilMorfologicoResponseDTO dto = new PerfilMorfologicoResponseDTO();
        dto.setId(perfil.getId());
        dto.setUserId(perfil.getUser().getId());
        dto.setUserName(perfil.getUser().getNome());
        dto.setAltura(perfil.getAltura());
        dto.setPeso(perfil.getPeso());
        dto.setCircunferenciaPeito(perfil.getCircunferenciaPeito());
        dto.setCircunferenciaCintura(perfil.getCircunferenciaCintura());
        dto.setComprimentoTorso(perfil.getComprimentoTorso());
        dto.setLarguraOmbros(perfil.getLarguraOmbros());
        dto.setTamanhoRecomendado(perfil.getTamanhoRecomendado());
        dto.setImc(perfil.getIMC());
        dto.setCreatedAt(perfil.getCreatedAt());
        dto.setUpdatedAt(perfil.getUpdatedAt());
        
        return dto;
    }
    
    public static PerfilMorfologico toEntity(PerfilMorfologicoRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        
        PerfilMorfologico perfil = new PerfilMorfologico();
        perfil.setAltura(dto.getAltura());
        perfil.setPeso(dto.getPeso());
        perfil.setCircunferenciaPeito(dto.getCircunferenciaPeito());
        perfil.setCircunferenciaCintura(dto.getCircunferenciaCintura());
        perfil.setComprimentoTorso(dto.getComprimentoTorso());
        perfil.setLarguraOmbros(dto.getLarguraOmbros());
        
        return perfil;
    }
}
