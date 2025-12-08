package com.pds.pingou.pacote;

import com.pds.pingou.planos.Plano;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Component
public class PacoteMapper {
    
    public static Pacote toEntity(PacoteRequestDTO dto, Plano plano) {
        Pacote pacote = new Pacote();
        pacote.setNome(dto.getNome());
        pacote.setDescricao(dto.getDescricao());
        pacote.setDataEntrega(dto.getDataEntrega());
        pacote.setMes(dto.getMes());
        pacote.setAno(dto.getAno());
        pacote.setPlano(plano);
        return pacote;
    }
    
    public static PacoteResponseDTO toDTO(Pacote pacote) {
        PacoteResponseDTO dto = new PacoteResponseDTO();
        dto.setId(pacote.getId());
        dto.setNome(pacote.getNome());
        dto.setDescricao(pacote.getDescricao());
        dto.setDataEntrega(pacote.getDataEntrega());
        dto.setMes(pacote.getMes());
        dto.setAno(pacote.getAno());
        dto.setPlanoId(pacote.getPlano().getId());
        dto.setPlanoNome(pacote.getPlano().getNome());
        
        if (pacote.getItens() != null) {
            dto.setItens(pacote.getItens().stream()
                    .map(ItemPacoteMapper::toDTO)
                    .collect(Collectors.toList()));
        } else {
            dto.setItens(new ArrayList<>());
        }
        
        return dto;
    }
    
    public static void updateEntity(Pacote pacote, PacoteRequestDTO dto) {
        pacote.setNome(dto.getNome());
        pacote.setDescricao(dto.getDescricao());
        pacote.setDataEntrega(dto.getDataEntrega());
        pacote.setMes(dto.getMes());
        pacote.setAno(dto.getAno());
    }
}