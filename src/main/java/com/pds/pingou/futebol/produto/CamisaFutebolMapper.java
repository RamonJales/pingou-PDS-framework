package com.pds.pingou.futebol.produto;

import com.pds.pingou.futebol.produto.dto.CamisaFutebolRequestDTO;
import com.pds.pingou.futebol.produto.dto.CamisaFutebolResponseDTO;

import java.math.BigDecimal;

/**
 * Mapper para conversão entre entidade CamisaFutebol e seus DTOs.
 */
public class CamisaFutebolMapper {

    private CamisaFutebolMapper() {
        // Utility class
    }

    /**
     * Converte DTO de request para entidade.
     */
    public static CamisaFutebol toEntity(CamisaFutebolRequestDTO dto) {
        CamisaFutebol camisa = new CamisaFutebol();
        camisa.setNome(dto.getNome());
        camisa.setDescricao(dto.getDescricao());
        camisa.setPreco(dto.getPreco());
        camisa.setTime(dto.getTime());
        camisa.setPais(dto.getPais());
        camisa.setTemporada(dto.getTemporada());
        camisa.setTipoCamisa(dto.getTipoCamisa());
        camisa.setCompeticao(dto.getCompeticao());
        camisa.setMarca(dto.getMarca());
        camisa.setNumeroJogador(dto.getNumeroJogador());
        camisa.setNomeJogador(dto.getNomeJogador());
        camisa.setPermitePersonalizacao(dto.getPermitePersonalizacao() != null ? dto.getPermitePersonalizacao() : true);
        camisa.setCustoPersonalizacao(dto.getCustoPersonalizacao() != null ? dto.getCustoPersonalizacao() : BigDecimal.ZERO);
        camisa.setMaterial(dto.getMaterial());
        camisa.setVersaoJogador(dto.getVersaoJogador() != null ? dto.getVersaoJogador() : false);
        camisa.setOficial(dto.getOficial() != null ? dto.getOficial() : true);
        camisa.setEstoqueTotal(dto.getEstoqueTotal());
        camisa.setUrlImagem(dto.getUrlImagem());
        return camisa;
    }

    /**
     * Converte entidade para DTO de response.
     */
    public static CamisaFutebolResponseDTO toDTO(CamisaFutebol camisa) {
        CamisaFutebolResponseDTO dto = new CamisaFutebolResponseDTO();
        dto.setId(camisa.getId());
        dto.setNome(camisa.getNome());
        dto.setDescricao(camisa.getDescricao());
        dto.setPreco(camisa.getPreco());
        dto.setTime(camisa.getTime());
        dto.setPais(camisa.getPais());
        dto.setTemporada(camisa.getTemporada());
        dto.setTipoCamisa(camisa.getTipoCamisa());
        dto.setTipoCamisaNome(camisa.getTipoCamisa() != null ? camisa.getTipoCamisa().getNome() : null);
        dto.setCompeticao(camisa.getCompeticao());
        dto.setCompeticaoNome(camisa.getCompeticao() != null ? camisa.getCompeticao().getNome() : null);
        dto.setMarca(camisa.getMarca());
        dto.setNumeroJogador(camisa.getNumeroJogador());
        dto.setNomeJogador(camisa.getNomeJogador());
        dto.setPermitePersonalizacao(camisa.getPermitePersonalizacao());
        dto.setCustoPersonalizacao(camisa.getCustoPersonalizacao());
        dto.setMaterial(camisa.getMaterial());
        dto.setVersaoJogador(camisa.getVersaoJogador());
        dto.setOficial(camisa.getOficial());
        dto.setEstoqueTotal(camisa.getEstoqueTotal());
        dto.setUrlImagem(camisa.getUrlImagem());
        dto.setAtivo(camisa.getAtivo());
        dto.setDescricaoCurta(camisa.getShortDescription());
        dto.setCategoria(camisa.getCategory());
        dto.setPrecoFinal(camisa.getPrecoFinal(false));
        return dto;
    }

    /**
     * Atualiza entidade existente com dados do DTO.
     */
    public static void updateEntity(CamisaFutebol camisa, CamisaFutebolRequestDTO dto) {
        camisa.setNome(dto.getNome());
        camisa.setDescricao(dto.getDescricao());
        camisa.setPreco(dto.getPreco());
        camisa.setTime(dto.getTime());
        camisa.setPais(dto.getPais());
        camisa.setTemporada(dto.getTemporada());
        camisa.setTipoCamisa(dto.getTipoCamisa());
        camisa.setCompeticao(dto.getCompeticao());
        camisa.setMarca(dto.getMarca());
        camisa.setNumeroJogador(dto.getNumeroJogador());
        camisa.setNomeJogador(dto.getNomeJogador());
        if (dto.getPermitePersonalizacao() != null) {
            camisa.setPermitePersonalizacao(dto.getPermitePersonalizacao());
        }
        if (dto.getCustoPersonalizacao() != null) {
            camisa.setCustoPersonalizacao(dto.getCustoPersonalizacao());
        }
        camisa.setMaterial(dto.getMaterial());
        if (dto.getVersaoJogador() != null) {
            camisa.setVersaoJogador(dto.getVersaoJogador());
        }
        if (dto.getOficial() != null) {
            camisa.setOficial(dto.getOficial());
        }
        camisa.setEstoqueTotal(dto.getEstoqueTotal());
        camisa.setUrlImagem(dto.getUrlImagem());
    }
}
