package com.pds.pingou.camisa.entity;

import com.pds.pingou.camisa.enums.MaterialCamisa;
import com.pds.pingou.camisa.enums.TamanhoCamisa;
import com.pds.pingou.camisa.enums.TipoCamisa;
import com.pds.pingou.framework.core.entity.BaseProduct;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "camisas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Camisa extends BaseProduct {
    
    @Column(nullable = false)
    private String time;
    
    @Column(nullable = false)
    private String timeRival;
    
    @Column(nullable = false)
    private Integer ano;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TamanhoCamisa tamanho;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoCamisa tipo;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MaterialCamisa material;
    
    @Column(length = 500)
    private String personalizacao;
    
    @Column(nullable = false)
    private Integer estoque = 0;
    
    @Override
    public String getShortDescription() {
        return String.format("%s - %s %d (%s)", time, tipo.getDescricao(), ano, tamanho.getDescricao());
    }
    
    @Override
    public String getCategory() {
        return "CAMISA_FUTEBOL";
    }
    
    public boolean isDeJogador() {
        return material == MaterialCamisa.JOGADOR;
    }
    
    public boolean isDeTorcedor() {
        return material == MaterialCamisa.TORCEDOR;
    }
    
    public boolean isDisponivelEmEstoque() {
        return estoque > 0;
    }
    
    public void decrementarEstoque() {
        if (estoque > 0) {
            estoque--;
        }
    }
    
    public void incrementarEstoque(int quantidade) {
        estoque += quantidade;
    }
}
