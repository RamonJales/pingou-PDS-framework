package com.pds.pingou.produto.hq;

import org.springframework.stereotype.Component;

@Component
public class HqMapper {

    public static Hq toEntity(HqRequestDTO dto) {
        Hq hq = new Hq();
        hq.setNome(dto.getNome());
        hq.setDescricao(dto.getDescricao());
        hq.setPreco(dto.getPreco());
        hq.setUrlImagem(dto.getUrlImagem());
        hq.setTipoHq(dto.getTipoHq());
        hq.setCategoria(dto.getCategoria());
        hq.setEditora(dto.getEditora());
        hq.setAutor(dto.getAutor());
        hq.setDesenhista(dto.getDesenhista());
        hq.setAnoPublicacao(dto.getAnoPublicacao());
        hq.setNumeroEdicao(dto.getNumeroEdicao());
        hq.setNumeroPaginas(dto.getNumeroPaginas());
        hq.setIsbn(dto.getIsbn());
        hq.setEdicaoColecionador(dto.getEdicaoColecionador() != null ? dto.getEdicaoColecionador() : false);
        hq.setSinopse(dto.getSinopse());
        hq.setPontosGanho(dto.getPontosGanho() != null ? dto.getPontosGanho() : 10);
        return hq;
    }

    public static HqResponseDTO toDTO(Hq hq) {
        HqResponseDTO dto = new HqResponseDTO();
        dto.setId(hq.getId());
        dto.setNome(hq.getNome());
        dto.setDescricao(hq.getDescricao());
        dto.setPreco(hq.getPreco());
        dto.setUrlImagem(hq.getUrlImagem());
        dto.setAtivo(hq.getAtivo());
        dto.setTipoHq(hq.getTipoHq());
        dto.setCategoria(hq.getCategoria());
        dto.setEditora(hq.getEditora());
        dto.setAutor(hq.getAutor());
        dto.setDesenhista(hq.getDesenhista());
        dto.setAnoPublicacao(hq.getAnoPublicacao());
        dto.setNumeroEdicao(hq.getNumeroEdicao());
        dto.setNumeroPaginas(hq.getNumeroPaginas());
        dto.setIsbn(hq.getIsbn());
        dto.setEdicaoColecionador(hq.getEdicaoColecionador());
        dto.setSinopse(hq.getSinopse());
        dto.setPontosGanho(hq.getPontosGanho());
        dto.setShortDescription(hq.getShortDescription());
        return dto;
    }

    public static void updateEntity(Hq hq, HqRequestDTO dto) {
        hq.setNome(dto.getNome());
        hq.setDescricao(dto.getDescricao());
        hq.setPreco(dto.getPreco());
        hq.setUrlImagem(dto.getUrlImagem());
        hq.setTipoHq(dto.getTipoHq());
        hq.setCategoria(dto.getCategoria());
        hq.setEditora(dto.getEditora());
        hq.setAutor(dto.getAutor());
        hq.setDesenhista(dto.getDesenhista());
        hq.setAnoPublicacao(dto.getAnoPublicacao());
        hq.setNumeroEdicao(dto.getNumeroEdicao());
        hq.setNumeroPaginas(dto.getNumeroPaginas());
        hq.setIsbn(dto.getIsbn());
        hq.setEdicaoColecionador(dto.getEdicaoColecionador());
        hq.setSinopse(dto.getSinopse());
        hq.setPontosGanho(dto.getPontosGanho());
    }
}