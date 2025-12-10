package com.pds.pingou.hq.enums;

/**
 * Enumeração das editoras de quadrinhos disponíveis no sistema
 */
public enum EditoraHQ {
    MARVEL("Marvel Comics"),
    DC("DC Comics"),
    IMAGE("Image Comics"),
    DARK_HORSE("Dark Horse Comics"),
    IDW("IDW Publishing"),
    BOOM("Boom! Studios"),
    VERTIGO("Vertigo Comics"),
    OUTROS("Outras Editoras");

    private final String descricao;

    EditoraHQ(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
