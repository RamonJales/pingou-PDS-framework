package com.pds.pingou.camisa.service;

import com.pds.pingou.camisa.entity.*;
import com.pds.pingou.camisa.repository.PacoteCamisaRepository;
import com.pds.pingou.framework.core.service.BasePackageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PacoteCamisaService extends BasePackageService<PacoteCamisa, PacoteCamisaRepository> {
    
    private final PacoteCamisaRepository repository;
    private final CuradoriaCamisaService curadoriaService;
    private final AssinaturaCamisaService assinaturaService;
    
    @Override
    protected PacoteCamisaRepository getRepository() {
        return repository;
    }
    
    @Override
    protected RuntimeException createNotFoundException(Long id) {
        return new RuntimeException("Pacote não encontrado com ID: " + id);
    }
    
    @Transactional
    public PacoteCamisa criarPacoteCurado(Long assinaturaId, LocalDate dataEnvio) {
        AssinaturaCamisa assinatura = assinaturaService.findByIdOrThrow(assinaturaId);
        
        if (!assinatura.isActive()) {
            throw new IllegalStateException("Assinatura não está ativa");
        }
        
        // Busca camisas curadas
        List<Camisa> camisasCuradas = curadoriaService.curarCamisasParaUsuario(
            assinatura.getUser(), 
            assinatura, 
            assinatura.getPlan().getCamisasPorMes()
        );
        
        // Cria o pacote
        PacoteCamisa pacote = new PacoteCamisa();
        pacote.setPlan(assinatura.getPlan());
        pacote.setDataEnvio(dataEnvio);
        
        // Adiciona itens ao pacote
        for (Camisa camisa : camisasCuradas) {
            ItemPacoteCamisa item = new ItemPacoteCamisa();
            item.setPacote(pacote);
            item.setCamisa(camisa);
            item.setQuantidade(1);
            item.setMotivoSelecao(String.format("Selecionada por curadoria para %s", assinatura.getUser().getNome()));
            
            pacote.getItens().add(item);
        }
        
        // Marca como curado
        String observacoes = curadoriaService.gerarObservacoesCuradoria(
            camisasCuradas, 
            assinatura.getTimeFavorito()
        );
        pacote.marcarComoCurado(observacoes);
        
        return repository.save(pacote);
    }
    
    @Transactional(readOnly = true)
    public List<PacoteCamisa> buscarPorPlano(Long planoId) {
        return repository.findByPlanoId(planoId);
    }
    
    @Transactional(readOnly = true)
    public List<PacoteCamisa> buscarPorDataEnvio(LocalDate dataEnvio) {
        return repository.findByDataEnvio(dataEnvio);
    }
    
    @Transactional(readOnly = true)
    public List<PacoteCamisa> buscarPorPeriodo(LocalDate inicio, LocalDate fim) {
        return repository.findByDataEnvioBetween(inicio, fim);
    }
}
