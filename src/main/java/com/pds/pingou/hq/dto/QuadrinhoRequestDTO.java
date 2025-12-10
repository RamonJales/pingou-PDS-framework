package com.pds.pingou.hq.dto;

import com.pds.pingou.hq.enums.CategoriaHQ;
import com.pds.pingou.hq.enums.EditoraHQ;
import com.pds.pingou.hq.enums.TipoHQ;
import lombok.Data;

import java.math.BigDecimal;

/**
 * DTO para criar/atualizar Quadrinho
 */
@Data
public class QuadrinhoRequestDTO {
    
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private EditoraHQ editora;
    private TipoHQ tipoHQ;
    private CategoriaHQ categoria;
    private Boolean edicaoColecionador;
    private Integer numeroEdicao;
    private Integer anoPublicacao;
    private String tituloSerie;
    private String autor;
    private String ilustrador;
    private String isbn;
    private Integer numeroPaginas;
    private Integer estoque;
    private String urlImagem;
}
