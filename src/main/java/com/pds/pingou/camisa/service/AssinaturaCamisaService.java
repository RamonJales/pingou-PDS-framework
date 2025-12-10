package com.pds.pingou.camisa.service;

import com.pds.pingou.camisa.entity.*;
import com.pds.pingou.camisa.repository.AssinaturaCamisaRepository;
import com.pds.pingou.framework.core.service.BaseSubscriptionService;
import com.pds.pingou.security.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssinaturaCamisaService extends BaseSubscriptionService<AssinaturaCamisa, AssinaturaCamisaRepository> {
    
    private final AssinaturaCamisaRepository repository;
    private final PerfilMorfologicoService perfilService;
    
    @Override
    protected AssinaturaCamisaRepository getRepository() {
        return repository;
    }
    
    @Override
    protected RuntimeException createNotFoundException(Long id) {
        return new RuntimeException("Assinatura não encontrada com ID: " + id);
    }
    
    @Transactional
    public AssinaturaCamisa criarAssinatura(User user, PlanoCamisa plano, String timeFavorito, String timeRival) {
        // Valida se usuário possui perfil morfológico
        if (!perfilService.usuarioPossuiPerfil(user.getId())) {
            throw new IllegalStateException("Usuário deve ter perfil morfológico cadastrado antes de assinar");
        }
        
        // Valida se usuário já possui assinatura
        if (repository.existsByUserId(user.getId())) {
            throw new IllegalStateException("Usuário já possui uma assinatura ativa");
        }
        
        AssinaturaCamisa assinatura = new AssinaturaCamisa();
        assinatura.setUser(user);
        assinatura.setPlan(plano);
        assinatura.setTimeFavorito(timeFavorito);
        assinatura.setTimeRival(timeRival);
        assinatura.activate();
        
        return repository.save(assinatura);
    }
    
    @Transactional
    public AssinaturaCamisa adicionarParticipante(Long assinaturaPrincipalId, User novoParticipante, 
                                                   String timeFavorito, String timeRival) {
        AssinaturaCamisa principal = findByIdOrThrow(assinaturaPrincipalId);
        
        if (!principal.isAssinaturaPrincipal()) {
            throw new IllegalStateException("Apenas assinatura principal pode adicionar participantes");
        }
        
        if (!principal.getPlan().getPermiteCompartilhamento()) {
            throw new IllegalStateException("Este plano não permite compartilhamento");
        }
        
        if (!perfilService.usuarioPossuiPerfil(novoParticipante.getId())) {
            throw new IllegalStateException("Participante deve ter perfil morfológico cadastrado");
        }
        
        if (repository.existsByUserId(novoParticipante.getId())) {
            throw new IllegalStateException("Participante já possui uma assinatura");
        }
        
        AssinaturaCamisa assinaturaParticipante = new AssinaturaCamisa();
        assinaturaParticipante.setUser(novoParticipante);
        assinaturaParticipante.setPlan(principal.getPlan());
        assinaturaParticipante.setTimeFavorito(timeFavorito);
        assinaturaParticipante.setTimeRival(timeRival);
        assinaturaParticipante.activate();
        
        principal.adicionarParticipante(assinaturaParticipante);
        
        repository.save(principal);
        return repository.save(assinaturaParticipante);
    }
    
    @Transactional
    public void removerParticipante(Long assinaturaPrincipalId, Long participanteId) {
        AssinaturaCamisa principal = findByIdOrThrow(assinaturaPrincipalId);
        AssinaturaCamisa participante = findByIdOrThrow(participanteId);
        
        if (!participante.isAssinaturaCompartilhada()) {
            throw new IllegalStateException("Assinatura não é compartilhada");
        }
        
        if (!participante.getAssinaturaPrincipal().getId().equals(assinaturaPrincipalId)) {
            throw new IllegalStateException("Participante não pertence a esta assinatura principal");
        }
        
        principal.removerParticipante(participante);
        repository.save(principal);
        repository.delete(participante);
    }
    
    @Transactional(readOnly = true)
    public List<AssinaturaCamisa> buscarParticipantes(Long assinaturaPrincipalId) {
        return repository.findParticipantesByAssinaturaPrincipalId(assinaturaPrincipalId);
    }
    
    @Transactional(readOnly = true)
    public AssinaturaCamisa buscarPorUserId(Long userId) {
        return repository.findByUserId(userId)
            .orElseThrow(() -> new RuntimeException("Assinatura não encontrada para o usuário"));
    }
}
