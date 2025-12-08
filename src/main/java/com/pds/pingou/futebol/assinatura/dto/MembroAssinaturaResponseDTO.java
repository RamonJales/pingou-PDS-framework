package com.pds.pingou.futebol.assinatura.dto;

import com.pds.pingou.futebol.enums.TamanhoCamisa;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO para respostas de membro de assinatura.
 */
@Getter
@Setter
public class MembroAssinaturaResponseDTO {

    private Long id;
    private String nome;
    private TamanhoCamisa tamanho;
    private String tamanhoDescricao;
    private String tamanhosigla;
    private Boolean infantil;
    private String timeFavorito;
    private String jogadorFavorito;
    private Integer numeroFavorito;
    private Boolean titular;
    private Integer ordem;
    private Boolean ativo;
    private String email;
    private String descricaoParaEnvio;
}
