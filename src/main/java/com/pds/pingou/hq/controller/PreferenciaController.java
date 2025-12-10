package com.pds.pingou.hq.controller;

import com.pds.pingou.hq.dto.QuizPreferenciasDTO;
import com.pds.pingou.hq.entity.PreferenciaUsuario;
import com.pds.pingou.hq.service.PreferenciaService;
import com.pds.pingou.security.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

/**
 * Controller para gerenciar preferências e quiz de onboarding
 */
@RestController
@RequestMapping("/api/hq/preferencias")
@CrossOrigin(origins = "*")
public class PreferenciaController {

    @Autowired
    private PreferenciaService service;

    /**
     * Completa o quiz de onboarding
     */
    @PostMapping("/quiz")
    public ResponseEntity<String> completarQuiz(
            @AuthenticationPrincipal User user,
            @RequestBody QuizPreferenciasDTO quiz) {
        
        service.configurarPreferenciasCompletas(
            user,
            quiz.getCategoriasFavoritas(),
            quiz.getEditorasFavoritas(),
            quiz.getPrefereClassicas(),
            quiz.getPrefereModernas(),
            quiz.getInteresseEdicoesColecionador()
        );

        return ResponseEntity.ok("Quiz de preferências completado com sucesso!");
    }

    /**
     * Busca preferências do usuário atual
     */
    @GetMapping("/minhas")
    public ResponseEntity<PreferenciaUsuario> getMinhasPreferencias(
            @AuthenticationPrincipal User user) {
        PreferenciaUsuario preferencias = service.findByUserOrCreate(user);
        return ResponseEntity.ok(preferencias);
    }

    /**
     * Verifica se usuário completou o quiz
     */
    @GetMapping("/quiz-completo")
    public ResponseEntity<Boolean> verificarQuizCompleto(
            @AuthenticationPrincipal User user) {
        boolean completo = service.usuarioCompletouQuiz(user);
        return ResponseEntity.ok(completo);
    }

    /**
     * Reseta preferências
     */
    @DeleteMapping("/resetar")
    public ResponseEntity<String> resetarPreferencias(
            @AuthenticationPrincipal User user) {
        service.resetarPreferencias(user);
        return ResponseEntity.ok("Preferências resetadas com sucesso!");
    }
}
