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
}
