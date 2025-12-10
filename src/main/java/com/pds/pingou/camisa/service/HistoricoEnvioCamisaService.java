package com.pds.pingou.camisa.service;

import com.pds.pingou.camisa.entity.AssinaturaCamisa;
import com.pds.pingou.camisa.entity.Camisa;
import com.pds.pingou.camisa.entity.HistoricoEnvioCamisa;
import com.pds.pingou.camisa.repository.HistoricoEnvioCamisaRepository;
import com.pds.pingou.security.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoricoEnvioCamisaService {
    
    private final HistoricoEnvioCamisaRepository repository;
    
    @Transactional
    public HistoricoEnvioCamisa registrarEnvio(User user, Camisa camisa, AssinaturaCamisa assinatura, 
                                               LocalDateTime dataEntregaPrevista, String observacoes) {
        HistoricoEnvioCamisa historico = new HistoricoEnvioCamisa();
        historico.setUser(user);
        historico.setCamisa(camisa);
        historico.setAssinatura(assinatura);
        historico.setDataEnvio(LocalDateTime.now());
        historico.setDataEntregaPrevista(dataEntregaPrevista);
        historico.setObservacoes(observacoes);
        
        // Decrementa estoque
        camisa.decrementarEstoque();
        
        return repository.save(historico);
    }
    
    @Transactional
    public HistoricoEnvioCamisa marcarComoEntregue(Long historicoId) {
        HistoricoEnvioCamisa historico = repository.findById(historicoId)
            .orElseThrow(() -> new RuntimeException("Histórico de envio não encontrado"));
        
        historico.marcarComoEntregue();
        return repository.save(historico);
    }
    
    @Transactional(readOnly = true)
    public List<HistoricoEnvioCamisa> buscarPorUsuario(Long userId) {
        return repository.findByUserId(userId);
    }
    
    @Transactional(readOnly = true)
    public List<HistoricoEnvioCamisa> buscarPorAssinatura(Long assinaturaId) {
        return repository.findByAssinaturaId(assinaturaId);
    }
    
    @Transactional(readOnly = true)
    public List<HistoricoEnvioCamisa> buscarEnviosPendentes() {
        return repository.findByDataEntregaRealizadaIsNull();
    }
}
