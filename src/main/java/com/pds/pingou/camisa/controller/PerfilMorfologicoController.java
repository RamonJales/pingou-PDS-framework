package com.pds.pingou.camisa.controller;

import com.pds.pingou.camisa.dto.PerfilMorfologicoRequestDTO;
import com.pds.pingou.camisa.dto.PerfilMorfologicoResponseDTO;
import com.pds.pingou.camisa.entity.PerfilMorfologico;
import com.pds.pingou.camisa.mapper.PerfilMorfologicoMapper;
import com.pds.pingou.camisa.service.PerfilMorfologicoService;
import com.pds.pingou.security.user.User;
import com.pds.pingou.security.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/perfil-morfologico")
@RequiredArgsConstructor
public class PerfilMorfologicoController {
    
    private final PerfilMorfologicoService service;
    private final UserRepository userRepository;
    
    @PostMapping
    public ResponseEntity<PerfilMorfologicoResponseDTO> criarPerfil(
            @RequestBody PerfilMorfologicoRequestDTO request,
            Authentication authentication) {
        
        User user = userRepository.findByEmail(authentication.getName())
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        
        PerfilMorfologico perfil = PerfilMorfologicoMapper.toEntity(request);
        PerfilMorfologico saved = service.criarPerfil(user, perfil);
        
        return ResponseEntity.ok(PerfilMorfologicoMapper.toDTO(saved));
    }
    
    @GetMapping("/meu-perfil")
    public ResponseEntity<PerfilMorfologicoResponseDTO> getMeuPerfil(Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName())
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        
        PerfilMorfologico perfil = service.buscarPorUserId(user.getId());
        return ResponseEntity.ok(PerfilMorfologicoMapper.toDTO(perfil));
    }
    
    @PutMapping("/meu-perfil")
    public ResponseEntity<PerfilMorfologicoResponseDTO> atualizarMeuPerfil(
            @RequestBody PerfilMorfologicoRequestDTO request,
            Authentication authentication) {
        
        User user = userRepository.findByEmail(authentication.getName())
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        
        PerfilMorfologico novosDados = PerfilMorfologicoMapper.toEntity(request);
        PerfilMorfologico updated = service.atualizarPerfil(user.getId(), novosDados);
        
        return ResponseEntity.ok(PerfilMorfologicoMapper.toDTO(updated));
    }
    
    @DeleteMapping("/meu-perfil")
    public ResponseEntity<Void> deletarMeuPerfil(Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName())
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        
        service.deletarPerfil(user.getId());
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/usuario/{userId}")
    public ResponseEntity<PerfilMorfologicoResponseDTO> getPerfilPorUserId(@PathVariable Long userId) {
        PerfilMorfologico perfil = service.buscarPorUserId(userId);
        return ResponseEntity.ok(PerfilMorfologicoMapper.toDTO(perfil));
    }
}
