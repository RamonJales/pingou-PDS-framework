package com.pds.pingou.camisa.assinatura;

import com.pds.pingou.framework.core.service.BaseSubscriptionService;
import com.pds.pingou.framework.core.enums.SubscriptionStatus;
import com.pds.pingou.security.user.User;
import com.pds.pingou.camisa.planos.PlanoCamisa;
import com.pds.pingou.camisa.assinatura.exception.AssinaturaCamisaNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Serviço para gerenciamento de assinaturas de camisas.
 * Estende BaseSubscriptionService do framework.
 */
@Service
public class AssinaturaCamisaService extends BaseSubscriptionService<AssinaturaCamisa, User, PlanoCamisa, AssinaturaCamisaRepository> {
    
    @Autowired
    private AssinaturaCamisaRepository repository;
    
    @Override
    protected AssinaturaCamisaRepository getRepository() {
        return repository;
    }
    
    @Override
    protected RuntimeException createNotFoundException(Long id) {
        return new AssinaturaCamisaNotFoundException(id);
    }
    
    public AssinaturaCamisa findByUser(User user) {
        return repository.findByUser(user)
            .orElseThrow(() -> new AssinaturaCamisaNotFoundException("Assinatura não encontrada para o usuário"));
    }
    
    public AssinaturaCamisa findByUserId(Long userId) {
        return repository.findByUserId(userId)
            .orElseThrow(() -> new AssinaturaCamisaNotFoundException("Assinatura não encontrada para o usuário com ID " + userId));
    }
    
    public boolean existsByUserId(Long userId) {
        return repository.existsByUserId(userId);
    }
    
    public List<AssinaturaCamisa> findByPlano(PlanoCamisa plano) {
        return repository.findByPlano(plano);
    }
    
    public List<AssinaturaCamisa> findByStatus(SubscriptionStatus status) {
        return repository.findByStatus(status);
    }
    
    public List<AssinaturaCamisa> findAllActive() {
        return repository.findAllActive();
    }
    
    public Long countActiveByPlano(Long planoId) {
        return repository.countActiveByPlano(planoId);
    }
    
    @Override
    public boolean hasActiveSubscription(User user) {
        return repository.findByUser(user)
            .map(AssinaturaCamisa::isActive)
            .orElse(false);
    }
    
    @Override
    protected AssinaturaCamisa createSubscription(User user, PlanoCamisa plan) {
        return new AssinaturaCamisa(user, plan);
    }
}
