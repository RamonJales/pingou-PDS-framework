package com.pds.pingou.futebol.plano;

import com.pds.pingou.futebol.plano.dto.PlanoFutebolRequestDTO;
import com.pds.pingou.futebol.plano.dto.PlanoFutebolResponseDTO;

/**
 * Mapper para conversão entre entidade PlanoFutebol e seus DTOs.
 */
public class PlanoFutebolMapper {

    private PlanoFutebolMapper() {
        // Utility class
    }

    /**
     * Converte DTO de request para entidade.
     */
    public static PlanoFutebol toEntity(PlanoFutebolRequestDTO dto) {
        PlanoFutebol plano = new PlanoFutebol();
        plano.setNome(dto.getNome());
        plano.setDescricao(dto.getDescricao());
        plano.setPreco(dto.getPreco());
        plano.setTipoPlano(dto.getTipoPlano());
        plano.setCamisasPorMembro(dto.getCamisasPorMembro());
        plano.setMaxProdutosPorPeriodo(dto.getCamisasPorMembro() * dto.getTipoPlano().getMaxMembros());
        plano.setFrequenciaEntrega(dto.getFrequenciaEntrega() != null ? dto.getFrequenciaEntrega() : "MENSAL");
        plano.setPersonalizacaoInclusa(dto.getPersonalizacaoInclusa() != null ? dto.getPersonalizacaoInclusa() : false);
        plano.setPrioridadeEdicaoLimitada(dto.getPrioridadeEdicaoLimitada() != null ? dto.getPrioridadeEdicaoLimitada() : false);
        plano.setIncluiSelecoes(dto.getIncluiSelecoes() != null ? dto.getIncluiSelecoes() : false);
        plano.setTrocasTamanhoAno(dto.getTrocasTamanhoAno() != null ? dto.getTrocasTamanhoAno() : 2);
        plano.setPermiteEscolhaTime(dto.getPermiteEscolhaTime() != null ? dto.getPermiteEscolhaTime() : true);
        plano.setDescontoCamisaAdicional(dto.getDescontoCamisaAdicional());
        plano.setFreteGratis(dto.getFreteGratis() != null ? dto.getFreteGratis() : true);
        return plano;
    }

    /**
     * Converte entidade para DTO de response.
     */
    public static PlanoFutebolResponseDTO toDTO(PlanoFutebol plano) {
        PlanoFutebolResponseDTO dto = new PlanoFutebolResponseDTO();
        dto.setId(plano.getId());
        dto.setNome(plano.getNome());
        dto.setDescricao(plano.getDescricao());
        dto.setPrecoBase(plano.getPreco());
        dto.setPrecoFinal(plano.getPrecoFinalCalculado());
        dto.setPrecoPorMembro(plano.getPrecoPorMembro());
        dto.setTipoPlano(plano.getTipoPlano());
        dto.setTipoPlanoNome(plano.getTipoPlano().getNome());
        dto.setTipoPlanoDescricao(plano.getTipoPlano().getDescricao());
        dto.setMaxMembros(plano.getMaxMembros());
        dto.setCamisasPorMembro(plano.getCamisasPorMembro());
        dto.setMaxProdutosPorPeriodo(plano.getMaxProdutosPorPeriodo());
        dto.setFrequenciaEntrega(plano.getFrequenciaEntrega());
        dto.setPersonalizacaoInclusa(plano.getPersonalizacaoInclusa());
        dto.setPrioridadeEdicaoLimitada(plano.getPrioridadeEdicaoLimitada());
        dto.setIncluiSelecoes(plano.getIncluiSelecoes());
        dto.setTrocasTamanhoAno(plano.getTrocasTamanhoAno());
        dto.setPermiteEscolhaTime(plano.getPermiteEscolhaTime());
        dto.setDescontoCamisaAdicional(plano.getDescontoCamisaAdicional());
        dto.setFreteGratis(plano.getFreteGratis());
        dto.setAtivo(plano.getAtivo());
        dto.setFamiliar(plano.isFamiliar());
        dto.setDescricaoCompleta(plano.getDescricaoCompleta());
        return dto;
    }

    /**
     * Atualiza entidade existente com dados do DTO.
     */
    public static void updateEntity(PlanoFutebol plano, PlanoFutebolRequestDTO dto) {
        plano.setNome(dto.getNome());
        plano.setDescricao(dto.getDescricao());
        plano.setPreco(dto.getPreco());
        plano.setTipoPlano(dto.getTipoPlano());
        plano.setCamisasPorMembro(dto.getCamisasPorMembro());
        plano.setMaxProdutosPorPeriodo(dto.getCamisasPorMembro() * dto.getTipoPlano().getMaxMembros());
        if (dto.getFrequenciaEntrega() != null) {
            plano.setFrequenciaEntrega(dto.getFrequenciaEntrega());
        }
        if (dto.getPersonalizacaoInclusa() != null) {
            plano.setPersonalizacaoInclusa(dto.getPersonalizacaoInclusa());
        }
        if (dto.getPrioridadeEdicaoLimitada() != null) {
            plano.setPrioridadeEdicaoLimitada(dto.getPrioridadeEdicaoLimitada());
        }
        if (dto.getIncluiSelecoes() != null) {
            plano.setIncluiSelecoes(dto.getIncluiSelecoes());
        }
        if (dto.getTrocasTamanhoAno() != null) {
            plano.setTrocasTamanhoAno(dto.getTrocasTamanhoAno());
        }
        if (dto.getPermiteEscolhaTime() != null) {
            plano.setPermiteEscolhaTime(dto.getPermiteEscolhaTime());
        }
        plano.setDescontoCamisaAdicional(dto.getDescontoCamisaAdicional());
        if (dto.getFreteGratis() != null) {
            plano.setFreteGratis(dto.getFreteGratis());
        }
    }
}
