package com.pds.pingou.pontuacao;

import com.pds.pingou.pontuacao.dto.HistoricoPontosResponseDTO;
import com.pds.pingou.pontuacao.dto.PontuacaoResponseDTO;
import com.pds.pingou.pontuacao.dto.TrocaPontosRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pontuacao")
public class PontuacaoController {

    @Autowired
    private PontuacaoService pontuacaoService;

    @GetMapping("/usuario/{userId}")
    public ResponseEntity<PontuacaoResponseDTO> buscarPorUsuario(@PathVariable Long userId) {
        PontuacaoResponseDTO pontuacao = pontuacaoService.buscarPorUsuario(userId);
        return ResponseEntity.ok(pontuacao);
    }

    @PostMapping("/usuario/{userId}")
    public ResponseEntity<PontuacaoResponseDTO> criarOuObter(@PathVariable Long userId) {
        PontuacaoResponseDTO pontuacao = pontuacaoService.criarOuObterPontuacao(userId);
        return ResponseEntity.ok(pontuacao);
    }

    @PostMapping("/trocar")
    public ResponseEntity<PontuacaoResponseDTO> trocarPontos(@RequestBody TrocaPontosRequestDTO dto) {
        PontuacaoResponseDTO pontuacao = pontuacaoService.trocarPontosPorHq(dto);
        return ResponseEntity.ok(pontuacao);
    }

    @PostMapping("/bonus/{userId}")
    public ResponseEntity<PontuacaoResponseDTO> adicionarBonus(
            @PathVariable Long userId,
            @RequestParam Integer pontos,
            @RequestParam String motivo) {
        PontuacaoResponseDTO pontuacao = pontuacaoService.adicionarBonus(userId, pontos, motivo);
        return ResponseEntity.ok(pontuacao);
    }

    @GetMapping("/historico/{userId}")
    public ResponseEntity<List<HistoricoPontosResponseDTO>> buscarHistorico(@PathVariable Long userId) {
        List<HistoricoPontosResponseDTO> historico = pontuacaoService.buscarHistorico(userId);
        return ResponseEntity.ok(historico);
    }

    @GetMapping("/historico/{userId}/tipo/{tipo}")
    public ResponseEntity<List<HistoricoPontosResponseDTO>> buscarHistoricoPorTipo(
            @PathVariable Long userId,
            @PathVariable TipoMovimentacao tipo) {
        List<HistoricoPontosResponseDTO> historico = pontuacaoService.buscarHistoricoPorTipo(userId, tipo);
        return ResponseEntity.ok(historico);
    }
}