package com.pds.pingou.pacote;

import com.pds.pingou.framework.core.entity.BasePackageItem;
import com.pds.pingou.produto.Produto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Entidade que representa um item específico dentro de um pacote.
 * 
 * Esta classe faz a ligação entre um pacote e os produtos que o compõem,
 * permitindo especificar a quantidade de cada produto e observações
 * particulares. É uma entidade de relacionamento many-to-many entre
 * Pacote e Produto, com atributos adicionais.
 * 
 * Agora estende BasePackageItem do framework, reutilizando funcionalidades comuns.
 * 
 * @author Pingou Team
 * @version 2.0
 * @since 1.0
 */
@Entity
@Table(name = "item_pacote")
@Getter
@Setter
public class ItemPacote extends BasePackageItem<Pacote, Produto> {
    
    /** Pacote ao qual este item pertence */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pacote_id", nullable = false)
    private Pacote pacote;
    
    /** Produto incluído neste item */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;
    
    public ItemPacote() {}
    
    /**
     * Construtor para criação de um item de pacote.
     * 
     * @param pacote Pacote ao qual este item pertence
     * @param produto Produto incluído neste item
     * @param quantidade Quantidade do produto no pacote
     */
    public ItemPacote(Pacote pacote, Produto produto, Integer quantidade) {
        this.pacote = pacote;
        this.produto = produto;
        this.setQuantidade(quantidade);
    }
    
    /**
     * Implementação do método abstrato do framework.
     */
    @Override
    public Pacote getPackage() {
        return pacote;
    }
    
    /**
     * Implementação do método abstrato do framework.
     */
    @Override
    public void setPackage(Pacote pkg) {
        this.pacote = pkg;
    }
    
    /**
     * Implementação do método abstrato do framework.
     */
    @Override
    public Produto getProduct() {
        return produto;
    }
    
    /**
     * Implementação do método abstrato do framework.
     */
    @Override
    public void setProduct(Produto product) {
        this.produto = product;
    }
}