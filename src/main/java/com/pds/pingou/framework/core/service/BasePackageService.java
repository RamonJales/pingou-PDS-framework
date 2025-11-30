package com.pds.pingou.framework.core.service;

import com.pds.pingou.framework.core.entity.BasePackage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Serviço abstrato base para gerenciamento de pacotes no framework.
 * 
 * Esta classe fornece operações comuns para gerenciar pacotes de produtos.
 * 
 * @param <PKG> Tipo do pacote (deve estender BasePackage)
 * @param <PLN> Tipo do plano
 * @param <R> Tipo do repositório
 * 
 * @author Pingou Framework Team
 * @version 1.0
 * @since 1.0
 */
public abstract class BasePackageService<PKG extends BasePackage<PLN, ?>, PLN, R extends JpaRepository<PKG, Long>> 
    extends BaseCrudService<PKG, Long, R> {
    
    /**
     * Lista todos os pacotes ativos.
     */
    public List<PKG> findActivePackages() {
        return findAll().stream()
            .filter(BasePackage::isAvailable)
            .collect(Collectors.toList());
    }
    
    /**
     * Busca pacotes por plano.
     */
    public abstract List<PKG> findByPlan(PLN plan);
    
    /**
     * Busca pacotes por período e ano.
     */
    public abstract List<PKG> findByPeriodAndYear(Integer periodo, Integer ano);
    
    /**
     * Busca pacotes com data de entrega em um intervalo.
     */
    public List<PKG> findByDeliveryDateRange(LocalDate startDate, LocalDate endDate) {
        return findAll().stream()
            .filter(pkg -> !pkg.getDataEntrega().isBefore(startDate) && 
                          !pkg.getDataEntrega().isAfter(endDate))
            .collect(Collectors.toList());
    }
    
    /**
     * Busca pacotes com entrega agendada para hoje.
     */
    public List<PKG> findPackagesForDeliveryToday() {
        return findAll().stream()
            .filter(BasePackage::isDeliveryDateToday)
            .collect(Collectors.toList());
    }
    
    /**
     * Busca pacotes atrasados (data de entrega já passou).
     */
    public List<PKG> findOverduePackages() {
        return findAll().stream()
            .filter(BasePackage::isOverdue)
            .collect(Collectors.toList());
    }
    
    /**
     * Ativa um pacote.
     */
    public PKG activatePackage(Long id) {
        PKG pkg = findByIdOrThrow(id);
        pkg.activate();
        return update(pkg);
    }
    
    /**
     * Desativa um pacote.
     */
    public PKG deactivatePackage(Long id) {
        PKG pkg = findByIdOrThrow(id);
        pkg.deactivate();
        return update(pkg);
    }
    
    /**
     * Valida um pacote antes de salvar.
     */
    @Override
    protected void validate(PKG pkg) {
        if (pkg.getNome() == null || pkg.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do pacote não pode ser vazio");
        }
        if (pkg.getDataEntrega() == null) {
            throw new IllegalArgumentException("Data de entrega não pode ser nula");
        }
        if (pkg.getPeriodo() == null || pkg.getPeriodo() < 1) {
            throw new IllegalArgumentException("Período deve ser maior que zero");
        }
        if (pkg.getAno() == null || pkg.getAno() < 2000) {
            throw new IllegalArgumentException("Ano inválido");
        }
        if (pkg.getPlan() == null) {
            throw new IllegalArgumentException("Plano não pode ser nulo");
        }
    }
}
