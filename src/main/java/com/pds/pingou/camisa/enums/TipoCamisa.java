package com.pds.pingou.camisa.enums;

/**
 * Enum que representa os tipos de camisas de futebol disponíveis no sistema.
 */
public enum TipoCamisa {
    TITULAR("Titular"),
    RESERVA("Reserva"),
    TERCEIRO_UNIFORME("Terceiro Uniforme"),
    GOLEIRO("Goleiro"),
    RETRO("Retrô"),
    EDICAO_ESPECIAL("Edição Especial"),
    TREINO("Treino");
    
    private final String descricao;
    
    TipoCamisa(String descricao) {
        this.descricao = descricao;
    }
    
    public String getDescricao() {
        return descricao;
    }
}
