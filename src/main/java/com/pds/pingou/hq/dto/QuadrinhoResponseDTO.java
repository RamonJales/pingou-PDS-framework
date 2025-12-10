package com.pds.pingou.hq.dto;

import com.pds.pingou.hq.enums.CategoriaHQ;
import com.pds.pingou.hq.enums.EditoraHQ;
import com.pds.pingou.hq.enums.TipoHQ;
import lombok.Data;

import java.math.BigDecimal;

/**
 * DTO para resposta de Quadrinho
 */
@Data
public class QuadrinhoResponseDTO {
    
    private Long id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private String urlImagem;
    private EditoraHQ editora;
    private TipoHQ tipoHQ;
    private CategoriaHQ categoria;
    private Integer pontosGanho;
    private Boolean edicaoColecionador;
    private Integer numeroEdicao;
    private Integer anoPublicacao;
    private String tituloSerie;
    private String autor;
    private String ilustrador;
    private String isbn;
    private Integer numeroPaginas;
    private Integer estoque;
    private Boolean ativo;
    private String shortDescription;
}
