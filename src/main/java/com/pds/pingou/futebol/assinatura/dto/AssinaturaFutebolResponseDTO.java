package com.pds.pingou.futebol.assinatura.dto;

import com.pds.pingou.enums.StatusAssinatura;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * DTO para respostas de assinatura de futebol.
 */
@Getter
@Setter
public class AssinaturaFutebolResponseDTO {

    private Long id;
    private Long userId;
    private String userEmail;
    private String userName;
    private Long planoId;
    private String planoNome;
    private BigDecimal planoPreco;
    private StatusAssinatura status;
    private String statusDescricao;
    private LocalDate dataInicio;
    private LocalDate dataExpiracao;
    private List<MembroAssinaturaResponseDTO> membros;
    private Integer quantidadeMembrosAtivos;
    private Integer maxMembros;
    private Boolean podeAdicionarMembro;
    private String timeFavoritoPrincipal;
    private String timesSecundarios;
    private Boolean aceitaTimesRivais;
    private String selecoesPreferidas;
    private Integer trocasTamanhoUsadas;
    private Integer trocasTamanhoDisponiveis;
    private Boolean podeTrocarTamanho;
    private String enderecoEntrega;
    private String observacoes;
    private String resumo;
}
