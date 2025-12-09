package com.pds.pingou.planos;

import com.pds.pingou.framework.core.entity.BasePlan;
import com.pds.pingou.pacote.Pacote;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidade que representa um plano de assinatura no sistema Pingou.
 * 
 * Esta classe define os diferentes tipos de planos de assinatura oferecidos,
 * cada um com suas características específicas como preço, quantidade máxima
 * de produtos por mês e frequência de entrega. Os planos são associados a
 * pacotes mensais que definem exatamente quais produtos serão enviados.
 * 
 * Agora estende BasePlan do framework, reutilizando funcionalidades comuns.
 * 
 * @author Pingou Team
 * @version 3.0
 * @since 1.0
 */
@Setter
@Getter
@Entity
@Table(name = "planos")
public class Plano extends BasePlan<Pacote> {

    /** Lista de pacotes mensais associados a este plano */
    @OneToMany(mappedBy = "plano", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Pacote> pacotes = new ArrayList<>();

    public Plano() {
    }

    /**
     * Construtor para criação de um plano com informações essenciais.
     * 
     * @param nome              Nome comercial do plano
     * @param descricao         Descrição detalhada do plano
     * @param preco             Preço mensal em reais
     * @param maxProdutosPorMes Quantidade máxima de produtos por mês
     */
    public Plano(String nome, String descricao, BigDecimal preco, Integer maxProdutosPorMes) {
        this.setNome(nome);
        this.setDescricao(descricao);
        this.setPreco(preco);
        this.setMaxProdutosPorPeriodo(maxProdutosPorMes);
    }

    /**
     * Implementação do método abstrato do framework.
     */
    @Override
    public List<Pacote> getPackages() {
        return pacotes;
    }

    /**
     * Implementação do método abstrato do framework.
     */
    @Override
    public void setPackages(List<Pacote> packages) {
        this.pacotes = packages;
    }

    /**
     * Adiciona um pacote mensal a este plano.
     * 
     * @param pacote Pacote a ser adicionado
     */
    public void adicionarPacote(Pacote pacote) {
        addPackage(pacote);
        pacote.setPlano(this);
    }

    /**
     * Remove um pacote mensal deste plano.
     * 
     * @param pacote Pacote a ser removido
     */
    public void removerPacote(Pacote pacote) {
        removePackage(pacote);
        pacote.setPlano(null);
    }

    // Compatibilidade com código legado
    public Integer getMaxProdutosPorMes() {
        return getMaxProdutosPorPeriodo();
    }

    public void setMaxProdutosPorMes(Integer max) {
        setMaxProdutosPorPeriodo(max);
    }

    /** Tipo do plano, define a estratégia de negócio e curadoria */
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_plano")
    private com.pds.pingou.enums.TipoPlano tipo = com.pds.pingou.enums.TipoPlano.GENERICO;
}
