package com.pds.pingou.examples.produto;

import com.pds.pingou.produto.Produto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * EXEMPLO: Como criar um produto especializado de Vinho.
 * 
 * Este é um exemplo de como estender a classe Produto do framework
 * para criar um clube de assinatura de vinhos.
 * 
 * @author Pingou Framework Team
 * @version 2.0
 */
@Entity
@Table(name = "exemplo_vinhos")
@Getter
@Setter
@NoArgsConstructor
public class ExemploProdutoVinho extends Produto {

    /** Tipo de uva (ex: "Cabernet Sauvignon", "Merlot", "Chardonnay") */
    @Column(name = "uva")
    private String uva;

    /** Ano da safra */
    @Column(name = "safra")
    private Integer safra;

    /** País de origem */
    @Column(name = "pais")
    private String pais;

    /** Região vinícola (ex: "Vale dos Vinhedos", "Mendoza", "Bordeaux") */
    @Column(name = "regiao")
    private String regiao;

    /** Tipo do vinho (ex: "Tinto", "Branco", "Rosé", "Espumante") */
    @Column(name = "tipo_vinho")
    private String tipoVinho;

    /** Teor alcoólico em porcentagem */
    @Column(name = "teor_alcoolico")
    private Double teorAlcoolico;

    /** Volume em mililitros */
    @Column(name = "volume_ml")
    private Integer volumeMl;

    /** Harmonização sugerida */
    @Column(name = "harmonizacao", length = 500)
    private String harmonizacao;

    /**
     * Construtor com campos básicos.
     */
    public ExemploProdutoVinho(String nome, String descricao, BigDecimal preco) {
        super(nome, descricao, preco);
        setCategoria("Vinho");
    }

    /**
     * Construtor completo.
     */
    public ExemploProdutoVinho(String nome, String descricao, BigDecimal preco,
                                String marca, String uva, Integer safra,
                                String pais, String tipoVinho) {
        super(nome, descricao, preco, "Vinho", marca);
        this.uva = uva;
        this.safra = safra;
        this.pais = pais;
        this.tipoVinho = tipoVinho;
    }

    @Override
    public String getShortDescription() {
        return String.format("%s %d - %s", getNome(), safra != null ? safra : 0, uva);
    }

    @Override
    public String getCategory() {
        return "Vinho " + (tipoVinho != null ? tipoVinho : "");
    }
}
