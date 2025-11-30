package com.pds.pingou.framework.core.enums;

/**
 * Enumeração que representa os possíveis status de uma assinatura no framework.
 * 
 * @author Pingou Framework Team
 * @version 1.0
 * @since 1.0
 */
public enum SubscriptionStatus {
    /** Assinatura ativa e em funcionamento */
    ATIVA,
    
    /** Assinatura inativa/desativada */
    INATIVA,
    
    /** Assinatura suspensa temporariamente */
    SUSPENSA,
    
    /** Assinatura cancelada pelo usuário ou sistema */
    CANCELADA,
    
    /** Assinatura pendente de ativação/pagamento */
    PENDENTE
}
