package com.pds.pingou.futebol.assinatura;

import com.pds.pingou.futebol.enums.TamanhoCamisa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository para operações de persistência de membros de assinatura.
 */
@Repository
public interface MembroAssinaturaRepository extends JpaRepository<MembroAssinatura, Long> {

    List<MembroAssinatura> findByAssinatura(AssinaturaFutebol assinatura);

    List<MembroAssinatura> findByAssinaturaId(Long assinaturaId);

    List<MembroAssinatura> findByAssinaturaAndAtivoTrue(AssinaturaFutebol assinatura);

    List<MembroAssinatura> findByTamanho(TamanhoCamisa tamanho);

    @Query("SELECT m FROM MembroAssinatura m WHERE m.assinatura.id = :assinaturaId AND m.ativo = true ORDER BY m.ordem")
    List<MembroAssinatura> findMembrosAtivosByAssinatura(@Param("assinaturaId") Long assinaturaId);

    @Query("SELECT m FROM MembroAssinatura m WHERE m.assinatura.id = :assinaturaId AND m.titular = true")
    MembroAssinatura findTitularByAssinatura(@Param("assinaturaId") Long assinaturaId);

    @Query("SELECT COUNT(m) FROM MembroAssinatura m WHERE m.assinatura.id = :assinaturaId AND m.ativo = true")
    int countMembrosAtivosByAssinatura(@Param("assinaturaId") Long assinaturaId);

    @Query("SELECT m FROM MembroAssinatura m WHERE m.timeFavorito = :time AND m.ativo = true")
    List<MembroAssinatura> findByTimeFavorito(@Param("time") String time);
}
