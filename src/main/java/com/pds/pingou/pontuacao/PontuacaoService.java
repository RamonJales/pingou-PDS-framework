package com.pds.pingou.pontuacao;

import com.pds.pingou.pacote.hq.ItemPacoteHq;
import com.pds.pingou.pacote.hq.PacoteHq;
import com.pds.pingou.pontuacao.dto.HistoricoPontosResponseDTO;
import com.pds.pingou.pontuacao.dto.PontuacaoResponseDTO;
import com.pds.pingou.pontuacao.dto.TrocaPontosRequestDTO;
import com.pds.pingou.produto.hq.Hq;
import com.pds.pingou.produto.hq.HqService;
import com.pds.pingou.security.exception.UserNotFoundException;
import com.pds.pingou.security.user.User;
import com.pds.pingou.security.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Serviço para gerenciamento de pontuação de usuários.
 *
 * @author Pingou Team
 * @version 1.0
 * @since 2.0
 */
@Service
public class PontuacaoService {

    @Autowired
    private PontuacaoRepository pontuacaoRepository;

    @Autowired
    private HistoricoPontosRepository historicoPontosRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HqService hqService;

    @Transactional
    public PontuacaoResponseDTO criarOuObterPontuacao(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId.toString()));

        Pontuacao pontuacao = pontuacaoRepository.findByUser(user)
                .orElseGet(() -> {
                    Pontuacao nova = new Pontuacao(user);
                    return pontuacaoRepository.save(nova);
                });

        return toDTO(pontuacao);
    }


    public PontuacaoResponseDTO buscarPorUsuario(Long userId) {
        Pontuacao pontuacao = pontuacaoRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Pontuação não encontrada para o usuário"));

        return toDTO(pontuacao);
    }

    @Transactional
    public PontuacaoResponseDTO adicionarPontosPorPacote(Long userId, PacoteHq pacote) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId.toString()));

        Pontuacao pontuacao = pontuacaoRepository.findByUser(user)
                .orElseGet(() -> {
                    Pontuacao nova = new Pontuacao(user);
                    return pontuacaoRepository.save(nova);
                });

        int pontosPacote = calcularPontosPacote(pacote);

        BigDecimal multiplicador = pacote.getPlano().getMultiplicadorPontos();
        int pontosFinais = multiplicador.multiply(new BigDecimal(pontosPacote)).intValue();

        pontuacao.adicionarPontos(pontosFinais);
        pontuacao = pontuacaoRepository.save(pontuacao);

        String descricao = String.format("Recebimento do pacote: %s (%d HQs)",
                pacote.getNome(), pacote.getItens().size());
        registrarHistorico(user, TipoMovimentacao.GANHO, pontosFinais, descricao, null);

        return toDTO(pontuacao);
    }

    private int calcularPontosPacote(PacoteHq pacote) {
        return pacote.getItens().stream()
                .mapToInt(item -> {
                    Hq hq = item.getHq();
                    int pontosPorHq = hq.getPontosGanho() != null ? hq.getPontosGanho() : 10;
                    return pontosPorHq * item.getQuantidade();
                })
                .sum();
    }

    @Transactional
    public PontuacaoResponseDTO trocarPontosPorHq(TrocaPontosRequestDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new UserNotFoundException(dto.getUserId().toString()));

        Pontuacao pontuacao = pontuacaoRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Pontuação não encontrada"));

        Hq hq = hqService.buscarEntidadePorId(dto.getHqId());

        if (pontuacao.getPontosDisponiveis() < dto.getPontosUtilizar()) {
            throw new RuntimeException("Pontos insuficientes para esta troca");
        }

        pontuacao.utilizarPontos(dto.getPontosUtilizar());
        pontuacao = pontuacaoRepository.save(pontuacao);

        String descricao = String.format("Troca de %d pontos pela HQ: %s",
                dto.getPontosUtilizar(), hq.getNome());
        registrarHistorico(user, TipoMovimentacao.TROCA, dto.getPontosUtilizar(), descricao, hq.getId());

        return toDTO(pontuacao);
    }

    @Transactional
    public PontuacaoResponseDTO adicionarBonus(Long userId, Integer pontos, String motivo) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId.toString()));

        Pontuacao pontuacao = pontuacaoRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Pontuação não encontrada"));

        pontuacao.adicionarPontos(pontos);
        pontuacao = pontuacaoRepository.save(pontuacao);

        // Registrar no histórico
        registrarHistorico(user, TipoMovimentacao.BONUS, pontos, motivo, null);

        return toDTO(pontuacao);
    }

    public List<HistoricoPontosResponseDTO> buscarHistorico(Long userId) {
        return historicoPontosRepository.findByUserIdOrderByDataHoraDesc(userId).stream()
                .map(this::toHistoricoDTO)
                .collect(Collectors.toList());
    }

    public List<HistoricoPontosResponseDTO> buscarHistoricoPorTipo(Long userId, TipoMovimentacao tipo) {
        return historicoPontosRepository.findByUserIdAndTipo(userId, tipo).stream()
                .map(this::toHistoricoDTO)
                .collect(Collectors.toList());
    }

    private void registrarHistorico(User user, TipoMovimentacao tipo, Integer pontos,
                                    String descricao, Long hqId) {
        HistoricoPontos historico = new HistoricoPontos(user, tipo, pontos, descricao);
        historico.setHqId(hqId);
        historicoPontosRepository.save(historico);
    }

    private PontuacaoResponseDTO toDTO(Pontuacao pontuacao) {
        PontuacaoResponseDTO dto = new PontuacaoResponseDTO();
        dto.setId(pontuacao.getId());
        dto.setUserId(pontuacao.getUser().getId());
        dto.setUserEmail(pontuacao.getUser().getEmail());
        dto.setPontosTotais(pontuacao.getPontosTotais());
        dto.setPontosDisponiveis(pontuacao.getPontosDisponiveis());
        dto.setPontosUtilizados(pontuacao.getPontosUtilizados());
        dto.setUltimaAtualizacao(pontuacao.getUltimaAtualizacao());
        return dto;
    }

    private HistoricoPontosResponseDTO toHistoricoDTO(HistoricoPontos historico) {
        HistoricoPontosResponseDTO dto = new HistoricoPontosResponseDTO();
        dto.setId(historico.getId());
        dto.setUserId(historico.getUser().getId());
        dto.setUserEmail(historico.getUser().getEmail());
        dto.setTipo(historico.getTipo());
        dto.setPontos(historico.getPontos());
        dto.setDescricao(historico.getDescricao());
        dto.setHqId(historico.getHqId());

        // Buscar nome da HQ
        if (historico.getHqId() != null) {
            try {
                Hq hq = hqService.buscarEntidadePorId(historico.getHqId());
                dto.setHqNome(hq.getNome());
            } catch (Exception e) {
                dto.setHqNome("HQ não disponível");
            }
        }

        dto.setDataHora(historico.getDataHora());
        return dto;
    }
}