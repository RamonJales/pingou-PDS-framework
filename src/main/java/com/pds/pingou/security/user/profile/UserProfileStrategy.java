package com.pds.pingou.security.user.profile;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.io.Serializable;
import java.util.Map;

/**
 * Interface para estratégias de perfil de usuário específicas por domínio.
 * 
 * Permite que o usuário tenha dados diferentes dependendo do plano
 * (ex: time do coração para Futebol, preferências de sabor para Cachaça).
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "@class")
public interface UserProfileStrategy extends Serializable {

    /**
     * Retorna um resumo legível das preferências do usuário.
     */
    String getSummary();

    /**
     * Valida se os dados do perfil estão completos e consistentes.
     */
    boolean isValid();

    /**
     * Retorna metadados do perfil como mapa.
     */
    Map<String, Object> getMetadata();
}
