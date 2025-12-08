package com.pds.pingou.futebol.assinatura.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * DTO para requisições de criação de assinatura de futebol.
 */
@Getter
@Setter
public class AssinaturaFutebolRequestDTO {

    @NotNull(message = "ID do plano é obrigatório")
    private Long planoId;

    /** Lista de membros da assinatura (obrigatório pelo menos o titular) */
    @Valid
    @NotNull(message = "Pelo menos um membro é obrigatório")
    private List<MembroAssinaturaRequestDTO> membros;

    private String timeFavoritoPrincipal;
    private String timesSecundarios;
    private Boolean aceitaTimesRivais = false;
    private String selecoesPreferidas;
    private String enderecoEntrega;
    private String observacoes;
}
