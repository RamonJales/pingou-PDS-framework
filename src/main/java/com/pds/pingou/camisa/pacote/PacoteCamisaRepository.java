package com.pds.pingou.camisa.pacote;

import com.pds.pingou.camisa.planos.PlanoCamisa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repositório JPA para a entidade PacoteCamisa.
 */
@Repository
public interface PacoteCamisaRepository extends JpaRepository<PacoteCamisa, Long> {
    
    List<PacoteCamisa> findByPlano(PlanoCamisa plano);
    
    List<PacoteCamisa> findByPeriodoAndAno(Integer periodo, Integer ano);
    
    List<PacoteCamisa> findByAno(Integer ano);
    
    List<PacoteCamisa> findByAtivoTrue();
    
    @Query("SELECT p FROM PacoteCamisa p WHERE p.dataEntrega >= :dataInicio AND p.dataEntrega <= :dataFim")
    List<PacoteCamisa> findByDataEntregaBetween(@Param("dataInicio") LocalDate dataInicio, 
                                                  @Param("dataFim") LocalDate dataFim);
    
    @Query("SELECT p FROM PacoteCamisa p WHERE p.plano.id = :planoId AND p.ativo = true ORDER BY p.dataEntrega DESC")
    List<PacoteCamisa> findActivePacotesByPlano(@Param("planoId") Long planoId);
}
