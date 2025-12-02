package com.pds.pingou.camisa.pacote;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositório JPA para a entidade ItemPacoteCamisa.
 */
@Repository
public interface ItemPacoteCamisaRepository extends JpaRepository<ItemPacoteCamisa, Long> {
    
    List<ItemPacoteCamisa> findByPacote(PacoteCamisa pacote);
    
    @Query("SELECT i FROM ItemPacoteCamisa i WHERE i.pacote.id = :pacoteId")
    List<ItemPacoteCamisa> findByPacoteId(@Param("pacoteId") Long pacoteId);
    
    @Query("SELECT i FROM ItemPacoteCamisa i WHERE i.camisa.id = :camisaId")
    List<ItemPacoteCamisa> findByCamisaId(@Param("camisaId") Long camisaId);
}
