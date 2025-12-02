package com.pds.pingou.pontuacao;

import com.pds.pingou.security.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoricoPontosRepository extends JpaRepository<HistoricoPontos, Long> {

    List<HistoricoPontos> findByUserOrderByDataHoraDesc(User user);

    List<HistoricoPontos> findByUserIdOrderByDataHoraDesc(Long userId);

    @Query("SELECT h FROM HistoricoPontos h WHERE h.user.id = :userId AND h.tipo = :tipo ORDER BY h.dataHora DESC")
    List<HistoricoPontos> findByUserIdAndTipo(@Param("userId") Long userId, @Param("tipo") TipoMovimentacao tipo);
}