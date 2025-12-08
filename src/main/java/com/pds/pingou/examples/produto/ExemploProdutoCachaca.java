package com.pds.pingou.examples.produto;

import com.pds.pingou.produto.Produto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * EXEMPLO: Como criar um produto especializado de Cachaça.
 * 
 * Este é um exemplo de como estender a classe Produto do framework
 * para criar um tipo específico de produto para seu negócio.
 * 
 * Para usar em produção:
 * 1. Copie esta classe para seu pacote de domínio
 * 2. Ajuste os campos conforme sua necessidade
 * 3. Crie Repository, Service, Controller e DTOs correspondentes
 * 
 * @author Pingou Framework Team
 * @version 2.0
 */
@Entity
@Table(name = "exemplo_cachacas")
@Getter
@Setter
@NoArgsConstructor
public class ExemploProdutoCachaca extends Produto {

    /** Região de origem da cachaça (ex: "Minas Gerais", "Pernambuco") */
    @Column(name = "regiao")
    private String regiao;

    /** Teor alcoólico em porcentagem */
    @Column(name = "teor_alcoolico")
    private Double teorAlcoolico;

    /** Volume em mililitros */
    @Column(name = "volume_ml")
    private Integer volumeMl;

    /** Tipo de envelhecimento (ex: "Carvalho", "Amburana", "Branca") */
    @Column(name = "tipo_envelhecimento")
    private String tipoEnvelhecimento;

    /** Anos de envelhecimento */
    @Column(name = "anos_envelhecimento")
    private Integer anosEnvelhecimento;

    /**
     * Construtor com campos básicos.
     */
    public ExemploProdutoCachaca(String nome, String descricao, BigDecimal preco) {
        super(nome, descricao, preco);
        setCategoria("Cachaça");
    }

    /**
     * Construtor completo.
     */
    public ExemploProdutoCachaca(String nome, String descricao, BigDecimal preco,
                                  String marca, String regiao, Double teorAlcoolico,
                                  Integer volumeMl, String tipoEnvelhecimento) {
        super(nome, descricao, preco, "Cachaça", marca);
        this.regiao = regiao;
        this.teorAlcoolico = teorAlcoolico;
        this.volumeMl = volumeMl;
        this.tipoEnvelhecimento = tipoEnvelhecimento;
    }

    @Override
    public String getShortDescription() {
        return String.format("%s - %s (%s)", getNome(), getMarca(), tipoEnvelhecimento);
    }

    @Override
    public String getCategory() {
        return "Cachaça - " + (tipoEnvelhecimento != null ? tipoEnvelhecimento : "Branca");
    }
}
