package com.pds.pingou.camisa.dto;

import com.pds.pingou.camisa.enums.TamanhoCamisa;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PerfilMorfologicoResponseDTO {
    private Long id;
    private Long userId;
    private String userName;
    private Double altura;
    private Double peso;
    private Double circunferenciaPeito;
    private Double circunferenciaCintura;
    private Double comprimentoTorso;
    private Double larguraOmbros;
    private TamanhoCamisa tamanhoRecomendado;
    private Double imc;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
