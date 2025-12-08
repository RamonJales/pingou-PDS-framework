package com.pds.pingou.produto;

import com.pds.pingou.produto.dto.ProdutoRequestDTO;
import com.pds.pingou.produto.dto.ProdutoResponseDTO;
import com.pds.pingou.produto.exception.ProdutoDuplicatedException;
import com.pds.pingou.produto.exception.ProdutoNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * Serviço para operações CRUD e consultas de produtos.
 * 
 * @author Pingou Framework Team
 * @version 2.0
 */
@Service
@RequiredArgsConstructor
public class ProdutoService {
    
    private final ProdutoRepository produtoRepository;
    private final ProdutoMapper produtoMapper;
    
    /**
     * Lista todos os produtos ativos.
     */
    public List<ProdutoResponseDTO> listarAtivos() {
        return produtoMapper.toResponseDTOList(produtoRepository.findByAtivoTrue());
    }
    
    /**
     * Lista todos os produtos (ativos e inativos).
     */
    public List<ProdutoResponseDTO> listarTodos() {
        return produtoMapper.toResponseDTOList(produtoRepository.findAll());
    }
    
    /**
     * Busca um produto pelo ID.
     */
    public ProdutoResponseDTO buscarPorId(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ProdutoNotFoundException(id));
        return produtoMapper.toResponseDTO(produto);
    }
    
    /**
     * Busca produtos por nome (busca parcial).
     */
    public List<ProdutoResponseDTO> buscarPorNome(String nome) {
        return produtoMapper.toResponseDTOList(
                produtoRepository.findByNomeContainingAndAtivoTrue(nome));
    }
    
    /**
     * Busca produtos por categoria.
     */
    public List<ProdutoResponseDTO> buscarPorCategoria(String categoria) {
        return produtoMapper.toResponseDTOList(
                produtoRepository.findByCategoriaAndAtivoTrue(categoria));
    }
    
    /**
     * Busca produtos por marca.
     */
    public List<ProdutoResponseDTO> buscarPorMarca(String marca) {
        return produtoMapper.toResponseDTOList(
                produtoRepository.findByMarcaAndAtivoTrue(marca));
    }
    
    /**
     * Busca produtos por faixa de preço.
     */
    public List<ProdutoResponseDTO> buscarPorFaixaDePreco(BigDecimal precoMin, BigDecimal precoMax) {
        return produtoMapper.toResponseDTOList(
                produtoRepository.findByPrecoRangeAndAtivoTrue(precoMin, precoMax));
    }
    
    /**
     * Lista todas as categorias disponíveis.
     */
    public List<String> listarCategorias() {
        return produtoRepository.findDistinctCategorias();
    }
    
    /**
     * Lista todas as marcas disponíveis.
     */
    public List<String> listarMarcas() {
        return produtoRepository.findDistinctMarcas();
    }
    
    /**
     * Cria um novo produto.
     */
    @Transactional
    public ProdutoResponseDTO criar(ProdutoRequestDTO dto) {
        // Verifica se SKU já existe (se fornecido)
        if (dto.getSku() != null && !dto.getSku().isBlank()) {
            produtoRepository.findBySkuAndAtivoTrue(dto.getSku())
                    .ifPresent(p -> { throw new ProdutoDuplicatedException(dto.getSku()); });
        }
        
        Produto produto = produtoMapper.toEntity(dto);
        produto.setAtivo(true);
        produto = produtoRepository.save(produto);
        return produtoMapper.toResponseDTO(produto);
    }
    
    /**
     * Atualiza um produto existente.
     */
    @Transactional
    public ProdutoResponseDTO atualizar(Long id, ProdutoRequestDTO dto) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ProdutoNotFoundException(id));
        
        // Verifica conflito de SKU
        if (dto.getSku() != null && !dto.getSku().isBlank()) {
            produtoRepository.findBySkuAndAtivoTrue(dto.getSku())
                    .filter(p -> !p.getId().equals(id))
                    .ifPresent(p -> { throw new ProdutoDuplicatedException(dto.getSku()); });
        }
        
        produtoMapper.updateEntityFromDTO(produto, dto);
        produto = produtoRepository.save(produto);
        return produtoMapper.toResponseDTO(produto);
    }
    
    /**
     * Desativa (soft delete) um produto.
     */
    @Transactional
    public void desativar(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ProdutoNotFoundException(id));
        produto.setAtivo(false);
        produtoRepository.save(produto);
    }
    
    /**
     * Reativa um produto.
     */
    @Transactional
    public void ativar(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ProdutoNotFoundException(id));
        produto.setAtivo(true);
        produtoRepository.save(produto);
    }
    
    /**
     * Exclui permanentemente um produto.
     * Use com cuidado - prefira desativar.
     */
    @Transactional
    public void excluir(Long id) {
        if (!produtoRepository.existsById(id)) {
            throw new ProdutoNotFoundException(id);
        }
        produtoRepository.deleteById(id);
    }
}
