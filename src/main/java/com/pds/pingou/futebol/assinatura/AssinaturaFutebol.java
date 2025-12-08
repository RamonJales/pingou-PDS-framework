package com.pds.pingou.futebol.assinatura;

import com.pds.pingou.enums.StatusAssinatura;
import com.pds.pingou.futebol.enums.TamanhoCamisa;
import com.pds.pingou.futebol.plano.PlanoFutebol;
import com.pds.pingou.security.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Entidade que representa uma assinatura de camisas de futebol.
 * 
 * Esta é a entidade central que conecta um usuário a um plano de assinatura.
 * A grande diferença em relação a assinaturas simples é o suporte a MÚLTIPLOS MEMBROS,
 * permitindo que uma família inteira seja atendida com tamanhos diferentes.
 * 
 * Características especiais:
 * - Lista de membros com tamanhos individuais
 * - Time favorito da família
 * - Preferências de tipos de camisa
 * - Histórico de trocas de tamanho
 * 
 * @author Pingou Team
 * @version 1.0
 */
@Entity
@Table(name = "assinaturas_futebol")
@Getter
@Setter
public class AssinaturaFutebol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Usuário titular da assinatura */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    /** Plano de assinatura selecionado */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plano_id", nullable = false)
    private PlanoFutebol plano;

    /** Status atual da assinatura */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusAssinatura status;

    /** Data de início da assinatura */
    @Column(name = "data_inicio", nullable = false)
    private LocalDate dataInicio;

    /** Data de expiração/renovação */
    @Column(name = "data_expiracao")
    private LocalDate dataExpiracao;

    /** Lista de membros da assinatura (para planos família) */
    @OneToMany(mappedBy = "assinatura", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<MembroAssinatura> membros = new ArrayList<>();

    /** Time favorito principal (para curadoria de pacotes) */
    @Column(name = "time_favorito_principal")
    private String timeFavoritoPrincipal;

    /** Times secundários de interesse (separados por vírgula) */
    @Column(name = "times_secundarios", length = 500)
    private String timesSecundarios;

    /** Se aceita camisas de times rivais (para surpresas) */
    @Column(name = "aceita_times_rivais", nullable = false)
    private Boolean aceitaTimesRivais = false;

    /** Preferência por seleções (Brasil, Argentina, etc.) */
    @Column(name = "selecoes_preferidas")
    private String selecoesPreferidas;

    /** Quantidade de trocas de tamanho usadas no ano */
    @Column(name = "trocas_tamanho_usadas")
    private Integer trocasTamanhoUsadas = 0;

    /** Endereço de entrega diferente do cadastro */
    @Column(name = "endereco_entrega", length = 500)
    private String enderecoEntrega;

    /** Observações gerais para entregas */
    @Column(name = "observacoes", length = 1000)
    private String observacoes;

    public AssinaturaFutebol() {}

    /**
     * Construtor para criação de uma assinatura.
     */
    public AssinaturaFutebol(User user, PlanoFutebol plano, StatusAssinatura status,
                              LocalDate dataInicio, LocalDate dataExpiracao) {
        this.user = user;
        this.plano = plano;
        this.status = status;
        this.dataInicio = dataInicio;
        this.dataExpiracao = dataExpiracao;
    }

    /**
     * Adiciona um membro à assinatura.
     * 
     * @throws IllegalStateException se o limite de membros do plano foi atingido
     */
    public void adicionarMembro(MembroAssinatura membro) {
        if (!podAdicionarMembro()) {
            throw new IllegalStateException(
                "Limite de membros atingido para o plano " + plano.getNome() + 
                ". Máximo: " + plano.getMaxMembros()
            );
        }
        membro.setAssinatura(this);
        membro.setOrdem(membros.size() + 1);
        membros.add(membro);
    }

    /**
     * Remove um membro da assinatura.
     */
    public void removerMembro(MembroAssinatura membro) {
        if (membro.getTitular()) {
            throw new IllegalStateException("Não é possível remover o titular da assinatura");
        }
        membros.remove(membro);
        reordenarMembros();
    }

    /**
     * Verifica se pode adicionar mais membros.
     */
    public boolean podAdicionarMembro() {
        return plano.podeAdicionarMembro(getQuantidadeMembrosAtivos());
    }

    /**
     * Retorna a quantidade de membros ativos.
     */
    public int getQuantidadeMembrosAtivos() {
        return (int) membros.stream().filter(MembroAssinatura::getAtivo).count();
    }

    /**
     * Retorna o membro titular.
     */
    public MembroAssinatura getTitular() {
        return membros.stream()
            .filter(MembroAssinatura::getTitular)
            .findFirst()
            .orElse(null);
    }

    /**
     * Retorna apenas membros ativos.
     */
    public List<MembroAssinatura> getMembrosAtivos() {
        return membros.stream()
            .filter(MembroAssinatura::getAtivo)
            .collect(Collectors.toList());
    }

    /**
     * Retorna membros por tipo de tamanho (adulto/infantil).
     */
    public List<MembroAssinatura> getMembrosAdultos() {
        return membros.stream()
            .filter(m -> m.getAtivo() && m.isAdulto())
            .collect(Collectors.toList());
    }

    public List<MembroAssinatura> getMembrosCriancas() {
        return membros.stream()
            .filter(m -> m.getAtivo() && m.isCrianca())
            .collect(Collectors.toList());
    }

    /**
     * Retorna lista de tamanhos únicos necessários.
     */
    public List<TamanhoCamisa> getTamanhosNecessarios() {
        return membros.stream()
            .filter(MembroAssinatura::getAtivo)
            .map(MembroAssinatura::getTamanho)
            .distinct()
            .collect(Collectors.toList());
    }

    /**
     * Verifica se ainda pode trocar tamanhos este ano.
     */
    public boolean podeTrocarTamanho() {
        return trocasTamanhoUsadas < plano.getTrocasTamanhoAno();
    }

    /**
     * Registra uma troca de tamanho.
     */
    public void registrarTrocaTamanho() {
        if (!podeTrocarTamanho()) {
            throw new IllegalStateException("Limite de trocas de tamanho atingido para este ano");
        }
        trocasTamanhoUsadas++;
    }

    /**
     * Reseta o contador de trocas (chamado na renovação anual).
     */
    public void resetarTrocasTamanho() {
        trocasTamanhoUsadas = 0;
    }

    /**
     * Verifica se a assinatura está ativa.
     */
    public boolean isAtiva() {
        return status == StatusAssinatura.ATIVA;
    }

    /**
     * Verifica se a assinatura expirou.
     */
    public boolean isExpirada() {
        return dataExpiracao != null && dataExpiracao.isBefore(LocalDate.now());
    }

    /**
     * Suspende a assinatura.
     */
    public void suspender() {
        this.status = StatusAssinatura.INATIVA;
    }

    /**
     * Reativa a assinatura.
     */
    public void reativar() {
        this.status = StatusAssinatura.ATIVA;
    }

    /**
     * Cancela a assinatura.
     */
    public void cancelar() {
        this.status = StatusAssinatura.CANCELADA;
    }

    /**
     * Reordena os membros após remoção.
     */
    private void reordenarMembros() {
        int ordem = 1;
        for (MembroAssinatura membro : membros) {
            membro.setOrdem(ordem++);
        }
    }

    /**
     * Retorna resumo da assinatura para exibição.
     */
    public String getResumo() {
        StringBuilder sb = new StringBuilder();
        sb.append("Assinatura: ").append(plano.getNome()).append("\n");
        sb.append("Status: ").append(status.name()).append("\n");
        sb.append("Membros (").append(getQuantidadeMembrosAtivos()).append("/").append(plano.getMaxMembros()).append("):\n");
        
        for (MembroAssinatura membro : getMembrosAtivos()) {
            sb.append("  - ").append(membro.getDescricaoParaEnvio()).append("\n");
        }
        
        if (timeFavoritoPrincipal != null) {
            sb.append("Time favorito: ").append(timeFavoritoPrincipal).append("\n");
        }
        
        return sb.toString();
    }
}
