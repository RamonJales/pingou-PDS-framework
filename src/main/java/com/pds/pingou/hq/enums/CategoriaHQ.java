package com.pds.pingou.hq.enums;

/**
 * Categorias/Gêneros de quadrinhos
 */
public enum CategoriaHQ {
    SUPER_HEROI("Super-Herói"),
    MANGA("Mangá"),
    INDEPENDENTE("Independente"),
    SUSPENSE("Suspense/Terror"),
    FICCAO_CIENTIFICA("Ficção Científica"),
    FANTASIA("Fantasia"),
    AVENTURA("Aventura"),
    COMEDIA("Comédia"),
    BIOGRAFIA("Biografia"),
    WESTERN("Western");

    private final String descricao;

    CategoriaHQ(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
