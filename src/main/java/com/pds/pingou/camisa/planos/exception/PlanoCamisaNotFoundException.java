package com.pds.pingou.camisa.planos.exception;

/**
 * Exceção lançada quando um plano de camisa não é encontrado.
 */
public class PlanoCamisaNotFoundException extends RuntimeException {
    
    public PlanoCamisaNotFoundException(Long id) {
        super("Plano de camisa com ID " + id + " não encontrado");
    }
    
    public PlanoCamisaNotFoundException(String message) {
        super(message);
    }
}
