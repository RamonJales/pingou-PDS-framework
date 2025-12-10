package com.pds.pingou.camisa.dto;

import com.pds.pingou.camisa.enums.MaterialCamisa;
import com.pds.pingou.camisa.enums.TamanhoCamisa;
import com.pds.pingou.camisa.enums.TipoCamisa;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CamisaRequestDTO {
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private String time;
    private String timeRival;
    private Integer ano;
    private TamanhoCamisa tamanho;
    private TipoCamisa tipo;
    private MaterialCamisa material;
    private String personalizacao;
    private Integer estoque;
}
