package com.pds.pingou.hq.controller;

import com.pds.pingou.framework.core.controller.BaseRestController;
import com.pds.pingou.hq.dto.QuadrinhoRequestDTO;
import com.pds.pingou.hq.dto.QuadrinhoResponseDTO;
import com.pds.pingou.hq.entity.Quadrinho;
import com.pds.pingou.hq.enums.CategoriaHQ;
import com.pds.pingou.hq.enums.EditoraHQ;
import com.pds.pingou.hq.enums.TipoHQ;
import com.pds.pingou.hq.mapper.QuadrinhoMapper;
import com.pds.pingou.hq.service.QuadrinhoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller REST para gerenciar Quadrinhos
 */
@RestController
@RequestMapping("/api/hq/quadrinhos")
@CrossOrigin(origins = "*")
public class QuadrinhoController extends BaseRestController<Quadrinho, QuadrinhoResponseDTO, QuadrinhoRequestDTO, QuadrinhoService> {

    @Autowired
    private QuadrinhoService service;

    @Override
    protected QuadrinhoService getService() {
        return service;
    }

    @Override
    protected List<QuadrinhoResponseDTO> findAll() {
        return service.findAll().stream()
                .map(QuadrinhoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    protected QuadrinhoResponseDTO findEntityById(Long id) {
        Quadrinho quadrinho = service.findByIdOrThrow(id);
        return QuadrinhoMapper.toDTO(quadrinho);
    }

    @Override
    protected QuadrinhoResponseDTO createEntity(QuadrinhoRequestDTO request) {
        Quadrinho quadrinho = QuadrinhoMapper.toEntity(request);
        return QuadrinhoMapper.toDTO(service.save(quadrinho));
    }

    @Override
    protected QuadrinhoResponseDTO updateEntity(Long id, QuadrinhoRequestDTO request) {
        Quadrinho quadrinho = service.findByIdOrThrow(id);
        QuadrinhoMapper.updateEntity(quadrinho, request);
        return QuadrinhoMapper.toDTO(service.update(quadrinho));
    }

    @Override
    protected void deleteEntity(Long id) {
        service.deleteById(id);
    }

    /**
     * Busca quadrinhos por editora
     */
    @GetMapping("/editora/{editora}")
    public ResponseEntity<List<QuadrinhoResponseDTO>> findByEditora(@PathVariable EditoraHQ editora) {
        List<QuadrinhoResponseDTO> quadrinhos = service.findByEditora(editora).stream()
                .map(QuadrinhoMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(quadrinhos);
    }

    /**
     * Busca quadrinhos por tipo (Clássica/Moderna)
     */
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<QuadrinhoResponseDTO>> findByTipo(@PathVariable TipoHQ tipo) {
        List<QuadrinhoResponseDTO> quadrinhos = service.findByTipo(tipo).stream()
                .map(QuadrinhoMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(quadrinhos);
    }

    /**
     * Busca quadrinhos por categoria
     */
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<QuadrinhoResponseDTO>> findByCategoria(@PathVariable CategoriaHQ categoria) {
        List<QuadrinhoResponseDTO> quadrinhos = service.findByCategoria(categoria).stream()
                .map(QuadrinhoMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(quadrinhos);
    }

    /**
     * Busca edições de colecionador
     */
    @GetMapping("/colecionador")
    public ResponseEntity<List<QuadrinhoResponseDTO>> findEdicoesColecionador() {
        List<QuadrinhoResponseDTO> quadrinhos = service.findEdicoesColecionador().stream()
                .map(QuadrinhoMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(quadrinhos);
    }

    /**
     * Busca quadrinhos com estoque
     */
    @GetMapping("/com-estoque")
    public ResponseEntity<List<QuadrinhoResponseDTO>> findComEstoque() {
        List<QuadrinhoResponseDTO> quadrinhos = service.findComEstoque().stream()
                .map(QuadrinhoMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(quadrinhos);
    }

    /**
     * Incrementa estoque
     */
    @PostMapping("/{id}/estoque/incrementar")
    public ResponseEntity<QuadrinhoResponseDTO> incrementarEstoque(
            @PathVariable Long id,
            @RequestParam int quantidade) {
        Quadrinho quadrinho = service.incrementarEstoque(id, quantidade);
        return ResponseEntity.ok(QuadrinhoMapper.toDTO(quadrinho));
    }

    /**
     * Decrementa estoque
     */
    @PostMapping("/{id}/estoque/decrementar")
    public ResponseEntity<QuadrinhoResponseDTO> decrementarEstoque(@PathVariable Long id) {
        Quadrinho quadrinho = service.decrementarEstoque(id);
        return ResponseEntity.ok(QuadrinhoMapper.toDTO(quadrinho));
    }
}
