package com.pds.pingou.futebol.pacote;

import com.pds.pingou.framework.core.entity.BasePackage;
import com.pds.pingou.futebol.plano.PlanoFutebol;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidade que representa um pacote de camisas de futebol.
 * 
 * Estende BasePackage do framework para herdar funcionalidades comuns,
 * adicionando características específicas para envios de camisas como
 * temática do pacote, evento relacionado, etc.
 * 
 * @author Pingou Team
 * @version 1.0
 */
@Entity
@Table(name = "pacotes_futebol")
@Getter
@Setter
public class PacoteFutebol extends BasePackage<PlanoFutebol, ItemPacoteFutebol> {

    /** Mês de referência do pacote (1-12) - Compatibilidade com framework */
    @Column(nullable = false)
    private Integer mes;

    /** Plano ao qual este pacote pertence */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plano_id", nullable = false)
    private PlanoFutebol plano;

    /** Lista de itens (camisas com tamanho) incluídos neste pacote */
    @OneToMany(mappedBy = "pacote", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ItemPacoteFutebol> itens = new ArrayList<>();

    /** Temática do pacote (ex: "Clássicos", "Libertadores", "Seleções Copa") */
    @Column(name = "tematica")
    private String tematica;

    /** Evento relacionado (ex: "Final Libertadores 2024", "Copa do Mundo 2026") */
    @Column(name = "evento_relacionado")
    private String eventoRelacionado;

    /** Se é um pacote de edição limitada */
    @Column(name = "edicao_limitada", nullable = false)
    private Boolean edicaoLimitada = false;

    /** Quantidade máxima de pacotes disponíveis (para edições limitadas) */
    @Column(name = "quantidade_maxima")
    private Integer quantidadeMaxima;

    /** Quantidade já vendida/reservada */
    @Column(name = "quantidade_vendida")
    private Integer quantidadeVendida = 0;

    public PacoteFutebol() {}

    /**
     * Construtor para criação de um pacote de futebol.
     */
    public PacoteFutebol(String nome, String descricao, LocalDate dataEntrega,
                         Integer mes, Integer ano, PlanoFutebol plano) {
        setNome(nome);
        setDescricao(descricao);
        setDataEntrega(dataEntrega);
        setPeriodo(mes);
        this.mes = mes;
        setAno(ano);
        this.plano = plano;
    }

    /**
     * Construtor completo com temática.
     */
    public PacoteFutebol(String nome, String descricao, LocalDate dataEntrega,
                         Integer mes, Integer ano, PlanoFutebol plano, 
                         String tematica, String eventoRelacionado) {
        this(nome, descricao, dataEntrega, mes, ano, plano);
        this.tematica = tematica;
        this.eventoRelacionado = eventoRelacionado;
    }

    @Override
    public PlanoFutebol getPlan() {
        return plano;
    }

    @Override
    public void setPlan(PlanoFutebol plan) {
        this.plano = plan;
    }

    @Override
    public List<ItemPacoteFutebol> getItems() {
        return itens;
    }

    @Override
    public void setItems(List<ItemPacoteFutebol> items) {
        this.itens = items;
    }

    /**
     * Adiciona um item ao pacote.
     */
    public void adicionarItem(ItemPacoteFutebol item) {
        addItem(item);
        item.setPacote(this);
    }

    /**
     * Remove um item do pacote.
     */
    public void removerItem(ItemPacoteFutebol item) {
        removeItem(item);
        item.setPacote(null);
    }

    /**
     * Verifica se ainda há disponibilidade para edições limitadas.
     */
    public boolean temDisponibilidade() {
        if (!edicaoLimitada || quantidadeMaxima == null) {
            return true;
        }
        return quantidadeVendida < quantidadeMaxima;
    }

    /**
     * Retorna a quantidade disponível.
     */
    public int getQuantidadeDisponivel() {
        if (!edicaoLimitada || quantidadeMaxima == null) {
            return Integer.MAX_VALUE;
        }
        return Math.max(0, quantidadeMaxima - quantidadeVendida);
    }

    /**
     * Incrementa a quantidade vendida.
     */
    public void incrementarVendas() {
        if (quantidadeVendida == null) {
            quantidadeVendida = 0;
        }
        quantidadeVendida++;
    }

    /**
     * Conta quantas camisas distintas estão no pacote.
     */
    public int getQuantidadeCamisasDistintas() {
        if (itens == null) return 0;
        return (int) itens.stream()
                .map(ItemPacoteFutebol::getCamisa)
                .distinct()
                .count();
    }

    /**
     * Retorna descrição formatada do pacote.
     */
    public String getDescricaoFormatada() {
        StringBuilder sb = new StringBuilder();
        sb.append(getNome());
        if (tematica != null) {
            sb.append(" | Temática: ").append(tematica);
        }
        if (eventoRelacionado != null) {
            sb.append(" | Evento: ").append(eventoRelacionado);
        }
        if (edicaoLimitada) {
            sb.append(" | EDIÇÃO LIMITADA");
            if (quantidadeMaxima != null) {
                sb.append(" (").append(getQuantidadeDisponivel()).append(" restantes)");
            }
        }
        return sb.toString();
    }
}
