package com.pds.pingou.camisa.repository;

import com.pds.pingou.camisa.entity.ItemPacoteCamisa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemPacoteCamisaRepository extends JpaRepository<ItemPacoteCamisa, Long> {
    
    List<ItemPacoteCamisa> findByPacoteId(Long pacoteId);
    
    List<ItemPacoteCamisa> findByCamisaId(Long camisaId);
    
    List<ItemPacoteCamisa> findByJaEnviada(Boolean jaEnviada);
}
