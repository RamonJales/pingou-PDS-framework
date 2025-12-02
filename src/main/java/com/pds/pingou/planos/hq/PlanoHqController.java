package com.pds.pingou.planos.hq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/planos-hq")
public class PlanoHqController {

    @Autowired
    private PlanoHqService planoHqService;

    @GetMapping
    public List<PlanoHqResponseDTO> listarTodos() {
        return planoHqService.listarAtivos();
    }

    @GetMapping("/todos")
    public List<PlanoHqResponseDTO> listarTodosIncluindoInativos() {
        return planoHqService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlanoHqResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(planoHqService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<PlanoHqResponseDTO> criar(@RequestBody PlanoHqRequestDTO dto) {
        PlanoHqResponseDTO plano = planoHqService.criar(dto);
        return ResponseEntity.ok(plano);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlanoHqResponseDTO> atualizar(@PathVariable Long id,
                                                        @RequestBody PlanoHqRequestDTO dto) {
        PlanoHqResponseDTO plano = planoHqService.atualizar(id, dto);
        return ResponseEntity.ok(plano);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        planoHqService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/tipo/{tipo}")
    public List<PlanoHqResponseDTO> buscarPorTipo(@PathVariable TipoPlanoHq tipo) {
        return planoHqService.buscarPorTipo(tipo);
    }
}