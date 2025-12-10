package com.pds.pingou.hq.repository;

import com.pds.pingou.hq.entity.PreferenciaUsuario;
import com.pds.pingou.security.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositório para operações com Preferências de Usuário
 */
@Repository
public interface PreferenciaUsuarioRepository extends JpaRepository<PreferenciaUsuario, Long> {

    /**
     * Busca preferências por usuário
     */
    Optional<PreferenciaUsuario> findByUser(User user);

    /**
     * Verifica se usuário possui preferências cadastradas
     */
    boolean existsByUser(User user);

    /**
     * Busca preferências de usuários que completaram o quiz
     */
    java.util.List<PreferenciaUsuario> findByQuizCompletoTrue();

    /**
     * Busca preferências de usuários que não completaram o quiz
     */
    java.util.List<PreferenciaUsuario> findByQuizCompletoFalse();
}
