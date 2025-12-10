package com.pds.pingou.hq.entity;

import com.pds.pingou.security.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Entidade que registra o histórico de HQs recebidas por um usuário
 * Utilizada para evitar duplicatas na curadoria
 */
@Entity
@Table(name = "historico_hq_usuario", 
       uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "quadrinho_id"}))
@Getter
@Setter
public class HistoricoHQUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quadrinho_id", nullable = false)
    private Quadrinho quadrinho;

    @Column(name = "data_recebimento", nullable = false)
    private LocalDate dataRecebimento;

    @Column(name = "pacote_id")
    private Long pacoteId;

    @Column(name = "avaliacao")
    private Integer avaliacao; // 1-5 estrelas

    @Column(name = "comentario", length = 500)
    private String comentario;

    /**
     * Construtor padrão
     */
    public HistoricoHQUsuario() {
        this.dataRecebimento = LocalDate.now();
    }

    /**
     * Construtor com dados essenciais
     */
    public HistoricoHQUsuario(User user, Quadrinho quadrinho, Long pacoteId) {
        this();
        this.user = user;
        this.quadrinho = quadrinho;
        this.pacoteId = pacoteId;
    }

    /**
     * Define uma avaliação (validando entre 1-5)
     */
    public void setAvaliacao(Integer avaliacao) {
        if (avaliacao != null && (avaliacao < 1 || avaliacao > 5)) {
            throw new IllegalArgumentException("Avaliação deve estar entre 1 e 5");
        }
        this.avaliacao = avaliacao;
    }
}
