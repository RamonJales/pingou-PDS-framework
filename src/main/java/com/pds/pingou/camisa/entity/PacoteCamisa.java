package com.pds.pingou.camisa.entity;

import com.pds.pingou.framework.core.entity.BasePackage;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pacotes_camisa")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PacoteCamisa extends BasePackage<PlanoCamisa, ItemPacoteCamisa> {
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plano_id", nullable = false)
    private PlanoCamisa plano;
    
    @OneToMany(mappedBy = "pacote", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemPacoteCamisa> itens = new ArrayList<>();
    
    @Column(nullable = false)
    private Boolean curado = false;
    
    @Column(length = 1000)
    private String observacoesCuradoria;
    
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
    
    public void marcarComoCurado(String observacoes) {
        this.curado = true;
        this.observacoesCuradoria = observacoes;
    }
}
