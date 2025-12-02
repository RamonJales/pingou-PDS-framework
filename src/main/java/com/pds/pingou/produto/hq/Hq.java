package com.pds.pingou.produto.hq;

import com.pds.pingou.produto.Produto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Entidade que representa uma HQ no sistema.
 *
 * Esta classe especializa a classe Produto para incluir atributos específicos
 * de histórias em quadrinhos, como editora, categoria, ano de publicação, etc.
 *
 * @author Pingou Team
 * @version 1.0
 * @since 2.0
 */
@Entity
@Table(name = "hqs")
@Getter
@Setter
public class Hq extends Produto {

    /** Tipo da HQ (clássica ou moderna) */
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_hq", nullable = false)
    private TipoHq tipoHq;

    /** Categoria da HQ */
    @Enumerated(EnumType.STRING)
    @Column(name = "categoria", nullable = false)
    private CategoriaHq categoria;

    /** Editora responsável pela publicação */
    @Enumerated(EnumType.STRING)
    @Column(name = "editora", nullable = false)
    private EditoraHq editora;

    /** Autor(es) da HQ */
    @Column(nullable = false)
    private String autor;

    /** Desenhista(s) da HQ */
    @Column(nullable = false)
    private String desenhista;

    /** Ano de publicação */
    @Column(name = "ano_publicacao", nullable = false)
    private Integer anoPublicacao;

    /** Número da edição */
    @Column(name = "numero_edicao")
    private Integer numeroEdicao;

    /** Número de páginas */
    @Column(name = "numero_paginas")
    private Integer numeroPaginas;

    /** ISBN da publicação */
    @Column(length = 20)
    private String isbn;

    /** Indica se é uma edição de colecionador */
    @Column(name = "edicao_colecionador")
    private Boolean edicaoColecionador = false;

    /** Sinopse da HQ */
    @Column(length = 2000)
    private String sinopse;

    /** Pontos que o usuário ganha ao receber esta HQ */
    @Column(name = "pontos_ganho")
    private Integer pontosGanho = 10;

    public Hq() {
        super();
    }

    public Hq(String nome, String descricao, BigDecimal preco, TipoHq tipoHq,
              CategoriaHq categoria, EditoraHq editora, String autor, String desenhista,
              Integer anoPublicacao) {
        super(nome, descricao, preco);
        this.tipoHq = tipoHq;
        this.categoria = categoria;
        this.editora = editora;
        this.autor = autor;
        this.desenhista = desenhista;
        this.anoPublicacao = anoPublicacao;
    }

    @Override
    public String getShortDescription() {
        return String.format("%s - %s (%d)",
                getNome(),
                categoria.getDescricao(),
                anoPublicacao);
    }

    @Override
    public String getCategory() {
        return "HQ";
    }

    public boolean isClassica() {
        return tipoHq == TipoHq.CLASSICA;
    }

    public boolean isModerna() {
        return tipoHq == TipoHq.MODERNA;
    }
}