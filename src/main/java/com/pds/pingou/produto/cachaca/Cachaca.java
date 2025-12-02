package com.pds.pingou.produto.cachaca;

import com.pds.pingou.produto.Produto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Entidade que representa uma cachaça no sistema Pingou.
 *
 * Esta classe especializa a classe Produto para incluir atributos específicos
 * da cachaça brasileira, como região de origem, informações do alambique,
 * características de envelhecimento e notas de degustação. É utilizada para
 * catalogar as cachaças artesanais que fazem parte dos pacotes de assinatura.
 *
 * @author Pingou Team
 * @version 1.0
 * @since 1.0
 */
@Entity
@Table(name = "cachacas")
@Getter
@Setter
public class Cachaca extends Produto {

    /** Região brasileira de origem da cachaça */
    @Column(nullable = false)
    private String regiao;

    /** Teor alcoólico da cachaça (ex: 38.5%) */
    @Column(name = "teor_alcoolico", nullable = false)
    private BigDecimal teorAlcoolico;

    /** Volume da garrafa em mililitros */
    @Column(nullable = false)
    private Integer volume; // em ML

    /** Classificação da cachaça (branca, ouro, envelhecida, etc.) */
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_cachaca", nullable = false)
    private TipoCachaca tipoCachaca;

    /** Tipo de envelhecimento aplicado */
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_envelhecimento")
    private TipoEnvelhecimento tipoEnvelhecimento;

    /** Tempo de envelhecimento em meses */
    @Column(name = "tempo_envelhecimento_meses")
    private Integer tempoEnvelhecimentoMeses;

    /** Ano de produção da cachaça */
    @Column(name = "ano_producao")
    private Integer anoProducao;

    /**
     * Construtor padrão.
     */
    public Cachaca() {
        super();
    }

    /**
     * Construtor para criação de uma cachaça com informações essenciais.
     *
     * @param nome Nome comercial da cachaça
     * @param descricao Descrição detalhada da cachaça
     * @param preco Preço da cachaça em reais
     * @param regiao Região brasileira de origem
     * @param teorAlcoolico Teor alcoólico da cachaça
     * @param volume Volume da garrafa em mililitros
     * @param tipoCachaca Classificação da cachaça
     */
    public Cachaca(String nome, String descricao, BigDecimal preco, String regiao,
                   BigDecimal teorAlcoolico, Integer volume, TipoCachaca tipoCachaca) {
        super(nome, descricao, preco);
        this.regiao = regiao;
        this.teorAlcoolico = teorAlcoolico;
        this.volume = volume;
        this.tipoCachaca = tipoCachaca;
    }

    /**
     * Retorna uma descrição curta da cachaça.
     * Implementação do método abstrato de BaseProduct.
     */
    @Override
    public String getShortDescription() {
        return String.format("%s - %s, %dml, %.1f%%",
                getNome(),
                regiao,
                volume,
                teorAlcoolico
        );
    }

    /**
     * Retorna a categoria do produto.
     * Implementação do método abstrato de BaseProduct.
     */
    @Override
    public String getCategory() {
        return "Cachaça";
    }
}