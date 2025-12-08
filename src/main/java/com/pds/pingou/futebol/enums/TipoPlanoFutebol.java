package com.pds.pingou.futebol.enums;

/**
 * Enumeração que representa os tipos de planos de assinatura de camisas de futebol.
 * 
 * Define a quantidade máxima de membros e características de cada tipo de plano.
 * 
 * @author Pingou Team
 * @version 1.0
 */
public enum TipoPlanoFutebol {
    
    INDIVIDUAL("Individual", "Plano para um único assinante", 1, 1.0),
    CASAL("Casal", "Plano para duas pessoas", 2, 1.8),
    FAMILIA_PEQUENA("Família Pequena", "Plano para até 3 pessoas", 3, 2.5),
    FAMILIA("Família", "Plano para até 5 pessoas", 5, 4.0),
    FAMILIA_GRANDE("Família Grande", "Plano para até 8 pessoas", 8, 6.0),
    TORCIDA("Torcida", "Plano para grupos de até 12 pessoas", 12, 9.0);

    private final String nome;
    private final String descricao;
    private final int maxMembros;
    private final double multiplicadorPreco;

    TipoPlanoFutebol(String nome, String descricao, int maxMembros, double multiplicadorPreco) {
        this.nome = nome;
        this.descricao = descricao;
        this.maxMembros = maxMembros;
        this.multiplicadorPreco = multiplicadorPreco;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public int getMaxMembros() {
        return maxMembros;
    }

    public double getMultiplicadorPreco() {
        return multiplicadorPreco;
    }

    /**
     * Verifica se permite adicionar mais membros.
     */
    public boolean permiteAdicionarMembro(int membrosAtuais) {
        return membrosAtuais < maxMembros;
    }

    /**
     * Verifica se é um plano familiar (mais de 1 membro).
     */
    public boolean isFamiliar() {
        return maxMembros > 1;
    }

    /**
     * Retorna o plano mais adequado para a quantidade de membros.
     */
    public static TipoPlanoFutebol getPlanoParaMembros(int quantidadeMembros) {
        for (TipoPlanoFutebol tipo : values()) {
            if (tipo.maxMembros >= quantidadeMembros) {
                return tipo;
            }
        }
        return TORCIDA; // Maior plano disponível
    }
}
