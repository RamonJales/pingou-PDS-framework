package com.pds.pingou.camisa.enums;

public enum TipoCamisa {
    GOLEIRO("Goleiro"),
    TREINO("Treino"),
    SOCIAL("Social"),
    JOGO("Jogo");
    
    private final String descricao;
    
    TipoCamisa(String descricao) {
        this.descricao = descricao;
    }
    
    public String getDescricao() {
        return descricao;
    }
}
