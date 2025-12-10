package com.pds.pingou.hq.entity;

import com.pds.pingou.framework.core.entity.BasePackage;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidade que representa um pacote mensal de HQs
 * Estende BasePackage do framework para herdar funcionalidades básicas
 */
@Entity
@Table(name = "pacotes_hq")
@Getter
@Setter
public class PacoteHQ extends BasePackage<PlanoHQ, ItemPacoteHQ> {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plano_id", nullable = false)
    private PlanoHQ plano;

    @OneToMany(mappedBy = "pacote", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<ItemPacoteHQ> itens = new ArrayList<>();

    @Column(name = "tema_mes", length = 200)
    private String temaMes;

    @Column(name = "curado_para_user_id")
    private Long curadoParaUserId;

    @Column(name = "pontos_totais")
    private Integer pontosTotais;

    @Column(name = "quantidade_classicas")
    private Integer quantidadeClassicas;

    @Column(name = "quantidade_modernas")
    private Integer quantidadeModernas;

    /**
     * Construtor padrão
     */
    public PacoteHQ() {
        super();
        this.itens = new ArrayList<>();
        this.pontosTotais = 0;
        this.quantidadeClassicas = 0;
        this.quantidadeModernas = 0;
    }

    /**
     * Construtor com dados essenciais
     */
    public PacoteHQ(String nome, String descricao, LocalDate dataEntrega,
                    Integer periodo, Integer ano, PlanoHQ plano) {
        this();
        this.setNome(nome);
        this.setDescricao(descricao);
        this.setDataEntrega(dataEntrega);
        this.setPeriodo(periodo);
        this.setAno(ano);
        this.plano = plano;
    }

    @Override
    public PlanoHQ getPlan() {
        return plano;
    }

    @Override
    public void setPlan(PlanoHQ plan) {
        this.plano = plan;
    }

    @Override
    public List<ItemPacoteHQ> getItems() {
        return itens;
    }

    @Override
    public void setItems(List<ItemPacoteHQ> items) {
        this.itens = items;
        recalcularEstatisticas();
    }

    /**
     * Adiciona um item ao pacote
     */
    @Override
    public void addItem(ItemPacoteHQ item) {
        super.addItem(item);
        item.setPacote(this);
        recalcularEstatisticas();
    }

    /**
     * Remove um item do pacote
     */
    @Override
    public void removeItem(ItemPacoteHQ item) {
        super.removeItem(item);
        item.setPacote(null);
        recalcularEstatisticas();
    }

    /**
     * Recalcula as estatísticas do pacote (pontos e quantidades)
     */
    public void recalcularEstatisticas() {
        this.pontosTotais = itens.stream()
            .mapToInt(item -> item.getQuadrinho().getPontosGanho() * item.getQuantidade())
            .sum();

        this.quantidadeClassicas = (int) itens.stream()
            .filter(item -> item.getQuadrinho().isClassica())
            .mapToInt(ItemPacoteHQ::getQuantidade)
            .sum();

        this.quantidadeModernas = (int) itens.stream()
            .filter(item -> item.getQuadrinho().isModerna())
            .mapToInt(ItemPacoteHQ::getQuantidade)
            .sum();

        // Adiciona pontos bônus do plano
        if (plano != null && plano.getPontosBonusMensal() != null) {
            this.pontosTotais += plano.getPontosBonusMensal();
        }
    }

    /**
     * Verifica se o pacote está completo de acordo com o plano
     */
    public boolean isCompleto() {
        if (plano == null) {
            return false;
        }

        int totalItens = itens.stream()
            .mapToInt(ItemPacoteHQ::getQuantidade)
            .sum();

        return totalItens == plano.getMaxProdutosPorPeriodo();
    }

    /**
     * Verifica se o pacote respeita os percentuais do plano
     */
    public boolean respeitaPercentuais() {
        if (plano == null || !isCompleto()) {
            return false;
        }

        int qtdEsperadaClassicas = plano.calcularQuantidadeClassicas();
        int qtdEsperadaModernas = plano.calcularQuantidadeModernas();

        return quantidadeClassicas == qtdEsperadaClassicas && 
               quantidadeModernas == qtdEsperadaModernas;
    }

    /**
     * Verifica se o pacote foi curado para um usuário específico
     */
    public boolean isCuradoParaUsuario(Long userId) {
        return curadoParaUserId != null && curadoParaUserId.equals(userId);
    }

    /**
     * Lista todos os quadrinhos do pacote
     */
    public List<Quadrinho> getQuadrinhos() {
        return itens.stream()
            .map(ItemPacoteHQ::getQuadrinho)
            .toList();
    }

    @PrePersist
    @PreUpdate
    private void validateBeforeSave() {
        recalcularEstatisticas();
    }
}
