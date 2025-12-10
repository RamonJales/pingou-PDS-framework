package com.pds.pingou.camisa.repository;

import com.pds.pingou.camisa.entity.AssinaturaCamisa;
import com.pds.pingou.framework.core.enums.SubscriptionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AssinaturaCamisaRepository extends JpaRepository<AssinaturaCamisa, Long> {
    
    Optional<AssinaturaCamisa> findByUserId(Long userId);
    
    List<AssinaturaCamisa> findByPlanoId(Long planoId);
    
    List<AssinaturaCamisa> findByStatus(SubscriptionStatus status);
    
    @Query("SELECT a FROM AssinaturaCamisa a WHERE a.assinaturaPrincipal IS NULL")
    List<AssinaturaCamisa> findAssinaturasPrincipais();
    
    @Query("SELECT a FROM AssinaturaCamisa a WHERE a.assinaturaPrincipal.id = :assinaturaPrincipalId")
    List<AssinaturaCamisa> findParticipantesByAssinaturaPrincipalId(Long assinaturaPrincipalId);
    
    boolean existsByUserId(Long userId);
}
