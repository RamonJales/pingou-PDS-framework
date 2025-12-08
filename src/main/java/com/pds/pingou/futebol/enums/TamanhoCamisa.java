package com.pds.pingou.futebol.enums;

/**
 * Enumeração que representa os tamanhos disponíveis para camisas de futebol.
 * 
 * Inclui tamanhos adulto e infantil para suportar planos família.
 * 
 * @author Pingou Team
 * @version 1.0
 */
public enum TamanhoCamisa {
    
    // Tamanhos Infantis
    INF_2("Infantil 2 anos", "2", true),
    INF_4("Infantil 4 anos", "4", true),
    INF_6("Infantil 6 anos", "6", true),
    INF_8("Infantil 8 anos", "8", true),
    INF_10("Infantil 10 anos", "10", true),
    INF_12("Infantil 12 anos", "12", true),
    INF_14("Infantil 14 anos", "14", true),
    
    // Tamanhos Adultos
    PP("Extra Pequeno", "PP", false),
    P("Pequeno", "P", false),
    M("Médio", "M", false),
    G("Grande", "G", false),
    GG("Extra Grande", "GG", false),
    XGG("Extra Extra Grande", "XGG", false),
    XXGG("Triple Extra Grande", "XXGG", false);

    private final String descricao;
    private final String sigla;
    private final boolean infantil;

    TamanhoCamisa(String descricao, String sigla, boolean infantil) {
        this.descricao = descricao;
        this.sigla = sigla;
        this.infantil = infantil;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getSigla() {
        return sigla;
    }

    public boolean isInfantil() {
        return infantil;
    }

    public boolean isAdulto() {
        return !infantil;
    }

    /**
     * Retorna todos os tamanhos infantis disponíveis.
     */
    public static TamanhoCamisa[] getTamanhosInfantis() {
        return java.util.Arrays.stream(values())
                .filter(TamanhoCamisa::isInfantil)
                .toArray(TamanhoCamisa[]::new);
    }

    /**
     * Retorna todos os tamanhos adultos disponíveis.
     */
    public static TamanhoCamisa[] getTamanhosAdultos() {
        return java.util.Arrays.stream(values())
                .filter(TamanhoCamisa::isAdulto)
                .toArray(TamanhoCamisa[]::new);
    }
}
