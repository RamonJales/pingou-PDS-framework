package com.pds.pingou.hq.controller;

import com.pds.pingou.framework.core.controller.BaseRestController;
import com.pds.pingou.hq.dto.PlanoHQRequestDTO;
import com.pds.pingou.hq.dto.PlanoHQResponseDTO;
import com.pds.pingou.hq.entity.PlanoHQ;
import com.pds.pingou.hq.mapper.PlanoHQMapper;
import com.pds.pingou.hq.service.PlanoHQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller REST para gerenciar Planos de HQ
 */
@RestController
@RequestMapping("/api/hq/planos")
@CrossOrigin(origins = "*")
public class PlanoHQController extends BaseRestController<PlanoHQ, PlanoHQResponseDTO, PlanoHQRequestDTO, PlanoHQService> {

    @Autowired
    private PlanoHQService service;

    @Override
    protected PlanoHQService getService() {
        return service;
    }

    @Override
    protected List<PlanoHQResponseDTO> findAll() {
        return service.findActiveItems().stream()
                .map(PlanoHQMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    protected PlanoHQResponseDTO findEntityById(Long id) {
        PlanoHQ plano = service.findByIdOrThrow(id);
        return PlanoHQMapper.toDTO(plano);
    }

    @Override
    protected PlanoHQResponseDTO createEntity(PlanoHQRequestDTO request) {
        PlanoHQ plano = PlanoHQMapper.toEntity(request);
        return PlanoHQMapper.toDTO(service.save(plano));
    }

    @Override
    protected PlanoHQResponseDTO updateEntity(Long id, PlanoHQRequestDTO request) {
        PlanoHQ plano = service.findByIdOrThrow(id);
        PlanoHQMapper.updateEntity(plano, request);
        return PlanoHQMapper.toDTO(service.update(plano));
    }

    @Override
    protected void deleteEntity(Long id) {
        service.deleteById(id);
    }

    /**
     * Busca planos focados em clássicas
     */
    @GetMapping("/focados-classicas")
    public ResponseEntity<List<PlanoHQResponseDTO>> findFocadosEmClassicas() {
        List<PlanoHQResponseDTO> planos = service.findFocadosEmClassicas().stream()
                .map(PlanoHQMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(planos);
    }

    /**
     * Busca planos focados em modernas
     */
    @GetMapping("/focados-modernas")
    public ResponseEntity<List<PlanoHQResponseDTO>> findFocadosEmModernas() {
        List<PlanoHQResponseDTO> planos = service.findFocadosEmModernas().stream()
                .map(PlanoHQMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(planos);
    }

    /**
     * Busca planos equilibrados
     */
    @GetMapping("/equilibrados")
    public ResponseEntity<List<PlanoHQResponseDTO>> findEquilibrados() {
        List<PlanoHQResponseDTO> planos = service.findEquilibrados().stream()
                .map(PlanoHQMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(planos);
    }

    /**
     * Busca planos com edições de colecionador
     */
    @GetMapping("/com-colecionador")
    public ResponseEntity<List<PlanoHQResponseDTO>> findComEdicoesColecionador() {
        List<PlanoHQResponseDTO> planos = service.findComEdicoesColecionador().stream()
                .map(PlanoHQMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(planos);
    }
}
