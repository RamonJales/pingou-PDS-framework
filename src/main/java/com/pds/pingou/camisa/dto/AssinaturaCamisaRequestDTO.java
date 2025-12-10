package com.pds.pingou.camisa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssinaturaCamisaRequestDTO {
    private Long planoId;
    private String timeFavorito;
    private String timeRival;
}
