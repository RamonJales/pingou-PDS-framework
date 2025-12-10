package com.pds.pingou.camisa.controller;

import com.pds.pingou.camisa.dto.CamisaRequestDTO;
import com.pds.pingou.camisa.dto.CamisaResponseDTO;
import com.pds.pingou.camisa.entity.Camisa;
import com.pds.pingou.camisa.mapper.CamisaMapper;
import com.pds.pingou.camisa.service.CamisaService;
import com.pds.pingou.framework.core.controller.BaseRestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
    @GetMapping
    public ResponseEntity<List<CamisaResponseDTO>> getAll() {
        return ResponseEntity.ok(findAll());
    }
    
    @Override
    protected List<CamisaResponseDTO> findAll() {
        return service.findAll().stream()
            .map(CamisaMapper::toDTO)
            .collect(Collectors.toList());
    }
    
    @Override
    @GetMapping("/{id}")
    public ResponseEntity<CamisaResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(findEntityById(id));
    }
    
    @Override
    protected CamisaResponseDTO findEntityById(Long id) {
        Camisa camisa = service.findByIdOrThrow(id);
        return CamisaMapper.toDTO(camisa);
    }
    
    @Override
    @PostMapping
    public ResponseEntity<CamisaResponseDTO> create(@RequestBody CamisaRequestDTO request) {
        return ResponseEntity.ok(createEntity(request));
    }
    
    @Override
    protected CamisaResponseDTO createEntity(CamisaRequestDTO request) {
        Camisa camisa = CamisaMapper.toEntity(request);
        return CamisaMapper.toDTO(service.save(camisa));
    }
    
    @Override
    @PutMapping("/{id}")
    public ResponseEntity<CamisaResponseDTO> update(@PathVariable Long id, @RequestBody CamisaRequestDTO request) {
        return ResponseEntity.ok(updateEntity(id, request));
    }
    
    @Override
    protected CamisaResponseDTO updateEntity(Long id, CamisaRequestDTO request) {
        Camisa camisa = service.findByIdOrThrow(id);
        CamisaMapper.updateEntity(camisa, request);
        return CamisaMapper.toDTO(service.update(camisa));
    }
    
    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        deleteEntity(id);
        return ResponseEntity.noContent().build();
    }
    
    @Override
    protected void deleteEntity(Long id) {
        service.deleteById(id);
    }
    
    @GetMapping("/disponiveis")
    public ResponseEntity<List<CamisaResponseDTO>> getCamisasDisponiveis() {
        List<Camisa> camisas = service.getRepository().findByEstoqueGreaterThan(0);
        return ResponseEntity.ok(
            camisas.stream()
                .map(CamisaMapper::toDTO)
                .collect(Collectors.toList())
        );
    }
    
    @GetMapping("/time/{time}")
    public ResponseEntity<List<CamisaResponseDTO>> getCamisasPorTime(@PathVariable String time) {
        List<Camisa> camisas = service.getRepository().findByTime(time);
        return ResponseEntity.ok(
            camisas.stream()
                .map(CamisaMapper::toDTO)
                .collect(Collectors.toList())
        );
    }
}
