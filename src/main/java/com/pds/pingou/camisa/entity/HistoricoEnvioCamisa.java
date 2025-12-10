package com.pds.pingou.camisa.entity;

import com.pds.pingou.security.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "historico_envio_camisa")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HistoricoEnvioCamisa {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "camisa_id", nullable = false)
    private Camisa camisa;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assinatura_id", nullable = false)
    private AssinaturaCamisa assinatura;
    
    @Column(nullable = false)
    private LocalDateTime dataEnvio;
    
    @Column(nullable = false)
    private LocalDateTime dataEntregaPrevista;
    
    @Column
    private LocalDateTime dataEntregaRealizada;
    
    @Column(length = 500)
    private String observacoes;
    
    @PrePersist
    protected void onCreate() {
        if (dataEnvio == null) {
            dataEnvio = LocalDateTime.now();
        }
    }
    
    public boolean foiEntregue() {
        return dataEntregaRealizada != null;
    }
    
    public void marcarComoEntregue() {
        this.dataEntregaRealizada = LocalDateTime.now();
    }
}
