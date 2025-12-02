package com.pds.pingou.planos.hq;

public enum TipoPlanoHq {
    CLASSICO("Clássico", "Apenas HQs clássicas"),
    MODERNO("Moderno", "Apenas HQs modernas"),
    MISTO("Misto", "Combinação de HQs clássicas e modernas");

    private final String nome;
    private final String descricao;

    TipoPlanoHq(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }
}