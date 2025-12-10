package com.pds.pingou.hq.repository;

import com.pds.pingou.framework.core.enums.SubscriptionStatus;
import com.pds.pingou.hq.entity.AssinaturaHQ;
import com.pds.pingou.security.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositório para operações com Assinaturas de HQ
 */
@Repository
public interface AssinaturaHQRepository extends JpaRepository<AssinaturaHQ, Long> {

    /**
     * Busca assinatura por usuário
     */
    Optional<AssinaturaHQ> findByUser(User user);

    /**
     * Verifica se usuário possui assinatura
     */
    boolean existsByUser(User user);

    /**
     * Busca assinaturas por status
     */
    List<AssinaturaHQ> findByStatus(SubscriptionStatus status);

    /**
     * Busca assinaturas ativas
     */
    @Query("SELECT a FROM AssinaturaHQ a WHERE a.status = 'ATIVA'")
    List<AssinaturaHQ> findAssinaturasAtivas();

    /**
     * Busca assinaturas por nível de colecionador
     */
    List<AssinaturaHQ> findByNivelColecionador(String nivelColecionador);

    /**
     * Busca assinaturas elegíveis para upgrade
     */
    @Query("SELECT a FROM AssinaturaHQ a WHERE a.pontosAcumulados >= 1000 AND a.pacotesRecebidos >= 3 AND a.status = 'ATIVA'")
    List<AssinaturaHQ> findElegiveisParaUpgrade();

    /**
     * Busca assinaturas com mais pontos
     */
    @Query("SELECT a FROM AssinaturaHQ a WHERE a.status = 'ATIVA' ORDER BY (a.pontosAcumulados - a.pontosResgatados) DESC")
    List<AssinaturaHQ> findTopByPontos(@Param("limit") int limit);

    /**
     * Busca assinaturas sem pacote recente
     */
    @Query("SELECT a FROM AssinaturaHQ a WHERE a.ultimoPacoteData < :dataLimite OR a.ultimoPacoteData IS NULL")
    List<AssinaturaHQ> findSemPacoteRecente(@Param("dataLimite") java.time.LocalDate dataLimite);

    /**
     * Conta total de pontos distribuídos
     */
    @Query("SELECT SUM(a.pontosAcumulados) FROM AssinaturaHQ a")
    Long countTotalPontosDistribuidos();

    /**
     * Conta total de HQs distribuídas
     */
    @Query("SELECT SUM(a.hqsRecebidas) FROM AssinaturaHQ a")
    Long countTotalHQsDistribuidas();
}
