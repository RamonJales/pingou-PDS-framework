package com.pds.pingou.futebol.pacote;

import com.pds.pingou.futebol.assinatura.MembroAssinatura;
import com.pds.pingou.futebol.enums.TamanhoCamisa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository para operações de persistência de itens de pacote de futebol.
 */
@Repository
public interface ItemPacoteFutebolRepository extends JpaRepository<ItemPacoteFutebol, Long> {

    List<ItemPacoteFutebol> findByPacote(PacoteFutebol pacote);

    List<ItemPacoteFutebol> findByPacoteId(Long pacoteId);

    List<ItemPacoteFutebol> findByTamanho(TamanhoCamisa tamanho);

    List<ItemPacoteFutebol> findByMembroDestino(MembroAssinatura membro);

    List<ItemPacoteFutebol> findByMembroDestinoId(Long membroId);

    @Query("SELECT i FROM ItemPacoteFutebol i WHERE i.pacote.id = :pacoteId AND i.membroDestino.id = :membroId")
    List<ItemPacoteFutebol> findByPacoteAndMembro(@Param("pacoteId") Long pacoteId, 
                                                    @Param("membroId") Long membroId);

    @Query("SELECT i FROM ItemPacoteFutebol i WHERE i.statusItem = :status")
    List<ItemPacoteFutebol> findByStatus(@Param("status") String status);

    @Query("SELECT i FROM ItemPacoteFutebol i WHERE i.pacote.id = :pacoteId AND i.statusItem = 'PENDENTE'")
    List<ItemPacoteFutebol> findItensPendentesByPacote(@Param("pacoteId") Long pacoteId);

    @Query("SELECT COUNT(i) FROM ItemPacoteFutebol i WHERE i.pacote.id = :pacoteId")
    int countByPacote(@Param("pacoteId") Long pacoteId);
}
