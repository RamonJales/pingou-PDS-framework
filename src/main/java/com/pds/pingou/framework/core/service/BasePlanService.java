package com.pds.pingou.framework.core.service;

import com.pds.pingou.framework.core.entity.BasePlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Serviço abstrato base para gerenciamento de planos no framework.
 * 
 * Esta classe fornece operações comuns para gerenciar planos de assinatura.
 * 
 * @param <P> Tipo do plano (deve estender BasePlan)
 * @param <R> Tipo do repositório
 * 
 * @author Pingou Framework Team
 * @version 1.0
 * @since 1.0
 */
public abstract class BasePlanService<P extends BasePlan<?>, R extends JpaRepository<P, Long>> 
    extends BaseCrudService<P, Long, R> {
    
    /**
     * Lista todos os planos ativos.
     */
    public List<P> findActivePlans() {
        return findAll().stream()
            .filter(BasePlan::isAvailable)
            .collect(Collectors.toList());
    }
    
    /**
     * Busca um plano por nome.
     */
    public abstract P findByName(String nome);
    
    /**
     * Verifica se já existe um plano com o nome especificado.
     */
    public abstract boolean existsByName(String nome);
    
    /**
     * Ativa um plano.
     */
    public P activatePlan(Long id) {
        P plan = findByIdOrThrow(id);
        plan.activate();
        return update(plan);
    }
    
    /**
     * Desativa um plano.
     */
    public P deactivatePlan(Long id) {
        P plan = findByIdOrThrow(id);
        plan.deactivate();
        return update(plan);
    }
    
    /**
     * Valida um plano antes de salvar.
     */
    @Override
    protected void validate(P plan) {
        if (plan.getNome() == null || plan.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do plano não pode ser vazio");
        }
        if (plan.getDescricao() == null || plan.getDescricao().trim().isEmpty()) {
            throw new IllegalArgumentException("Descrição do plano não pode ser vazia");
        }
        if (plan.getPreco() == null || plan.getPreco().doubleValue() <= 0) {
            throw new IllegalArgumentException("Preço do plano deve ser maior que zero");
        }
        if (plan.getMaxProdutosPorPeriodo() == null || plan.getMaxProdutosPorPeriodo() <= 0) {
            throw new IllegalArgumentException("Quantidade máxima de produtos deve ser maior que zero");
        }
    }
}
