package com.pds.pingou.hq.mapper;

import com.pds.pingou.hq.dto.PlanoHQRequestDTO;
import com.pds.pingou.hq.dto.PlanoHQResponseDTO;
import com.pds.pingou.hq.entity.PlanoHQ;

/**
 * Mapper para converter entre PlanoHQ e DTOs
 */
public class PlanoHQMapper {

    public static PlanoHQResponseDTO toDTO(PlanoHQ plano) {
        if (plano == null) {
            return null;
        }

        PlanoHQResponseDTO dto = new PlanoHQResponseDTO();
        dto.setId(plano.getId());
        dto.setNome(plano.getNome());
        dto.setDescricao(plano.getDescricao());
        dto.setPreco(plano.getPreco());
        dto.setMaxProdutosPorPeriodo(plano.getMaxProdutosPorPeriodo());
        dto.setFrequenciaEntrega(plano.getFrequenciaEntrega());
        dto.setPercentualClassicas(plano.getPercentualClassicas());
        dto.setPercentualModernas(plano.getPercentualModernas());
        dto.setPontosBonusMensal(plano.getPontosBonusMensal());
        dto.setIncluiEdicoesColecionador(plano.getIncluiEdicoesColecionador());
        dto.setNivelCuradoria(plano.getNivelCuradoria());
        dto.setAtivo(plano.getAtivo());
        dto.setQuantidadeClassicas(plano.calcularQuantidadeClassicas());
        dto.setQuantidadeModernas(plano.calcularQuantidadeModernas());

        return dto;
    }

    public static PlanoHQ toEntity(PlanoHQRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        PlanoHQ plano = new PlanoHQ();
        updateEntity(plano, dto);
        return plano;
    }

    public static void updateEntity(PlanoHQ plano, PlanoHQRequestDTO dto) {
        if (dto == null || plano == null) {
            return;
        }

        plano.setNome(dto.getNome());
        plano.setDescricao(dto.getDescricao());
        plano.setPreco(dto.getPreco());
        plano.setMaxProdutosPorPeriodo(dto.getMaxProdutosPorPeriodo());
        plano.setFrequenciaEntrega(dto.getFrequenciaEntrega());
        plano.setPercentualClassicas(dto.getPercentualClassicas());
        plano.setPercentualModernas(dto.getPercentualModernas());
        plano.setPontosBonusMensal(dto.getPontosBonusMensal());
        plano.setIncluiEdicoesColecionador(dto.getIncluiEdicoesColecionador());
        plano.setNivelCuradoria(dto.getNivelCuradoria());
    }
}
