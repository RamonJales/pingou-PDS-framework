package com.pds.pingou.assinatura.hq;

import com.pds.pingou.framework.core.entity.BaseSubscription;
import com.pds.pingou.framework.core.enums.SubscriptionStatus;
import com.pds.pingou.planos.hq.PlanoHq;
import com.pds.pingou.security.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Entidade que representa uma assinatura de HQ.
 *
 * @author Pingou Team
 * @version 1.0
 * @since 2.0
 */
@Entity
@Table(name = "assinaturas_hq")
@Getter
@Setter
public class AssinaturaHq extends BaseSubscription<User, PlanoHq> {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plano_id", nullable = false)
    private PlanoHq plano;

    /** Preferências de categorias do usuário */
    @Column(name = "preferencias_categorias")
    private String preferenciasCategorias;

    /** Preferências de editoras do usuário */
    @Column(name = "preferencias_editoras")
    private String preferenciasEditoras;

    public AssinaturaHq() {}

    public AssinaturaHq(User user, PlanoHq plano) {
        this.user = user;
        this.plano = plano;
        this.setStatus(SubscriptionStatus.ATIVA);
        this.setDataInicio(LocalDate.now());
    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public PlanoHq getPlan() {
        return plano;
    }

    @Override
    public void setPlan(PlanoHq plan) {
        this.plano = plan;
    }
}