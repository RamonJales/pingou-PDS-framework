package com.pds.pingou.hq.mapper;

import com.pds.pingou.hq.dto.QuadrinhoRequestDTO;
import com.pds.pingou.hq.dto.QuadrinhoResponseDTO;
import com.pds.pingou.hq.entity.Quadrinho;

/**
 * Mapper para converter entre Quadrinho e DTOs
 */
public class QuadrinhoMapper {

    public static QuadrinhoResponseDTO toDTO(Quadrinho quadrinho) {
        if (quadrinho == null) {
            return null;
        }

        QuadrinhoResponseDTO dto = new QuadrinhoResponseDTO();
        dto.setId(quadrinho.getId());
        dto.setNome(quadrinho.getNome());
        dto.setDescricao(quadrinho.getDescricao());
        dto.setPreco(quadrinho.getPreco());
        dto.setUrlImagem(quadrinho.getUrlImagem());
        dto.setEditora(quadrinho.getEditora());
        dto.setTipoHQ(quadrinho.getTipoHQ());
        dto.setCategoria(quadrinho.getCategoria());
        dto.setPontosGanho(quadrinho.getPontosGanho());
        dto.setEdicaoColecionador(quadrinho.getEdicaoColecionador());
        dto.setNumeroEdicao(quadrinho.getNumeroEdicao());
        dto.setAnoPublicacao(quadrinho.getAnoPublicacao());
        dto.setTituloSerie(quadrinho.getTituloSerie());
        dto.setAutor(quadrinho.getAutor());
        dto.setIlustrador(quadrinho.getIlustrador());
        dto.setIsbn(quadrinho.getIsbn());
        dto.setNumeroPaginas(quadrinho.getNumeroPaginas());
        dto.setEstoque(quadrinho.getEstoque());
        dto.setAtivo(quadrinho.getAtivo());
        dto.setShortDescription(quadrinho.getShortDescription());

        return dto;
    }

    public static Quadrinho toEntity(QuadrinhoRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        Quadrinho quadrinho = new Quadrinho();
        updateEntity(quadrinho, dto);
        return quadrinho;
    }

    public static void updateEntity(Quadrinho quadrinho, QuadrinhoRequestDTO dto) {
        if (dto == null || quadrinho == null) {
            return;
        }

        quadrinho.setNome(dto.getNome());
        quadrinho.setDescricao(dto.getDescricao());
        quadrinho.setPreco(dto.getPreco());
        quadrinho.setUrlImagem(dto.getUrlImagem());
        quadrinho.setEditora(dto.getEditora());
        quadrinho.setTipoHQ(dto.getTipoHQ());
        quadrinho.setCategoria(dto.getCategoria());
        quadrinho.setEdicaoColecionador(dto.getEdicaoColecionador());
        quadrinho.setNumeroEdicao(dto.getNumeroEdicao());
        quadrinho.setAnoPublicacao(dto.getAnoPublicacao());
        quadrinho.setTituloSerie(dto.getTituloSerie());
        quadrinho.setAutor(dto.getAutor());
        quadrinho.setIlustrador(dto.getIlustrador());
        quadrinho.setIsbn(dto.getIsbn());
        quadrinho.setNumeroPaginas(dto.getNumeroPaginas());
        
        if (dto.getEstoque() != null) {
            quadrinho.setEstoque(dto.getEstoque());
        }
    }
}
