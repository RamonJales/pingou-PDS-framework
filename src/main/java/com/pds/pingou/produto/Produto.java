package com.pds.pingou.produto;

import com.pds.pingou.framework.core.entity.BaseProduct;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Entidade que representa um produto genérico no sistema de assinatura.
 * 
 * Esta classe pode ser usada diretamente para produtos simples ou estendida
 * para criar produtos especializados com atributos adicionais.
 * 
 * Exemplos de especialização:
 * - Bebidas (cachaça, vinho, cerveja artesanal)
 * - Alimentos (café, chocolate, queijos)
 * - Cosméticos (produtos de beleza, perfumes)
 * - Livros e revistas
 * 
 * @author Pingou Framework Team
 * @version 2.0
 * @since 1.0
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "produtos")
@Getter
@Setter
public class Produto extends BaseProduct {

    /** Categoria do produto (ex: "Bebida", "Alimento", "Livro") */
    @Column(name = "categoria")
    private String categoria;

    /** Marca ou fabricante do produto */
    @Column(name = "marca")
    private String marca;

    /** Código SKU ou identificador único do produto */
    @Column(name = "sku", unique = true)
    private String sku;

    /** Unidade de medida (ex: "ml", "g", "unidade") */
    @Column(name = "unidade_medida")
    private String unidadeMedida;

    /** Quantidade na unidade de medida */
    @Column(name = "quantidade_unidade")
    private Integer quantidadeUnidade;

    public Produto() {}
    
    /**
     * Construtor para criação de um produto com informações básicas.
     * 
     * @param nome Nome comercial do produto
     * @param descricao Descrição detalhada do produto
     * @param preco Preço do produto em reais
     */
    public Produto(String nome, String descricao, BigDecimal preco) {
        setNome(nome);
        setDescricao(descricao);
        setPreco(preco);
    }

    /**
     * Construtor completo para criação de um produto.
     * 
     * @param nome Nome comercial do produto
     * @param descricao Descrição detalhada do produto
     * @param preco Preço do produto em reais
     * @param categoria Categoria do produto
     * @param marca Marca ou fabricante
     */
    public Produto(String nome, String descricao, BigDecimal preco, String categoria, String marca) {
        this(nome, descricao, preco);
        this.categoria = categoria;
        this.marca = marca;
    }

    @Override
    public String getShortDescription() {
        if (marca != null && !marca.isBlank()) {
            return String.format("%s - %s", getNome(), marca);
        }
        return getNome();
    }

    @Override
    public String getCategory() {
        return categoria != null ? categoria : "Geral";
    }
}