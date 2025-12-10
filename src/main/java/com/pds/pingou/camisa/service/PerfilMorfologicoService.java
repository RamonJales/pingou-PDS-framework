package com.pds.pingou.camisa.service;

import com.pds.pingou.camisa.entity.PerfilMorfologico;
import com.pds.pingou.camisa.repository.PerfilMorfologicoRepository;
import com.pds.pingou.security.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PerfilMorfologicoService {
    
    private final PerfilMorfologicoRepository repository;
    
    @Transactional
    public PerfilMorfologico criarPerfil(User user, PerfilMorfologico perfil) {
        if (repository.existsByUserId(user.getId())) {
            throw new IllegalStateException("Usuário já possui perfil morfológico cadastrado");
        }
        
        perfil.setUser(user);
        return repository.save(perfil);
    }
    
    @Transactional
    public PerfilMorfologico atualizarPerfil(Long userId, PerfilMorfologico novosDados) {
        PerfilMorfologico perfil = repository.findByUserId(userId)
            .orElseThrow(() -> new RuntimeException("Perfil morfológico não encontrado"));
        
        perfil.setAltura(novosDados.getAltura());
        perfil.setPeso(novosDados.getPeso());
        perfil.setCircunferenciaPeito(novosDados.getCircunferenciaPeito());
        perfil.setCircunferenciaCintura(novosDados.getCircunferenciaCintura());
        perfil.setComprimentoTorso(novosDados.getComprimentoTorso());
        perfil.setLarguraOmbros(novosDados.getLarguraOmbros());
        
        return repository.save(perfil);
    }
    
    @Transactional(readOnly = true)
    public PerfilMorfologico buscarPorUserId(Long userId) {
        return repository.findByUserId(userId)
            .orElseThrow(() -> new RuntimeException("Perfil morfológico não encontrado"));
    }
    
    @Transactional(readOnly = true)
    public boolean usuarioPossuiPerfil(Long userId) {
        return repository.existsByUserId(userId);
    }
    
    @Transactional
    public void deletarPerfil(Long userId) {
        PerfilMorfologico perfil = repository.findByUserId(userId)
            .orElseThrow(() -> new RuntimeException("Perfil morfológico não encontrado"));
        repository.delete(perfil);
    }
}
