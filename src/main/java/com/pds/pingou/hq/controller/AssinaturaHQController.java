package com.pds.pingou.hq.controller;

import com.pds.pingou.hq.dto.AssinaturaHQRequestDTO;
import com.pds.pingou.hq.dto.AssinaturaHQResponseDTO;
import com.pds.pingou.hq.entity.AssinaturaHQ;
import com.pds.pingou.hq.entity.PlanoHQ;
import com.pds.pingou.hq.mapper.AssinaturaHQMapper;
import com.pds.pingou.hq.service.AssinaturaHQService;
import com.pds.pingou.hq.service.PlanoHQService;
import com.pds.pingou.security.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller REST para gerenciar Assinaturas de HQ
 */
@RestController
@RequestMapping("/api/hq/assinaturas")
@CrossOrigin(origins = "*")
public class AssinaturaHQController {

    @Autowired
    private AssinaturaHQService service;

    @Autowired
    private PlanoHQService planoService;

    /**
     * Cria/ativa uma assinatura
     */
    @PostMapping("/ativar")
    public ResponseEntity<AssinaturaHQResponseDTO> ativarAssinatura(
            @AuthenticationPrincipal User user,
            @RequestBody AssinaturaHQRequestDTO request) {
        
        PlanoHQ plano = planoService.findByIdOrThrow(request.getPlanoId());
        AssinaturaHQ assinatura = service.activateSubscription(user, plano);
        
        return ResponseEntity.ok(AssinaturaHQMapper.toDTO(assinatura));
    }

    /**
     * Busca minha assinatura
     */
    @GetMapping("/minha")
    public ResponseEntity<AssinaturaHQResponseDTO> getMinhaAssinatura(
            @AuthenticationPrincipal User user) {
        AssinaturaHQ assinatura = service.findByUser(user);
        return ResponseEntity.ok(AssinaturaHQMapper.toDTO(assinatura));
    }

    /**
     * Verifica se tenho assinatura ativa
     */
    @GetMapping("/ativa")
    public ResponseEntity<Boolean> verificarAssinaturaAtiva(
            @AuthenticationPrincipal User user) {
        boolean ativa = service.hasActiveSubscription(user);
        return ResponseEntity.ok(ativa);
    }

    /**
     * Desativa minha assinatura
     */
    @PutMapping("/desativar")
    public ResponseEntity<AssinaturaHQResponseDTO> desativarAssinatura(
            @AuthenticationPrincipal User user) {
        AssinaturaHQ assinatura = service.findByUser(user);
        assinatura = service.deactivateSubscription(assinatura.getId());
        return ResponseEntity.ok(AssinaturaHQMapper.toDTO(assinatura));
    }

    /**
     * Suspende minha assinatura
     */
    @PutMapping("/suspender")
    public ResponseEntity<AssinaturaHQResponseDTO> suspenderAssinatura(
            @AuthenticationPrincipal User user) {
        AssinaturaHQ assinatura = service.findByUser(user);
        assinatura = service.suspendSubscription(assinatura.getId());
        return ResponseEntity.ok(AssinaturaHQMapper.toDTO(assinatura));
    }

    /**
     * Cancela minha assinatura
     */
    @PutMapping("/cancelar")
    public ResponseEntity<AssinaturaHQResponseDTO> cancelarAssinatura(
            @AuthenticationPrincipal User user) {
        AssinaturaHQ assinatura = service.findByUser(user);
        assinatura = service.cancelSubscription(assinatura.getId());
        return ResponseEntity.ok(AssinaturaHQMapper.toDTO(assinatura));
    }

    /**
     * Resgata pontos
     */
    @PostMapping("/resgatar-pontos")
    public ResponseEntity<AssinaturaHQResponseDTO> resgatarPontos(
            @AuthenticationPrincipal User user,
            @RequestParam int pontos) {
        AssinaturaHQ assinatura = service.findByUser(user);
        assinatura = service.resgatarPontos(assinatura.getId(), pontos);
        return ResponseEntity.ok(AssinaturaHQMapper.toDTO(assinatura));
    }

    /**
     * Troca de plano
     */
    @PutMapping("/trocar-plano")
    public ResponseEntity<AssinaturaHQResponseDTO> trocarPlano(
            @AuthenticationPrincipal User user,
            @RequestBody AssinaturaHQRequestDTO request) {
        AssinaturaHQ assinatura = service.findByUser(user);
        PlanoHQ novoPlano = planoService.findByIdOrThrow(request.getPlanoId());
        assinatura = service.trocarPlano(assinatura.getId(), novoPlano);
        return ResponseEntity.ok(AssinaturaHQMapper.toDTO(assinatura));
    }

    /**
     * Realiza upgrade de plano
     */
    @PutMapping("/upgrade")
    public ResponseEntity<AssinaturaHQResponseDTO> realizarUpgrade(
            @AuthenticationPrincipal User user,
            @RequestBody AssinaturaHQRequestDTO request) {
        AssinaturaHQ assinatura = service.findByUser(user);
        PlanoHQ planoSuperior = planoService.findByIdOrThrow(request.getPlanoId());
        assinatura = service.realizarUpgrade(assinatura.getId(), planoSuperior);
        return ResponseEntity.ok(AssinaturaHQMapper.toDTO(assinatura));
    }

    /**
     * Lista todas assinaturas (admin)
     */
    @GetMapping
    public ResponseEntity<List<AssinaturaHQResponseDTO>> listarTodas() {
        List<AssinaturaHQResponseDTO> assinaturas = service.findAll().stream()
                .map(AssinaturaHQMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(assinaturas);
    }

    /**
     * Busca assinaturas elegíveis para upgrade
     */
    @GetMapping("/elegiveis-upgrade")
    public ResponseEntity<List<AssinaturaHQResponseDTO>> getElegiveisParaUpgrade() {
        List<AssinaturaHQResponseDTO> assinaturas = service.findElegiveisParaUpgrade().stream()
                .map(AssinaturaHQMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(assinaturas);
    }

    /**
     * Estatísticas gerais
     */
    @GetMapping("/estatisticas")
    public ResponseEntity<String> getEstatisticas() {
        Long totalPontos = service.getTotalPontosDistribuidos();
        Long totalHQs = service.getTotalHQsDistribuidas();
        
        String stats = String.format(
            "Total de pontos distribuídos: %d | Total de HQs distribuídas: %d",
            totalPontos != null ? totalPontos : 0,
            totalHQs != null ? totalHQs : 0
        );
        
        return ResponseEntity.ok(stats);
    }
}
