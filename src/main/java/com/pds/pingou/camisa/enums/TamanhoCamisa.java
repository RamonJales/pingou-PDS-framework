package com.pds.pingou.camisa.enums;

public enum TamanhoCamisa {
    PP("PP", 60, 45),
    P("P", 65, 48),
    M("M", 70, 52),
    G("G", 75, 56),
    GG("GG", 80, 60),
    XG("XG", 85, 64),
    XXG("XXG", 90, 68);
    
    private final String descricao;
    private final int comprimento; // cm
    private final int largura; // cm
    
    TamanhoCamisa(String descricao, int comprimento, int largura) {
        this.descricao = descricao;
        this.comprimento = comprimento;
        this.largura = largura;
    }
    
    public String getDescricao() {
        return descricao;
    }
    
    public int getComprimento() {
        return comprimento;
    }
    
    public int getLargura() {
        return largura;
    }
}
