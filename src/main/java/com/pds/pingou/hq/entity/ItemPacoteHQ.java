package com.pds.pingou.hq.entity;

import com.pds.pingou.framework.core.entity.BasePackageItem;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Entidade que representa um item dentro de um pacote de HQs
 * Estende BasePackageItem do framework para herdar funcionalidades básicas
 */
@Entity
@Table(name = "item_pacote_hq")
@Getter
@Setter
public class ItemPacoteHQ extends BasePackageItem<PacoteHQ, Quadrinho> {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pacote_id", nullable = false)
    private PacoteHQ pacote;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "quadrinho_id", nullable = false)
    private Quadrinho quadrinho;

    @Column(name = "destaque")
    private Boolean destaque = false;

    @Column(name = "motivo_escolha", length = 500)
    private String motivoEscolha;

    /**
     * Construtor padrão
     */
    public ItemPacoteHQ() {
        super();
    }

    /**
     * Construtor com dados essenciais
     */
    public ItemPacoteHQ(PacoteHQ pacote, Quadrinho quadrinho, Integer quantidade) {
        this();
        this.pacote = pacote;
        this.quadrinho = quadrinho;
        this.setQuantidade(quantidade);
    }

    @Override
    public PacoteHQ getPackage() {
        return pacote;
    }

    @Override
    public void setPackage(PacoteHQ pkg) {
        this.pacote = pkg;
    }

    @Override
    public Quadrinho getProduct() {
        return quadrinho;
    }

    @Override
    public void setProduct(Quadrinho product) {
        this.quadrinho = product;
    }

    /**
     * Calcula os pontos deste item
     */
    public int calcularPontos() {
        if (quadrinho == null) {
            return 0;
        }
        return quadrinho.getPontosGanho() * getQuantidade();
    }

    /**
     * Verifica se este item é destaque no pacote
     */
    public boolean isDestaque() {
        return Boolean.TRUE.equals(destaque);
    }

    /**
     * Marca este item como destaque
     */
    public void marcarComoDestaque(String motivo) {
        this.destaque = true;
        this.motivoEscolha = motivo;
    }

    /**
     * Define o motivo da escolha deste quadrinho
     */
    public void setMotivoEscolha(String motivo) {
        this.motivoEscolha = motivo;
    }

    @PrePersist
    @PreUpdate
    private void validateBeforeSave() {
        if (quadrinho == null) {
            throw new IllegalStateException("ItemPacoteHQ deve ter um quadrinho associado");
        }
        if (pacote == null) {
            throw new IllegalStateException("ItemPacoteHQ deve estar associado a um pacote");
        }
    }
}
