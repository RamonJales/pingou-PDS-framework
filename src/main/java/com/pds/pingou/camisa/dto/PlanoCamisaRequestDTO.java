package com.pds.pingou.camisa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanoCamisaRequestDTO {
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private Integer duracaoMeses;
    private Boolean ativo;
    private Boolean permiteCompartilhamento;
    private Integer maxParticipantes;
    private Integer camisasPorMes;
}
