package com.pds.pingou.pacote.hq;

import com.pds.pingou.planos.hq.PlanoHq;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Component
public class PacoteHqMapper {

    public static PacoteHq toEntity(PacoteHqRequestDTO dto, PlanoHq plano) {
        PacoteHq pacote = new PacoteHq();
        pacote.setNome(dto.getNome());
        pacote.setDescricao(dto.getDescricao());
        pacote.setDataEntrega(dto.getDataEntrega());
        pacote.setMes(dto.getMes());
        pacote.setAno(dto.getAno());
        pacote.setPlano(plano);
        pacote.setTemaMes(dto.getTemaMes());
        return pacote;
    }

    public static PacoteHqResponseDTO toDTO(PacoteHq pacote) {
        PacoteHqResponseDTO dto = new PacoteHqResponseDTO();
        dto.setId(pacote.getId());
        dto.setNome(pacote.getNome());
        dto.setDescricao(pacote.getDescricao());
        dto.setDataEntrega(pacote.getDataEntrega());
        dto.setMes(pacote.getMes());
        dto.setAno(pacote.getAno());
        dto.setPlanoId(pacote.getPlano().getId());
        dto.setPlanoNome(pacote.getPlano().getNome());
        dto.setTemaMes(pacote.getTemaMes());

        if (pacote.getItens() != null) {
            dto.setItens(pacote.getItens().stream()
                    .map(ItemPacoteHqMapper::toDTO)
                    .collect(Collectors.toList()));
        } else {
            dto.setItens(new ArrayList<>());
        }

        return dto;
    }

    public static void updateEntity(PacoteHq pacote, PacoteHqRequestDTO dto) {
        pacote.setNome(dto.getNome());
        pacote.setDescricao(dto.getDescricao());
        pacote.setDataEntrega(dto.getDataEntrega());
        pacote.setMes(dto.getMes());
        pacote.setAno(dto.getAno());
        pacote.setTemaMes(dto.getTemaMes());
    }
}