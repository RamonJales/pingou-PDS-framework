package com.pds.pingou.futebol.plano;

import com.pds.pingou.futebol.enums.TipoPlanoFutebol;
import com.pds.pingou.futebol.plano.dto.PlanoFutebolRequestDTO;
import com.pds.pingou.futebol.plano.dto.PlanoFutebolResponseDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller REST para gerenciamento de planos de assinatura de futebol.
 * 
 * Endpoints para CRUD de planos e busca por diferentes critérios.
 */
@RestController
@RequestMapping("/api/futebol/planos")
public class PlanoFutebolController {

    @Autowired
    private PlanoFutebolService planoService;

    /**
     * Lista todos os planos ativos.
     */
    @GetMapping
    public ResponseEntity<List<PlanoFutebolResponseDTO>> listarTodos() {
        return ResponseEntity.ok(planoService.listarTodos());
    }

    /**
     * Busca plano por ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PlanoFutebolResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(planoService.buscarPorId(id));
    }

    /**
     * Busca plano por nome.
     */
    @GetMapping("/nome/{nome}")
    public ResponseEntity<PlanoFutebolResponseDTO> buscarPorNome(@PathVariable String nome) {
        return ResponseEntity.ok(planoService.buscarPorNome(nome));
    }

    /**
     * Lista planos por tipo.
     */
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<PlanoFutebolResponseDTO>> listarPorTipo(@PathVariable TipoPlanoFutebol tipo) {
        return ResponseEntity.ok(planoService.listarPorTipo(tipo));
    }

    /**
     * Lista planos familiares.
     */
    @GetMapping("/familiares")
    public ResponseEntity<List<PlanoFutebolResponseDTO>> listarPlanosFamiliares() {
        return ResponseEntity.ok(planoService.listarPlanosFamiliares());
    }

    /**
     * Lista planos individuais.
     */
    @GetMapping("/individuais")
    public ResponseEntity<List<PlanoFutebolResponseDTO>> listarPlanosIndividuais() {
        return ResponseEntity.ok(planoService.listarPlanosIndividuais());
    }

    /**
     * Lista planos com personalização inclusa.
     */
    @GetMapping("/com-personalizacao")
    public ResponseEntity<List<PlanoFutebolResponseDTO>> listarPlanosComPersonalizacao() {
        return ResponseEntity.ok(planoService.listarPlanosComPersonalizacao());
    }

    /**
     * Sugere plano ideal para quantidade de membros.
     */
    @GetMapping("/sugerir/{quantidadeMembros}")
    public ResponseEntity<PlanoFutebolResponseDTO> sugerirPlano(@PathVariable int quantidadeMembros) {
        return ResponseEntity.ok(planoService.sugerirPlanoParaMembros(quantidadeMembros));
    }

    /**
     * Cria um novo plano.
     */
    @PostMapping
    public ResponseEntity<PlanoFutebolResponseDTO> criar(@Valid @RequestBody PlanoFutebolRequestDTO dto) {
        PlanoFutebolResponseDTO response = planoService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Atualiza um plano existente.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PlanoFutebolResponseDTO> atualizar(@PathVariable Long id, 
                                                              @Valid @RequestBody PlanoFutebolRequestDTO dto) {
        return ResponseEntity.ok(planoService.atualizar(id, dto));
    }

    /**
     * Ativa um plano.
     */
    @PatchMapping("/{id}/ativar")
    public ResponseEntity<Void> ativar(@PathVariable Long id) {
        planoService.ativar(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Desativa um plano.
     */
    @PatchMapping("/{id}/desativar")
    public ResponseEntity<Void> desativar(@PathVariable Long id) {
        planoService.desativar(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Deleta um plano.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        planoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
