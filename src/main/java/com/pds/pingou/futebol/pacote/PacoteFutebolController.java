package com.pds.pingou.futebol.pacote;

import com.pds.pingou.futebol.pacote.dto.ItemPacoteFutebolRequestDTO;
import com.pds.pingou.futebol.pacote.dto.ItemPacoteFutebolResponseDTO;
import com.pds.pingou.futebol.pacote.dto.PacoteFutebolRequestDTO;
import com.pds.pingou.futebol.pacote.dto.PacoteFutebolResponseDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller REST para gerenciamento de pacotes de camisas de futebol.
 * 
 * Este controller expõe endpoints para:
 * - CRUD de pacotes base
 * - Geração de pacotes personalizados para assinaturas família
 * - Gerenciamento de itens de pacote
 * - Rastreamento de status de envio
 */
@RestController
@RequestMapping("/api/futebol/pacotes")
public class PacoteFutebolController {

    @Autowired
    private PacoteFutebolService pacoteService;

    /**
     * Lista todos os pacotes.
     */
    @GetMapping
    public ResponseEntity<List<PacoteFutebolResponseDTO>> listarTodos() {
        return ResponseEntity.ok(pacoteService.listarTodos());
    }

    /**
     * Busca pacote por ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PacoteFutebolResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(pacoteService.buscarPorId(id));
    }

    /**
     * Lista pacotes por plano.
     */
    @GetMapping("/plano/{planoId}")
    public ResponseEntity<List<PacoteFutebolResponseDTO>> listarPorPlano(@PathVariable Long planoId) {
        return ResponseEntity.ok(pacoteService.listarPorPlano(planoId));
    }

    /**
     * Lista pacotes por período (mês/ano).
     */
    @GetMapping("/periodo")
    public ResponseEntity<List<PacoteFutebolResponseDTO>> listarPorPeriodo(
            @RequestParam Integer mes,
            @RequestParam Integer ano) {
        return ResponseEntity.ok(pacoteService.listarPorPeriodo(mes, ano));
    }

    /**
     * Lista pacotes disponíveis para o usuário logado.
     */
    @GetMapping("/meus")
    public ResponseEntity<List<PacoteFutebolResponseDTO>> meusPacotes(
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(pacoteService.listarPacotesParaUsuario(userDetails.getUsername()));
    }

    /**
     * Lista edições limitadas disponíveis.
     */
    @GetMapping("/edicoes-limitadas")
    public ResponseEntity<List<PacoteFutebolResponseDTO>> listarEdicoesLimitadas() {
        return ResponseEntity.ok(pacoteService.listarEdicoesLimitadasDisponiveis());
    }

    /**
     * Cria um novo pacote base.
     */
    @PostMapping
    public ResponseEntity<PacoteFutebolResponseDTO> criar(@Valid @RequestBody PacoteFutebolRequestDTO dto) {
        PacoteFutebolResponseDTO response = pacoteService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Gera pacote personalizado para uma assinatura específica.
     * 
     * Este endpoint é a magia do plano família:
     * Pega um pacote base e gera itens personalizados para cada membro
     * da assinatura, respeitando seus tamanhos individuais.
     */
    @PostMapping("/{pacoteId}/gerar-para-assinatura/{assinaturaId}")
    public ResponseEntity<PacoteFutebolResponseDTO> gerarParaAssinatura(
            @PathVariable Long pacoteId,
            @PathVariable Long assinaturaId) {
        PacoteFutebolResponseDTO response = pacoteService.gerarPacoteParaAssinatura(pacoteId, assinaturaId);
        return ResponseEntity.ok(response);
    }

    /**
     * Gera pacotes para todos os assinantes ativos de um plano.
     * 
     * Útil para processamento em lote mensal.
     */
    @PostMapping("/{pacoteId}/gerar-para-todos")
    public ResponseEntity<List<PacoteFutebolResponseDTO>> gerarParaTodosAssinantes(
            @PathVariable Long pacoteId) {
        List<PacoteFutebolResponseDTO> response = pacoteService.gerarPacotesParaTodosAssinantes(pacoteId);
        return ResponseEntity.ok(response);
    }

    /**
     * Adiciona um item ao pacote.
     */
    @PostMapping("/{pacoteId}/itens")
    public ResponseEntity<ItemPacoteFutebolResponseDTO> adicionarItem(
            @PathVariable Long pacoteId,
            @Valid @RequestBody ItemPacoteFutebolRequestDTO dto) {
        ItemPacoteFutebolResponseDTO response = pacoteService.adicionarItem(pacoteId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Remove um item do pacote.
     */
    @DeleteMapping("/{pacoteId}/itens/{itemId}")
    public ResponseEntity<Void> removerItem(
            @PathVariable Long pacoteId,
            @PathVariable Long itemId) {
        pacoteService.removerItem(pacoteId, itemId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Atualiza status de um item (PENDENTE -> SEPARADO -> ENVIADO -> ENTREGUE).
     */
    @PatchMapping("/itens/{itemId}/status")
    public ResponseEntity<ItemPacoteFutebolResponseDTO> atualizarStatusItem(
            @PathVariable Long itemId,
            @RequestParam String status) {
        return ResponseEntity.ok(pacoteService.atualizarStatusItem(itemId, status));
    }

    /**
     * Atualiza um pacote.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PacoteFutebolResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody PacoteFutebolRequestDTO dto) {
        return ResponseEntity.ok(pacoteService.atualizar(id, dto));
    }

    /**
     * Deleta um pacote.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        pacoteService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
