package com.pds.pingou.produto.hq;

public enum EditoraHq {
    MARVEL("Marvel Comics"),
    DC("DC Comics"),
    DARK_HORSE("Dark Horse Comics"),
    IMAGE("Image Comics"),
    PANINI("Panini Comics"),
    MYTHOS("Mythos Editora"),
    JBC("JBC (Japan Brazil Communication)"),
    OUTRAS("Outras");

    private final String descricao;

    EditoraHq(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}