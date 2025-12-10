package com.pds.pingou.hq.service;

import com.pds.pingou.framework.core.service.BasePlanService;
import com.pds.pingou.hq.entity.PlanoHQ;
import com.pds.pingou.hq.exception.PlanoHQNotFoundException;
import com.pds.pingou.hq.repository.PlanoHQRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Serviço para operações com Planos de HQ
 * Estende BasePlanService do framework
 */
@Service
@Transactional
public class PlanoHQService extends BasePlanService<PlanoHQ, PlanoHQRepository> {

    @Autowired
    private PlanoHQRepository repository;

    @Override
    protected PlanoHQRepository getRepository() {
        return repository;
    }

    @Override
    protected RuntimeException createNotFoundException(Long id) {
        return new PlanoHQNotFoundException(id);
    }

    @Override
    public PlanoHQ findByName(String nome) {
        return repository.findByNome(nome)
            .orElseThrow(() -> new PlanoHQNotFoundException("Plano não encontrado: " + nome));
    }

    @Override
    public boolean existsByName(String nome) {
        return repository.existsByNome(nome);
    }

    /**
     * Busca planos focados em clássicas
     */
    public List<PlanoHQ> findFocadosEmClassicas() {
        return repository.findPlanosFocadosEmClassicas();
    }

    /**
     * Busca planos focados em modernas
     */
    public List<PlanoHQ> findFocadosEmModernas() {
        return repository.findPlanosFocadosEmModernas();
    }

    /**
     * Busca planos equilibrados
     */
    public List<PlanoHQ> findEquilibrados() {
        return repository.findPlanosEquilibrados();
    }

    /**
     * Busca planos que incluem edições de colecionador
     */
    public List<PlanoHQ> findComEdicoesColecionador() {
        return repository.findByIncluiEdicoesColecionadorTrueAndAtivoTrue();
    }

    /**
     * Busca planos por nível de curadoria
     */
    public List<PlanoHQ> findByNivelCuradoria(String nivelCuradoria) {
        return repository.findByNivelCuradoriaAndAtivoTrue(nivelCuradoria);
    }

    /**
     * Valida plano antes de salvar
     */
    @Override
    protected void beforeSave(PlanoHQ plano) {
        super.beforeSave(plano);
        plano.validatePercentuais();
    }
}
