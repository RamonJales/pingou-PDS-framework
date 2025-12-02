package com.pds.pingou.camisa.planos;

import com.pds.pingou.framework.core.service.BasePlanService;
import com.pds.pingou.camisa.planos.exception.PlanoCamisaNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Serviço para gerenciamento de planos de assinatura de camisas.
 * Estende BasePlanService do framework.
 */
@Service
public class PlanoCamisaService extends BasePlanService<PlanoCamisa, PlanoCamisaRepository> {
    
    @Autowired
    private PlanoCamisaRepository repository;
    
    @Override
    protected PlanoCamisaRepository getRepository() {
        return repository;
    }
    
    @Override
    protected RuntimeException createNotFoundException(Long id) {
        return new PlanoCamisaNotFoundException(id);
    }
    
    @Override
    public PlanoCamisa findByName(String nome) {
        return repository.findByNome(nome)
            .orElseThrow(() -> new PlanoCamisaNotFoundException("Plano com nome " + nome + " não encontrado"));
    }
    
    @Override
    public boolean existsByName(String nome) {
        return repository.existsByNome(nome);
    }
    
    public List<PlanoCamisa> findByCategoria(String categoria) {
        return repository.findByCategoriaPlano(categoria);
    }
    
    public List<PlanoCamisa> findAllActiveOrderByPrice() {
        return repository.findAllActiveOrderByPrice();
    }
}
