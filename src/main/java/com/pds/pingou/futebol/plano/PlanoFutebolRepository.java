package com.pds.pingou.futebol.plano;

import com.pds.pingou.futebol.enums.TipoPlanoFutebol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository para operações de persistência de planos de futebol.
 */
@Repository
public interface PlanoFutebolRepository extends JpaRepository<PlanoFutebol, Long> {

    Optional<PlanoFutebol> findByNome(String nome);

    boolean existsByNome(String nome);

    List<PlanoFutebol> findByAtivoTrue();

    List<PlanoFutebol> findByTipoPlano(TipoPlanoFutebol tipoPlano);

    List<PlanoFutebol> findByTipoPlanoAndAtivoTrue(TipoPlanoFutebol tipoPlano);

    List<PlanoFutebol> findByPersonalizacaoInclusaTrue();

    List<PlanoFutebol> findByPrioridadeEdicaoLimitadaTrue();
}
