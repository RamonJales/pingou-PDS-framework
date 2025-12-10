package com.pds.pingou.camisa.repository;

import com.pds.pingou.camisa.entity.Camisa;
import com.pds.pingou.camisa.enums.MaterialCamisa;
import com.pds.pingou.camisa.enums.TamanhoCamisa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CamisaRepository extends JpaRepository<Camisa, Long> {
    
    List<Camisa> findByTamanhoAndEstoqueGreaterThan(TamanhoCamisa tamanho, Integer estoque);
    
    List<Camisa> findByTime(String time);
    
    List<Camisa> findByMaterial(MaterialCamisa material);
    
    List<Camisa> findByTamanho(TamanhoCamisa tamanho);
    
    List<Camisa> findByEstoqueGreaterThan(Integer estoque);
    
    List<Camisa> findByTimeAndTamanho(String time, TamanhoCamisa tamanho);
}
