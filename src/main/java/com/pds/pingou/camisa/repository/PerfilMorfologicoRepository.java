package com.pds.pingou.camisa.repository;

import com.pds.pingou.camisa.entity.PerfilMorfologico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PerfilMorfologicoRepository extends JpaRepository<PerfilMorfologico, Long> {
    
    @Query("SELECT p FROM PerfilMorfologico p WHERE p.user.id = :userId")
    Optional<PerfilMorfologico> findByUserId(Long userId);
    
    boolean existsByUserId(Long userId);
}
