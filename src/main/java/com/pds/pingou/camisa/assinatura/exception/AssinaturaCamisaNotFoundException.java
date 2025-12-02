package com.pds.pingou.camisa.assinatura.exception;

/**
 * Exceção lançada quando uma assinatura de camisa não é encontrada.
 */
public class AssinaturaCamisaNotFoundException extends RuntimeException {
    
    public AssinaturaCamisaNotFoundException(Long id) {
        super("Assinatura de camisa com ID " + id + " não encontrada");
    }
    
    public AssinaturaCamisaNotFoundException(String message) {
        super(message);
    }
}
