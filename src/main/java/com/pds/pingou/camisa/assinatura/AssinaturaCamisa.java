package com.pds.pingou.camisa.assinatura;

import com.pds.pingou.framework.core.entity.BaseSubscription;
import com.pds.pingou.framework.core.enums.SubscriptionStatus;
import com.pds.pingou.security.user.User;
import com.pds.pingou.camisa.planos.PlanoCamisa;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Entidade que representa uma assinatura de camisas de futebol.
 * Estende BaseSubscription do framework genérico de assinaturas.
 */
@Entity
@Table(name = "assinaturas_camisa")
@Getter
@Setter
public class AssinaturaCamisa extends BaseSubscription<User, PlanoCamisa> {
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plano_id", nullable = false)
    private PlanoCamisa plano;
    
    @Column(name = "tamanho_preferido")
    private String tamanhoPreferido; // Tamanho padrão do assinante
    
    @Column(name = "times_favoritos")
    private String timesFavoritos; // Lista separada por vírgulas
    
    @Column(name = "ligas_preferidas")
    private String ligasPreferidas; // Lista separada por vírgulas
    
    @Column(name = "aceita_personalizacao")
    private Boolean aceitaPersonalizacao = true;
    
    public AssinaturaCamisa() {}
    
    public AssinaturaCamisa(User user, PlanoCamisa plano) {
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
    public PlanoCamisa getPlan() {
        return plano;
    }
    
    @Override
    public void setPlan(PlanoCamisa plan) {
        this.plano = plan;
    }
}
