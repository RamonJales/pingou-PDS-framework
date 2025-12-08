package com.pds.pingou.produto;

import com.pds.pingou.produto.dto.ProdutoRequestDTO;
import com.pds.pingou.produto.dto.ProdutoResponseDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper para conversão entre Produto e seus DTOs.
 * 
 * @author Pingou Framework Team
 * @version 2.0
 */
@Component
public class ProdutoMapper {
    
    /**
     * Converte uma entidade Produto para ProdutoResponseDTO.
     */
    public ProdutoResponseDTO toResponseDTO(Produto produto) {
        if (produto == null) return null;
        
        return ProdutoResponseDTO.builder()
                .id(produto.getId())
                .nome(produto.getNome())
                .descricao(produto.getDescricao())
                .preco(produto.getPreco())
                .categoria(produto.getCategoria())
                .marca(produto.getMarca())
                .sku(produto.getSku())
                .unidadeMedida(produto.getUnidadeMedida())
                .quantidadeUnidade(produto.getQuantidadeUnidade())
                .ativo(produto.getAtivo())
                .shortDescription(produto.getShortDescription())
                .build();
    }
    
    /**
     * Converte uma lista de entidades Produto para lista de ProdutoResponseDTO.
     */
    public List<ProdutoResponseDTO> toResponseDTOList(List<Produto> produtos) {
        return produtos.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Converte um ProdutoRequestDTO para entidade Produto.
     */
    public Produto toEntity(ProdutoRequestDTO dto) {
        if (dto == null) return null;
        
        Produto produto = new Produto();
        updateEntityFromDTO(produto, dto);
        return produto;
    }
    
    /**
     * Atualiza uma entidade Produto existente com dados do DTO.
     */
    public void updateEntityFromDTO(Produto produto, ProdutoRequestDTO dto) {
        if (dto == null || produto == null) return;
        
        produto.setNome(dto.getNome());
        produto.setDescricao(dto.getDescricao());
        produto.setPreco(dto.getPreco());
        produto.setCategoria(dto.getCategoria());
        produto.setMarca(dto.getMarca());
        produto.setSku(dto.getSku());
        produto.setUnidadeMedida(dto.getUnidadeMedida());
        produto.setQuantidadeUnidade(dto.getQuantidadeUnidade());
        
        if (dto.getAtivo() != null) {
            produto.setAtivo(dto.getAtivo());
        }
    }
}
