package com.pds.pingou.camisa.service;

import com.pds.pingou.camisa.entity.Camisa;
import com.pds.pingou.camisa.repository.CamisaRepository;
import com.pds.pingou.framework.core.service.BaseProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CamisaService extends BaseProductService<Camisa, CamisaRepository> {
    
    @Autowired
    private CamisaRepository repository;
    
    @Override
    protected CamisaRepository getRepository() {
        return repository;
    }
    
    @Override
    protected RuntimeException createNotFoundException(Long id) {
        return new RuntimeException("Camisa não encontrada com ID: " + id);
    }
}
