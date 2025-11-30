package com.pds.pingou.framework.core.entity;

import com.pds.pingou.framework.core.enums.SubscriptionStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Classe abstrata base para representar assinaturas em um sistema de assinatura genérico.
 * 
 * Esta classe faz parte do framework de assinaturas e deve ser estendida por implementações
 * específicas de domínio. Ela encapsula a lógica comum de assinaturas, incluindo
 * relacionamento com usuários, planos, status e datas de vigência.
 * 
 * @param <U> Tipo do usuário (deve estender BaseUser)
 * @param <P> Tipo do plano (deve estender BasePlan)
 * 
 * @author Pingou Framework Team
 * @version 1.0
 * @since 1.0
 */
@MappedSuperclass
@Getter
@Setter
public abstract class BaseSubscription<U, P> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Status atual da assinatura */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SubscriptionStatus status;

    /** Data de início da assinatura */
    @Column(name = "data_inicio", nullable = false)
    private LocalDate dataInicio;

    /** Data de expiração da assinatura (null se ativa) */
    @Column(name = "data_expiracao")
    private LocalDate dataExpiracao;

    /**
     * Retorna o usuário associado a esta assinatura.
     * Deve ser implementado pelas subclasses.
     */
    public abstract U getUser();

    /**
     * Define o usuário associado a esta assinatura.
     * Deve ser implementado pelas subclasses.
     */
    public abstract void setUser(U user);

    /**
     * Retorna o plano associado a esta assinatura.
     * Deve ser implementado pelas subclasses.
     */
    public abstract P getPlan();

    /**
     * Define o plano associado a esta assinatura.
     * Deve ser implementado pelas subclasses.
     */
    public abstract void setPlan(P plan);

    /**
     * Verifica se a assinatura está ativa.
     */
    public boolean isActive() {
        return status == SubscriptionStatus.ATIVA && 
               (dataExpiracao == null || dataExpiracao.isAfter(LocalDate.now()));
    }

    /**
     * Ativa a assinatura.
     */
    public void activate() {
        this.status = SubscriptionStatus.ATIVA;
        if (this.dataInicio == null) {
            this.dataInicio = LocalDate.now();
        }
        this.dataExpiracao = null;
    }

    /**
     * Desativa a assinatura.
     */
    public void deactivate() {
        this.status = SubscriptionStatus.INATIVA;
        this.dataExpiracao = LocalDate.now();
    }

    /**
     * Suspende a assinatura temporariamente.
     */
    public void suspend() {
        this.status = SubscriptionStatus.SUSPENSA;
    }

    /**
     * Cancela a assinatura.
     */
    public void cancel() {
        this.status = SubscriptionStatus.CANCELADA;
        this.dataExpiracao = LocalDate.now();
    }
}
