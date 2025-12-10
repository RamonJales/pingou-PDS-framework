package com.pds.pingou.hq.entity;

import com.pds.pingou.framework.core.entity.BaseSubscription;
import com.pds.pingou.security.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Entidade que representa uma assinatura de HQs
 * Estende BaseSubscription do framework para herdar funcionalidades básicas
 * Inclui sistema de gamificação com pontos
 */
@Entity
@Table(name = "assinaturas_hq")
@Getter
@Setter
public class AssinaturaHQ extends BaseSubscription<User, PlanoHQ> {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plano_id", nullable = false)
    private PlanoHQ plano;

    @Column(name = "pontos_acumulados", nullable = false)
    private Integer pontosAcumulados = 0;

    @Column(name = "pontos_resgatados", nullable = false)
    private Integer pontosResgatados = 0;

    @Column(name = "nivel_colecionador")
    private String nivelColecionador; // BRONZE, PRATA, OURO, PLATINA

    @Column(name = "pacotes_recebidos", nullable = false)
    private Integer pacotesRecebidos = 0;

    @Column(name = "hqs_recebidas", nullable = false)
    private Integer hqsRecebidas = 0;

    @Column(name = "ultimo_pacote_data")
    private LocalDate ultimoPacoteData;

    /**
     * Construtor padrão
     */
    public AssinaturaHQ() {
        super();
        this.pontosAcumulados = 0;
        this.pontosResgatados = 0;
        this.pacotesRecebidos = 0;
        this.hqsRecebidas = 0;
        this.nivelColecionador = "BRONZE";
    }

    /**
     * Construtor com dados essenciais
     */
    public AssinaturaHQ(User user, PlanoHQ plano) {
        this();
        this.user = user;
        this.plano = plano;
        this.setDataInicio(LocalDate.now());
    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public PlanoHQ getPlan() {
        return plano;
    }

    @Override
    public void setPlan(PlanoHQ plan) {
        this.plano = plan;
    }

    /**
     * Adiciona pontos à assinatura
     */
    public void adicionarPontos(int pontos) {
        if (pontos < 0) {
            throw new IllegalArgumentException("Pontos devem ser positivos");
        }
        this.pontosAcumulados += pontos;
        atualizarNivelColecionador();
    }

    /**
     * Resgata pontos (consome pontos para benefícios)
     */
    public void resgatarPontos(int pontos) {
        if (pontos < 0) {
            throw new IllegalArgumentException("Pontos devem ser positivos");
        }
        
        if (pontos > getPontosDisponiveis()) {
            throw new IllegalStateException(
                String.format("Pontos insuficientes. Disponível: %d, Solicitado: %d",
                    getPontosDisponiveis(), pontos)
            );
        }
        
        this.pontosResgatados += pontos;
    }

    /**
     * Retorna o saldo de pontos disponíveis
     */
    public int getPontosDisponiveis() {
        return pontosAcumulados - pontosResgatados;
    }

    /**
     * Registra o recebimento de um pacote
     */
    public void registrarRecebimentoPacote(PacoteHQ pacote) {
        this.pacotesRecebidos++;
        this.hqsRecebidas += pacote.getItems().stream()
            .mapToInt(ItemPacoteHQ::getQuantidade)
            .sum();
        this.ultimoPacoteData = LocalDate.now();
        
        // Adiciona pontos do pacote
        if (pacote.getPontosTotais() != null) {
            adicionarPontos(pacote.getPontosTotais());
        }
    }

    /**
     * Atualiza o nível de colecionador baseado nos pontos acumulados
     */
    private void atualizarNivelColecionador() {
        if (pontosAcumulados >= 10000) {
            this.nivelColecionador = "PLATINA";
        } else if (pontosAcumulados >= 5000) {
            this.nivelColecionador = "OURO";
        } else if (pontosAcumulados >= 2000) {
            this.nivelColecionador = "PRATA";
        } else {
            this.nivelColecionador = "BRONZE";
        }
    }

    /**
     * Verifica se o usuário tem pontos suficientes
     */
    public boolean temPontosSuficientes(int pontos) {
        return getPontosDisponiveis() >= pontos;
    }

    /**
     * Calcula a média de HQs por pacote
     */
    public double getMediaHQsPorPacote() {
        if (pacotesRecebidos == 0) {
            return 0.0;
        }
        return (double) hqsRecebidas / pacotesRecebidos;
    }

    /**
     * Verifica se está elegível para upgrade de plano
     */
    public boolean isElegivelParaUpgrade() {
        return pontosAcumulados >= 1000 && pacotesRecebidos >= 3;
    }

    /**
     * Verifica se há muito tempo sem receber pacote
     */
    public boolean isSemPacoteRecente() {
        if (ultimoPacoteData == null) {
            return true;
        }
        return LocalDate.now().minusMonths(2).isAfter(ultimoPacoteData);
    }

    /**
     * Retorna estatísticas da assinatura
     */
    public String getEstatisticas() {
        return String.format(
            "Nível: %s | Pontos: %d | Pacotes: %d | HQs: %d | Média: %.1f HQs/pacote",
            nivelColecionador,
            getPontosDisponiveis(),
            pacotesRecebidos,
            hqsRecebidas,
            getMediaHQsPorPacote()
        );
    }

    @PrePersist
    @PreUpdate
    private void validateBeforeSave() {
        if (pontosAcumulados == null) {
            pontosAcumulados = 0;
        }
        if (pontosResgatados == null) {
            pontosResgatados = 0;
        }
        if (pacotesRecebidos == null) {
            pacotesRecebidos = 0;
        }
        if (hqsRecebidas == null) {
            hqsRecebidas = 0;
        }
        atualizarNivelColecionador();
    }
}
