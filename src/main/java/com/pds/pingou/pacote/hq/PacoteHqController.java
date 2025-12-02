package com.pds.pingou.pacote.hq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pacotes-hq")
public class PacoteHqController {

    @Autowired
    private PacoteHqService pacoteHqService;

    @GetMapping
    public List<PacoteHqResponseDTO> listarTodos() {
        return pacoteHqService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PacoteHqResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(pacoteHqService.buscarPorId(id));
    }

    @GetMapping("/plano/{planoId}")
    public List<PacoteHqResponseDTO> buscarPorPlano(@PathVariable Long planoId) {
        return pacoteHqService.buscarPorPlano(planoId);
    }

    @GetMapping("/mes/{mes}/ano/{ano}")
    public List<PacoteHqResponseDTO> buscarPorMesEAno(@PathVariable Integer mes,
                                                      @PathVariable Integer ano) {
        return pacoteHqService.buscarPorMesEAno(mes, ano);
    }

    @PostMapping
    public ResponseEntity<PacoteHqResponseDTO> criar(@RequestBody PacoteHqRequestDTO dto) {
        PacoteHqResponseDTO pacote = pacoteHqService.criar(dto);
        return ResponseEntity.ok(pacote);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PacoteHqResponseDTO> atualizar(@PathVariable Long id,
                                                         @RequestBody PacoteHqRequestDTO dto) {
        PacoteHqResponseDTO pacote = pacoteHqService.atualizar(id, dto);
        return ResponseEntity.ok(pacote);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        pacoteHqService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/itens")
    public ResponseEntity<PacoteHqResponseDTO> adicionarItem(@PathVariable Long id,
                                                             @RequestBody ItemPacoteHqRequestDTO itemDto) {
        PacoteHqResponseDTO pacote = pacoteHqService.adicionarItem(id, itemDto);
        return ResponseEntity.ok(pacote);
    }

    @DeleteMapping("/{pacoteId}/itens/{itemId}")
    public ResponseEntity<PacoteHqResponseDTO> removerItem(@PathVariable Long pacoteId,
                                                           @PathVariable Long itemId) {
        PacoteHqResponseDTO pacote = pacoteHqService.removerItem(pacoteId, itemId);
        return ResponseEntity.ok(pacote);
    }

    @GetMapping("/{id}/itens")
    public ResponseEntity<List<ItemPacoteHqResponseDTO>> listarItensDoPacote(@PathVariable Long id) {
        List<ItemPacoteHqResponseDTO> itens = pacoteHqService.listarItensDoPacote(id);
        return ResponseEntity.ok(itens);
    }
}