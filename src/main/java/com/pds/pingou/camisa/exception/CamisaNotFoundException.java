package com.pds.pingou.camisa.exception;

/**
 * Exceção lançada quando uma camisa não é encontrada.
 */
public class CamisaNotFoundException extends RuntimeException {
    
    public CamisaNotFoundException(Long id) {
        super("Camisa com ID " + id + " não encontrada");
    }
    
    public CamisaNotFoundException(String message) {
        super(message);
    }
}
