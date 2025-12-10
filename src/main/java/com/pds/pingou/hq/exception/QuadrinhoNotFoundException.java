package com.pds.pingou.hq.exception;

/**
 * Exceção lançada quando um quadrinho não é encontrado
 */
public class QuadrinhoNotFoundException extends RuntimeException {
    
    public QuadrinhoNotFoundException(Long id) {
        super("Quadrinho não encontrado com ID: " + id);
    }

    public QuadrinhoNotFoundException(String message) {
        super(message);
    }
}
