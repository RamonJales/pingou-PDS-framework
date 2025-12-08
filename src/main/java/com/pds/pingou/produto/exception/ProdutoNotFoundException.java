package com.pds.pingou.produto.exception;

/**
 * Exceção lançada quando um produto não é encontrado.
 * 
 * @author Pingou Framework Team
 * @version 2.0
 */
public class ProdutoNotFoundException extends RuntimeException {
    
    public ProdutoNotFoundException(Long id) {
        super("Produto não encontrado com ID: " + id);
    }
    
    public ProdutoNotFoundException(String sku) {
        super("Produto não encontrado com SKU: " + sku);
    }
}
