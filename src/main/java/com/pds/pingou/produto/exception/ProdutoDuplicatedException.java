package com.pds.pingou.produto.exception;

/**
 * Exceção lançada quando há um conflito de produto (ex: SKU duplicado).
 * 
 * @author Pingou Framework Team
 * @version 2.0
 */
public class ProdutoDuplicatedException extends RuntimeException {
    
    public ProdutoDuplicatedException(String sku) {
        super("Produto já existe com SKU: " + sku);
    }
}
