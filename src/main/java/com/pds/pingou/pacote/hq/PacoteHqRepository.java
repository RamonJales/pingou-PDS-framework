// src/main/java/com/pds/pingou/pacote/hq/PacoteHqRepository.java
package com.pds.pingou.pacote.hq;

import com.pds.pingou.planos.hq.PlanoHq;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PacoteHqRepository extends JpaRepository<PacoteHq, Long> {
    List<PacoteHq> findByPlano(PlanoHq plano);
    List<PacoteHq> findByMesAndAno(Integer mes, Integer ano);
}