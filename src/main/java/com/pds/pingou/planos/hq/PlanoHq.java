package com.pds.pingou.planos.hq;

import com.pds.pingou.framework.core.entity.BasePlan;
import com.pds.pingou.pacote.hq.PacoteHq;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidade que representa um plano de assinatura de HQs.
 *
 * Os planos podem ser:
 * - CLASSICO: apenas HQs clássicas
 * - MODERNO: apenas HQs modernas
 * - MISTO: combinação de clássicas e modernas
 *
 * @author Pingou Team
 * @version 1.0
 * @since 2.0
 */
@Entity
@Table(name = "planos_hq")
@Getter
@Setter
public class PlanoHq extends BasePlan<PacoteHq> {

    @OneToMany(mappedBy = "plano", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PacoteHq> pacotes = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_plano", nullable = false)
    private TipoPlanoHq tipoPlano;

    @Column(name = "percentual_classicas")
    private Integer percentualClassicas;

    @Column(name = "percentual_modernas")
    private Integer percentualModernas;

    @Column(name = "multiplicador_pontos")
    private BigDecimal multiplicadorPontos = BigDecimal.ONE;

    public PlanoHq() {}

    public PlanoHq(String nome, String descricao, BigDecimal preco,
                   Integer maxHqsPorMes, TipoPlanoHq tipoPlano) {
        this.setNome(nome);
        this.setDescricao(descricao);
        this.setPreco(preco);
        this.setMaxProdutosPorPeriodo(maxHqsPorMes);
        this.tipoPlano = tipoPlano;

        // Configurar percentuais baseado no tipo
        configurarPercentuais();
    }

    @Override
    public List<PacoteHq> getPackages() {
        return pacotes;
    }

    @Override
    public void setPackages(List<PacoteHq> packages) {
        this.pacotes = packages;
    }

    private void configurarPercentuais() {
        switch (tipoPlano) {
            case CLASSICO:
                this.percentualClassicas = 100;
                this.percentualModernas = 0;
                break;
            case MODERNO:
                this.percentualClassicas = 0;
                this.percentualModernas = 100;
                break;
            case MISTO:
                this.percentualClassicas = 50;
                this.percentualModernas = 50;
                break;
        }
    }

    public boolean aceitaClassicas() {
        return percentualClassicas != null && percentualClassicas > 0;
    }

    public boolean aceitaModernas() {
        return percentualModernas != null && percentualModernas > 0;
    }

    // Compatibilidade
    public Integer getMaxHqsPorMes() {
        return getMaxProdutosPorPeriodo();
    }

    public void setMaxHqsPorMes(Integer max) {
        setMaxProdutosPorPeriodo(max);
    }
}