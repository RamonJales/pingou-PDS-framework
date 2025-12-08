package com.pds.pingou.examples.produto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * EXEMPLO: Repository para produto especializado de Vinho.
 * 
 * Demonstra como criar queries específicas para seu tipo de produto.
 * 
 * @author Pingou Framework Team
 * @version 2.0
 */
// @Repository  // Descomente para usar em produção
public interface ExemploVinhoRepository extends JpaRepository<ExemploProdutoVinho, Long> {

    /**
     * Busca vinhos ativos por tipo de uva.
     */
    List<ExemploProdutoVinho> findByUvaAndAtivoTrue(String uva);

    /**
     * Busca vinhos ativos por safra.
     */
    List<ExemploProdutoVinho> findBySafraAndAtivoTrue(Integer safra);

    /**
     * Busca vinhos ativos por tipo (Tinto, Branco, Rosé).
     */
    List<ExemploProdutoVinho> findByTipoVinhoAndAtivoTrue(String tipoVinho);

    /**
     * Busca vinhos ativos por país.
     */
    List<ExemploProdutoVinho> findByPaisAndAtivoTrue(String pais);

    /**
     * Busca vinhos ativos por região.
     */
    List<ExemploProdutoVinho> findByRegiaoAndAtivoTrue(String regiao);

    /**
     * Busca vinhos de uma determinada safra ou mais recentes.
     */
    @Query("SELECT v FROM ExemploProdutoVinho v WHERE v.safra >= :ano AND v.ativo = true ORDER BY v.safra DESC")
    List<ExemploProdutoVinho> findBySafraGreaterThanEqual(@Param("ano") Integer ano);

    /**
     * Lista todos os tipos de uva disponíveis.
     */
    @Query("SELECT DISTINCT v.uva FROM ExemploProdutoVinho v WHERE v.ativo = true AND v.uva IS NOT NULL")
    List<String> findDistinctUvas();

    /**
     * Lista todos os países disponíveis.
     */
    @Query("SELECT DISTINCT v.pais FROM ExemploProdutoVinho v WHERE v.ativo = true AND v.pais IS NOT NULL")
    List<String> findDistinctPaises();
}
