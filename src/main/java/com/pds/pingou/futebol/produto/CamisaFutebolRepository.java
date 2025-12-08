package com.pds.pingou.futebol.produto;

import com.pds.pingou.futebol.enums.Competicao;
import com.pds.pingou.futebol.enums.TipoCamisa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository para operações de persistência de camisas de futebol.
 */
@Repository
public interface CamisaFutebolRepository extends JpaRepository<CamisaFutebol, Long> {

    List<CamisaFutebol> findByAtivoTrue();

    List<CamisaFutebol> findByTime(String time);

    List<CamisaFutebol> findByTimeContainingIgnoreCase(String time);

    List<CamisaFutebol> findByTimeAndTemporada(String time, String temporada);

    List<CamisaFutebol> findByTipoCamisa(TipoCamisa tipoCamisa);

    List<CamisaFutebol> findByCompeticao(Competicao competicao);

    List<CamisaFutebol> findByMarcaIgnoreCase(String marca);

    List<CamisaFutebol> findByTemporada(String temporada);

    @Query("SELECT c FROM CamisaFutebol c WHERE c.ativo = true AND c.time IN :times")
    List<CamisaFutebol> findByTimesPreferidos(@Param("times") List<String> times);

    @Query("SELECT c FROM CamisaFutebol c WHERE c.ativo = true AND c.competicao IN :competicoes")
    List<CamisaFutebol> findByCompeticoes(@Param("competicoes") List<Competicao> competicoes);

    @Query("SELECT DISTINCT c.time FROM CamisaFutebol c WHERE c.ativo = true ORDER BY c.time")
    List<String> findAllTimes();

    @Query("SELECT DISTINCT c.temporada FROM CamisaFutebol c ORDER BY c.temporada DESC")
    List<String> findAllTemporadas();

    @Query("SELECT c FROM CamisaFutebol c WHERE c.ativo = true AND c.tipoCamisa IN :tipos")
    List<CamisaFutebol> findByTiposCamisa(@Param("tipos") List<TipoCamisa> tipos);

    boolean existsByTimeAndTemporadaAndTipoCamisa(String time, String temporada, TipoCamisa tipoCamisa);
}
