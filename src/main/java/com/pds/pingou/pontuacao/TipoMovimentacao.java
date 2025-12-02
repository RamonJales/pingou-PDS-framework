// src/main/java/com/pds/pingou/pontuacao/TipoMovimentacao.java
package com.pds.pingou.pontuacao;

public enum TipoMovimentacao {
    GANHO("Ganho de pontos"),
    TROCA("Troca por HQ"),
    BONUS("Bônus especial"),
    EXPIRACAO("Expiração de pontos");

    private final String descricao;

    TipoMovimentacao(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}