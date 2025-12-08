package com.pds.pingou.futebol.pacote.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

/**
 * DTO para requisições de criação/atualização de pacote de futebol.
 */
@Getter
@Setter
public class PacoteFutebolRequestDTO {

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    private String descricao;

    @NotNull(message = "Data de entrega é obrigatória")
    private LocalDate dataEntrega;

    @NotNull(message = "Mês é obrigatório")
    private Integer mes;

    @NotNull(message = "Ano é obrigatório")
    private Integer ano;

    @NotNull(message = "ID do plano é obrigatório")
    private Long planoId;

    private String tematica;
    private String eventoRelacionado;
    private Boolean edicaoLimitada = false;
    private Integer quantidadeMaxima;

    /** Itens a serem incluídos no pacote */
    @Valid
    private List<ItemPacoteFutebolRequestDTO> itens;
}
