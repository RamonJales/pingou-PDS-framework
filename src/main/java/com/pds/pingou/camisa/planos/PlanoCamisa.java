package com.pds.pingou.camisa.planos;

import com.pds.pingou.framework.core.entity.BasePlan;
import com.pds.pingou.camisa.pacote.PacoteCamisa;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidade que representa um plano de assinatura de camisas de futebol.
 * Estende BasePlan do framework genérico de assinaturas.
 */
@Entity
@Table(name = "planos_camisa")
@Getter
@Setter
public class PlanoCamisa extends BasePlan<PacoteCamisa> {
    
    @OneToMany(mappedBy = "plano", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PacoteCamisa> pacotes = new ArrayList<>();
    
    @Column(name = "categoria_plano")
    private String categoriaPlano; // CLASSICOS, NACIONAIS, INTERNACIONAIS, RETRO
    
    @Column(name = "liga_foco")
    private String ligaFoco; // Liga específica do plano (opcional)
    
    @Column(name = "permite_personalizacao")
    private Boolean permitePersonalizacao = false;
    
    @Column(name = "prioridade_lancamentos")
    private Boolean prioridadeLancamentos = false;
    
    @Column(name = "desconto_loja")
    private BigDecimal descontoLoja; // Desconto em % para compras avulsas
    
    public PlanoCamisa() {}
    
    public PlanoCamisa(String nome, String descricao, BigDecimal preco, 
                       Integer maxCamisasPorMes, String categoriaPlano) {
        this.setNome(nome);
        this.setDescricao(descricao);
        this.setPreco(preco);
        this.setMaxProdutosPorPeriodo(maxCamisasPorMes);
        this.categoriaPlano = categoriaPlano;
        this.setFrequenciaEntrega("MENSAL");
    }
    
    @Override
    public List<PacoteCamisa> getPackages() {
        return pacotes;
    }
    
    @Override
    public void setPackages(List<PacoteCamisa> packages) {
        this.pacotes = packages;
    }
}
