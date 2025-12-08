package com.pds.pingou.assinatura;

import com.pds.pingou.assinatura.exception.AssinaturaDuplicadaException;
import com.pds.pingou.assinatura.exception.AssinaturaNotFoundException;
import com.pds.pingou.enums.StatusAssinatura;
import com.pds.pingou.framework.core.security.exception.UserNotFoundException;
import com.pds.pingou.planos.Plano;
import com.pds.pingou.planos.PlanoRepository;
import com.pds.pingou.planos.exception.PlanoNotFoundException;
import com.pds.pingou.security.user.User;
import com.pds.pingou.security.user.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AssinaturaService {
    @Autowired
    private AssinaturaRepository assinaturaRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PlanoRepository planoRepository;

    @Transactional
    public Assinatura ativarAssinatura(Long userId, Long planoId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId.toString()));
        Plano plano = planoRepository.findById(planoId).orElseThrow(() -> new PlanoNotFoundException(planoId));
        if (assinaturaRepository.findByUser(user).isPresent()) {
            throw new AssinaturaDuplicadaException("Usuário já possui uma assinatura ativa");
        }
        Assinatura assinatura = new Assinatura(user, plano, StatusAssinatura.ATIVA, LocalDate.now(), null);
        user.setAssinatura(assinatura);
        return assinaturaRepository.save(assinatura);
    }

    @Transactional
    public Assinatura desativarAssinatura(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId.toString()));
        Assinatura assinatura = assinaturaRepository.findByUser(user).orElseThrow(() -> new AssinaturaNotFoundException("Assinatura não encontrada"));
        assinatura.setStatus(StatusAssinatura.INATIVA);
        assinatura.setDataExpiracao(LocalDate.now());
        return assinaturaRepository.save(assinatura);
    }

    public List<Assinatura> listarAssinaturas() {
        return assinaturaRepository.findAll();
    }

    public Assinatura buscarPorId(Long id) {
        return assinaturaRepository.findById(id)
                .orElseThrow(() -> new AssinaturaNotFoundException("Assinatura não encontrada"));
    }

    @Transactional
    public Assinatura criarAssinatura(AssinaturaRequestDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new UserNotFoundException(dto.getUserId().toString()));
        Plano plano = planoRepository.findById(dto.getPlanoId())
                .orElseThrow(() -> new PlanoNotFoundException(dto.getPlanoId()));
        if (assinaturaRepository.existsByUser(user)) {
            throw new AssinaturaDuplicadaException("Usuário já possui uma assinatura");
        }
        Assinatura assinatura = new Assinatura(user, plano, StatusAssinatura.ATIVA, LocalDate.now(), null);
        user.setAssinatura(assinatura);
        return assinaturaRepository.save(assinatura);
    }

    @Transactional
    public Assinatura editarAssinatura(Long id, AssinaturaRequestDTO dto) {
        Assinatura assinatura = assinaturaRepository.findById(id)
                .orElseThrow(() -> new AssinaturaNotFoundException("Assinatura não encontrada"));
        Plano plano = planoRepository.findById(dto.getPlanoId())
                .orElseThrow(() -> new PlanoNotFoundException(dto.getPlanoId()));
        assinatura.setPlano(plano);
        return assinaturaRepository.save(assinatura);
    }

    @Transactional
    public void deletarAssinatura(Long id) {
        Assinatura assinatura = assinaturaRepository.findById(id)
                .orElseThrow(() -> new AssinaturaNotFoundException("Assinatura não encontrada"));
        assinaturaRepository.delete(assinatura);
    }
}
