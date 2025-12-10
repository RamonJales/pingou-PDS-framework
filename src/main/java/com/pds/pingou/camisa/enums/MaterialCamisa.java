package com.pds.pingou.camisa.enums;

public enum MaterialCamisa {
    JOGADOR("Jogador", "Material premium usado pelos jogadores profissionais"),
    TORCEDOR("Torcedor", "Material confortável para torcedores");
    
    private final String descricao;
    private final String detalhes;
    
    MaterialCamisa(String descricao, String detalhes) {
        this.descricao = descricao;
        this.detalhes = detalhes;
    }
    
    public String getDescricao() {
        return descricao;
    }
    
    public String getDetalhes() {
        return detalhes;
    }
}
