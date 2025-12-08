package com.pds.pingou.framework.core.security.exception;

/**
 * Exceção lançada quando um usuário já existe no sistema.
 * 
 * @author Pingou Framework Team
 * @version 1.0
 * @since 1.0
 */
public class UserDuplicatedException extends RuntimeException {
    
    public UserDuplicatedException(String identifier) {
        super("Usuário já existe: " + identifier);
    }
    
    public UserDuplicatedException(String message, Throwable cause) {
        super(message, cause);
    }
}
