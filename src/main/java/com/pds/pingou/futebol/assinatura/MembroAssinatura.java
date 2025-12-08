package com.pds.pingou.futebol.assinatura;

import com.pds.pingou.futebol.enums.TamanhoCamisa;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Entidade que representa um membro de uma assinatura familiar de camisas de futebol.
 * 
 * Cada membro possui seu próprio tamanho de camisa e preferências individuais,
 * permitindo que um plano família atenda pessoas com tamanhos diferentes.
 * 
 * Exemplo de uso:
 * - Pai: Tamanho G
 * - Mãe: Tamanho M
 * - Filho 1: Infantil 12 anos
 * - Filho 2: Infantil 8 anos
 * 
 * Cada um receberá sua camisa no tamanho correto dentro do mesmo pacote familiar.
 * 
 * @author Pingou Team
 * @version 1.0
 */
@Entity
@Table(name = "membros_assinatura_futebol")
@Getter
@Setter
public class MembroAssinatura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Assinatura à qual este membro pertence */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assinatura_id", nullable = false)
    private AssinaturaFutebol assinatura;

    /** Nome do membro (ex: "João", "Maria", "Filho Pedro") */
    @Column(name = "nome", nullable = false)
    private String nome;

    /** Tamanho da camisa para este membro */
    @Enumerated(EnumType.STRING)
    @Column(name = "tamanho", nullable = false)
    private TamanhoCamisa tamanho;

    /** Time favorito (para preferências de envio) */
    @Column(name = "time_favorito")
    private String timeFavorito;

    /** Se prefere camisa com nome de jogador específico */
    @Column(name = "jogador_favorito")
    private String jogadorFavorito;

    /** Número favorito (para personalização) */
    @Column(name = "numero_favorito")
    private Integer numeroFavorito;

    /** Se é o titular/responsável da assinatura */
    @Column(name = "titular", nullable = false)
    private Boolean titular = false;

    /** Ordem do membro na família (para organização dos envios) */
    @Column(name = "ordem")
    private Integer ordem = 1;

    /** Se o membro está ativo (pode ser desativado temporariamente) */
    @Column(name = "ativo", nullable = false)
    private Boolean ativo = true;

    /** Email do membro (opcional, para notificações individuais) */
    @Column(name = "email")
    private String email;

    public MembroAssinatura() {}

    /**
     * Construtor para criação de um membro de assinatura.
     */
    public MembroAssinatura(AssinaturaFutebol assinatura, String nome, TamanhoCamisa tamanho) {
        this.assinatura = assinatura;
        this.nome = nome;
        this.tamanho = tamanho;
    }

    /**
     * Construtor completo para criação de um membro.
     */
    public MembroAssinatura(AssinaturaFutebol assinatura, String nome, TamanhoCamisa tamanho,
                            String timeFavorito, Boolean titular) {
        this(assinatura, nome, tamanho);
        this.timeFavorito = timeFavorito;
        this.titular = titular;
    }

    /**
     * Verifica se o membro é criança (tamanho infantil).
     */
    public boolean isCrianca() {
        return tamanho != null && tamanho.isInfantil();
    }

    /**
     * Verifica se o membro é adulto.
     */
    public boolean isAdulto() {
        return tamanho != null && tamanho.isAdulto();
    }

    /**
     * Retorna uma descrição resumida do membro para envio.
     */
    public String getDescricaoParaEnvio() {
        StringBuilder sb = new StringBuilder();
        sb.append(nome).append(" - Tamanho ").append(tamanho.getSigla());
        if (timeFavorito != null) {
            sb.append(" (Torcedor: ").append(timeFavorito).append(")");
        }
        return sb.toString();
    }

    /**
     * Verifica se o membro tem preferência de personalização.
     */
    public boolean temPreferenciaPersonalizacao() {
        return (jogadorFavorito != null && !jogadorFavorito.isBlank()) 
            || (numeroFavorito != null && numeroFavorito > 0);
    }

    /**
     * Ativa o membro.
     */
    public void ativar() {
        this.ativo = true;
    }

    /**
     * Desativa o membro temporariamente.
     */
    public void desativar() {
        this.ativo = false;
    }
}
