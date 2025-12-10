package com.pds.pingou.hq.dto;

import com.pds.pingou.hq.enums.CategoriaHQ;
import com.pds.pingou.hq.enums.EditoraHQ;
import lombok.Data;

import java.util.Set;

/**
 * DTO para o quiz de onboarding/preferências do usuário
 */
@Data
public class QuizPreferenciasDTO {
    
    private Set<CategoriaHQ> categoriasFavoritas;
    private Set<EditoraHQ> editorasFavoritas;
    private Boolean prefereClassicas;
    private Boolean prefereModernas;
    private Boolean interesseEdicoesColecionador;
}
