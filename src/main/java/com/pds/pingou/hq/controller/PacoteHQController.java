package com.pds.pingou.hq.controller;

import com.pds.pingou.hq.dto.PacoteCuradoRequestDTO;
import com.pds.pingou.hq.dto.PacoteHQResponseDTO;
import com.pds.pingou.hq.entity.PacoteHQ;
import com.pds.pingou.hq.entity.PlanoHQ;
import com.pds.pingou.hq.mapper.PacoteHQMapper;
import com.pds.pingou.hq.service.AssinaturaHQService;
import com.pds.pingou.hq.service.PacoteHQService;
import com.pds.pingou.hq.service.PlanoHQService;
import com.pds.pingou.security.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller REST para gerenciar Pacotes de HQ
 */
@RestController
@RequestMapping("/api/hq/pacotes")
@CrossOrigin(origins = "*")
public class PacoteHQController {

    @Autowired
    private PacoteHQService service;

    @Autowired
    private PlanoHQService planoService;

    @Autowired
    private AssinaturaHQService assinaturaService;

    /**
     * Cria um pacote curado personalizado para o usuário autenticado
     */
    @PostMapping("/curar")
    public ResponseEntity<PacoteHQResponseDTO> criarPacoteCurado(
            @AuthenticationPrincipal User user,
            @RequestBody PacoteCuradoRequestDTO request) {
        
        PlanoHQ plano = planoService.findByIdOrThrow(request.getPlanoId());
        
        PacoteHQ pacote = service.criarPacoteCurado(
            user,
            plano,
            request.getPeriodo(),
            request.getAno(),
            request.getTemaMes()
        );

        // Registra recebimento na assinatura (se existir)
        try {
            var assinatura = assinaturaService.findByUser(user);
            assinaturaService.registrarRecebimentoPacote(assinatura.getId(), pacote);
        } catch (Exception e) {
            // Ignora se não houver assinatura
        }

        return ResponseEntity.ok(PacoteHQMapper.toDTO(pacote));
    }

    /**
     * Lista todos os pacotes
     */
    @GetMapping
    public ResponseEntity<List<PacoteHQResponseDTO>> listarTodos() {
        List<PacoteHQResponseDTO> pacotes = service.findAll().stream()
                .map(PacoteHQMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(pacotes);
    }

    /**
     * Busca pacote por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<PacoteHQResponseDTO> findById(@PathVariable Long id) {
        PacoteHQ pacote = service.findByIdOrThrow(id);
        return ResponseEntity.ok(PacoteHQMapper.toDTO(pacote));
    }

    /**
     * Busca meus pacotes curados
     */
    @GetMapping("/meus")
    public ResponseEntity<List<PacoteHQResponseDTO>> getMeusPacotes(
            @AuthenticationPrincipal User user) {
        List<PacoteHQResponseDTO> pacotes = service.findCuradosParaUsuario(user.getId()).stream()
                .map(PacoteHQMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(pacotes);
    }

    /**
     * Busca pacotes para entrega hoje
     */
    @GetMapping("/entrega-hoje")
    public ResponseEntity<List<PacoteHQResponseDTO>> getParaEntregaHoje() {
        List<PacoteHQResponseDTO> pacotes = service.findParaEntregaHoje().stream()
                .map(PacoteHQMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(pacotes);
    }

    /**
     * Busca pacotes atrasados
     */
    @GetMapping("/atrasados")
    public ResponseEntity<List<PacoteHQResponseDTO>> getAtrasados() {
        List<PacoteHQResponseDTO> pacotes = service.findAtrasados().stream()
                .map(PacoteHQMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(pacotes);
    }
}
