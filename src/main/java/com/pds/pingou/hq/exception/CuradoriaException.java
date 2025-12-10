package com.pds.pingou.hq.exception;

/**
 * Exceção lançada quando há erro na curadoria de pacotes
 */
public class CuradoriaException extends RuntimeException {
    
    public CuradoriaException(String message) {
        super(message);
    }

    public CuradoriaException(String message, Throwable cause) {
        super(message, cause);
    }
}
