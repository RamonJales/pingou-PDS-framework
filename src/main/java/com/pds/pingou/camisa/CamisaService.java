package com.pds.pingou.camisa;

import com.pds.pingou.framework.core.service.BaseProductService;
import com.pds.pingou.camisa.enums.LigaCamisa;
import com.pds.pingou.camisa.enums.TipoCamisa;
import com.pds.pingou.camisa.exception.CamisaNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Serviço para gerenciamento de camisas de futebol.
 * Estende BaseProductService do framework.
 */
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
        return new CamisaNotFoundException(id);
    }
    
    public List<Camisa> findByTime(String time) {
        return repository.findByTime(time);
    }
    
    public List<Camisa> findByLiga(LigaCamisa liga) {
        return repository.findByLiga(liga);
    }
    
    public List<Camisa> findByTipo(TipoCamisa tipo) {
        return repository.findByTipoCamisa(tipo);
    }
    
    public List<Camisa> findByAnoTemporada(Integer ano) {
        return repository.findByAnoTemporada(ano);
    }
    
    public List<Camisa> findByMarca(String marca) {
        return repository.findByMarca(marca);
    }
    
    public List<Camisa> findAvailableInStock() {
        return repository.findAvailableInStock();
    }
    
    public List<Camisa> searchByTerm(String termo) {
        return repository.searchByTerm(termo);
    }
    
    public List<Camisa> findEdicoesLimitadas() {
        return repository.findByEdicaoLimitadaTrue();
    }
}
