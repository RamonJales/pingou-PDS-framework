package com.pds.pingou.futebol.assinatura;

import com.pds.pingou.futebol.assinatura.dto.AssinaturaFutebolRequestDTO;
import com.pds.pingou.futebol.assinatura.dto.AssinaturaFutebolResponseDTO;
import com.pds.pingou.futebol.assinatura.dto.MembroAssinaturaRequestDTO;
import com.pds.pingou.futebol.assinatura.dto.MembroAssinaturaResponseDTO;
import com.pds.pingou.futebol.enums.TamanhoCamisa;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller REST para gerenciamento de assinaturas de futebol.
 * 
 * Este controller expõe endpoints para:
 * - Criar assinaturas com múltiplos membros (planos família)
 * - Gerenciar membros e seus tamanhos
 * - Trocar tamanhos de camisas
 * - Suspender/Reativar/Cancelar assinaturas
 */
@RestController
@RequestMapping("/api/futebol/assinaturas")
public class AssinaturaFutebolController {

    @Autowired
    private AssinaturaFutebolService assinaturaService;

    /**
     * Busca a assinatura do usuário logado.
     */
    @GetMapping("/minha")
    public ResponseEntity<AssinaturaFutebolResponseDTO> minhaAssinatura(
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(assinaturaService.buscarPorEmail(userDetails.getUsername()));
    }

    /**
     * Busca assinatura por ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AssinaturaFutebolResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(assinaturaService.buscarPorId(id));
    }

    /**
     * Busca assinatura por email.
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<AssinaturaFutebolResponseDTO> buscarPorEmail(@PathVariable String email) {
        return ResponseEntity.ok(assinaturaService.buscarPorEmail(email));
    }

    /**
     * Lista todas as assinaturas ativas.
     */
    @GetMapping("/ativas")
    public ResponseEntity<List<AssinaturaFutebolResponseDTO>> listarAtivas() {
        return ResponseEntity.ok(assinaturaService.listarAtivas());
    }

    /**
     * Cria uma nova assinatura para o usuário logado.
     * 
     * Exemplo de request body para plano família:
     * {
     *   "planoId": 2,
     *   "membros": [
     *     {"nome": "João (Pai)", "tamanho": "G", "titular": true, "timeFavorito": "Flamengo"},
     *     {"nome": "Maria (Mãe)", "tamanho": "M", "timeFavorito": "Flamengo"},
     *     {"nome": "Pedro (Filho)", "tamanho": "INF_12", "timeFavorito": "Flamengo"},
     *     {"nome": "Ana (Filha)", "tamanho": "INF_8", "timeFavorito": "Flamengo"}
     *   ],
     *   "timeFavoritoPrincipal": "Flamengo",
     *   "timesSecundarios": "Brasil,Real Madrid",
     *   "aceitaTimesRivais": false
     * }
     */
    @PostMapping
    public ResponseEntity<AssinaturaFutebolResponseDTO> criar(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody AssinaturaFutebolRequestDTO dto) {
        AssinaturaFutebolResponseDTO response = assinaturaService.criar(userDetails.getUsername(), dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Adiciona um novo membro à assinatura (upgrade plano família).
     */
    @PostMapping("/{assinaturaId}/membros")
    public ResponseEntity<MembroAssinaturaResponseDTO> adicionarMembro(
            @PathVariable Long assinaturaId,
            @Valid @RequestBody MembroAssinaturaRequestDTO dto) {
        MembroAssinaturaResponseDTO response = assinaturaService.adicionarMembro(assinaturaId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Lista membros de uma assinatura.
     */
    @GetMapping("/{assinaturaId}/membros")
    public ResponseEntity<List<MembroAssinaturaResponseDTO>> listarMembros(@PathVariable Long assinaturaId) {
        return ResponseEntity.ok(assinaturaService.listarMembros(assinaturaId));
    }

    /**
     * Atualiza informações de um membro.
     */
    @PutMapping("/membros/{membroId}")
    public ResponseEntity<MembroAssinaturaResponseDTO> atualizarMembro(
            @PathVariable Long membroId,
            @Valid @RequestBody MembroAssinaturaRequestDTO dto) {
        return ResponseEntity.ok(assinaturaService.atualizarMembro(membroId, dto));
    }

    /**
     * Troca o tamanho de um membro.
     * 
     * Importante: Há limite de trocas por ano definido no plano.
     */
    @PatchMapping("/membros/{membroId}/tamanho")
    public ResponseEntity<MembroAssinaturaResponseDTO> trocarTamanho(
            @PathVariable Long membroId,
            @RequestParam TamanhoCamisa novoTamanho) {
        return ResponseEntity.ok(assinaturaService.atualizarTamanhoMembro(membroId, novoTamanho));
    }

    /**
     * Remove um membro da assinatura.
     * Nota: Não é possível remover o titular.
     */
    @DeleteMapping("/membros/{membroId}")
    public ResponseEntity<Void> removerMembro(@PathVariable Long membroId) {
        assinaturaService.removerMembro(membroId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Retorna os tamanhos necessários para uma assinatura.
     * Útil para operações de estoque e preparação de pacotes.
     */
    @GetMapping("/{assinaturaId}/tamanhos")
    public ResponseEntity<List<TamanhoCamisa>> getTamanhosNecessarios(@PathVariable Long assinaturaId) {
        return ResponseEntity.ok(assinaturaService.getTamanhosNecessarios(assinaturaId));
    }

    /**
     * Suspende uma assinatura.
     */
    @PatchMapping("/{id}/suspender")
    public ResponseEntity<Void> suspender(@PathVariable Long id) {
        assinaturaService.suspender(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Reativa uma assinatura suspensa.
     */
    @PatchMapping("/{id}/reativar")
    public ResponseEntity<Void> reativar(@PathVariable Long id) {
        assinaturaService.reativar(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Cancela uma assinatura.
     */
    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<Void> cancelar(@PathVariable Long id) {
        assinaturaService.cancelar(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Renova uma assinatura (reseta contadores e estende validade).
     */
    @PostMapping("/{id}/renovar")
    public ResponseEntity<AssinaturaFutebolResponseDTO> renovar(@PathVariable Long id) {
        return ResponseEntity.ok(assinaturaService.renovar(id));
    }
}
