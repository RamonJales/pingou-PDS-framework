package com.pds.pingou.assinatura.hq;

import com.pds.pingou.assinatura.exception.AssinaturaDuplicadaException;
import com.pds.pingou.assinatura.exception.AssinaturaNotFoundException;
import com.pds.pingou.framework.core.enums.SubscriptionStatus;
import com.pds.pingou.planos.hq.PlanoHq;
import com.pds.pingou.planos.hq.PlanoHqService;
import com.pds.pingou.pontuacao.PontuacaoService;
import com.pds.pingou.security.exception.UserNotFoundException;
import com.pds.pingou.security.user.User;
import com.pds.pingou.security.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AssinaturaHqService {

    @Autowired
    private AssinaturaHqRepository assinaturaHqRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PlanoHqService planoHqService;

    @Autowired
    private PontuacaoService pontuacaoService;

    @Transactional
    public AssinaturaHqResponseDTO ativar(Long userId, Long planoId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId.toString()));

        PlanoHq plano = planoHqService.buscarEntidadePorId(planoId);

        if (assinaturaHqRepository.existsByUser(user)) {
            throw new AssinaturaDuplicadaException("Usuário já possui uma assinatura de HQ ativa");
        }

        AssinaturaHq assinatura = new AssinaturaHq(user, plano);
        assinatura = assinaturaHqRepository.save(assinatura);

        // Criar pontuação para o usuário se ainda não existir
        pontuacaoService.criarOuObterPontuacao(userId);

        return AssinaturaHqMapper.toDTO(assinatura);
    }

    @Transactional
    public AssinaturaHqResponseDTO desativar(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId.toString()));

        AssinaturaHq assinatura = assinaturaHqRepository.findByUser(user)
                .orElseThrow(() -> new AssinaturaNotFoundException("Assinatura não encontrada"));

        assinatura.deactivate();
        assinatura = assinaturaHqRepository.save(assinatura);

        return AssinaturaHqMapper.toDTO(assinatura);
    }

    public List<AssinaturaHqResponseDTO> listarTodas() {
        return assinaturaHqRepository.findAll().stream()
                .map(AssinaturaHqMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<AssinaturaHqResponseDTO> listarAtivas() {
        return assinaturaHqRepository.findByStatus(SubscriptionStatus.ATIVA).stream()
                .map(AssinaturaHqMapper::toDTO)
                .collect(Collectors.toList());
    }

    public AssinaturaHqResponseDTO buscarPorId(Long id) {
        AssinaturaHq assinatura = assinaturaHqRepository.findById(id)
                .orElseThrow(() -> new AssinaturaNotFoundException("Assinatura não encontrada"));
        return AssinaturaHqMapper.toDTO(assinatura);
    }

    public AssinaturaHqResponseDTO buscarPorUsuario(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId.toString()));

        AssinaturaHq assinatura = assinaturaHqRepository.findByUser(user)
                .orElseThrow(() -> new AssinaturaNotFoundException("Assinatura não encontrada"));

        return AssinaturaHqMapper.toDTO(assinatura);
    }

    @Transactional
    public AssinaturaHqResponseDTO criar(AssinaturaHqRequestDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new UserNotFoundException(dto.getUserId().toString()));

        PlanoHq plano = planoHqService.buscarEntidadePorId(dto.getPlanoId());

        if (assinaturaHqRepository.existsByUser(user)) {
            throw new AssinaturaDuplicadaException("Usuário já possui uma assinatura de HQ");
        }

        AssinaturaHq assinatura = AssinaturaHqMapper.toEntity(dto, user, plano);
        assinatura = assinaturaHqRepository.save(assinatura);

        // Criar pontuação para o usuário
        pontuacaoService.criarOuObterPontuacao(dto.getUserId());

        return AssinaturaHqMapper.toDTO(assinatura);
    }

    @Transactional
    public void deletar(Long id) {
        AssinaturaHq assinatura = assinaturaHqRepository.findById(id)
                .orElseThrow(() -> new AssinaturaNotFoundException("Assinatura não encontrada"));
        assinaturaHqRepository.delete(assinatura);
    }
}