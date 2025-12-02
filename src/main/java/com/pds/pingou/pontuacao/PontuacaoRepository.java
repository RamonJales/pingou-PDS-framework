package com.pds.pingou.pontuacao;

import com.pds.pingou.security.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PontuacaoRepository extends JpaRepository<Pontuacao, Long> {
    Optional<Pontuacao> findByUser(User user);
    Optional<Pontuacao> findByUserId(Long userId);
    boolean existsByUser(User user);
}