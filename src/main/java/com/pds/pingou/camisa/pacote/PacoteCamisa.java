package com.pds.pingou.camisa.pacote;

import com.pds.pingou.framework.core.entity.BasePackage;
import com.pds.pingou.camisa.planos.PlanoCamisa;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidade que representa um pacote de camisas de futebol.
 * Estende BasePackage do framework genérico de assinaturas.
 */
@Entity
@Table(name = "pacotes_camisa")
@Getter
@Setter
public class PacoteCamisa extends BasePackage<PlanoCamisa, ItemPacoteCamisa> {
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plano_id", nullable = false)
    private PlanoCamisa plano;
    
    @OneToMany(mappedBy = "pacote", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ItemPacoteCamisa> itens = new ArrayList<>();
    
    @Column(name = "tema_mes")
    private String temaMes; // Ex: "Clássicos Brasileiros", "Champions 2024"
    
    @Column(name = "edicao_especial")
    private Boolean edicaoEspecial = false;
    
    public PacoteCamisa() {}
    
    public PacoteCamisa(String nome, String descricao, LocalDate dataEntrega,
                        Integer mes, Integer ano, PlanoCamisa plano, String temaMes) {
        this.setNome(nome);
        this.setDescricao(descricao);
        this.setDataEntrega(dataEntrega);
        this.setPeriodo(mes);
        this.setAno(ano);
        this.plano = plano;
        this.temaMes = temaMes;
    }
    
    @Override
    public PlanoCamisa getPlan() {
        return plano;
    }
    
    @Override
    public void setPlan(PlanoCamisa plan) {
        this.plano = plan;
    }
    
    @Override
    public List<ItemPacoteCamisa> getItems() {
        return itens;
    }
    
    @Override
    public void setItems(List<ItemPacoteCamisa> items) {
        this.itens = items;
    }
}
