package com.pds.pingou.framework.core.security.exception;

/**
 * Exceção lançada quando um usuário não é encontrado no sistema.
 * 
 * @author Pingou Framework Team
 * @version 1.0
 * @since 1.0
 */
public class UserNotFoundException extends RuntimeException {
    
    public UserNotFoundException(String identifier) {
        super("Usuário não encontrado: " + identifier);
    }
    
    public UserNotFoundException(Long id) {
        super("Usuário não encontrado com ID: " + id);
    }
    
    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
