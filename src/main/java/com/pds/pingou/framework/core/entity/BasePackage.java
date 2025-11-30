package com.pds.pingou.framework.core.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe abstrata base para representar pacotes de produtos em um sistema de assinatura genérico.
 * 
 * Esta classe faz parte do framework de assinaturas e deve ser estendida por implementações
 * específicas de domínio. Ela encapsula os atributos e comportamentos comuns a todos os
 * pacotes que serão enviados aos assinantes.
 * 
 * @param <PLN> Tipo do plano (deve estender BasePlan)
 * @param <ITM> Tipo do item do pacote (deve estender BasePackageItem)
 * 
 * @author Pingou Framework Team
 * @version 1.0
 * @since 1.0
 */
@MappedSuperclass
@Getter
@Setter
public abstract class BasePackage<PLN, ITM> {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /** Nome identificador do pacote */
    @Column(nullable = false)
    private String nome;
    
    /** Descrição detalhada do pacote */
    @Column(length = 1000)
    private String descricao;
    
    /** Data programada para entrega do pacote */
    @Column(name = "data_entrega", nullable = false)
    private LocalDate dataEntrega;
    
    /** Período de referência do pacote (ex: mês) */
    @Column(nullable = false)
    private Integer periodo;
    
    /** Ano de referência do pacote */
    @Column(nullable = false)
    private Integer ano;
    
    /** Indica se o pacote está ativo no sistema */
    @Column(nullable = false)
    private Boolean ativo = true;
    
    /**
     * Retorna o plano associado a este pacote.
     * Deve ser implementado pelas subclasses.
     */
    public abstract PLN getPlan();
    
    /**
     * Define o plano associado a este pacote.
     * Deve ser implementado pelas subclasses.
     */
    public abstract void setPlan(PLN plan);
    
    /**
     * Retorna a lista de itens incluídos neste pacote.
     * Deve ser implementado pelas subclasses.
     */
    public abstract List<ITM> getItems();
    
    /**
     * Define a lista de itens incluídos neste pacote.
     * Deve ser implementado pelas subclasses.
     */
    public abstract void setItems(List<ITM> items);
    
    /**
     * Adiciona um item a este pacote.
     */
    public void addItem(ITM item) {
        if (getItems() == null) {
            setItems(new ArrayList<>());
        }
        getItems().add(item);
    }
    
    /**
     * Remove um item deste pacote.
     */
    public void removeItem(ITM item) {
        if (getItems() != null) {
            getItems().remove(item);
        }
    }
    
    /**
     * Verifica se o pacote está disponível para envio.
     */
    public boolean isAvailable() {
        return ativo != null && ativo;
    }
    
    /**
     * Verifica se a data de entrega já passou.
     */
    public boolean isOverdue() {
        return dataEntrega != null && dataEntrega.isBefore(LocalDate.now());
    }
    
    /**
     * Verifica se o pacote pode ser entregue hoje.
     */
    public boolean isDeliveryDateToday() {
        return dataEntrega != null && dataEntrega.isEqual(LocalDate.now());
    }
    
    /**
     * Ativa o pacote.
     */
    public void activate() {
        this.ativo = true;
    }
    
    /**
     * Desativa o pacote.
     */
    public void deactivate() {
        this.ativo = false;
    }
    
    /**
     * Obtém a quantidade total de itens no pacote.
     */
    public int getTotalItems() {
        return getItems() != null ? getItems().size() : 0;
    }
}
