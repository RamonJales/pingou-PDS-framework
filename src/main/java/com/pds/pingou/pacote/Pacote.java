package com.pds.pingou.pacote;

import com.pds.pingou.framework.core.entity.BasePackage;
import com.pds.pingou.planos.Plano;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidade que representa um pacote mensal de produtos no sistema Pingou.
 * 
 * Esta classe define um conjunto de produtos que será entregue aos assinantes
 * em um determinado mês/ano. Cada pacote está vinculado a um plano específico
 * e contém uma lista de itens (produtos) que serão enviados na data programada.
 * É fundamental para a logística de entregas mensais do sistema de assinatura.
 * 
 * Agora estende BasePackage do framework, reutilizando funcionalidades comuns.
 * 
 * @author Pingou Team
 * @version 2.0
 * @since 1.0
 */
@Entity
@Table(name = "pacotes")
@Getter
@Setter
public class Pacote extends BasePackage<Plano, ItemPacote> {
    
    /** Mês de referência do pacote (1-12) - Compatibilidade */
    @Column(nullable = false)
    private Integer mes;
    
    /** Plano ao qual este pacote pertence */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plano_id", nullable = false)
    private Plano plano;
    
    /** Lista de produtos incluídos neste pacote */
    @OneToMany(mappedBy = "pacote", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ItemPacote> itens = new ArrayList<>();
    
    public Pacote() {}
    
    /**
     * Construtor para criação de um pacote com informações essenciais.
     * 
     * @param nome Nome identificador do pacote
     * @param descricao Descrição detalhada do pacote
     * @param dataEntrega Data programada para entrega
     * @param mes Mês de referência (1-12)
     * @param ano Ano de referência
     * @param plano Plano ao qual este pacote pertence
     */
    public Pacote(String nome, String descricao, LocalDate dataEntrega, 
                  Integer mes, Integer ano, Plano plano) {
        this.setNome(nome);
        this.setDescricao(descricao);
        this.setDataEntrega(dataEntrega);
        this.setPeriodo(mes); // mes agora é mapeado para periodo da classe base
        this.mes = mes; // manter compatibilidade
        this.setAno(ano);
        this.plano = plano;
    }
    
    /**
     * Implementação do método abstrato do framework.
     */
    @Override
    public Plano getPlan() {
        return plano;
    }
    
    /**
     * Implementação do método abstrato do framework.
     */
    @Override
    public void setPlan(Plano plan) {
        this.plano = plan;
    }
    
    /**
     * Implementação do método abstrato do framework.
     */
    @Override
    public List<ItemPacote> getItems() {
        return itens;
    }
    
    /**
     * Implementação do método abstrato do framework.
     */
    @Override
    public void setItems(List<ItemPacote> items) {
        this.itens = items;
    }
    
    /**
     * Adiciona um item (produto) ao pacote.
     * 
     * @param item Item a ser adicionado ao pacote
     */
    public void adicionarItem(ItemPacote item) {
        addItem(item);
        item.setPacote(this);
    }
    
    /**
     * Remove um item (produto) do pacote.
     * 
     * @param item Item a ser removido do pacote
     */
    public void removerItem(ItemPacote item) {
        removeItem(item);
        item.setPacote(null);
    }
}