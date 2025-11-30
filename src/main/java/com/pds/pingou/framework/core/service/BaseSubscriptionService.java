package com.pds.pingou.framework.core.service;

import com.pds.pingou.framework.core.entity.BaseSubscription;
import com.pds.pingou.framework.core.enums.SubscriptionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Serviço abstrato base para gerenciamento de assinaturas no framework.
 * 
 * Esta classe fornece operações comuns para gerenciar assinaturas, incluindo
 * ativação, desativação, suspensão e cancelamento.
 * 
 * @param <S> Tipo da assinatura (deve estender BaseSubscription)
 * @param <U> Tipo do usuário
 * @param <P> Tipo do plano
 * @param <R> Tipo do repositório
 * 
 * @author Pingou Framework Team
 * @version 1.0
 * @since 1.0
 */
public abstract class BaseSubscriptionService<S extends BaseSubscription<U, P>, U, P, R extends JpaRepository<S, Long>> 
    extends BaseCrudService<S, Long, R> {
    
    /**
     * Ativa uma assinatura para um usuário específico.
     */
    public S activateSubscription(U user, P plan) {
        S subscription = createSubscription(user, plan);
        subscription.setStatus(SubscriptionStatus.ATIVA);
        subscription.setDataInicio(LocalDate.now());
        subscription.setDataExpiracao(null);
        
        beforeSave(subscription);
        S saved = save(subscription);
        afterSave(saved);
        
        return saved;
    }
    
    /**
     * Desativa uma assinatura.
     */
    public S deactivateSubscription(Long id) {
        S subscription = findByIdOrThrow(id);
        subscription.deactivate();
        return update(subscription);
    }
    
    /**
     * Suspende uma assinatura temporariamente.
     */
    public S suspendSubscription(Long id) {
        S subscription = findByIdOrThrow(id);
        subscription.suspend();
        return update(subscription);
    }
    
    /**
     * Cancela uma assinatura.
     */
    public S cancelSubscription(Long id) {
        S subscription = findByIdOrThrow(id);
        subscription.cancel();
        return update(subscription);
    }
    
    /**
     * Reativa uma assinatura suspensa ou cancelada.
     */
    public S reactivateSubscription(Long id) {
        S subscription = findByIdOrThrow(id);
        subscription.activate();
        return update(subscription);
    }
    
    /**
     * Lista todas as assinaturas ativas.
     */
    public List<S> findActiveSubscriptions() {
        return findAll().stream()
            .filter(BaseSubscription::isActive)
            .collect(Collectors.toList());
    }
    
    /**
     * Lista assinaturas por status.
     */
    public List<S> findByStatus(SubscriptionStatus status) {
        return findAll().stream()
            .filter(s -> s.getStatus() == status)
            .collect(Collectors.toList());
    }
    
    /**
     * Verifica se um usuário já possui uma assinatura ativa.
     */
    public abstract boolean hasActiveSubscription(U user);
    
    /**
     * Busca a assinatura de um usuário específico.
     */
    public abstract S findByUser(U user);
    
    /**
     * Cria uma nova instância de assinatura.
     * Deve ser implementado pelas subclasses.
     */
    protected abstract S createSubscription(U user, P plan);
    
    /**
     * Valida uma assinatura antes de salvar.
     */
    @Override
    protected void validate(S subscription) {
        if (subscription.getUser() == null) {
            throw new IllegalArgumentException("Usuário não pode ser nulo");
        }
        if (subscription.getPlan() == null) {
            throw new IllegalArgumentException("Plano não pode ser nulo");
        }
        if (subscription.getStatus() == null) {
            throw new IllegalArgumentException("Status não pode ser nulo");
        }
    }
}
