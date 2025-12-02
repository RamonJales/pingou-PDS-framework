package com.pds.pingou.camisa.controller;

import com.pds.pingou.framework.core.controller.BaseRestController;
import com.pds.pingou.camisa.Camisa;
import com.pds.pingou.camisa.CamisaService;
import com.pds.pingou.camisa.dto.CamisaMapper;
import com.pds.pingou.camisa.dto.CamisaRequestDTO;
import com.pds.pingou.camisa.dto.CamisaResponseDTO;
import com.pds.pingou.camisa.enums.LigaCamisa;
import com.pds.pingou.camisa.enums.TipoCamisa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller REST para gerenciamento de camisas de futebol.
 */
@RestController
@RequestMapping("/api/camisas")
public class CamisaController extends BaseRestController<Camisa, CamisaResponseDTO, CamisaRequestDTO, CamisaService> {
    
    @Autowired
    private CamisaService service;
    
    @Override
    protected CamisaService getService() {
        return service;
    }
    
    @Override
    protected List<CamisaResponseDTO> findAll() {
        return service.findAll().stream()
            .map(CamisaMapper::toDTO)
            .collect(Collectors.toList());
    }
    
    @Override
    protected CamisaResponseDTO findEntityById(Long id) {
        Camisa camisa = service.findByIdOrThrow(id);
        return CamisaMapper.toDTO(camisa);
    }
    
    @Override
    protected CamisaResponseDTO createEntity(CamisaRequestDTO request) {
        Camisa camisa = CamisaMapper.toEntity(request);
        Camisa saved = service.save(camisa);
        return CamisaMapper.toDTO(saved);
    }
    
    @Override
    protected CamisaResponseDTO updateEntity(Long id, CamisaRequestDTO request) {
        Camisa camisa = service.findByIdOrThrow(id);
        CamisaMapper.updateEntity(camisa, request);
        Camisa updated = service.update(camisa);
        return CamisaMapper.toDTO(updated);
    }
    
    @Override
    protected void deleteEntity(Long id) {
        service.deleteById(id);
    }
    
    @GetMapping("/time/{time}")
    public ResponseEntity<List<CamisaResponseDTO>> findByTime(@PathVariable String time) {
        List<CamisaResponseDTO> camisas = service.findByTime(time).stream()
            .map(CamisaMapper::toDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(camisas);
    }
    
    @GetMapping("/liga/{liga}")
    public ResponseEntity<List<CamisaResponseDTO>> findByLiga(@PathVariable String liga) {
        List<CamisaResponseDTO> camisas = service.findByLiga(LigaCamisa.valueOf(liga)).stream()
            .map(CamisaMapper::toDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(camisas);
    }
    
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<CamisaResponseDTO>> findByTipo(@PathVariable String tipo) {
        List<CamisaResponseDTO> camisas = service.findByTipo(TipoCamisa.valueOf(tipo)).stream()
            .map(CamisaMapper::toDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(camisas);
    }
    
    @GetMapping("/ano/{ano}")
    public ResponseEntity<List<CamisaResponseDTO>> findByAno(@PathVariable Integer ano) {
        List<CamisaResponseDTO> camisas = service.findByAnoTemporada(ano).stream()
            .map(CamisaMapper::toDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(camisas);
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<CamisaResponseDTO>> search(@RequestParam String termo) {
        List<CamisaResponseDTO> camisas = service.searchByTerm(termo).stream()
            .map(CamisaMapper::toDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(camisas);
    }
    
    @GetMapping("/estoque")
    public ResponseEntity<List<CamisaResponseDTO>> findInStock() {
        List<CamisaResponseDTO> camisas = service.findAvailableInStock().stream()
            .map(CamisaMapper::toDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(camisas);
    }
    
    @GetMapping("/edicoes-limitadas")
    public ResponseEntity<List<CamisaResponseDTO>> findEdicoesLimitadas() {
        List<CamisaResponseDTO> camisas = service.findEdicoesLimitadas().stream()
            .map(CamisaMapper::toDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(camisas);
    }
}
