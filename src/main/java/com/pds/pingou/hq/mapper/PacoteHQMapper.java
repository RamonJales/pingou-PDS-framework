package com.pds.pingou.hq.mapper;

import com.pds.pingou.hq.dto.ItemPacoteHQResponseDTO;
import com.pds.pingou.hq.dto.PacoteHQResponseDTO;
import com.pds.pingou.hq.entity.ItemPacoteHQ;
import com.pds.pingou.hq.entity.PacoteHQ;

import java.util.stream.Collectors;

/**
 * Mapper para converter entre PacoteHQ/ItemPacoteHQ e DTOs
 */
public class PacoteHQMapper {

    public static PacoteHQResponseDTO toDTO(PacoteHQ pacote) {
        if (pacote == null) {
            return null;
        }

        PacoteHQResponseDTO dto = new PacoteHQResponseDTO();
        dto.setId(pacote.getId());
        dto.setNome(pacote.getNome());
        dto.setDescricao(pacote.getDescricao());
        dto.setDataEntrega(pacote.getDataEntrega());
        dto.setPeriodo(pacote.getPeriodo());
        dto.setAno(pacote.getAno());
        dto.setTemaMes(pacote.getTemaMes());
        dto.setPontosTotais(pacote.getPontosTotais());
        dto.setQuantidadeClassicas(pacote.getQuantidadeClassicas());
        dto.setQuantidadeModernas(pacote.getQuantidadeModernas());
        dto.setCompleto(pacote.isCompleto());
        dto.setRespeitaPercentuais(pacote.respeitaPercentuais());
        
        if (pacote.getItems() != null) {
            dto.setItens(
                pacote.getItems().stream()
                    .map(PacoteHQMapper::itemToDTO)
                    .collect(Collectors.toList())
            );
        }

        return dto;
    }

    public static ItemPacoteHQResponseDTO itemToDTO(ItemPacoteHQ item) {
        if (item == null) {
            return null;
        }

        ItemPacoteHQResponseDTO dto = new ItemPacoteHQResponseDTO();
        dto.setId(item.getId());
        dto.setQuadrinho(QuadrinhoMapper.toDTO(item.getQuadrinho()));
        dto.setQuantidade(item.getQuantidade());
        dto.setDestaque(item.getDestaque());
        dto.setMotivoEscolha(item.getMotivoEscolha());
        dto.setObservacoes(item.getObservacoes());
        dto.setPontos(item.calcularPontos());

        return dto;
    }
}
