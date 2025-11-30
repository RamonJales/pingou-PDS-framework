package com.pds.pingou.framework.core.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe abstrata base para representar planos de assinatura em um sistema genérico.
 * 
 * Esta classe faz parte do framework de assinaturas e deve ser estendida por implementações
 * específicas de domínio. Ela encapsula os atributos e comportamentos comuns a todos os
 * tipos de planos de assinatura.
 * 
 * @param <PKG> Tipo do pacote (deve estender BasePackage)
 * 
 * @author Pingou Framework Team
 * @version 1.0
 * @since 1.0
 */
@MappedSuperclass
@Getter
@Setter
public abstract class BasePlan<PKG> {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Nome comercial do plano */
    @Column(nullable = false, unique = true)
    private String nome;

    /** Descrição detalhada do plano e seus benefícios */
    @Column(nullable = false, length = 2000)
    private String descricao;

    /** Preço do plano (mensal, anual, etc.) */
    @Column(nullable = false)
    private BigDecimal preco;
    
    /** Quantidade máxima de produtos que podem ser enviados por período */
    @Column(name = "max_produtos_por_periodo", nullable = false)
    private Integer maxProdutosPorPeriodo;
    
    /** Frequência das entregas (MENSAL, BIMESTRAL, TRIMESTRAL, etc.) */
    @Column(name = "frequencia_entrega", nullable = false)
    private String frequenciaEntrega = "MENSAL";
    
    /** Indica se o plano está ativo e disponível para assinatura */
    @Column(nullable = false)
    private Boolean ativo = true;
    
    /**
     * Retorna a lista de pacotes associados a este plano.
     * Deve ser implementado pelas subclasses.
     */
    public abstract List<PKG> getPackages();
    
    /**
     * Define a lista de pacotes associados a este plano.
     * Deve ser implementado pelas subclasses.
     */
    public abstract void setPackages(List<PKG> packages);
    
    /**
     * Adiciona um pacote a este plano.
     */
    public void addPackage(PKG pkg) {
        if (getPackages() == null) {
            setPackages(new ArrayList<>());
        }
        getPackages().add(pkg);
    }
    
    /**
     * Remove um pacote deste plano.
     */
    public void removePackage(PKG pkg) {
        if (getPackages() != null) {
            getPackages().remove(pkg);
        }
    }
    
    /**
     * Verifica se o plano está disponível para novas assinaturas.
     */
    public boolean isAvailable() {
        return ativo != null && ativo;
    }
    
    /**
     * Ativa o plano.
     */
    public void activate() {
        this.ativo = true;
    }
    
    /**
     * Desativa o plano.
     */
    public void deactivate() {
        this.ativo = false;
    }
    
    /**
     * Verifica se a quantidade de produtos é válida para este plano.
     */
    public boolean isValidProductQuantity(int quantity) {
        return quantity > 0 && quantity <= maxProdutosPorPeriodo;
    }
}
