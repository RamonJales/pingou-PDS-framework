package com.pds.pingou.camisa.controller;

import com.pds.pingou.camisa.dto.AdicionarParticipanteRequestDTO;
import com.pds.pingou.camisa.dto.AssinaturaCamisaRequestDTO;
import com.pds.pingou.camisa.dto.AssinaturaCamisaResponseDTO;
import com.pds.pingou.camisa.dto.ParticipanteResponseDTO;
import com.pds.pingou.camisa.entity.AssinaturaCamisa;
import com.pds.pingou.camisa.entity.PlanoCamisa;
import com.pds.pingou.camisa.mapper.AssinaturaCamisaMapper;
import com.pds.pingou.camisa.service.AssinaturaCamisaService;
import com.pds.pingou.camisa.service.PlanoCamisaService;
import com.pds.pingou.security.user.User;
import com.pds.pingou.security.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/assinaturas-camisa")
@RequiredArgsConstructor
public class AssinaturaCamisaController {
    
    private final AssinaturaCamisaService service;
    private final PlanoCamisaService planoService;
    private final UserRepository userRepository;
    
    @PostMapping
    public ResponseEntity<AssinaturaCamisaResponseDTO> criarAssinatura(
            @RequestBody AssinaturaCamisaRequestDTO request,
            Authentication authentication) {
        
        User user = userRepository.findByEmail(authentication.getName())
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        
        PlanoCamisa plano = planoService.findByIdOrThrow(request.getPlanoId());
        
        AssinaturaCamisa assinatura = service.criarAssinatura(
            user, 
            plano, 
            request.getTimeFavorito(), 
            request.getTimeRival()
        );
        
        return ResponseEntity.ok(AssinaturaCamisaMapper.toDTO(assinatura));
    }
    
    @GetMapping("/minha-assinatura")
    public ResponseEntity<AssinaturaCamisaResponseDTO> getMinhaAssinatura(Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName())
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        
        AssinaturaCamisa assinatura = service.buscarPorUserId(user.getId());
        return ResponseEntity.ok(AssinaturaCamisaMapper.toDTO(assinatura));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<AssinaturaCamisaResponseDTO> getById(@PathVariable Long id) {
        AssinaturaCamisa assinatura = service.findByIdOrThrow(id);
        return ResponseEntity.ok(AssinaturaCamisaMapper.toDTO(assinatura));
    }
    
    @GetMapping
    public ResponseEntity<List<AssinaturaCamisaResponseDTO>> getAll() {
        List<AssinaturaCamisa> assinaturas = service.findAll();
        return ResponseEntity.ok(
            assinaturas.stream()
                .map(AssinaturaCamisaMapper::toDTO)
                .collect(Collectors.toList())
        );
    }
    
    @PostMapping("/{assinaturaId}/ativar")
    public ResponseEntity<AssinaturaCamisaResponseDTO> ativarAssinatura(@PathVariable Long assinaturaId) {
        AssinaturaCamisa assinatura = service.findByIdOrThrow(assinaturaId);
        assinatura.activate();
        AssinaturaCamisa updated = service.update(assinatura);
        return ResponseEntity.ok(AssinaturaCamisaMapper.toDTO(updated));
    }
    
    @PostMapping("/{assinaturaId}/suspender")
    public ResponseEntity<AssinaturaCamisaResponseDTO> suspenderAssinatura(@PathVariable Long assinaturaId) {
        AssinaturaCamisa assinatura = service.findByIdOrThrow(assinaturaId);
        assinatura.suspend();
        AssinaturaCamisa updated = service.update(assinatura);
        return ResponseEntity.ok(AssinaturaCamisaMapper.toDTO(updated));
    }
    
    @PostMapping("/{assinaturaId}/cancelar")
    public ResponseEntity<AssinaturaCamisaResponseDTO> cancelarAssinatura(@PathVariable Long assinaturaId) {
        AssinaturaCamisa assinatura = service.findByIdOrThrow(assinaturaId);
        assinatura.cancel();
        AssinaturaCamisa updated = service.update(assinatura);
        return ResponseEntity.ok(AssinaturaCamisaMapper.toDTO(updated));
    }
    
    @PostMapping("/{assinaturaId}/participantes")
    public ResponseEntity<AssinaturaCamisaResponseDTO> adicionarParticipante(
            @PathVariable Long assinaturaId,
            @RequestBody AdicionarParticipanteRequestDTO request) {
        
        User novoParticipante = userRepository.findById(request.getNovoParticipanteUserId())
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        
        AssinaturaCamisa assinatura = service.adicionarParticipante(
            assinaturaId, 
            novoParticipante, 
            request.getTimeFavorito(), 
            request.getTimeRival()
        );
        
        return ResponseEntity.ok(AssinaturaCamisaMapper.toDTO(assinatura));
    }
    
    @DeleteMapping("/{assinaturaId}/participantes/{participanteId}")
    public ResponseEntity<Void> removerParticipante(
            @PathVariable Long assinaturaId,
            @PathVariable Long participanteId) {
        
        service.removerParticipante(assinaturaId, participanteId);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/{assinaturaId}/participantes")
    public ResponseEntity<List<ParticipanteResponseDTO>> getParticipantes(@PathVariable Long assinaturaId) {
        List<AssinaturaCamisa> participantes = service.buscarParticipantes(assinaturaId);
        return ResponseEntity.ok(
            participantes.stream()
                .map(AssinaturaCamisaMapper::toParticipanteDTO)
                .collect(Collectors.toList())
        );
    }
}
