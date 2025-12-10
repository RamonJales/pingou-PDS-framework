package com.pds.pingou.hq.entity;

import com.pds.pingou.framework.core.entity.BaseProduct;
import com.pds.pingou.hq.enums.CategoriaHQ;
import com.pds.pingou.hq.enums.EditoraHQ;
import com.pds.pingou.hq.enums.TipoHQ;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Entidade que representa um quadrinho no sistema
 * Estende BaseProduct do framework para herdar funcionalidades básicas
 */
@Entity
@Table(name = "quadrinhos")
@Getter
@Setter
public class Quadrinho extends BaseProduct {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EditoraHQ editora;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_hq", nullable = false)
    private TipoHQ tipoHQ;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoriaHQ categoria;

    @Column(name = "pontos_ganho", nullable = false)
    private Integer pontosGanho;

    @Column(name = "edicao_colecionador", nullable = false)
    private Boolean edicaoColecionador = false;

    @Column(name = "numero_edicao")
    private Integer numeroEdicao;

    @Column(name = "ano_publicacao")
    private Integer anoPublicacao;

    @Column(name = "titulo_serie", nullable = false)
    private String tituloSerie;

    @Column(name = "autor")
    private String autor;

    @Column(name = "ilustrador")
    private String ilustrador;

    @Column(name = "isbn")
    private String isbn;

    @Column(name = "numero_paginas")
    private Integer numeroPaginas;

    @Column(name = "estoque", nullable = false)
    private Integer estoque = 0;

    /**
     * Construtor padrão
     */
    public Quadrinho() {
        super();
    }

    /**
     * Construtor completo
     */
    public Quadrinho(String nome, String descricao, BigDecimal preco,
                     EditoraHQ editora, TipoHQ tipoHQ, CategoriaHQ categoria,
                     Boolean edicaoColecionador, String tituloSerie) {
        super();
        this.setNome(nome);
        this.setDescricao(descricao);
        this.setPreco(preco);
        this.editora = editora;
        this.tipoHQ = tipoHQ;
        this.categoria = categoria;
        this.edicaoColecionador = edicaoColecionador != null ? edicaoColecionador : false;
        this.tituloSerie = tituloSerie;
        this.calcularPontosGanho();
    }

    /**
     * Calcula os pontos ganhos baseado no tipo e se é edição de colecionador
     */
    public void calcularPontosGanho() {
        int pontos = tipoHQ.getPontosBase();
        
        // Edições de colecionador valem o dobro de pontos
        if (Boolean.TRUE.equals(edicaoColecionador)) {
            pontos *= 2;
        }
        
        this.pontosGanho = pontos;
    }

    /**
     * Implementação obrigatória do framework
     */
    @Override
    public String getShortDescription() {
        return String.format("%s - %s #%d (%s)",
                editora.getDescricao(),
                tituloSerie,
                numeroEdicao != null ? numeroEdicao : 0,
                tipoHQ.getNome());
    }

    /**
     * Implementação obrigatória do framework
     */
    @Override
    public String getCategory() {
        return categoria.name();
    }

    /**
     * Verifica se há estoque disponível
     */
    public boolean temEstoque() {
        return estoque != null && estoque > 0;
    }

    /**
     * Diminui o estoque em uma unidade
     */
    public void decrementarEstoque() {
        if (temEstoque()) {
            this.estoque--;
        } else {
            throw new IllegalStateException("Estoque insuficiente para o quadrinho: " + getNome());
        }
    }

    /**
     * Aumenta o estoque
     */
    public void incrementarEstoque(int quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero");
        }
        this.estoque += quantidade;
    }

    /**
     * Override para recalcular pontos quando o tipo ou edição de colecionador mudar
     */
    public void setTipoHQ(TipoHQ tipoHQ) {
        this.tipoHQ = tipoHQ;
        calcularPontosGanho();
    }

    public void setEdicaoColecionador(Boolean edicaoColecionador) {
        this.edicaoColecionador = edicaoColecionador;
        calcularPontosGanho();
    }

    /**
     * Verifica se é uma HQ clássica
     */
    public boolean isClassica() {
        return TipoHQ.CLASSICA.equals(this.tipoHQ);
    }

    /**
     * Verifica se é uma HQ moderna
     */
    public boolean isModerna() {
        return TipoHQ.MODERNA.equals(this.tipoHQ);
    }

    @PrePersist
    @PreUpdate
    private void prePersistAndUpdate() {
        calcularPontosGanho();
    }
}
