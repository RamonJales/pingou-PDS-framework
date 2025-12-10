package com.pds.pingou.hq.service;

import com.pds.pingou.framework.core.service.BaseProductService;
import com.pds.pingou.hq.entity.Quadrinho;
import com.pds.pingou.hq.enums.CategoriaHQ;
import com.pds.pingou.hq.enums.EditoraHQ;
import com.pds.pingou.hq.enums.TipoHQ;
import com.pds.pingou.hq.exception.QuadrinhoNotFoundException;
import com.pds.pingou.hq.repository.QuadrinhoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Serviço para operações com Quadrinhos
 * Estende BaseProductService do framework
 */
@Service
@Transactional
public class QuadrinhoService extends BaseProductService<Quadrinho, QuadrinhoRepository> {

    @Autowired
    private QuadrinhoRepository repository;

    @Override
    protected QuadrinhoRepository getRepository() {
        return repository;
    }

    @Override
    protected RuntimeException createNotFoundException(Long id) {
        return new QuadrinhoNotFoundException(id);
    }

    /**
     * Busca quadrinhos por editora
     */
    public List<Quadrinho> findByEditora(EditoraHQ editora) {
        return repository.findByEditoraAndAtivoTrue(editora);
    }

    /**
     * Busca quadrinhos por tipo (Clássica/Moderna)
     */
    public List<Quadrinho> findByTipo(TipoHQ tipo) {
        return repository.findByTipoHQAndAtivoTrue(tipo);
    }

    /**
     * Busca quadrinhos por categoria
     */
    public List<Quadrinho> findByCategoria(CategoriaHQ categoria) {
        return repository.findByCategoriaAndAtivoTrue(categoria);
    }

    /**
     * Busca edições de colecionador
     */
    public List<Quadrinho> findEdicoesColecionador() {
        return repository.findByEdicaoColecionadorTrueAndAtivoTrue();
    }

    /**
     * Busca quadrinhos com estoque disponível
     */
    public List<Quadrinho> findComEstoque() {
        return repository.findComEstoqueDisponivel();
    }

    /**
     * Busca quadrinhos por tipo com estoque
     */
    public List<Quadrinho> findByTipoComEstoque(TipoHQ tipo) {
        return repository.findByTipoComEstoque(tipo);
    }

    /**
     * Busca quadrinhos por categoria com estoque
     */
    public List<Quadrinho> findByCategoriaComEstoque(CategoriaHQ categoria) {
        return repository.findByCategoriaComEstoque(categoria);
    }

    /**
     * Busca quadrinhos por editora com estoque
     */
    public List<Quadrinho> findByEditoraComEstoque(EditoraHQ editora) {
        return repository.findByEditoraComEstoque(editora);
    }

    /**
     * Busca quadrinhos por múltiplas categorias com estoque
     */
    public List<Quadrinho> findByCategoriasComEstoque(List<CategoriaHQ> categorias) {
        return repository.findByCategoriasComEstoque(categorias);
    }

    /**
     * Busca quadrinhos por múltiplas editoras com estoque
     */
    public List<Quadrinho> findByEditorasComEstoque(List<EditoraHQ> editoras) {
        return repository.findByEditorasComEstoque(editoras);
    }

    /**
     * Busca quadrinhos para curadoria personalizada
     */
    public List<Quadrinho> findParaCuradoria(TipoHQ tipo, List<CategoriaHQ> categorias, List<EditoraHQ> editoras) {
        return repository.findParaCuradoria(tipo, categorias, editoras);
    }

    /**
     * Busca quadrinho por título da série
     */
    public List<Quadrinho> findByTituloSerie(String tituloSerie) {
        return repository.findByTituloSerieContainingIgnoreCase(tituloSerie);
    }

    /**
     * Incrementa o estoque de um quadrinho
     */
    public Quadrinho incrementarEstoque(Long id, int quantidade) {
        Quadrinho quadrinho = findByIdOrThrow(id);
        quadrinho.incrementarEstoque(quantidade);
        return repository.save(quadrinho);
    }

    /**
     * Decrementa o estoque de um quadrinho
     */
    public Quadrinho decrementarEstoque(Long id) {
        Quadrinho quadrinho = findByIdOrThrow(id);
        quadrinho.decrementarEstoque();
        return repository.save(quadrinho);
    }

    /**
     * Verifica se há estoque disponível
     */
    public boolean temEstoque(Long id) {
        Quadrinho quadrinho = findByIdOrThrow(id);
        return quadrinho.temEstoque();
    }
}
