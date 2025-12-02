package com.pds.pingou.produto.hq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/hqs")
public class HqController {

    @Autowired
    private HqService hqService;

    @GetMapping
    public List<HqResponseDTO> listarTodas() {
        return hqService.listarAtivas();
    }

    @GetMapping("/todas")
    public List<HqResponseDTO> listarTodasIncluindoInativas() {
        return hqService.listarTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<HqResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(hqService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<HqResponseDTO> criar(@RequestBody HqRequestDTO dto) {
        HqResponseDTO hq = hqService.criar(dto);
        return ResponseEntity.ok(hq);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HqResponseDTO> atualizar(@PathVariable Long id,
                                                   @RequestBody HqRequestDTO dto) {
        HqResponseDTO hq = hqService.atualizar(id, dto);
        return ResponseEntity.ok(hq);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        hqService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/tipo/{tipo}")
    public List<HqResponseDTO> buscarPorTipo(@PathVariable TipoHq tipo) {
        return hqService.buscarPorTipo(tipo);
    }

    @GetMapping("/categoria/{categoria}")
    public List<HqResponseDTO> buscarPorCategoria(@PathVariable CategoriaHq categoria) {
        return hqService.buscarPorCategoria(categoria);
    }

    @GetMapping("/editora/{editora}")
    public List<HqResponseDTO> buscarPorEditora(@PathVariable EditoraHq editora) {
        return hqService.buscarPorEditora(editora);
    }

    @GetMapping("/ano/{ano}")
    public List<HqResponseDTO> buscarPorAno(@PathVariable Integer ano) {
        return hqService.buscarPorAno(ano);
    }

    @GetMapping("/colecionador")
    public List<HqResponseDTO> buscarEdicoesColecionador() {
        return hqService.buscarEdicoesColecionador();
    }
}