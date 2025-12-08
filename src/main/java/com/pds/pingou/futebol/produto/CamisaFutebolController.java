package com.pds.pingou.futebol.produto;

import com.pds.pingou.futebol.enums.Competicao;
import com.pds.pingou.futebol.enums.TipoCamisa;
import com.pds.pingou.futebol.produto.dto.CamisaFutebolRequestDTO;
import com.pds.pingou.futebol.produto.dto.CamisaFutebolResponseDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller REST para gerenciamento de camisas de futebol.
 * 
 * Endpoints para CRUD de camisas e busca por diferentes critérios.
 */
@RestController
@RequestMapping("/api/futebol/camisas")
public class CamisaFutebolController {

    @Autowired
    private CamisaFutebolService camisaService;

    /**
     * Lista todas as camisas ativas.
     */
    @GetMapping
    public ResponseEntity<List<CamisaFutebolResponseDTO>> listarTodas() {
        return ResponseEntity.ok(camisaService.listarTodas());
    }

    /**
     * Busca camisa por ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CamisaFutebolResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(camisaService.buscarPorId(id));
    }

    /**
     * Busca camisas por time.
     */
    @GetMapping("/time/{time}")
    public ResponseEntity<List<CamisaFutebolResponseDTO>> buscarPorTime(@PathVariable String time) {
        return ResponseEntity.ok(camisaService.buscarPorTime(time));
    }

    /**
     * Busca camisas por tipo.
     */
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<CamisaFutebolResponseDTO>> buscarPorTipo(@PathVariable TipoCamisa tipo) {
        return ResponseEntity.ok(camisaService.buscarPorTipo(tipo));
    }

    /**
     * Busca camisas por competição.
     */
    @GetMapping("/competicao/{competicao}")
    public ResponseEntity<List<CamisaFutebolResponseDTO>> buscarPorCompeticao(@PathVariable Competicao competicao) {
        return ResponseEntity.ok(camisaService.buscarPorCompeticao(competicao));
    }

    /**
     * Busca camisas por temporada.
     */
    @GetMapping("/temporada/{temporada}")
    public ResponseEntity<List<CamisaFutebolResponseDTO>> buscarPorTemporada(@PathVariable String temporada) {
        return ResponseEntity.ok(camisaService.buscarPorTemporada(temporada));
    }

    /**
     * Lista todos os times disponíveis.
     */
    @GetMapping("/times")
    public ResponseEntity<List<String>> listarTimes() {
        return ResponseEntity.ok(camisaService.listarTimes());
    }

    /**
     * Lista todas as temporadas disponíveis.
     */
    @GetMapping("/temporadas")
    public ResponseEntity<List<String>> listarTemporadas() {
        return ResponseEntity.ok(camisaService.listarTemporadas());
    }

    /**
     * Cria uma nova camisa.
     */
    @PostMapping
    public ResponseEntity<CamisaFutebolResponseDTO> criar(@Valid @RequestBody CamisaFutebolRequestDTO dto) {
        CamisaFutebolResponseDTO response = camisaService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Atualiza uma camisa existente.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CamisaFutebolResponseDTO> atualizar(@PathVariable Long id, 
                                                               @Valid @RequestBody CamisaFutebolRequestDTO dto) {
        return ResponseEntity.ok(camisaService.atualizar(id, dto));
    }

    /**
     * Ativa uma camisa.
     */
    @PatchMapping("/{id}/ativar")
    public ResponseEntity<Void> ativar(@PathVariable Long id) {
        camisaService.ativar(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Desativa uma camisa.
     */
    @PatchMapping("/{id}/desativar")
    public ResponseEntity<Void> desativar(@PathVariable Long id) {
        camisaService.desativar(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Deleta uma camisa.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        camisaService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Busca camisas recomendadas para times favoritos.
     */
    @PostMapping("/recomendadas")
    public ResponseEntity<List<CamisaFutebolResponseDTO>> buscarRecomendadas(@RequestBody List<String> timesFavoritos) {
        return ResponseEntity.ok(camisaService.buscarRecomendadas(timesFavoritos));
    }
}
