package com.pds.pingou.camisa;

import com.pds.pingou.framework.core.entity.BaseProduct;
import com.pds.pingou.camisa.enums.LigaCamisa;
import com.pds.pingou.camisa.enums.TipoCamisa;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Entidade que representa uma camisa de futebol no sistema de assinatura.
 * Estende BaseProduct do framework genérico de assinaturas.
 */
@Entity
@Table(name = "camisas")
@Getter
@Setter
public class Camisa extends BaseProduct {
    
    @Column(nullable = false)
    private String time;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LigaCamisa liga;
    
    @Column(name = "ano_temporada", nullable = false)
    private Integer anoTemporada;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_camisa", nullable = false)
    private TipoCamisa tipoCamisa;
    
    @Column(nullable = false)
    private String marca;
    
    @Column(name = "tamanhos_disponiveis")
    private String tamanhosDisponiveis; // Ex: "P,M,G,GG"
    
    @Column(name = "pais_time")
    private String paisTime;
    
    @Column(name = "material")
    private String material; // Ex: "Poliéster", "Dry-Fit"
    
    @Column(name = "numeracao_disponivel")
    private Boolean numeracaoDisponivel = false;
    
    @Column(name = "nome_personalizacao_disponivel")
    private Boolean nomePersonalizacaoDisponivel = false;
    
    @Column(name = "edicao_limitada")
    private Boolean edicaoLimitada = false;
    
    @Column(name = "estoque_quantidade")
    private Integer estoqueQuantidade = 0;
    
    public Camisa() {
        super();
    }
    
    public Camisa(String nome, String descricao, java.math.BigDecimal preco,
                  String time, LigaCamisa liga, Integer anoTemporada, 
                  TipoCamisa tipoCamisa, String marca) {
        this.setNome(nome);
        this.setDescricao(descricao);
        this.setPreco(preco);
        this.time = time;
        this.liga = liga;
        this.anoTemporada = anoTemporada;
        this.tipoCamisa = tipoCamisa;
        this.marca = marca;
    }
    
    @Override
    public String getShortDescription() {
        return String.format("%s - %s %d (%s)", 
            time, 
            tipoCamisa.getDescricao(), 
            anoTemporada,
            marca);
    }
    
    @Override
    public String getCategory() {
        return "CAMISA_FUTEBOL";
    }
    
    /**
     * Verifica se a camisa tem estoque disponível.
     */
    public boolean hasStock() {
        return estoqueQuantidade != null && estoqueQuantidade > 0;
    }
    
    /**
     * Reduz o estoque em uma quantidade específica.
     */
    public void decreaseStock(Integer quantidade) {
        if (estoqueQuantidade != null && estoqueQuantidade >= quantidade) {
            estoqueQuantidade -= quantidade;
        } else {
            throw new IllegalStateException("Estoque insuficiente");
        }
    }
    
    /**
     * Aumenta o estoque em uma quantidade específica.
     */
    public void increaseStock(Integer quantidade) {
        if (estoqueQuantidade == null) {
            estoqueQuantidade = quantidade;
        } else {
            estoqueQuantidade += quantidade;
        }
    }
}
