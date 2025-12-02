package com.pds.pingou.camisa.planos.controller;

import com.pds.pingou.framework.core.controller.BaseRestController;
import com.pds.pingou.camisa.planos.PlanoCamisa;
import com.pds.pingou.camisa.planos.PlanoCamisaService;
import com.pds.pingou.camisa.planos.dto.PlanoCamisaMapper;
import com.pds.pingou.camisa.planos.dto.PlanoCamisaRequestDTO;
import com.pds.pingou.camisa.planos.dto.PlanoCamisaResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller REST para gerenciamento de planos de assinatura de camisas.
 */
@RestController
@RequestMapping("/api/planos")
public class PlanoCamisaController extends BaseRestController<PlanoCamisa, PlanoCamisaResponseDTO, PlanoCamisaRequestDTO, PlanoCamisaService> {
    
    @Autowired
    private PlanoCamisaService service;
    
    @Override
    protected PlanoCamisaService getService() {
        return service;
    }
    
    @Override
    protected List<PlanoCamisaResponseDTO> findAll() {
        return service.findAll().stream()
            .map(PlanoCamisaMapper::toDTO)
            .collect(Collectors.toList());
    }
    
    @Override
    protected PlanoCamisaResponseDTO findEntityById(Long id) {
        PlanoCamisa plano = service.findByIdOrThrow(id);
        return PlanoCamisaMapper.toDTO(plano);
    }
    
    @Override
    protected PlanoCamisaResponseDTO createEntity(PlanoCamisaRequestDTO request) {
        PlanoCamisa plano = PlanoCamisaMapper.toEntity(request);
        PlanoCamisa saved = service.save(plano);
        return PlanoCamisaMapper.toDTO(saved);
    }
    
    @Override
    protected PlanoCamisaResponseDTO updateEntity(Long id, PlanoCamisaRequestDTO request) {
        PlanoCamisa plano = service.findByIdOrThrow(id);
        PlanoCamisaMapper.updateEntity(plano, request);
        PlanoCamisa updated = service.update(plano);
        return PlanoCamisaMapper.toDTO(updated);
    }
    
    @Override
    protected void deleteEntity(Long id) {
        service.deleteById(id);
    }
    
    @GetMapping("/ativos")
    public ResponseEntity<List<PlanoCamisaResponseDTO>> findAtivos() {
        List<PlanoCamisaResponseDTO> planos = service.findActivePlans().stream()
            .map(PlanoCamisaMapper::toDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(planos);
    }
    
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<PlanoCamisaResponseDTO>> findByCategoria(@PathVariable String categoria) {
        List<PlanoCamisaResponseDTO> planos = service.findByCategoria(categoria).stream()
            .map(PlanoCamisaMapper::toDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(planos);
    }
    
    @GetMapping("/ordem-preco")
    public ResponseEntity<List<PlanoCamisaResponseDTO>> findAllOrderByPrice() {
        List<PlanoCamisaResponseDTO> planos = service.findAllActiveOrderByPrice().stream()
            .map(PlanoCamisaMapper::toDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(planos);
    }
    
    @PatchMapping("/{id}/ativar")
    public ResponseEntity<PlanoCamisaResponseDTO> ativar(@PathVariable Long id) {
        PlanoCamisa plano = service.activatePlan(id);
        return ResponseEntity.ok(PlanoCamisaMapper.toDTO(plano));
    }
    
    @PatchMapping("/{id}/desativar")
    public ResponseEntity<PlanoCamisaResponseDTO> desativar(@PathVariable Long id) {
        PlanoCamisa plano = service.deactivatePlan(id);
        return ResponseEntity.ok(PlanoCamisaMapper.toDTO(plano));
    }
}
