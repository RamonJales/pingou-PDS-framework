package com.pds.pingou.hq.entity;

import com.pds.pingou.framework.core.entity.BasePlan;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidade que representa um plano de assinatura de HQs
 * Estende BasePlan do framework para herdar funcionalidades básicas
 */
@Entity
@Table(name = "planos_hq")
@Getter
@Setter
public class PlanoHQ extends BasePlan<PacoteHQ> {

    @OneToMany(mappedBy = "plano", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PacoteHQ> pacotes = new ArrayList<>();

    @Column(name = "percentual_classicas", nullable = false)
    private Integer percentualClassicas;

    @Column(name = "percentual_modernas", nullable = false)
    private Integer percentualModernas;

    @Column(name = "pontos_bonus_mensal", nullable = false)
    private Integer pontosBonusMensal;

    @Column(name = "inclui_edicoes_colecionador")
    private Boolean incluiEdicoesColecionador = false;

    @Column(name = "nivel_curadoria")
    private String nivelCuradoria; // BASICO, INTERMEDIARIO, PREMIUM

    /**
     * Construtor padrão
     */
    public PlanoHQ() {
        super();
        this.pacotes = new ArrayList<>();
    }

    /**
     * Construtor completo
     */
    public PlanoHQ(String nome, String descricao, BigDecimal preco,
                   Integer maxProdutosPorPeriodo, Integer percentualClassicas,
                   Integer percentualModernas, Integer pontosBonusMensal) {
        super();
        this.setNome(nome);
        this.setDescricao(descricao);
        this.setPreco(preco);
        this.setMaxProdutosPorPeriodo(maxProdutosPorPeriodo);
        this.percentualClassicas = percentualClassicas;
        this.percentualModernas = percentualModernas;
        this.pontosBonusMensal = pontosBonusMensal;
        validatePercentuais();
    }

    @Override
    public List<PacoteHQ> getPackages() {
        return pacotes;
    }

    @Override
    public void setPackages(List<PacoteHQ> packages) {
        this.pacotes = packages;
    }

    /**
     * Valida se os percentuais de clássicas e modernas somam 100%
     */
    public void validatePercentuais() {
        if (percentualClassicas == null || percentualModernas == null) {
            throw new IllegalArgumentException("Percentuais não podem ser nulos");
        }
        
        if (percentualClassicas + percentualModernas != 100) {
            throw new IllegalArgumentException(
                "A soma dos percentuais de clássicas e modernas deve ser 100%. " +
                "Recebido: " + percentualClassicas + "% + " + percentualModernas + "%"
            );
        }

        if (percentualClassicas < 0 || percentualModernas < 0) {
            throw new IllegalArgumentException("Percentuais não podem ser negativos");
        }
    }

    /**
     * Calcula quantas HQs clássicas devem estar no pacote
     */
    public int calcularQuantidadeClassicas() {
        int total = getMaxProdutosPorPeriodo();
        return Math.round((total * percentualClassicas) / 100f);
    }

    /**
     * Calcula quantas HQs modernas devem estar no pacote
     */
    public int calcularQuantidadeModernas() {
        int total = getMaxProdutosPorPeriodo();
        return total - calcularQuantidadeClassicas();
    }

    /**
     * Calcula o total de pontos que o usuário ganhará neste plano
     * considerando o bônus mensal
     */
    public int calcularPontosTotais(List<Quadrinho> quadrinhos) {
        int pontosQuadrinhos = quadrinhos.stream()
            .mapToInt(Quadrinho::getPontosGanho)
            .sum();
        
        return pontosQuadrinhos + pontosBonusMensal;
    }

    /**
     * Verifica se este plano é focado em clássicas (>50%)
     */
    public boolean isFocadoEmClassicas() {
        return percentualClassicas > 50;
    }

    /**
     * Verifica se este plano é focado em modernas (>50%)
     */
    public boolean isFocadoEmModernas() {
        return percentualModernas > 50;
    }

    /**
     * Verifica se este plano é equilibrado (50/50)
     */
    public boolean isEquilibrado() {
        return percentualClassicas == 50 && percentualModernas == 50;
    }

    @PrePersist
    @PreUpdate
    private void validateBeforeSave() {
        validatePercentuais();
        
        if (pontosBonusMensal == null || pontosBonusMensal < 0) {
            throw new IllegalArgumentException("Pontos bônus mensal deve ser maior ou igual a zero");
        }
    }
}
