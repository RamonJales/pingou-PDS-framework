package com.pds.pingou.camisa.dto;

import com.pds.pingou.camisa.enums.TamanhoCamisa;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PerfilMorfologicoRequestDTO {
    private Double altura;
    private Double peso;
    private Double circunferenciaPeito;
    private Double circunferenciaCintura;
    private Double comprimentoTorso;
    private Double larguraOmbros;
}
