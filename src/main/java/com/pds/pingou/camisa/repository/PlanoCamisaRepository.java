package com.pds.pingou.camisa.repository;

import com.pds.pingou.camisa.entity.PlanoCamisa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlanoCamisaRepository extends JpaRepository<PlanoCamisa, Long> {
    
    Optional<PlanoCamisa> findByNome(String nome);
    
    boolean existsByNome(String nome);
    
    List<PlanoCamisa> findByPermiteCompartilhamento(Boolean permiteCompartilhamento);
    
    List<PlanoCamisa> findByAtivo(Boolean ativo);
}
