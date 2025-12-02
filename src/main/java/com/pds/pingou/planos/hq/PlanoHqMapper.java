package com.pds.pingou.planos.hq;

public class PlanoHqMapper {

    public static PlanoHq toEntity(PlanoHqRequestDTO dto) {
        PlanoHq plano = new PlanoHq();
        plano.setNome(dto.getNome());
        plano.setDescricao(dto.getDescricao());
        plano.setPreco(dto.getPreco());
        plano.setMaxHqsPorMes(dto.getMaxHqsPorMes());
        plano.setFrequenciaEntrega(dto.getFrequenciaEntrega());
        plano.setTipoPlano(dto.getTipoPlano());

        // Se percentuais foram fornecidos, usa eles; senão usa os padrões do tipo
        if (dto.getPercentualClassicas() != null) {
            plano.setPercentualClassicas(dto.getPercentualClassicas());
        }
        if (dto.getPercentualModernas() != null) {
            plano.setPercentualModernas(dto.getPercentualModernas());
        }
        if (dto.getMultiplicadorPontos() != null) {
            plano.setMultiplicadorPontos(dto.getMultiplicadorPontos());
        }

        return plano;
    }

    public static PlanoHqResponseDTO toDTO(PlanoHq plano) {
        PlanoHqResponseDTO dto = new PlanoHqResponseDTO();
        dto.setId(plano.getId());
        dto.setNome(plano.getNome());
        dto.setDescricao(plano.getDescricao());
        dto.setPreco(plano.getPreco());
        dto.setMaxHqsPorMes(plano.getMaxHqsPorMes());
        dto.setFrequenciaEntrega(plano.getFrequenciaEntrega());
        dto.setAtivo(plano.getAtivo());
        dto.setTipoPlano(plano.getTipoPlano());
        dto.setPercentualClassicas(plano.getPercentualClassicas());
        dto.setPercentualModernas(plano.getPercentualModernas());
        dto.setMultiplicadorPontos(plano.getMultiplicadorPontos());
        return dto;
    }

    public static void updateEntity(PlanoHq plano, PlanoHqRequestDTO dto) {
        plano.setNome(dto.getNome());
        plano.setDescricao(dto.getDescricao());
        plano.setPreco(dto.getPreco());
        plano.setMaxHqsPorMes(dto.getMaxHqsPorMes());
        plano.setFrequenciaEntrega(dto.getFrequenciaEntrega());
        plano.setTipoPlano(dto.getTipoPlano());

        if (dto.getPercentualClassicas() != null) {
            plano.setPercentualClassicas(dto.getPercentualClassicas());
        }
        if (dto.getPercentualModernas() != null) {
            plano.setPercentualModernas(dto.getPercentualModernas());
        }
        if (dto.getMultiplicadorPontos() != null) {
            plano.setMultiplicadorPontos(dto.getMultiplicadorPontos());
        }
    }
}