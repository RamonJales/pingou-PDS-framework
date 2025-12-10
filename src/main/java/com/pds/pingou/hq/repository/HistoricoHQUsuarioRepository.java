package com.pds.pingou.hq.repository;

import com.pds.pingou.hq.entity.HistoricoHQUsuario;
import com.pds.pingou.hq.entity.Quadrinho;
import com.pds.pingou.security.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositório para operações com Histórico de HQs do Usuário
 */
@Repository
public interface HistoricoHQUsuarioRepository extends JpaRepository<HistoricoHQUsuario, Long> {

    /**
     * Busca histórico por usuário
     */
    List<HistoricoHQUsuario> findByUser(User user);

    /**
     * Busca histórico por usuário e quadrinho
     */
    List<HistoricoHQUsuario> findByUserAndQuadrinho(User user, Quadrinho quadrinho);

    /**
     * Verifica se usuário já recebeu determinado quadrinho
     */
    boolean existsByUserAndQuadrinho(User user, Quadrinho quadrinho);

    /**
     * Busca histórico por pacote
     */
    List<HistoricoHQUsuario> findByPacoteId(Long pacoteId);

    /**
     * Busca IDs dos quadrinhos já recebidos pelo usuário
     */
    @Query("SELECT h.quadrinho.id FROM HistoricoHQUsuario h WHERE h.user = :user")
    List<Long> findQuadrinhosIdsRecebidosPorUser(@Param("user") User user);

    /**
     * Busca histórico com avaliação
     */
    List<HistoricoHQUsuario> findByUserAndAvaliacaoIsNotNull(User user);

    /**
     * Conta quantas HQs o usuário já recebeu
     */
    long countByUser(User user);

    /**
     * Busca histórico ordenado por data (mais recente primeiro)
     */
    List<HistoricoHQUsuario> findByUserOrderByDataRecebimentoDesc(User user);
}
