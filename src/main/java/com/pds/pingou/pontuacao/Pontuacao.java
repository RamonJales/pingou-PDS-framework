package com.pds.pingou.pontuacao;

import com.pds.pingou.security.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Entidade que representa a pontuação acumulada de um usuário.
 *
 * @author Pingou Team
 * @version 1.0
 * @since 2.0
 */
@Entity
@Table(name = "pontuacao")
@Getter
@Setter
public class Pontuacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(name = "pontos_totais", nullable = false)
    private Integer pontosTotais = 0;

    @Column(name = "pontos_disponiveis", nullable = false)
    private Integer pontosDisponiveis = 0;

    @Column(name = "pontos_utilizados", nullable = false)
    private Integer pontosUtilizados = 0;

    @Column(name = "ultima_atualizacao")
    private LocalDateTime ultimaAtualizacao;

    public Pontuacao() {
        this.ultimaAtualizacao = LocalDateTime.now();
    }

    public Pontuacao(User user) {
        this.user = user;
        this.pontosTotais = 0;
        this.pontosDisponiveis = 0;
        this.pontosUtilizados = 0;
        this.ultimaAtualizacao = LocalDateTime.now();
    }

    public void adicionarPontos(Integer pontos) {
        if (pontos > 0) {
            this.pontosTotais += pontos;
            this.pontosDisponiveis += pontos;
            this.ultimaAtualizacao = LocalDateTime.now();
        }
    }

    public void utilizarPontos(Integer pontos) {
        if (pontos > 0 && pontos <= this.pontosDisponiveis) {
            this.pontosDisponiveis -= pontos;
            this.pontosUtilizados += pontos;
            this.ultimaAtualizacao = LocalDateTime.now();
        } else {
            throw new IllegalArgumentException("Pontos insuficientes ou valor inválido");
        }
    }
}