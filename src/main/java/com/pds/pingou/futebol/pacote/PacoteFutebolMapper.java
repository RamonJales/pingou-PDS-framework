package com.pds.pingou.futebol.pacote;

import com.pds.pingou.futebol.assinatura.MembroAssinatura;
import com.pds.pingou.futebol.pacote.dto.ItemPacoteFutebolRequestDTO;
import com.pds.pingou.futebol.pacote.dto.ItemPacoteFutebolResponseDTO;
import com.pds.pingou.futebol.pacote.dto.PacoteFutebolRequestDTO;
import com.pds.pingou.futebol.pacote.dto.PacoteFutebolResponseDTO;
import com.pds.pingou.futebol.plano.PlanoFutebol;
import com.pds.pingou.futebol.produto.CamisaFutebol;

import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * Mapper para conversão entre entidades de pacote e seus DTOs.
 */
public class PacoteFutebolMapper {

    private PacoteFutebolMapper() {
        // Utility class
    }

    /**
     * Converte DTO de request para entidade de pacote.
     */
    public static PacoteFutebol toEntity(PacoteFutebolRequestDTO dto, PlanoFutebol plano) {
        PacoteFutebol pacote = new PacoteFutebol();
        pacote.setNome(dto.getNome());
        pacote.setDescricao(dto.getDescricao());
        pacote.setDataEntrega(dto.getDataEntrega());
        pacote.setMes(dto.getMes());
        pacote.setPeriodo(dto.getMes());
        pacote.setAno(dto.getAno());
        pacote.setPlano(plano);
        pacote.setTematica(dto.getTematica());
        pacote.setEventoRelacionado(dto.getEventoRelacionado());
        pacote.setEdicaoLimitada(dto.getEdicaoLimitada() != null ? dto.getEdicaoLimitada() : false);
        pacote.setQuantidadeMaxima(dto.getQuantidadeMaxima());
        pacote.setQuantidadeVendida(0);
        return pacote;
    }

    /**
     * Converte DTO de request para entidade de item.
     */
    public static ItemPacoteFutebol toEntity(ItemPacoteFutebolRequestDTO dto, 
                                              PacoteFutebol pacote, 
                                              CamisaFutebol camisa,
                                              MembroAssinatura membro) {
        ItemPacoteFutebol item = new ItemPacoteFutebol();
        item.setPacote(pacote);
        item.setCamisa(camisa);
        item.setTamanho(dto.getTamanho());
        item.setMembroDestino(membro);
        item.setQuantidade(dto.getQuantidade() != null ? dto.getQuantidade() : 1);
        item.setNomePersonalizado(dto.getNomePersonalizado());
        item.setNumeroPersonalizado(dto.getNumeroPersonalizado());
        item.setVersaoJogador(dto.getVersaoJogador());
        item.setObservacoes(dto.getObservacoes());
        item.setStatusItem("PENDENTE");
        return item;
    }

    /**
     * Converte entidade de item para DTO de response.
     */
    public static ItemPacoteFutebolResponseDTO toDTO(ItemPacoteFutebol item) {
        ItemPacoteFutebolResponseDTO dto = new ItemPacoteFutebolResponseDTO();
        dto.setId(item.getId());
        dto.setCamisaId(item.getCamisa().getId());
        dto.setCamisaNome(item.getCamisa().getNome());
        dto.setCamisaTime(item.getCamisa().getTime());
        dto.setCamisaTipoCamisa(item.getCamisa().getTipoCamisa().getNome());
        dto.setCamisaTemporada(item.getCamisa().getTemporada());
        dto.setTamanho(item.getTamanho());
        dto.setTamanhoDescricao(item.getTamanho().getDescricao());
        dto.setTamanhoSigla(item.getTamanho().getSigla());
        dto.setInfantil(item.getTamanho().isInfantil());
        if (item.getMembroDestino() != null) {
            dto.setMembroId(item.getMembroDestino().getId());
            dto.setMembroNome(item.getMembroDestino().getNome());
        }
        dto.setNomePersonalizado(item.getNomePersonalizado());
        dto.setNumeroPersonalizado(item.getNumeroPersonalizado());
        dto.setTemPersonalizacao(item.temPersonalizacao());
        dto.setVersaoJogador(item.getVersaoJogador());
        dto.setQuantidade(item.getQuantidade());
        dto.setObservacoes(item.getObservacoes());
        dto.setStatusItem(item.getStatusItem());
        dto.setDescricaoParaEtiqueta(item.getDescricaoParaEtiqueta());
        dto.setDescricaoCurta(item.getDescricaoCurta());
        return dto;
    }

    /**
     * Converte entidade de pacote para DTO de response.
     */
    public static PacoteFutebolResponseDTO toDTO(PacoteFutebol pacote) {
        PacoteFutebolResponseDTO dto = new PacoteFutebolResponseDTO();
        dto.setId(pacote.getId());
        dto.setNome(pacote.getNome());
        dto.setDescricao(pacote.getDescricao());
        dto.setDataEntrega(pacote.getDataEntrega());
        dto.setMes(pacote.getMes());
        dto.setAno(pacote.getAno());
        dto.setPeriodo(formatarPeriodo(pacote.getMes(), pacote.getAno()));
        dto.setPlanoId(pacote.getPlano().getId());
        dto.setPlanoNome(pacote.getPlano().getNome());
        dto.setItens(pacote.getItems().stream()
            .map(PacoteFutebolMapper::toDTO)
            .collect(Collectors.toList()));
        dto.setTotalItens(pacote.getTotalItems());
        dto.setQuantidadeCamisasDistintas(pacote.getQuantidadeCamisasDistintas());
        dto.setTematica(pacote.getTematica());
        dto.setEventoRelacionado(pacote.getEventoRelacionado());
        dto.setEdicaoLimitada(pacote.getEdicaoLimitada());
        dto.setQuantidadeMaxima(pacote.getQuantidadeMaxima());
        dto.setQuantidadeVendida(pacote.getQuantidadeVendida());
        dto.setQuantidadeDisponivel(pacote.getQuantidadeDisponivel());
        dto.setTemDisponibilidade(pacote.temDisponibilidade());
        dto.setAtivo(pacote.getAtivo());
        dto.setAtrasado(pacote.isOverdue());
        dto.setDescricaoFormatada(pacote.getDescricaoFormatada());
        return dto;
    }

    /**
     * Formata o período para exibição.
     */
    private static String formatarPeriodo(Integer mes, Integer ano) {
        if (mes == null || ano == null) return "";
        try {
            String nomeMes = Month.of(mes).getDisplayName(TextStyle.FULL, new Locale("pt", "BR"));
            return nomeMes.substring(0, 1).toUpperCase() + nomeMes.substring(1) + " " + ano;
        } catch (Exception e) {
            return mes + "/" + ano;
        }
    }

    /**
     * Atualiza entidade de pacote com dados do DTO.
     */
    public static void updateEntity(PacoteFutebol pacote, PacoteFutebolRequestDTO dto) {
        pacote.setNome(dto.getNome());
        pacote.setDescricao(dto.getDescricao());
        pacote.setDataEntrega(dto.getDataEntrega());
        pacote.setMes(dto.getMes());
        pacote.setPeriodo(dto.getMes());
        pacote.setAno(dto.getAno());
        pacote.setTematica(dto.getTematica());
        pacote.setEventoRelacionado(dto.getEventoRelacionado());
        if (dto.getEdicaoLimitada() != null) {
            pacote.setEdicaoLimitada(dto.getEdicaoLimitada());
        }
        pacote.setQuantidadeMaxima(dto.getQuantidadeMaxima());
    }
}
