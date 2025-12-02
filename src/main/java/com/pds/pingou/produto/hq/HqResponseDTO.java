package com.pds.pingou.produto.hq;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class HqResponseDTO {
    private Long id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private String urlImagem;
    private Boolean ativo;
    private TipoHq tipoHq;
    private CategoriaHq categoria;
    private EditoraHq editora;
    private String autor;
    private String desenhista;
    private Integer anoPublicacao;
    private Integer numeroEdicao;
    private Integer numeroPaginas;
    private String isbn;
    private Boolean edicaoColecionador;
    private String sinopse;
    private Integer pontosGanho;
    private String shortDescription;
}