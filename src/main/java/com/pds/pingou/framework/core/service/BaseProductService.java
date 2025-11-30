package com.pds.pingou.framework.core.service;

import com.pds.pingou.framework.core.entity.BaseProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Serviço abstrato base para gerenciamento de produtos no framework.
 * 
 * Esta classe fornece operações comuns para gerenciar produtos.
 * 
 * @param <P> Tipo do produto (deve estender BaseProduct)
 * @param <R> Tipo do repositório
 * 
 * @author Pingou Framework Team
 * @version 1.0
 * @since 1.0
 */
public abstract class BaseProductService<P extends BaseProduct, R extends JpaRepository<P, Long>> 
    extends BaseCrudService<P, Long, R> {
    
    /**
     * Lista todos os produtos ativos.
     */
    public List<P> findActiveProducts() {
        return findAll().stream()
            .filter(BaseProduct::isAvailable)
            .collect(Collectors.toList());
    }
    
    /**
     * Busca produtos por nome (contém).
     */
    public List<P> findByNameContaining(String nome) {
        return findAll().stream()
            .filter(p -> p.getNome() != null && 
                        p.getNome().toLowerCase().contains(nome.toLowerCase()))
            .collect(Collectors.toList());
    }
    
    /**
     * Busca produtos por categoria.
     */
    public List<P> findByCategory(String category) {
        return findAll().stream()
            .filter(p -> p.getCategory() != null && 
                        p.getCategory().equalsIgnoreCase(category))
            .collect(Collectors.toList());
    }
    
    /**
     * Ativa um produto.
     */
    public P activateProduct(Long id) {
        P product = findByIdOrThrow(id);
        product.activate();
        return update(product);
    }
    
    /**
     * Desativa um produto.
     */
    public P deactivateProduct(Long id) {
        P product = findByIdOrThrow(id);
        product.deactivate();
        return update(product);
    }
    
    /**
     * Valida um produto antes de salvar.
     */
    @Override
    protected void validate(P product) {
        if (product.getNome() == null || product.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do produto não pode ser vazio");
        }
        if (product.getPreco() == null || product.getPreco().doubleValue() <= 0) {
            throw new IllegalArgumentException("Preço do produto deve ser maior que zero");
        }
    }
}
