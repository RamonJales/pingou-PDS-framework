package com.pds.pingou.futebol.enums;

/**
 * Enumeração que representa os tipos de camisas de futebol disponíveis.
 * 
 * @author Pingou Team
 * @version 1.0
 */
public enum TipoCamisa {
    
    PRINCIPAL("Camisa Principal", "Uniforme 1 - Casa", 1.0),
    RESERVA("Camisa Reserva", "Uniforme 2 - Fora", 1.0),
    TERCEIRA("Terceira Camisa", "Uniforme 3 - Alternativo", 1.1),
    GOLEIRO("Camisa de Goleiro", "Uniforme do Goleiro", 1.15),
    TREINO("Camisa de Treino", "Camisa de treinamento", 0.7),
    RETRO("Camisa Retrô", "Edição comemorativa histórica", 1.3),
    EDICAO_ESPECIAL("Edição Especial", "Edição limitada comemorativa", 1.5);

    private final String nome;
    private final String descricao;
    private final double multiplicadorPreco;

    TipoCamisa(String nome, String descricao, double multiplicadorPreco) {
        this.nome = nome;
        this.descricao = descricao;
        this.multiplicadorPreco = multiplicadorPreco;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public double getMultiplicadorPreco() {
        return multiplicadorPreco;
    }

    /**
     * Verifica se é uma camisa de jogo (usada em partidas oficiais).
     */
    public boolean isCamisaDeJogo() {
        return this == PRINCIPAL || this == RESERVA || this == TERCEIRA || this == GOLEIRO;
    }

    /**
     * Verifica se é uma edição especial/colecionável.
     */
    public boolean isEdicaoEspecial() {
        return this == RETRO || this == EDICAO_ESPECIAL;
    }
}
