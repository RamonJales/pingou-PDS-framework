package com.pds.pingou.hq.enums;

/**
 * Classificação temporal dos quadrinhos
 */
public enum TipoHQ {
    CLASSICA("Clássica", "Quadrinhos publicados até 1999", 100),
    MODERNA("Moderna", "Quadrinhos publicados a partir de 2000", 50);

    private final String nome;
    private final String descricao;
    private final int pontosBase;

    TipoHQ(String nome, String descricao, int pontosBase) {
        this.nome = nome;
        this.descricao = descricao;
        this.pontosBase = pontosBase;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public int getPontosBase() {
        return pontosBase;
    }
}
