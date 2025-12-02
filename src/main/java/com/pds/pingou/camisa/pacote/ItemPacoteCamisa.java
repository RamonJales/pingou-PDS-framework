package com.pds.pingou.camisa.pacote;

import com.pds.pingou.framework.core.entity.BasePackageItem;
import com.pds.pingou.camisa.Camisa;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Entidade que representa um item de pacote de camisas.
 * Estende BasePackageItem do framework genérico de assinaturas.
 */
@Entity
@Table(name = "item_pacote_camisa")
@Getter
@Setter
public class ItemPacoteCamisa extends BasePackageItem<PacoteCamisa, Camisa> {
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pacote_id", nullable = false)
    private PacoteCamisa pacote;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "camisa_id", nullable = false)
    private Camisa camisa;
    
    @Column(name = "tamanho_selecionado")
    private String tamanhoSelecionado;
    
    @Column(name = "numero_personalizacao")
    private Integer numeroPersonalizacao;
    
    @Column(name = "nome_personalizacao")
    private String nomePersonalizacao;
    
    public ItemPacoteCamisa() {}
    
    public ItemPacoteCamisa(PacoteCamisa pacote, Camisa camisa, Integer quantidade) {
        this.pacote = pacote;
        this.camisa = camisa;
        this.setQuantidade(quantidade);
    }
    
    @Override
    public PacoteCamisa getPackage() {
        return pacote;
    }
    
    @Override
    public void setPackage(PacoteCamisa pkg) {
        this.pacote = pkg;
    }
    
    @Override
    public Camisa getProduct() {
        return camisa;
    }
    
    @Override
    public void setProduct(Camisa product) {
        this.camisa = product;
    }
}
