package com.pds.pingou.hq.exception;

/**
 * Exceção lançada quando um plano não é encontrado
 */
public class PlanoHQNotFoundException extends RuntimeException {
    
    public PlanoHQNotFoundException(Long id) {
        super("Plano de HQ não encontrado com ID: " + id);
    }

    public PlanoHQNotFoundException(String message) {
        super(message);
    }
}
