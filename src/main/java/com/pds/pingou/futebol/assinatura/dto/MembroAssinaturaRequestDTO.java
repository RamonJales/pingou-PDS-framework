package com.pds.pingou.futebol.assinatura.dto;

import com.pds.pingou.futebol.enums.TamanhoCamisa;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO para requisições de criação/atualização de membro de assinatura.
 */
@Getter
@Setter
public class MembroAssinaturaRequestDTO {

    @NotBlank(message = "Nome do membro é obrigatório")
    private String nome;

    @NotNull(message = "Tamanho é obrigatório")
    private TamanhoCamisa tamanho;

    private String timeFavorito;
    private String jogadorFavorito;
    private Integer numeroFavorito;
    private Boolean titular = false;
    private String email;
}
