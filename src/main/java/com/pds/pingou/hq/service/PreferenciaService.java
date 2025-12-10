package com.pds.pingou.hq.service;

import com.pds.pingou.hq.entity.PreferenciaUsuario;
import com.pds.pingou.hq.enums.CategoriaHQ;
import com.pds.pingou.hq.enums.EditoraHQ;
import com.pds.pingou.hq.exception.PreferenciaNotFoundException;
import com.pds.pingou.hq.repository.PreferenciaUsuarioRepository;
import com.pds.pingou.security.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * Serviço para gerenciar preferências e quiz de onboarding
 */
@Service
@Transactional
public class PreferenciaService {

    @Autowired
    private PreferenciaUsuarioRepository repository;

    /**
     * Busca preferências por usuário
     */
    public PreferenciaUsuario findByUser(User user) {
        return repository.findByUser(user)
            .orElseThrow(() -> new PreferenciaNotFoundException(user.getId()));
    }

    /**
     * Busca preferências por usuário ou cria uma nova
     */
    public PreferenciaUsuario findByUserOrCreate(User user) {
        return repository.findByUser(user)
            .orElseGet(() -> {
                PreferenciaUsuario preferencia = new PreferenciaUsuario(user);
                return repository.save(preferencia);
            });
    }

    /**
     * Verifica se usuário possui preferências
     */
    public boolean existsByUser(User user) {
        return repository.existsByUser(user);
    }

    /**
     * Salva ou atualiza preferências
     */
    public PreferenciaUsuario save(PreferenciaUsuario preferencia) {
        return repository.save(preferencia);
    }

    /**
     * Adiciona uma categoria favorita
     */
    public PreferenciaUsuario adicionarCategoria(User user, CategoriaHQ categoria) {
        PreferenciaUsuario preferencia = findByUserOrCreate(user);
        preferencia.adicionarCategoria(categoria);
        return repository.save(preferencia);
    }

    /**
     * Remove uma categoria favorita
     */
    public PreferenciaUsuario removerCategoria(User user, CategoriaHQ categoria) {
        PreferenciaUsuario preferencia = findByUser(user);
        preferencia.removerCategoria(categoria);
        return repository.save(preferencia);
    }

    /**
     * Adiciona uma editora favorita
     */
    public PreferenciaUsuario adicionarEditora(User user, EditoraHQ editora) {
        PreferenciaUsuario preferencia = findByUserOrCreate(user);
        preferencia.adicionarEditora(editora);
        return repository.save(preferencia);
    }

    /**
     * Remove uma editora favorita
     */
    public PreferenciaUsuario removerEditora(User user, EditoraHQ editora) {
        PreferenciaUsuario preferencia = findByUser(user);
        preferencia.removerEditora(editora);
        return repository.save(preferencia);
    }

    /**
     * Configura preferências completas (usado no quiz de onboarding)
     */
    public PreferenciaUsuario configurarPreferenciasCompletas(
        User user,
        Set<CategoriaHQ> categorias,
        Set<EditoraHQ> editoras,
        Boolean prefereClassicas,
        Boolean prefereModernas,
        Boolean interesseEdicoesColecionador
    ) {
        PreferenciaUsuario preferencia = findByUserOrCreate(user);
        
        // Define categorias
        preferencia.getCategoriasFavoritas().clear();
        if (categorias != null) {
            preferencia.getCategoriasFavoritas().addAll(categorias);
        }
        
        // Define editoras
        preferencia.getEditorasFavoritas().clear();
        if (editoras != null) {
            preferencia.getEditorasFavoritas().addAll(editoras);
        }
        
        // Define outras preferências
        preferencia.setPrefereClassicas(prefereClassicas);
        preferencia.setPrefereModernas(prefereModernas);
        preferencia.setInteresseEdicoesColecionador(interesseEdicoesColecionador);
        
        // Marca quiz como completo
        preferencia.marcarQuizCompleto();
        
        return repository.save(preferencia);
    }

    /**
     * Busca usuários que completaram o quiz
     */
    public List<PreferenciaUsuario> findQuizCompletos() {
        return repository.findByQuizCompletoTrue();
    }

    /**
     * Busca usuários que não completaram o quiz
     */
    public List<PreferenciaUsuario> findQuizIncompletos() {
        return repository.findByQuizCompletoFalse();
    }

    /**
     * Verifica se usuário completou o quiz
     */
    public boolean usuarioCompletouQuiz(User user) {
        return repository.findByUser(user)
            .map(PreferenciaUsuario::getQuizCompleto)
            .orElse(false);
    }

    /**
     * Reseta as preferências do usuário
     */
    public void resetarPreferencias(User user) {
        PreferenciaUsuario preferencia = findByUser(user);
        preferencia.getCategoriasFavoritas().clear();
        preferencia.getEditorasFavoritas().clear();
        preferencia.setPrefereClassicas(false);
        preferencia.setPrefereModernas(false);
        preferencia.setInteresseEdicoesColecionador(false);
        preferencia.setQuizCompleto(false);
        repository.save(preferencia);
    }

    /**
     * Deleta preferências de um usuário
     */
    public void deleteByUser(User user) {
        repository.findByUser(user).ifPresent(repository::delete);
    }
}
