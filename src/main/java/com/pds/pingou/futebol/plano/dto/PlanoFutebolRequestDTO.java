package com.pds.pingou.futebol.plano.dto;

import com.pds.pingou.futebol.enums.TipoPlanoFutebol;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * DTO para requisições de criação/atualização de plano de futebol.
 */
@Getter
@Setter
public class PlanoFutebolRequestDTO {

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @NotBlank(message = "Descrição é obrigatória")
    private String descricao;

    @NotNull(message = "Preço base é obrigatório")
    @Positive(message = "Preço deve ser positivo")
    private BigDecimal preco;

    @NotNull(message = "Tipo de plano é obrigatório")
    private TipoPlanoFutebol tipoPlano;

    @NotNull(message = "Quantidade de camisas por membro é obrigatória")
    @Positive(message = "Quantidade deve ser positiva")
    private Integer camisasPorMembro;

    private String frequenciaEntrega = "MENSAL";
    private Boolean personalizacaoInclusa = false;
    private Boolean prioridadeEdicaoLimitada = false;
    private Boolean incluiSelecoes = false;
    private Integer trocasTamanhoAno = 2;
    private Boolean permiteEscolhaTime = true;
    private BigDecimal descontoCamisaAdicional = BigDecimal.ZERO;
    private Boolean freteGratis = true;
}
