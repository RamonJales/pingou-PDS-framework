package com.pds.pingou.futebol.pacote;

import com.pds.pingou.futebol.plano.PlanoFutebol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repository para operações de persistência de pacotes de futebol.
 */
@Repository
public interface PacoteFutebolRepository extends JpaRepository<PacoteFutebol, Long> {

    List<PacoteFutebol> findByPlano(PlanoFutebol plano);

    List<PacoteFutebol> findByPlanoId(Long planoId);

    List<PacoteFutebol> findByMesAndAno(Integer mes, Integer ano);

    List<PacoteFutebol> findByAno(Integer ano);

    List<PacoteFutebol> findByAtivoTrue();

    List<PacoteFutebol> findByEdicaoLimitadaTrue();

    @Query("SELECT p FROM PacoteFutebol p WHERE p.plano.id = :planoId AND p.ativo = true")
    List<PacoteFutebol> findPacotesAtivosByPlano(@Param("planoId") Long planoId);

    @Query("SELECT p FROM PacoteFutebol p WHERE p.dataEntrega >= :dataInicio AND p.dataEntrega <= :dataFim")
    List<PacoteFutebol> findByPeriodoEntrega(@Param("dataInicio") LocalDate dataInicio, 
                                               @Param("dataFim") LocalDate dataFim);

    @Query("SELECT p FROM PacoteFutebol p WHERE p.tematica = :tematica AND p.ativo = true")
    List<PacoteFutebol> findByTematica(@Param("tematica") String tematica);

    @Query("SELECT p FROM PacoteFutebol p WHERE p.edicaoLimitada = true AND p.quantidadeVendida < p.quantidadeMaxima")
    List<PacoteFutebol> findEdicoesLimitadasDisponiveis();
}
