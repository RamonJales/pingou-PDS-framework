package com.pds.pingou.security.user;

import com.pds.pingou.assinatura.Assinatura;
import com.pds.pingou.enums.StatusAssinatura;
import com.pds.pingou.framework.core.security.user.BaseUser;
import com.pds.pingou.framework.core.security.user.UserRole;
import com.pds.pingou.planos.Plano;
import jakarta.persistence.*;
import lombok.*;

/**
 * Entidade de usuário específica da aplicação Pingou.
 * 
 * Estende BaseUser do framework e adiciona relacionamentos específicos
 * do domínio, como assinatura.
 */
@Table(name = "users")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class User extends BaseUser {

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Assinatura assinatura;

    public User(String email, String nome, String sobrenome, String password, UserRole role) {
        super(email, nome, sobrenome, password, role);
    }

    /**
     * Retorna a assinatura ativa do usuário para um determinado plano.
     */
    public Assinatura getAssinaturaAtiva(Plano plano) {
        if (this.assinatura != null &&
                this.assinatura.getStatus() == StatusAssinatura.ATIVA &&
                this.assinatura.getPlano().equals(plano)) {
            return this.assinatura;
        }
        return null;
    }

    /**
     * Perfis especializados por tipo de plano (ex: perfil de torcedor para
     * FUTEBOL).
     * Nota: Em produção, mapear como JSONB ou tabela separada.
     */
    @Transient // Simplificação para este exercício
    private java.util.Map<com.pds.pingou.enums.TipoPlano, com.pds.pingou.security.user.profile.UserProfileStrategy> profiles = new java.util.HashMap<>();

    public void addProfile(com.pds.pingou.enums.TipoPlano tipo,
            com.pds.pingou.security.user.profile.UserProfileStrategy profile) {
        this.profiles.put(tipo, profile);
    }

    public com.pds.pingou.security.user.profile.UserProfileStrategy getProfile(com.pds.pingou.enums.TipoPlano tipo) {
        return this.profiles.get(tipo);
    }
}
