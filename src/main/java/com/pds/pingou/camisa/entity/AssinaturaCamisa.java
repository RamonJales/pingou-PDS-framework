package com.pds.pingou.camisa.entity;

import com.pds.pingou.framework.core.entity.BaseSubscription;
import com.pds.pingou.security.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "assinaturas_camisa")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AssinaturaCamisa extends BaseSubscription<User, PlanoCamisa> {
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plano_id", nullable = false)
    private PlanoCamisa plano;
    
    @Column(nullable = false)
    private String timeFavorito;
    
    @Column(nullable = false)
    private String timeRival;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assinatura_principal_id")
    private AssinaturaCamisa assinaturaPrincipal;
    
    @OneToMany(mappedBy = "assinaturaPrincipal", cascade = CascadeType.ALL)
    private List<AssinaturaCamisa> participantesCompartilhados = new ArrayList<>();
    
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
    
    public boolean isAssinaturaPrincipal() {
        return assinaturaPrincipal == null;
    }
    
    public boolean isAssinaturaCompartilhada() {
        return assinaturaPrincipal != null;
    }
    
    public int getTotalParticipantes() {
        if (isAssinaturaPrincipal()) {
            return 1 + participantesCompartilhados.size();
        }
        return 0;
    }
    
    public void adicionarParticipante(AssinaturaCamisa assinatura) {
        if (!isAssinaturaPrincipal()) {
            throw new IllegalStateException("Apenas assinatura principal pode adicionar participantes");
        }
        
        if (!plano.getPermiteCompartilhamento()) {
            throw new IllegalStateException("Este plano não permite compartilhamento");
        }
        
        if (getTotalParticipantes() >= plano.getMaxParticipantes()) {
            throw new IllegalStateException("Número máximo de participantes atingido");
        }
        
        assinatura.setAssinaturaPrincipal(this);
        participantesCompartilhados.add(assinatura);
    }
    
    public void removerParticipante(AssinaturaCamisa assinatura) {
        participantesCompartilhados.remove(assinatura);
        assinatura.setAssinaturaPrincipal(null);
    }
}
