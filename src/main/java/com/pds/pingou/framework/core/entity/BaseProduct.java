package com.pds.pingou.framework.core.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Classe abstrata base para representar produtos em um sistema de assinatura genérico.
 * 
 * Esta classe faz parte do framework de assinaturas e deve ser estendida por implementações
 * específicas de domínio (ex: cachaça, vinho, café, livros, etc.). Ela encapsula os
 * atributos comuns a todos os produtos que podem ser incluídos em pacotes de assinatura.
 * 
 * Utiliza o padrão de herança JPA com estratégia JOINED para permitir especialização
 * mantendo a integridade relacional.
 * 
 * @author Pingou Framework Team
 * @version 1.0
 * @since 1.0
 */
@MappedSuperclass
@Getter
@Setter
public abstract class BaseProduct {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /** Nome comercial do produto */
    @Column(nullable = false)
    private String nome;
    
    /** Descrição detalhada do produto */
    @Column(length = 1000)
    private String descricao;
    
    /** Preço do produto */
    @Column(nullable = false)
    private BigDecimal preco;
    
    /** URL da imagem do produto */
    @Column(name = "url_imagem")
    private String urlImagem;
    
    /** Indica se o produto está ativo no sistema */
    @Column(nullable = false)
    private Boolean ativo = true;
    
    /**
     * Método abstrato para obter uma descrição resumida do produto.
     * Deve ser implementado pelas subclasses específicas de domínio.
     */
    public abstract String getShortDescription();
    
    /**
     * Método abstrato para obter a categoria do produto.
     * Deve ser implementado pelas subclasses específicas de domínio.
     */
    public abstract String getCategory();
    
    /**
     * Verifica se o produto está disponível para venda/assinatura.
     */
    public boolean isAvailable() {
        return ativo != null && ativo;
    }
    
    /**
     * Ativa o produto.
     */
    public void activate() {
        this.ativo = true;
    }
    
    /**
     * Desativa o produto.
     */
    public void deactivate() {
        this.ativo = false;
    }
}
