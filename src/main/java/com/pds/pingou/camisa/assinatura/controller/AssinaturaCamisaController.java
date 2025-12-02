package com.pds.pingou.camisa.assinatura.controller;

import com.pds.pingou.camisa.assinatura.AssinaturaCamisa;
import com.pds.pingou.camisa.assinatura.AssinaturaCamisaService;
import com.pds.pingou.camisa.assinatura.dto.AssinaturaCamisaMapper;
import com.pds.pingou.camisa.assinatura.dto.AssinaturaCamisaRequestDTO;
import com.pds.pingou.camisa.assinatura.dto.AssinaturaCamisaResponseDTO;
import com.pds.pingou.camisa.planos.PlanoCamisa;
import com.pds.pingou.camisa.planos.PlanoCamisaService;
import com.pds.pingou.framework.core.enums.SubscriptionStatus;
import com.pds.pingou.security.user.User;
import com.pds.pingou.security.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller REST para gerenciamento de assinaturas de camisas.
 */
@RestController
@RequestMapping("/api/assinaturas")
public class AssinaturaCamisaController {
    
    @Autowired
    private AssinaturaCamisaService service;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private PlanoCamisaService planoCamisaService;
    
    @GetMapping
    public ResponseEntity<List<AssinaturaCamisaResponseDTO>> listAll() {
        List<AssinaturaCamisaResponseDTO> assinaturas = service.findAll().stream()
            .map(AssinaturaCamisaMapper::toDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(assinaturas);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<AssinaturaCamisaResponseDTO> findById(@PathVariable Long id) {
        AssinaturaCamisa assinatura = service.findByIdOrThrow(id);
        return ResponseEntity.ok(AssinaturaCamisaMapper.toDTO(assinatura));
    }
    
    @PostMapping
    public ResponseEntity<AssinaturaCamisaResponseDTO> create(@RequestBody AssinaturaCamisaRequestDTO request) {
        User user = userService.findById(request.getUserId())
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        PlanoCamisa plano = planoCamisaService.findByIdOrThrow(request.getPlanoId());
        
        AssinaturaCamisa assinatura = new AssinaturaCamisa(user, plano);
        assinatura.setTamanhoPreferido(request.getTamanhoPreferido());
        assinatura.setTimesFavoritos(request.getTimesFavoritos());
        assinatura.setLigasPreferidas(request.getLigasPreferidas());
        assinatura.setAceitaPersonalizacao(request.getAceitaPersonalizacao());
        
        AssinaturaCamisa saved = service.save(assinatura);
        return ResponseEntity.status(HttpStatus.CREATED).body(AssinaturaCamisaMapper.toDTO(saved));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<AssinaturaCamisaResponseDTO> update(@PathVariable Long id, 
                                                               @RequestBody AssinaturaCamisaRequestDTO request) {
        AssinaturaCamisa assinatura = service.findByIdOrThrow(id);
        
        if (request.getPlanoId() != null) {
            PlanoCamisa plano = planoCamisaService.findByIdOrThrow(request.getPlanoId());
            assinatura.setPlano(plano);
        }
        
        assinatura.setTamanhoPreferido(request.getTamanhoPreferido());
        assinatura.setTimesFavoritos(request.getTimesFavoritos());
        assinatura.setLigasPreferidas(request.getLigasPreferidas());
        assinatura.setAceitaPersonalizacao(request.getAceitaPersonalizacao());
        
        AssinaturaCamisa updated = service.update(assinatura);
        return ResponseEntity.ok(AssinaturaCamisaMapper.toDTO(updated));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/usuario/{userId}")
    public ResponseEntity<AssinaturaCamisaResponseDTO> findByUserId(@PathVariable Long userId) {
        AssinaturaCamisa assinatura = service.findByUserId(userId);
        return ResponseEntity.ok(AssinaturaCamisaMapper.toDTO(assinatura));
    }
    
    @GetMapping("/ativas")
    public ResponseEntity<List<AssinaturaCamisaResponseDTO>> findAtivas() {
        List<AssinaturaCamisaResponseDTO> assinaturas = service.findAllActive().stream()
            .map(AssinaturaCamisaMapper::toDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(assinaturas);
    }
    
    @GetMapping("/status/{status}")
    public ResponseEntity<List<AssinaturaCamisaResponseDTO>> findByStatus(@PathVariable String status) {
        SubscriptionStatus subscriptionStatus = SubscriptionStatus.valueOf(status);
        List<AssinaturaCamisaResponseDTO> assinaturas = service.findByStatus(subscriptionStatus).stream()
            .map(AssinaturaCamisaMapper::toDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(assinaturas);
    }
    
    @PatchMapping("/{id}/ativar")
    public ResponseEntity<AssinaturaCamisaResponseDTO> ativar(@PathVariable Long id) {
        AssinaturaCamisa assinatura = service.findByIdOrThrow(id);
        assinatura.activate();
        AssinaturaCamisa updated = service.update(assinatura);
        return ResponseEntity.ok(AssinaturaCamisaMapper.toDTO(updated));
    }
    
    @PatchMapping("/{id}/desativar")
    public ResponseEntity<AssinaturaCamisaResponseDTO> desativar(@PathVariable Long id) {
        AssinaturaCamisa assinatura = service.findByIdOrThrow(id);
        assinatura.deactivate();
        AssinaturaCamisa updated = service.update(assinatura);
        return ResponseEntity.ok(AssinaturaCamisaMapper.toDTO(updated));
    }
    
    @PatchMapping("/{id}/suspender")
    public ResponseEntity<AssinaturaCamisaResponseDTO> suspender(@PathVariable Long id) {
        AssinaturaCamisa assinatura = service.findByIdOrThrow(id);
        assinatura.suspend();
        AssinaturaCamisa updated = service.update(assinatura);
        return ResponseEntity.ok(AssinaturaCamisaMapper.toDTO(updated));
    }
    
    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<AssinaturaCamisaResponseDTO> cancelar(@PathVariable Long id) {
        AssinaturaCamisa assinatura = service.findByIdOrThrow(id);
        assinatura.cancel();
        AssinaturaCamisa updated = service.update(assinatura);
        return ResponseEntity.ok(AssinaturaCamisaMapper.toDTO(updated));
    }
}
