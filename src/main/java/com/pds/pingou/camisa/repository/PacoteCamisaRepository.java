package com.pds.pingou.camisa.repository;

import com.pds.pingou.camisa.entity.PacoteCamisa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PacoteCamisaRepository extends JpaRepository<PacoteCamisa, Long> {
    
    List<PacoteCamisa> findByPlanoId(Long planoId);
    
    List<PacoteCamisa> findByDataEnvio(LocalDate dataEnvio);
    
    List<PacoteCamisa> findByCurado(Boolean curado);
    
    List<PacoteCamisa> findByDataEnvioBetween(LocalDate inicio, LocalDate fim);
}
