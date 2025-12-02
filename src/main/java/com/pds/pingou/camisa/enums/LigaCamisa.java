package com.pds.pingou.camisa.enums;

/**
 * Enum que representa as ligas de futebol disponíveis no sistema.
 */
public enum LigaCamisa {
    BRASILEIRAO("Campeonato Brasileiro"),
    PREMIER_LEAGUE("Premier League"),
    LA_LIGA("La Liga"),
    SERIE_A("Serie A"),
    BUNDESLIGA("Bundesliga"),
    LIGUE_1("Ligue 1"),
    LIBERTADORES("Copa Libertadores"),
    CHAMPIONS_LEAGUE("Champions League"),
    COPA_DO_BRASIL("Copa do Brasil"),
    SELECAO_NACIONAL("Seleção Nacional"),
    OUTROS("Outros");
    
    private final String descricao;
    
    LigaCamisa(String descricao) {
        this.descricao = descricao;
    }
    
    public String getDescricao() {
        return descricao;
    }
}
