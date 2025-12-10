package com.pds.pingou.camisa.entity;

import com.pds.pingou.framework.core.entity.BasePackageItem;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "item_pacote_camisa")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemPacoteCamisa extends BasePackageItem<PacoteCamisa, Camisa> {
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pacote_id", nullable = false)
    private PacoteCamisa pacote;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "camisa_id", nullable = false)
    private Camisa camisa;
    
    @Column(nullable = false)
    private Boolean jaEnviada = false;
    
    @Column(length = 500)
    private String motivoSelecao;
    
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
    
    public void marcarComoEnviada() {
        this.jaEnviada = true;
    }
}
