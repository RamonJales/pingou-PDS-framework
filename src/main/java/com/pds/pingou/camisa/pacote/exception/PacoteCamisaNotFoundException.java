package com.pds.pingou.camisa.pacote.exception;

/**
 * Exceção lançada quando um pacote de camisa não é encontrado.
 */
public class PacoteCamisaNotFoundException extends RuntimeException {
    
    public PacoteCamisaNotFoundException(Long id) {
        super("Pacote de camisa com ID " + id + " não encontrado");
    }
    
    public PacoteCamisaNotFoundException(String message) {
        super(message);
    }
}
