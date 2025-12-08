package com.pds.pingou.futebol.enums;

/**
 * Enumeração que representa as principais competições de futebol.
 * 
 * Usado para categorizar camisas por competição/liga.
 * 
 * @author Pingou Team
 * @version 1.0
 */
public enum Competicao {
    
    // Brasileirão
    BRASILEIRAO_SERIE_A("Campeonato Brasileiro Série A", "Brasil", true),
    BRASILEIRAO_SERIE_B("Campeonato Brasileiro Série B", "Brasil", true),
    
    // Europa
    PREMIER_LEAGUE("Premier League", "Inglaterra", true),
    LA_LIGA("La Liga", "Espanha", true),
    SERIE_A_ITALIA("Serie A", "Itália", true),
    BUNDESLIGA("Bundesliga", "Alemanha", true),
    LIGUE_1("Ligue 1", "França", true),
    
    // Competições Continentais
    CHAMPIONS_LEAGUE("UEFA Champions League", "Europa", true),
    EUROPA_LEAGUE("UEFA Europa League", "Europa", true),
    LIBERTADORES("Copa Libertadores", "América do Sul", true),
    SULAMERICANA("Copa Sul-Americana", "América do Sul", true),
    
    // Seleções
    COPA_DO_MUNDO("Copa do Mundo FIFA", "Internacional", false),
    COPA_AMERICA("Copa América", "América do Sul", false),
    EUROCOPA("Eurocopa", "Europa", false);

    private final String nome;
    private final String regiao;
    private final boolean clubes;

    Competicao(String nome, String regiao, boolean clubes) {
        this.nome = nome;
        this.regiao = regiao;
        this.clubes = clubes;
    }

    public String getNome() {
        return nome;
    }

    public String getRegiao() {
        return regiao;
    }

    public boolean isClubes() {
        return clubes;
    }

    public boolean isSelecoes() {
        return !clubes;
    }
}
