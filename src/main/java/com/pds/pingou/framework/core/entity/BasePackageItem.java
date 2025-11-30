package com.pds.pingou.framework.core.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Classe abstrata base para representar itens de pacotes em um sistema de assinatura genérico.
 * 
 * Esta classe faz parte do framework de assinaturas e deve ser estendida por implementações
 * específicas de domínio. Ela representa a relação entre um pacote e os produtos incluídos,
 * permitindo especificar quantidades e outras informações relevantes.
 * 
 * @param <PKG> Tipo do pacote (deve estender BasePackage)
 * @param <PRD> Tipo do produto (deve estender BaseProduct)
 * 
 * @author Pingou Framework Team
 * @version 1.0
 * @since 1.0
 */
@MappedSuperclass
@Getter
@Setter
public abstract class BasePackageItem<PKG, PRD> {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /** Quantidade deste produto no pacote */
    @Column(nullable = false)
    private Integer quantidade = 1;
    
    /** Observações específicas sobre este item */
    @Column(length = 500)
    private String observacoes;
    
    /**
     * Retorna o pacote ao qual este item pertence.
     * Deve ser implementado pelas subclasses.
     */
    public abstract PKG getPackage();
    
    /**
     * Define o pacote ao qual este item pertence.
     * Deve ser implementado pelas subclasses.
     */
    public abstract void setPackage(PKG pkg);
    
    /**
     * Retorna o produto associado a este item.
     * Deve ser implementado pelas subclasses.
     */
    public abstract PRD getProduct();
    
    /**
     * Define o produto associado a este item.
     * Deve ser implementado pelas subclasses.
     */
    public abstract void setProduct(PRD product);
    
    /**
     * Incrementa a quantidade deste item.
     */
    public void incrementQuantity() {
        this.quantidade++;
    }
    
    /**
     * Decrementa a quantidade deste item.
     */
    public void decrementQuantity() {
        if (this.quantidade > 1) {
            this.quantidade--;
        }
    }
    
    /**
     * Verifica se a quantidade é válida.
     */
    public boolean isValidQuantity() {
        return quantidade != null && quantidade > 0;
    }
}
