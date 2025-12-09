package com.pds.pingou.futebol.user;

import com.pds.pingou.security.user.profile.UserProfileStrategy;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Perfil de usuário específico para o plano de Camisas de Futebol.
 * 
 * Armazena preferências críticas para a estratégia "Anti-Rival",
 * como time do coração e lista de restrições (times odiados).
 */
@Getter
@Setter
@NoArgsConstructor
public class CamisaUserProfile implements UserProfileStrategy {

    /** Time do coração (prioridade máxima na curadoria) */
    private String timeCoracao;

    /** Tamanho da camisa (P, M, G, GG, XG) */
    private String tamanhoCamisa;

    /**
     * Lista de times restritos (rivais).
     * PRODUTOS DESTES TIMES NUNCA SERÃO ENVIADOS.
     */
    private List<String> timesRestritos = new ArrayList<>();

    /** Seleções nacionais de interesse */
    private List<String> selecoesInteresse = new ArrayList<>();

    public CamisaUserProfile(String timeCoracao, String tamanhoCamisa) {
        this.timeCoracao = timeCoracao;
        this.tamanhoCamisa = tamanhoCamisa;
    }

    public void adicionarRestricao(String time) {
        if (!timesRestritos.contains(time) && !time.equalsIgnoreCase(timeCoracao)) {
            timesRestritos.add(time);
        }
    }

    @Override
    public String getSummary() {
        return String.format("Torcedor do %s, veste %s. Odeia: %s",
                timeCoracao, tamanhoCamisa, String.join(", ", timesRestritos));
    }

    @Override
    public boolean isValid() {
        return timeCoracao != null && !timeCoracao.isBlank() &&
                tamanhoCamisa != null && !tamanhoCamisa.isBlank();
    }

    @Override
    public Map<String, Object> getMetadata() {
        Map<String, Object> meta = new HashMap<>();
        meta.put("time", timeCoracao);
        meta.put("tamanho", tamanhoCamisa);
        meta.put("restricoes_count", timesRestritos.size());
        return meta;
    }
}
