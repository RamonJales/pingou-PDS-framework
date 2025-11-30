package com.pds.pingou.produto;

import com.pds.pingou.framework.core.entity.BaseProduct;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Classe abstrata que representa um produto genérico no sistema Pingou.
 * 
 * Esta classe serve como base para todos os tipos de produtos que podem ser
 * incluídos nos pacotes de assinatura, como cachaças, whiskys, vodkas, etc.
 * Utiliza o padrão de herança JPA com estratégia JOINED para permitir
 * especialização de produtos mantendo a integridade relacional.
 * 
 * Agora estende BaseProduct do framework, reutilizando funcionalidades comuns.
 * 
 * @author Pingou Team
 * @version 2.0
 * @since 1.0
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "produtos")
@Getter
@Setter
public abstract class Produto extends BaseProduct {
    
    public Produto() {}
    
    /**
     * Construtor para criação de um produto com informações básicas.
     * 
     * @param nome Nome comercial do produto
     * @param descricao Descrição detalhada do produto
     * @param preco Preço do produto em reais
     */
    public Produto(String nome, String descricao, BigDecimal preco) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
    }
}