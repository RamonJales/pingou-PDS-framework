package com.pds.pingou.camisa.entity;

import com.pds.pingou.camisa.enums.TamanhoCamisa;
import com.pds.pingou.security.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "perfil_morfologico")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PerfilMorfologico {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;
    
    @Column(nullable = false)
    private Double altura; // em cm
    
    @Column(nullable = false)
    private Double peso; // em kg
    
    @Column(nullable = false)
    private Double circunferenciaPeito; // em cm
    
    @Column(nullable = false)
    private Double circunferenciaCintura; // em cm
    
    @Column(nullable = false)
    private Double comprimentoTorso; // em cm
    
    @Column(nullable = false)
    private Double larguraOmbros; // em cm
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TamanhoCamisa tamanhoRecomendado;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        calcularTamanhoRecomendado();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
        calcularTamanhoRecomendado();
    }
    
    /**
     * Calcula o tamanho recomendado baseado nas medidas corporais
     */
    private void calcularTamanhoRecomendado() {
        // Lógica de cálculo baseada em múltiplos fatores
        double imc = peso / Math.pow(altura / 100, 2);
        
        // Considera circunferência do peito como fator principal
        if (circunferenciaPeito < 85) {
            tamanhoRecomendado = TamanhoCamisa.PP;
        } else if (circunferenciaPeito < 90) {
            tamanhoRecomendado = TamanhoCamisa.P;
        } else if (circunferenciaPeito < 95) {
            tamanhoRecomendado = TamanhoCamisa.M;
        } else if (circunferenciaPeito < 100) {
            tamanhoRecomendado = TamanhoCamisa.G;
        } else if (circunferenciaPeito < 110) {
            tamanhoRecomendado = TamanhoCamisa.GG;
        } else if (circunferenciaPeito < 120) {
            tamanhoRecomendado = TamanhoCamisa.XG;
        } else {
            tamanhoRecomendado = TamanhoCamisa.XXG;
        }
        
        // Ajusta baseado no comprimento do torso
        if (comprimentoTorso > 75 && tamanhoRecomendado.ordinal() < TamanhoCamisa.XXG.ordinal()) {
            tamanhoRecomendado = TamanhoCamisa.values()[tamanhoRecomendado.ordinal() + 1];
        }
    }
    
    public double getIMC() {
        return peso / Math.pow(altura / 100, 2);
    }
}
