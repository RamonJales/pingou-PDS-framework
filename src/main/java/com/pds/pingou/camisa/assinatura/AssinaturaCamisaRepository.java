package com.pds.pingou.camisa.assinatura;

import com.pds.pingou.framework.core.enums.SubscriptionStatus;
import com.pds.pingou.security.user.User;
import com.pds.pingou.camisa.planos.PlanoCamisa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositório JPA para a entidade AssinaturaCamisa.
 */
@Repository
public interface AssinaturaCamisaRepository extends JpaRepository<AssinaturaCamisa, Long> {
    
    Optional<AssinaturaCamisa> findByUser(User user);
    
    Optional<AssinaturaCamisa> findByUserId(Long userId);
    
    boolean existsByUser(User user);
    
    boolean existsByUserId(Long userId);
    
    List<AssinaturaCamisa> findByPlano(PlanoCamisa plano);
    
    List<AssinaturaCamisa> findByStatus(SubscriptionStatus status);
    
    @Query("SELECT a FROM AssinaturaCamisa a WHERE a.status = 'ATIVA'")
    List<AssinaturaCamisa> findAllActive();
    
    @Query("SELECT COUNT(a) FROM AssinaturaCamisa a WHERE a.plano.id = :planoId AND a.status = 'ATIVA'")
    Long countActiveByPlano(@Param("planoId") Long planoId);
}
