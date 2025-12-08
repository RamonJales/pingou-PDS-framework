package com.pds.pingou.futebol.produto;

import com.pds.pingou.framework.core.entity.BaseProduct;
import com.pds.pingou.futebol.enums.Competicao;
import com.pds.pingou.futebol.enums.TipoCamisa;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Entidade que representa uma camisa de futebol no sistema de assinatura.
 * 
 * Estende BaseProduct do framework para herdar funcionalidades comuns de produtos,
 * adicionando atributos específicos para camisas de futebol como time, temporada,
 * tipo de camisa (casa/fora/goleiro), número, nome do jogador, etc.
 * 
 * Note: O tamanho NÃO é armazenado aqui - cada item do pacote terá seu tamanho
 * específico para suportar planos família onde cada membro tem seu tamanho.
 * 
 * @author Pingou Team
 * @version 1.0
 */
@Entity
@Table(name = "camisas_futebol")
@Getter
@Setter
public class CamisaFutebol extends BaseProduct {

    /** Nome do time/seleção (ex: "Flamengo", "Brasil", "Barcelona") */
    @Column(name = "time", nullable = false)
    private String time;

    /** País do time/liga */
    @Column(name = "pais")
    private String pais;

    /** Temporada da camisa (ex: "2024/2025") */
    @Column(name = "temporada", nullable = false)
    private String temporada;

    /** Tipo de camisa (Principal, Reserva, Goleiro, etc.) */
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_camisa", nullable = false)
    private TipoCamisa tipoCamisa;

    /** Competição principal do time */
    @Enumerated(EnumType.STRING)
    @Column(name = "competicao")
    private Competicao competicao;

    /** Marca/fabricante (Nike, Adidas, Puma, etc.) */
    @Column(name = "marca", nullable = false)
    private String marca;

    /** Número do jogador (opcional - null para camisa sem número) */
    @Column(name = "numero_jogador")
    private Integer numeroJogador;

    /** Nome do jogador (opcional - null para camisa sem nome) */
    @Column(name = "nome_jogador")
    private String nomeJogador;

    /** Se permite personalização (nome/número customizado) */
    @Column(name = "permite_personalizacao", nullable = false)
    private Boolean permitePersonalizacao = true;

    /** Custo adicional para personalização */
    @Column(name = "custo_personalizacao")
    private BigDecimal custoPersonalizacao = BigDecimal.ZERO;

    /** Material da camisa (ex: "Polyester Dri-FIT") */
    @Column(name = "material")
    private String material;

    /** Se é versão jogador (mais justa) ou torcedor */
    @Column(name = "versao_jogador", nullable = false)
    private Boolean versaoJogador = false;

    /** Se é camisa oficial licenciada */
    @Column(name = "oficial", nullable = false)
    private Boolean oficial = true;

    /** Quantidade em estoque (por tamanho seria tratado em outra entidade) */
    @Column(name = "estoque_total")
    private Integer estoqueTotal;

    public CamisaFutebol() {}

    /**
     * Construtor para criação de uma camisa de futebol.
     */
    public CamisaFutebol(String nome, String descricao, BigDecimal preco, 
                         String time, String temporada, TipoCamisa tipoCamisa, String marca) {
        setNome(nome);
        setDescricao(descricao);
        setPreco(preco);
        this.time = time;
        this.temporada = temporada;
        this.tipoCamisa = tipoCamisa;
        this.marca = marca;
    }

    @Override
    public String getShortDescription() {
        StringBuilder sb = new StringBuilder();
        sb.append(time).append(" - ").append(tipoCamisa.getNome());
        if (temporada != null) {
            sb.append(" ").append(temporada);
        }
        if (nomeJogador != null && numeroJogador != null) {
            sb.append(" #").append(numeroJogador).append(" ").append(nomeJogador);
        }
        return sb.toString();
    }

    @Override
    public String getCategory() {
        return "CAMISA_FUTEBOL";
    }

    /**
     * Calcula o preço final considerando tipo de camisa e personalização.
     */
    public BigDecimal getPrecoFinal(boolean comPersonalizacao) {
        BigDecimal precoBase = getPreco().multiply(BigDecimal.valueOf(tipoCamisa.getMultiplicadorPreco()));
        if (comPersonalizacao && permitePersonalizacao) {
            precoBase = precoBase.add(custoPersonalizacao);
        }
        if (versaoJogador) {
            precoBase = precoBase.multiply(BigDecimal.valueOf(1.3)); // 30% mais cara
        }
        return precoBase;
    }

    /**
     * Verifica se é uma camisa de seleção.
     */
    public boolean isSelecao() {
        return competicao != null && competicao.isSelecoes();
    }

    /**
     * Verifica se é edição especial/colecionável.
     */
    public boolean isEdicaoEspecial() {
        return tipoCamisa.isEdicaoEspecial();
    }

    /**
     * Retorna uma descrição completa para personalização.
     */
    public String getDescricaoCompleta() {
        StringBuilder sb = new StringBuilder();
        sb.append("Camisa ").append(time);
        sb.append(" - ").append(tipoCamisa.getNome());
        sb.append(" | Temporada ").append(temporada);
        sb.append(" | Marca: ").append(marca);
        if (versaoJogador) {
            sb.append(" | Versão Jogador");
        }
        return sb.toString();
    }
}
