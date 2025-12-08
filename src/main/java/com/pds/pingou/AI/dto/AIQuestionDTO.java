package com.pds.pingou.AI.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AIQuestionDTO {
    
    @NotBlank(message = "A pergunta não pode estar vazia")
    @Size(min = 3, max = 1000, message = "A pergunta deve ter entre 3 e 1000 caracteres")
    private String question;
    
    /**
     * Tipo de contexto para seleção do prompt provider.
     * Valores válidos: "futebol", "camisa10", "pingou", "cachaca"
     * Default: "futebol" para o módulo de camisas de futebol
     */
    private String contextType = "futebol";
}

