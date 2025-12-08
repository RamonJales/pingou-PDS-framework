package com.pds.pingou.futebol.pacote.dto;

import com.pds.pingou.futebol.enums.TamanhoCamisa;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO para respostas de item de pacote de futebol.
 */
@Getter
@Setter
public class ItemPacoteFutebolResponseDTO {

    private Long id;
    private Long camisaId;
    private String camisaNome;
    private String camisaTime;
    private String camisaTipoCamisa;
    private String camisaTemporada;
    private TamanhoCamisa tamanho;
    private String tamanhoDescricao;
    private String tamanhoSigla;
    private Boolean infantil;
    private Long membroId;
    private String membroNome;
    private String nomePersonalizado;
    private Integer numeroPersonalizado;
    private Boolean temPersonalizacao;
    private Boolean versaoJogador;
    private Integer quantidade;
    private String observacoes;
    private String statusItem;
    private String descricaoParaEtiqueta;
    private String descricaoCurta;
}
