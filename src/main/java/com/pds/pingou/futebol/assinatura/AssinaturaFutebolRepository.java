package com.pds.pingou.futebol.assinatura;

import com.pds.pingou.enums.StatusAssinatura;
import com.pds.pingou.futebol.plano.PlanoFutebol;
import com.pds.pingou.security.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository para operações de persistência de assinaturas de futebol.
 */
@Repository
public interface AssinaturaFutebolRepository extends JpaRepository<AssinaturaFutebol, Long> {

    Optional<AssinaturaFutebol> findByUser(User user);

    Optional<AssinaturaFutebol> findByUserId(Long userId);

    @Query("SELECT a FROM AssinaturaFutebol a WHERE a.user.email = :email")
    Optional<AssinaturaFutebol> findByUserEmail(@Param("email") String email);

    List<AssinaturaFutebol> findByStatus(StatusAssinatura status);

    List<AssinaturaFutebol> findByPlano(PlanoFutebol plano);

    List<AssinaturaFutebol> findByPlanoAndStatus(PlanoFutebol plano, StatusAssinatura status);

    @Query("SELECT a FROM AssinaturaFutebol a WHERE a.status = 'ATIVA' AND a.plano.id = :planoId")
    List<AssinaturaFutebol> findAssinaturasAtivasByPlano(@Param("planoId") Long planoId);

    @Query("SELECT a FROM AssinaturaFutebol a WHERE a.timeFavoritoPrincipal = :time AND a.status = 'ATIVA'")
    List<AssinaturaFutebol> findByTimeFavorito(@Param("time") String time);

    boolean existsByUser(User user);

    boolean existsByUserId(Long userId);

    @Query("SELECT COUNT(a) FROM AssinaturaFutebol a WHERE a.status = :status")
    long countByStatus(@Param("status") StatusAssinatura status);
}
