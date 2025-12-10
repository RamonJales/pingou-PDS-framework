package com.pds.pingou.hq.service;

import com.pds.pingou.framework.core.service.BaseSubscriptionService;
import com.pds.pingou.hq.entity.AssinaturaHQ;
import com.pds.pingou.hq.entity.PacoteHQ;
import com.pds.pingou.hq.entity.PlanoHQ;
import com.pds.pingou.hq.repository.AssinaturaHQRepository;
import com.pds.pingou.security.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * Serviço para operações com Assinaturas de HQ
 * Estende BaseSubscriptionService do framework
 */
@Service
@Transactional
public class AssinaturaHQService extends BaseSubscriptionService<AssinaturaHQ, User, PlanoHQ, AssinaturaHQRepository> {

    @Autowired
    private AssinaturaHQRepository repository;

    @Override
    protected AssinaturaHQRepository getRepository() {
        return repository;
    }

    @Override
    protected RuntimeException createNotFoundException(Long id) {
        return new RuntimeException("Assinatura não encontrada com ID: " + id);
    }

    @Override
    protected AssinaturaHQ createSubscription(User user, PlanoHQ plano) {
        return new AssinaturaHQ(user, plano);
    }

    @Override
    public boolean hasActiveSubscription(User user) {
        return repository.findByUser(user)
            .map(AssinaturaHQ::isActive)
            .orElse(false);
    }

    @Override
    public AssinaturaHQ findByUser(User user) {
        return repository.findByUser(user)
            .orElseThrow(() -> new RuntimeException("Assinatura não encontrada para usuário: " + user.getUsername()));
    }

    /**
     * Busca assinaturas por nível de colecionador
     */
    public List<AssinaturaHQ> findByNivelColecionador(String nivel) {
        return repository.findByNivelColecionador(nivel);
    }

    /**
     * Busca assinaturas elegíveis para upgrade
     */
    public List<AssinaturaHQ> findElegiveisParaUpgrade() {
        return repository.findElegiveisParaUpgrade();
    }

    /**
     * Busca assinaturas sem pacote recente (> 2 meses)
     */
    public List<AssinaturaHQ> findSemPacoteRecente() {
        LocalDate dataLimite = LocalDate.now().minusMonths(2);
        return repository.findSemPacoteRecente(dataLimite);
    }

    /**
     * Adiciona pontos à assinatura
     */
    public AssinaturaHQ adicionarPontos(Long assinaturaId, int pontos) {
        AssinaturaHQ assinatura = findByIdOrThrow(assinaturaId);
        assinatura.adicionarPontos(pontos);
        return repository.save(assinatura);
    }

    /**
     * Resgata pontos da assinatura
     */
    public AssinaturaHQ resgatarPontos(Long assinaturaId, int pontos) {
        AssinaturaHQ assinatura = findByIdOrThrow(assinaturaId);
        assinatura.resgatarPontos(pontos);
        return repository.save(assinatura);
    }

    /**
     * Registra recebimento de pacote
     */
    public AssinaturaHQ registrarRecebimentoPacote(Long assinaturaId, PacoteHQ pacote) {
        AssinaturaHQ assinatura = findByIdOrThrow(assinaturaId);
        assinatura.registrarRecebimentoPacote(pacote);
        return repository.save(assinatura);
    }

    /**
     * Busca saldo de pontos disponíveis
     */
    public int getPontosDisponiveis(Long assinaturaId) {
        AssinaturaHQ assinatura = findByIdOrThrow(assinaturaId);
        return assinatura.getPontosDisponiveis();
    }

    /**
     * Verifica se assinatura tem pontos suficientes
     */
    public boolean temPontosSuficientes(Long assinaturaId, int pontos) {
        AssinaturaHQ assinatura = findByIdOrThrow(assinaturaId);
        return assinatura.temPontosSuficientes(pontos);
    }

    /**
     * Busca estatísticas da assinatura
     */
    public String getEstatisticas(Long assinaturaId) {
        AssinaturaHQ assinatura = findByIdOrThrow(assinaturaId);
        return assinatura.getEstatisticas();
    }

    /**
     * Busca total de pontos distribuídos no sistema
     */
    public Long getTotalPontosDistribuidos() {
        return repository.countTotalPontosDistribuidos();
    }

    /**
     * Busca total de HQs distribuídas no sistema
     */
    public Long getTotalHQsDistribuidas() {
        return repository.countTotalHQsDistribuidas();
    }

    /**
     * Troca de plano
     */
    public AssinaturaHQ trocarPlano(Long assinaturaId, PlanoHQ novoPlano) {
        AssinaturaHQ assinatura = findByIdOrThrow(assinaturaId);
        
        if (!assinatura.isActive()) {
            throw new IllegalStateException("Assinatura deve estar ativa para trocar de plano");
        }
        
        assinatura.setPlan(novoPlano);
        return repository.save(assinatura);
    }

    /**
     * Upgrade de plano para assinaturas elegíveis
     */
    public AssinaturaHQ realizarUpgrade(Long assinaturaId, PlanoHQ planoSuperior) {
        AssinaturaHQ assinatura = findByIdOrThrow(assinaturaId);
        
        if (!assinatura.isElegivelParaUpgrade()) {
            throw new IllegalStateException(
                "Assinatura não elegível para upgrade. Requisitos: 1000 pontos e 3 pacotes recebidos"
            );
        }
        
        assinatura.setPlan(planoSuperior);
        // Bônus de upgrade: 500 pontos extras
        assinatura.adicionarPontos(500);
        
        return repository.save(assinatura);
    }
}
