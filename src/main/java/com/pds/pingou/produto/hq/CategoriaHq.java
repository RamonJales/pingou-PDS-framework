package com.pds.pingou.produto.hq;

public enum CategoriaHq {
    SUPER_HEROI("Super-Herói"),
    MARVEL("Marvel"),
    DC("DC Comics"),
    INDEPENDENTE("Independente"),
    MANGA("Mangá"),
    EUROPEIA("Europeia");

    private final String descricao;

    CategoriaHq(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}