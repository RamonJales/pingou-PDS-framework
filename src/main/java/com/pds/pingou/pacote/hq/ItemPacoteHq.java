package com.pds.pingou.pacote.hq;

import com.pds.pingou.framework.core.entity.BasePackageItem;
import com.pds.pingou.produto.hq.Hq;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Entidade que representa um item dentro de um pacote de HQ.
 *
 * @author Pingou Team
 * @version 1.0
 * @since 2.0
 */
@Entity
@Table(name = "item_pacote_hq")
@Getter
@Setter
public class ItemPacoteHq extends BasePackageItem<PacoteHq, Hq> {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pacote_id", nullable = false)
    private PacoteHq pacote;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hq_id", nullable = false)
    private Hq hq;

    /** Indica se é uma edição especial neste pacote */
    @Column(name = "edicao_especial")
    private Boolean edicaoEspecial = false;

    public ItemPacoteHq() {}

    public ItemPacoteHq(PacoteHq pacote, Hq hq, Integer quantidade) {
        this.pacote = pacote;
        this.hq = hq;
        this.setQuantidade(quantidade);
    }

    @Override
    public PacoteHq getPackage() {
        return pacote;
    }

    @Override
    public void setPackage(PacoteHq pkg) {
        this.pacote = pkg;
    }

    @Override
    public Hq getProduct() {
        return hq;
    }

    @Override
    public void setProduct(Hq product) {
        this.hq = product;
    }
}