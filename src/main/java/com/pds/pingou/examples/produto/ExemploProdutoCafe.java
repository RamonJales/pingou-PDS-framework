package com.pds.pingou.examples.produto;

import com.pds.pingou.produto.Produto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * EXEMPLO: Como criar um produto especializado de Café.
 * 
 * Este é um exemplo de como estender a classe Produto do framework
 * para criar um clube de assinatura de cafés especiais.
 * 
 * @author Pingou Framework Team
 * @version 2.0
 */
@Entity
@Table(name = "exemplo_cafes")
@Getter
@Setter
@NoArgsConstructor
public class ExemploProdutoCafe extends Produto {

    /** Origem do café (ex: "Sul de Minas", "Cerrado Mineiro", "Chapada Diamantina") */
    @Column(name = "origem")
    private String origem;

    /** Variedade do grão (ex: "Arábica", "Robusta", "Bourbon") */
    @Column(name = "variedade")
    private String variedade;

    /** Tipo de torra (ex: "Clara", "Média", "Escura") */
    @Column(name = "torra")
    private String torra;

    /** Notas sensoriais (ex: "Chocolate, caramelo, frutas vermelhas") */
    @Column(name = "notas_sensoriais", length = 500)
    private String notasSensoriais;

    /** Altitude do cultivo em metros */
    @Column(name = "altitude")
    private Integer altitude;

    /** Pontuação SCA (Specialty Coffee Association) - de 0 a 100 */
    @Column(name = "pontuacao_sca")
    private Integer pontuacaoSCA;

    /** Peso em gramas */
    @Column(name = "peso_gramas")
    private Integer pesoGramas;

    /** Tipo de moagem (ex: "Grão", "Fina", "Média", "Grossa") */
    @Column(name = "moagem")
    private String moagem;

    /**
     * Construtor com campos básicos.
     */
    public ExemploProdutoCafe(String nome, String descricao, BigDecimal preco) {
        super(nome, descricao, preco);
        setCategoria("Café");
    }

    /**
     * Construtor completo.
     */
    public ExemploProdutoCafe(String nome, String descricao, BigDecimal preco,
                               String marca, String origem, String variedade,
                               String torra, Integer pontuacaoSCA) {
        super(nome, descricao, preco, "Café", marca);
        this.origem = origem;
        this.variedade = variedade;
        this.torra = torra;
        this.pontuacaoSCA = pontuacaoSCA;
    }

    @Override
    public String getShortDescription() {
        return String.format("%s - %s (%s)", getNome(), origem, torra);
    }

    @Override
    public String getCategory() {
        return "Café " + (variedade != null ? variedade : "Especial");
    }

    /**
     * Verifica se o café é considerado especial (pontuação SCA >= 80).
     */
    public boolean isEspecial() {
        return pontuacaoSCA != null && pontuacaoSCA >= 80;
    }
}
