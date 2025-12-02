package com.pds.pingou.pontuacao;

import com.pds.pingou.security.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Entidade que representa o histórico de movimentação de pontos.
 *
 * @author Pingou Team
 * @version 1.0
 * @since 2.0
 */
@Entity
@Table(name = "historico_pontos")
@Getter
@Setter
public class HistoricoPontos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /** Tipo de movimentação (GANHO, TROCA, BONUS, EXPIRACAO) */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoMovimentacao tipo;

    @Column(nullable = false)
    private Integer pontos;

    @Column(length = 500)
    private String descricao;

    @Column(name = "hq_id")
    private Long hqId;

    @Column(name = "data_hora", nullable = false)
    private LocalDateTime dataHora;

    public HistoricoPontos() {
        this.dataHora = LocalDateTime.now();
    }

    public HistoricoPontos(User user, TipoMovimentacao tipo, Integer pontos, String descricao) {
        this.user = user;
        this.tipo = tipo;
        this.pontos = pontos;
        this.descricao = descricao;
        this.dataHora = LocalDateTime.now();
    }
}