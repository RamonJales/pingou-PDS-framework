package com.pds.pingou.futebol.plano;

import com.pds.pingou.framework.core.entity.BasePlan;
import com.pds.pingou.futebol.enums.TipoPlanoFutebol;
import com.pds.pingou.futebol.pacote.PacoteFutebol;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidade que representa um plano de assinatura de camisas de futebol.
 * 
 * Estende BasePlan do framework e adiciona funcionalidades específicas para
 * planos de camisas, incluindo suporte a planos família com múltiplos membros.
 * 
 * Características especiais:
 * - Suporte a diferentes tipos de planos (Individual, Família, Torcida)
 * - Configuração de quantidade de camisas por período
 * - Opções de personalização incluídas
 * - Prioridade para edições limitadas
 * 
 * @author Pingou Team
 * @version 1.0
 */
@Entity
@Table(name = "planos_futebol")
@Getter
@Setter
public class PlanoFutebol extends BasePlan<PacoteFutebol> {

    /** Tipo do plano (Individual, Família, Torcida, etc.) */
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_plano", nullable = false)
    private TipoPlanoFutebol tipoPlano;

    /** Quantidade de camisas por membro por período */
    @Column(name = "camisas_por_membro", nullable = false)
    private Integer camisasPorMembro = 1;

    /** Se inclui personalização gratuita (nome/número) */
    @Column(name = "personalizacao_inclusa", nullable = false)
    private Boolean personalizacaoInclusa = false;

    /** Se tem prioridade para edições limitadas */
    @Column(name = "prioridade_edicao_limitada", nullable = false)
    private Boolean prioridadeEdicaoLimitada = false;

    /** Se inclui camisas de seleções além de clubes */
    @Column(name = "inclui_selecoes", nullable = false)
    private Boolean incluiSelecoes = false;

    /** Quantidade de trocas de tamanho permitidas por ano */
    @Column(name = "trocas_tamanho_ano")
    private Integer trocasTamanhoAno = 2;

    /** Se permite escolher o time/camisa ou é surpresa curada */
    @Column(name = "permite_escolha_time", nullable = false)
    private Boolean permiteEscolhaTime = true;

    /** Desconto percentual para camisas adicionais */
    @Column(name = "desconto_camisa_adicional")
    private BigDecimal descontoCamisaAdicional = BigDecimal.ZERO;

    /** Frete grátis incluso */
    @Column(name = "frete_gratis", nullable = false)
    private Boolean freteGratis = true;

    /** Lista de pacotes associados a este plano */
    @OneToMany(mappedBy = "plano", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PacoteFutebol> pacotes = new ArrayList<>();

    public PlanoFutebol() {}

    /**
     * Construtor para criação de um plano de futebol.
     */
    public PlanoFutebol(String nome, String descricao, BigDecimal precoBase,
                        TipoPlanoFutebol tipoPlano, Integer camisasPorMembro) {
        setNome(nome);
        setDescricao(descricao);
        setPreco(precoBase);
        setMaxProdutosPorPeriodo(camisasPorMembro * tipoPlano.getMaxMembros());
        setFrequenciaEntrega("MENSAL");
        this.tipoPlano = tipoPlano;
        this.camisasPorMembro = camisasPorMembro;
    }

    @Override
    public List<PacoteFutebol> getPackages() {
        return pacotes;
    }

    @Override
    public void setPackages(List<PacoteFutebol> packages) {
        this.pacotes = packages;
    }

    /**
     * Calcula o preço final do plano considerando o tipo.
     */
    public BigDecimal getPrecoFinalCalculado() {
        return getPreco().multiply(BigDecimal.valueOf(tipoPlano.getMultiplicadorPreco()));
    }

    /**
     * Calcula o preço por membro (útil para comparação de planos).
     */
    public BigDecimal getPrecoPorMembro() {
        int maxMembros = tipoPlano.getMaxMembros();
        if (maxMembros == 0) return getPrecoFinalCalculado();
        return getPrecoFinalCalculado().divide(BigDecimal.valueOf(maxMembros), 2, java.math.RoundingMode.HALF_UP);
    }

    /**
     * Verifica se o plano suporta múltiplos membros.
     */
    public boolean isFamiliar() {
        return tipoPlano.isFamiliar();
    }

    /**
     * Retorna o máximo de membros permitidos neste plano.
     */
    public int getMaxMembros() {
        return tipoPlano.getMaxMembros();
    }

    /**
     * Verifica se pode adicionar mais membros.
     */
    public boolean podeAdicionarMembro(int membrosAtuais) {
        return tipoPlano.permiteAdicionarMembro(membrosAtuais);
    }

    /**
     * Calcula o total de camisas enviadas por período.
     */
    public int getTotalCamisasPorPeriodo(int quantidadeMembros) {
        return camisasPorMembro * Math.min(quantidadeMembros, tipoPlano.getMaxMembros());
    }

    /**
     * Adiciona um pacote a este plano.
     */
    public void adicionarPacote(PacoteFutebol pacote) {
        addPackage(pacote);
        pacote.setPlano(this);
    }

    /**
     * Remove um pacote deste plano.
     */
    public void removerPacote(PacoteFutebol pacote) {
        removePackage(pacote);
        pacote.setPlano(null);
    }

    /**
     * Gera uma descrição detalhada do plano para exibição.
     */
    public String getDescricaoCompleta() {
        StringBuilder sb = new StringBuilder();
        sb.append(getNome()).append("\n");
        sb.append("Tipo: ").append(tipoPlano.getNome()).append("\n");
        sb.append("Máximo de membros: ").append(tipoPlano.getMaxMembros()).append("\n");
        sb.append("Camisas por membro: ").append(camisasPorMembro).append("/mês\n");
        sb.append("Preço: R$ ").append(getPrecoFinalCalculado()).append("/mês\n");
        
        if (personalizacaoInclusa) {
            sb.append("✓ Personalização inclusa\n");
        }
        if (prioridadeEdicaoLimitada) {
            sb.append("✓ Prioridade em edições limitadas\n");
        }
        if (incluiSelecoes) {
            sb.append("✓ Inclui camisas de seleções\n");
        }
        if (freteGratis) {
            sb.append("✓ Frete grátis\n");
        }
        
        return sb.toString();
    }
}
