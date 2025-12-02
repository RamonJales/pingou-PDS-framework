package com.pds.pingou.camisa.enums;

/**
 * Enum que representa os tamanhos disponíveis para camisas.
 */
public enum TamanhosCamisa {
    PP("PP"),
    P("P"),
    M("M"),
    G("G"),
    GG("GG"),
    XG("XG"),
    XXG("XXG");
    
    private final String descricao;
    
    TamanhosCamisa(String descricao) {
        this.descricao = descricao;
    }
    
    public String getDescricao() {
        return descricao;
    }
}
