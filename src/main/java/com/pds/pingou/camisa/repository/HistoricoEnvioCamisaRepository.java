package com.pds.pingou.camisa.repository;

import com.pds.pingou.camisa.entity.HistoricoEnvioCamisa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface HistoricoEnvioCamisaRepository extends JpaRepository<HistoricoEnvioCamisa, Long> {
    
    List<HistoricoEnvioCamisa> findByUserId(Long userId);
    
    List<HistoricoEnvioCamisa> findByAssinaturaId(Long assinaturaId);
    
    List<HistoricoEnvioCamisa> findByCamisaId(Long camisaId);
    
    List<HistoricoEnvioCamisa> findByDataEnvioAfter(LocalDateTime data);
    
    List<HistoricoEnvioCamisa> findByDataEntregaRealizadaIsNull();
}
