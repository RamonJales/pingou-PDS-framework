package com.pds.pingou.camisa;

import com.pds.pingou.camisa.enums.LigaCamisa;
import com.pds.pingou.camisa.enums.TipoCamisa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositório JPA para a entidade Camisa.
 */
@Repository
public interface CamisaRepository extends JpaRepository<Camisa, Long> {
    
    List<Camisa> findByTime(String time);
    
    List<Camisa> findByLiga(LigaCamisa liga);
    
    List<Camisa> findByTipoCamisa(TipoCamisa tipo);
    
    List<Camisa> findByAnoTemporada(Integer ano);
    
    List<Camisa> findByMarca(String marca);
    
    List<Camisa> findByAtivoTrue();
    
    List<Camisa> findByEdicaoLimitadaTrue();
    
    @Query("SELECT c FROM Camisa c WHERE c.estoqueQuantidade > 0 AND c.ativo = true")
    List<Camisa> findAvailableInStock();
    
    @Query("SELECT c FROM Camisa c WHERE c.time LIKE %:termo% OR c.descricao LIKE %:termo%")
    List<Camisa> searchByTerm(@Param("termo") String termo);
}
