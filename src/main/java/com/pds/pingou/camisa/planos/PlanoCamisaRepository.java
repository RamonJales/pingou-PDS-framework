package com.pds.pingou.camisa.planos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositório JPA para a entidade PlanoCamisa.
 */
@Repository
public interface PlanoCamisaRepository extends JpaRepository<PlanoCamisa, Long> {
    
    Optional<PlanoCamisa> findByNome(String nome);
    
    boolean existsByNome(String nome);
    
    List<PlanoCamisa> findByAtivoTrue();
    
    List<PlanoCamisa> findByCategoriaPlano(String categoria);
    
    @Query("SELECT p FROM PlanoCamisa p WHERE p.ativo = true ORDER BY p.preco ASC")
    List<PlanoCamisa> findAllActiveOrderByPrice();
}
