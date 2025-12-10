package com.pds.pingou.camisa.controller;

import com.pds.pingou.camisa.dto.PlanoCamisaRequestDTO;
import com.pds.pingou.camisa.dto.PlanoCamisaResponseDTO;
import com.pds.pingou.camisa.entity.PlanoCamisa;
import com.pds.pingou.camisa.mapper.PlanoCamisaMapper;
import com.pds.pingou.camisa.service.PlanoCamisaService;
import com.pds.pingou.framework.core.controller.BaseRestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/planos-camisa")
public class PlanoCamisaController extends BaseRestController<PlanoCamisa, PlanoCamisaResponseDTO, PlanoCamisaRequestDTO, PlanoCamisaService> {
    
    @Autowired
    private PlanoCamisaService service;
    
    @Override
    protected PlanoCamisaService getService() {
        return service;
    }
    
    @Override
    @GetMapping
    public ResponseEntity<List<PlanoCamisaResponseDTO>> getAll() {
        return ResponseEntity.ok(findAll());
    }
    
    @Override
    protected List<PlanoCamisaResponseDTO> findAll() {
        return service.findAll().stream()
            .map(PlanoCamisaMapper::toDTO)
            .collect(Collectors.toList());
    }
    
    @Override
    @GetMapping("/{id}")
    public ResponseEntity<PlanoCamisaResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(findEntityById(id));
    }
    
    @Override
    protected PlanoCamisaResponseDTO findEntityById(Long id) {
        PlanoCamisa plano = service.findByIdOrThrow(id);
        return PlanoCamisaMapper.toDTO(plano);
    }
    
    @Override
    @PostMapping
    public ResponseEntity<PlanoCamisaResponseDTO> create(@RequestBody PlanoCamisaRequestDTO request) {
        return ResponseEntity.ok(createEntity(request));
    }
    
    @Override
    protected PlanoCamisaResponseDTO createEntity(PlanoCamisaRequestDTO request) {
        PlanoCamisa plano = PlanoCamisaMapper.toEntity(request);
        return PlanoCamisaMapper.toDTO(service.save(plano));
    }
    
    @Override
    @PutMapping("/{id}")
    public ResponseEntity<PlanoCamisaResponseDTO> update(@PathVariable Long id, @RequestBody PlanoCamisaRequestDTO request) {
        return ResponseEntity.ok(updateEntity(id, request));
    }
    
    @Override
    protected PlanoCamisaResponseDTO updateEntity(Long id, PlanoCamisaRequestDTO request) {
        PlanoCamisa plano = service.findByIdOrThrow(id);
        PlanoCamisaMapper.updateEntity(plano, request);
        return PlanoCamisaMapper.toDTO(service.update(plano));
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
    
    @GetMapping("/ativos")
    public ResponseEntity<List<PlanoCamisaResponseDTO>> getPlanosAtivos() {
        List<PlanoCamisa> planos = service.getRepository().findByAtivo(true);
        return ResponseEntity.ok(
            planos.stream()
                .map(PlanoCamisaMapper::toDTO)
                .collect(Collectors.toList())
        );
    }
    
    @GetMapping("/familia")
    public ResponseEntity<List<PlanoCamisaResponseDTO>> getPlanosFamilia() {
        List<PlanoCamisa> planos = service.getRepository().findByPermiteCompartilhamento(true);
        return ResponseEntity.ok(
            planos.stream()
                .map(PlanoCamisaMapper::toDTO)
                .collect(Collectors.toList())
        );
    }
}
