package com.pds.pingou.produto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Repositório JPA para acesso a dados de produtos.
 * 
 * Fornece métodos para buscar produtos por diferentes critérios como nome,
 * categoria, marca, SKU e faixa de preço.
 * 
 * @author Pingou Framework Team
 * @version 2.0
 */
@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    
    /**
     * Busca todos os produtos ativos.
     */
    List<Produto> findByAtivoTrue();
    
    /**
     * Busca produtos ativos por nome (busca parcial).
     */
    @Query("SELECT p FROM Produto p WHERE p.nome LIKE %:nome% AND p.ativo = true")
    List<Produto> findByNomeContainingAndAtivoTrue(@Param("nome") String nome);
    
    /**
     * Busca produtos ativos por categoria.
     */
    List<Produto> findByCategoriaAndAtivoTrue(String categoria);
    
    /**
     * Busca produtos ativos por marca.
     */
    List<Produto> findByMarcaAndAtivoTrue(String marca);
    
    /**
     * Busca produto ativo por SKU.
     */
    Optional<Produto> findBySkuAndAtivoTrue(String sku);
    
    /**
     * Busca produtos ativos dentro de uma faixa de preço.
     */
    @Query("SELECT p FROM Produto p WHERE p.preco BETWEEN :precoMin AND :precoMax AND p.ativo = true")
    List<Produto> findByPrecoRangeAndAtivoTrue(@Param("precoMin") BigDecimal precoMin, @Param("precoMax") BigDecimal precoMax);
    
    /**
     * Busca produtos ativos por categoria e faixa de preço.
     */
    @Query("SELECT p FROM Produto p WHERE p.categoria = :categoria AND p.preco BETWEEN :precoMin AND :precoMax AND p.ativo = true")
    List<Produto> findByCategoriaAndPrecoRangeAndAtivoTrue(
            @Param("categoria") String categoria,
            @Param("precoMin") BigDecimal precoMin, 
            @Param("precoMax") BigDecimal precoMax);
    
    /**
     * Conta produtos ativos por categoria.
     */
    long countByCategoriaAndAtivoTrue(String categoria);
    
    /**
     * Busca todas as categorias distintas de produtos ativos.
     */
    @Query("SELECT DISTINCT p.categoria FROM Produto p WHERE p.ativo = true AND p.categoria IS NOT NULL")
    List<String> findDistinctCategorias();
    
    /**
     * Busca todas as marcas distintas de produtos ativos.
     */
    @Query("SELECT DISTINCT p.marca FROM Produto p WHERE p.ativo = true AND p.marca IS NOT NULL")
    List<String> findDistinctMarcas();
}