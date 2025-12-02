package com.pds.pingou.produto.hq;

public enum TipoHq {
    CLASSICA("Clássica"),
    MODERNA("Moderna");

    private final String descricao;

    TipoHq(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}