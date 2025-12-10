package com.pds.pingou.hq.repository;

import com.pds.pingou.hq.entity.Quadrinho;
import com.pds.pingou.hq.enums.CategoriaHQ;
import com.pds.pingou.hq.enums.EditoraHQ;
import com.pds.pingou.hq.enums.TipoHQ;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositório para operações com Quadrinhos
 */
@Repository
public interface QuadrinhoRepository extends JpaRepository<Quadrinho, Long> {

    /**
     * Busca quadrinhos por editora
     */
    List<Quadrinho> findByEditora(EditoraHQ editora);

    /**
     * Busca quadrinhos por tipo (Clássica/Moderna)
     */
    List<Quadrinho> findByTipoHQ(TipoHQ tipoHQ);

    /**
     * Busca quadrinhos por categoria
     */
    List<Quadrinho> findByCategoria(CategoriaHQ categoria);

    /**
     * Busca quadrinhos ativos
     */
    List<Quadrinho> findByAtivoTrue();

    /**
     * Busca quadrinhos por editora e ativos
     */
    List<Quadrinho> findByEditoraAndAtivoTrue(EditoraHQ editora);

    /**
     * Busca quadrinhos por tipo e ativos
     */
    List<Quadrinho> findByTipoHQAndAtivoTrue(TipoHQ tipoHQ);

    /**
     * Busca quadrinhos por categoria e ativos
     */
    List<Quadrinho> findByCategoriaAndAtivoTrue(CategoriaHQ categoria);

    /**
     * Busca edições de colecionador
     */
    List<Quadrinho> findByEdicaoColecionadorTrueAndAtivoTrue();

    /**
     * Busca quadrinhos com estoque disponível
     */
    @Query("SELECT q FROM Quadrinho q WHERE q.estoque > 0 AND q.ativo = true")
    List<Quadrinho> findComEstoqueDisponivel();

    /**
     * Busca quadrinhos por tipo com estoque
     */
    @Query("SELECT q FROM Quadrinho q WHERE q.tipoHQ = :tipo AND q.estoque > 0 AND q.ativo = true")
    List<Quadrinho> findByTipoComEstoque(@Param("tipo") TipoHQ tipo);

    /**
     * Busca quadrinhos por categoria com estoque
     */
    @Query("SELECT q FROM Quadrinho q WHERE q.categoria = :categoria AND q.estoque > 0 AND q.ativo = true")
    List<Quadrinho> findByCategoriaComEstoque(@Param("categoria") CategoriaHQ categoria);

    /**
     * Busca quadrinhos por editora com estoque
     */
    @Query("SELECT q FROM Quadrinho q WHERE q.editora = :editora AND q.estoque > 0 AND q.ativo = true")
    List<Quadrinho> findByEditoraComEstoque(@Param("editora") EditoraHQ editora);

    /**
     * Busca quadrinhos por título da série
     */
    List<Quadrinho> findByTituloSerieContainingIgnoreCase(String tituloSerie);

    /**
     * Busca quadrinhos por múltiplas categorias com estoque
     */
    @Query("SELECT q FROM Quadrinho q WHERE q.categoria IN :categorias AND q.estoque > 0 AND q.ativo = true")
    List<Quadrinho> findByCategoriasComEstoque(@Param("categorias") List<CategoriaHQ> categorias);

    /**
     * Busca quadrinhos por múltiplas editoras com estoque
     */
    @Query("SELECT q FROM Quadrinho q WHERE q.editora IN :editoras AND q.estoque > 0 AND q.ativo = true")
    List<Quadrinho> findByEditorasComEstoque(@Param("editoras") List<EditoraHQ> editoras);

    /**
     * Busca quadrinhos por tipo, categorias e editoras (para curadoria)
     */
    @Query("SELECT q FROM Quadrinho q WHERE q.tipoHQ = :tipo " +
           "AND (:categorias IS NULL OR q.categoria IN :categorias) " +
           "AND (:editoras IS NULL OR q.editora IN :editoras) " +
           "AND q.estoque > 0 AND q.ativo = true " +
           "ORDER BY RAND()")
    List<Quadrinho> findParaCuradoria(
        @Param("tipo") TipoHQ tipo,
        @Param("categorias") List<CategoriaHQ> categorias,
        @Param("editoras") List<EditoraHQ> editoras
    );
}
