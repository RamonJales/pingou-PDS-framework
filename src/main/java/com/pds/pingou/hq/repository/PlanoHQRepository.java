package com.pds.pingou.hq.repository;

import com.pds.pingou.hq.entity.PlanoHQ;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositório para operações com Planos de HQ
 */
@Repository
public interface PlanoHQRepository extends JpaRepository<PlanoHQ, Long> {

    /**
     * Busca plano por nome
     */
    Optional<PlanoHQ> findByNome(String nome);

    /**
     * Verifica se existe plano com o nome
     */
    boolean existsByNome(String nome);

    /**
     * Busca planos ativos
     */
    List<PlanoHQ> findByAtivoTrue();

    /**
     * Busca planos focados em clássicas (>50%)
     */
    @Query("SELECT p FROM PlanoHQ p WHERE p.percentualClassicas > 50 AND p.ativo = true")
    List<PlanoHQ> findPlanosFocadosEmClassicas();

    /**
     * Busca planos focados em modernas (>50%)
     */
    @Query("SELECT p FROM PlanoHQ p WHERE p.percentualModernas > 50 AND p.ativo = true")
    List<PlanoHQ> findPlanosFocadosEmModernas();

    /**
     * Busca planos equilibrados (50/50)
     */
    @Query("SELECT p FROM PlanoHQ p WHERE p.percentualClassicas = 50 AND p.percentualModernas = 50 AND p.ativo = true")
    List<PlanoHQ> findPlanosEquilibrados();

    /**
     * Busca planos que incluem edições de colecionador
     */
    List<PlanoHQ> findByIncluiEdicoesColecionadorTrueAndAtivoTrue();

    /**
     * Busca planos por nível de curadoria
     */
    List<PlanoHQ> findByNivelCuradoriaAndAtivoTrue(String nivelCuradoria);
}
