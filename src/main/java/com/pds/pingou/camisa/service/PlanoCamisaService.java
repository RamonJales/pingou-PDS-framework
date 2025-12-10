package com.pds.pingou.camisa.service;

import com.pds.pingou.camisa.entity.PlanoCamisa;
import com.pds.pingou.camisa.repository.PlanoCamisaRepository;
import com.pds.pingou.framework.core.service.BasePlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return new RuntimeException("Plano não encontrado com ID: " + id);
    }
    
    @Override
    public PlanoCamisa findByName(String nome) {
        return repository.findByNome(nome)
            .orElseThrow(() -> new RuntimeException("Plano não encontrado com nome: " + nome));
    }
    
    @Override
    public boolean existsByName(String nome) {
        return repository.existsByNome(nome);
    }
}
