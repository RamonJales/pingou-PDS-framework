package com.pds.pingou.futebol.pacote.dto;

import com.pds.pingou.futebol.enums.TamanhoCamisa;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO para requisições de criação/atualização de item de pacote de futebol.
 */
@Getter
@Setter
public class ItemPacoteFutebolRequestDTO {

    @NotNull(message = "ID da camisa é obrigatório")
    private Long camisaId;

    @NotNull(message = "Tamanho é obrigatório")
    private TamanhoCamisa tamanho;

    /** ID do membro destino (para planos família) */
    private Long membroId;

    @Positive(message = "Quantidade deve ser positiva")
    private Integer quantidade = 1;

    /** Personalização opcional */
    private String nomePersonalizado;
    private Integer numeroPersonalizado;
    private Boolean versaoJogador;
    private String observacoes;
}
