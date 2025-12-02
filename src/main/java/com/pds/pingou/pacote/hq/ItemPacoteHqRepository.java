// src/main/java/com/pds/pingou/pacote/hq/ItemPacoteHqRepository.java
package com.pds.pingou.pacote.hq;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ItemPacoteHqRepository extends JpaRepository<ItemPacoteHq, Long> {
    List<ItemPacoteHq> findByPacote(PacoteHq pacote);
    List<ItemPacoteHq> findByHqId(Long hqId);
}