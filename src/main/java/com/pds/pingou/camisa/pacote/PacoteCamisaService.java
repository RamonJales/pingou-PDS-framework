package com.pds.pingou.camisa.pacote;

import com.pds.pingou.framework.core.service.BasePackageService;
import com.pds.pingou.camisa.planos.PlanoCamisa;
import com.pds.pingou.camisa.pacote.exception.PacoteCamisaNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * Serviço para gerenciamento de pacotes de camisas.
 * Estende BasePackageService do framework.
 */
@Service
public class PacoteCamisaService extends BasePackageService<PacoteCamisa, PlanoCamisa, PacoteCamisaRepository> {
    
    @Autowired
    private PacoteCamisaRepository repository;
    
    @Override
    protected PacoteCamisaRepository getRepository() {
        return repository;
    }
    
    @Override
    protected RuntimeException createNotFoundException(Long id) {
        return new PacoteCamisaNotFoundException(id);
    }
    
    @Override
    public List<PacoteCamisa> findByPlan(PlanoCamisa plan) {
        return repository.findByPlano(plan);
    }
    
    public List<PacoteCamisa> findByPlano(PlanoCamisa plano) {
        return repository.findByPlano(plano);
    }
    
    public List<PacoteCamisa> findByPeriodoAndAno(Integer periodo, Integer ano) {
        return repository.findByPeriodoAndAno(periodo, ano);
    }
    
    public List<PacoteCamisa> findByAno(Integer ano) {
        return repository.findByAno(ano);
    }
    
    public List<PacoteCamisa> findByDataEntregaBetween(LocalDate dataInicio, LocalDate dataFim) {
        return repository.findByDataEntregaBetween(dataInicio, dataFim);
    }
    
    public List<PacoteCamisa> findActivePacotesByPlano(Long planoId) {
        return repository.findActivePacotesByPlano(planoId);
    }
    
    @Override
    public List<PacoteCamisa> findByPeriodAndYear(Integer periodo, Integer ano) {
        return repository.findByPeriodoAndAno(periodo, ano);
    }
}
