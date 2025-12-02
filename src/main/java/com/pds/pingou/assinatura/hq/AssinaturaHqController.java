package com.pds.pingou.assinatura.hq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/assinaturas-hq")
public class AssinaturaHqController {

    @Autowired
    private AssinaturaHqService assinaturaHqService;

    @PostMapping("/ativar")
    public ResponseEntity<AssinaturaHqResponseDTO> ativar(@RequestBody AssinaturaHqRequestDTO dto) {
        AssinaturaHqResponseDTO assinatura = assinaturaHqService.ativar(dto.getUserId(), dto.getPlanoId());
        return ResponseEntity.ok(assinatura);
    }

    @PostMapping("/desativar/{userId}")
    public ResponseEntity<AssinaturaHqResponseDTO> desativar(@PathVariable Long userId) {
        AssinaturaHqResponseDTO assinatura = assinaturaHqService.desativar(userId);
        return ResponseEntity.ok(assinatura);
    }

    @GetMapping
    public List<AssinaturaHqResponseDTO> listarTodas() {
        return assinaturaHqService.listarTodas();
    }

    @GetMapping("/ativas")
    public List<AssinaturaHqResponseDTO> listarAtivas() {
        return assinaturaHqService.listarAtivas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssinaturaHqResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(assinaturaHqService.buscarPorId(id));
    }

    @GetMapping("/usuario/{userId}")
    public ResponseEntity<AssinaturaHqResponseDTO> buscarPorUsuario(@PathVariable Long userId) {
        return ResponseEntity.ok(assinaturaHqService.buscarPorUsuario(userId));
    }

    @PostMapping
    public ResponseEntity<AssinaturaHqResponseDTO> criar(@RequestBody AssinaturaHqRequestDTO dto) {
        AssinaturaHqResponseDTO assinatura = assinaturaHqService.criar(dto);
        return ResponseEntity.ok(assinatura);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        assinaturaHqService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}