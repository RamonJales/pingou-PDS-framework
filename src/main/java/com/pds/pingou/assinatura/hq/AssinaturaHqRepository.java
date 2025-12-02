// src/main/java/com/pds/pingou/assinatura/hq/AssinaturaHqRepository.java
package com.pds.pingou.assinatura.hq;

import com.pds.pingou.framework.core.enums.SubscriptionStatus;
import com.pds.pingou.security.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AssinaturaHqRepository extends JpaRepository<AssinaturaHq, Long> {
    Optional<AssinaturaHq> findByUser(User user);
    boolean existsByUser(User user);
    List<AssinaturaHq> findByStatus(SubscriptionStatus status);
}