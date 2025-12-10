package com.pds.pingou.camisa.entity;

import com.pds.pingou.framework.core.entity.BasePlan;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "planos_camisa")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlanoCamisa extends BasePlan<PacoteCamisa> {
    
    @OneToMany(mappedBy = "plano", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PacoteCamisa> pacotes = new ArrayList<>();
    
    @Column(nullable = false)
    private Boolean permiteCompartilhamento = false;
    
    @Column(nullable = false)
    private Integer maxParticipantes = 1;
    
    @Column(nullable = false)
    private Integer camisasPorMes = 1;
    
    @Override
    public List<PacoteCamisa> getPackages() {
        return pacotes;
    }
    
    @Override
    public void setPackages(List<PacoteCamisa> packages) {
        this.pacotes = packages;
    }
    
    public boolean isPlanoFamilia() {
        return permiteCompartilhamento && maxParticipantes > 1;
    }
    
    public void validarNumeroParticipantes(int numeroAtual) {
        if (numeroAtual > maxParticipantes) {
            throw new IllegalStateException(
                String.format("Número de participantes (%d) excede o máximo permitido (%d)", 
                    numeroAtual, maxParticipantes)
            );
        }
    }
}
