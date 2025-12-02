package com.pds.pingou.pacote.hq;

import com.pds.pingou.framework.core.entity.BasePackage;
import com.pds.pingou.planos.hq.PlanoHq;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidade que representa um pacote mensal de HQs.
 *
 * @author Pingou Team
 * @version 1.0
 * @since 2.0
 */
@Entity
@Table(name = "pacotes_hq")
@Getter
@Setter
public class PacoteHq extends BasePackage<PlanoHq, ItemPacoteHq> {

    @Column(nullable = false)
    private Integer mes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plano_id", nullable = false)
    private PlanoHq plano;

    @OneToMany(mappedBy = "pacote", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ItemPacoteHq> itens = new ArrayList<>();

    /** Tema do pacote (ex: "Vingadores Clássicos", "Novos Heróis") */
    @Column(name = "tema_mes")
    private String temaMes;

    public PacoteHq() {}

    public PacoteHq(String nome, String descricao, LocalDate dataEntrega,
                    Integer mes, Integer ano, PlanoHq plano, String temaMes) {
        this.setNome(nome);
        this.setDescricao(descricao);
        this.setDataEntrega(dataEntrega);
        this.setPeriodo(mes);
        this.mes = mes;
        this.setAno(ano);
        this.plano = plano;
        this.temaMes = temaMes;
    }

    @Override
    public PlanoHq getPlan() {
        return plano;
    }

    @Override
    public void setPlan(PlanoHq plan) {
        this.plano = plan;
    }

    @Override
    public List<ItemPacoteHq> getItems() {
        return itens;
    }

    @Override
    public void setItems(List<ItemPacoteHq> items) {
        this.itens = items;
    }

    public void adicionarItem(ItemPacoteHq item) {
        addItem(item);
        item.setPacote(this);
    }

    public void removerItem(ItemPacoteHq item) {
        removeItem(item);
        item.setPacote(null);
    }
}