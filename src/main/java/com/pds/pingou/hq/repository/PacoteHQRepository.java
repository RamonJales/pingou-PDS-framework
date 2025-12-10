package com.pds.pingou.hq.repository;

import com.pds.pingou.hq.entity.PacoteHQ;
import com.pds.pingou.hq.entity.PlanoHQ;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repositório para operações com Pacotes de HQ
 */
@Repository
public interface PacoteHQRepository extends JpaRepository<PacoteHQ, Long> {

    /**
     * Busca pacotes por plano
     */
    List<PacoteHQ> findByPlano(PlanoHQ plano);

    /**
     * Busca pacotes por período e ano
     */
    List<PacoteHQ> findByPeriodoAndAno(Integer periodo, Integer ano);

    /**
     * Busca pacotes ativos
     */
    List<PacoteHQ> findByAtivoTrue();

    /**
     * Busca pacotes curados para um usuário específico
     */
    List<PacoteHQ> findByCuradoParaUserId(Long userId);

    /**
     * Busca pacote curado para usuário em período específico
     */
    Optional<PacoteHQ> findByCuradoParaUserIdAndPeriodoAndAno(Long userId, Integer periodo, Integer ano);

    /**
     * Busca pacotes por intervalo de data de entrega
     */
    @Query("SELECT p FROM PacoteHQ p WHERE p.dataEntrega BETWEEN :dataInicio AND :dataFim")
    List<PacoteHQ> findByDataEntregaBetween(
        @Param("dataInicio") LocalDate dataInicio,
        @Param("dataFim") LocalDate dataFim
    );

    /**
     * Busca pacotes para entrega hoje
     */
    @Query("SELECT p FROM PacoteHQ p WHERE p.dataEntrega = :hoje AND p.ativo = true")
    List<PacoteHQ> findParaEntregaHoje(@Param("hoje") LocalDate hoje);

    /**
     * Busca pacotes atrasados
     */
    @Query("SELECT p FROM PacoteHQ p WHERE p.dataEntrega < :hoje AND p.ativo = true")
    List<PacoteHQ> findAtrasados(@Param("hoje") LocalDate hoje);

    /**
     * Busca pacotes por tema
     */
    List<PacoteHQ> findByTemaMesContainingIgnoreCase(String temaMes);
}
