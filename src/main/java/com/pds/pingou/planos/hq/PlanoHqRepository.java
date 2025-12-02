package com.pds.pingou.planos.hq;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlanoHqRepository extends JpaRepository<PlanoHq, Long> {
    Optional<PlanoHq> findByNome(String nome);
    boolean existsByNome(String nome);
    List<PlanoHq> findByAtivoTrue();
    List<PlanoHq> findByTipoPlano(TipoPlanoHq tipoPlano);
    List<PlanoHq> findByTipoPlanoAndAtivoTrue(TipoPlanoHq tipoPlano);
}