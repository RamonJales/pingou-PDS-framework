package com.pds.pingou.hq.exception;

/**
 * Exceção lançada quando preferências do usuário não são encontradas
 */
public class PreferenciaNotFoundException extends RuntimeException {
    
    public PreferenciaNotFoundException(String message) {
        super(message);
    }

    public PreferenciaNotFoundException(Long userId) {
        super("Preferências não encontradas para o usuário ID: " + userId);
    }
}
