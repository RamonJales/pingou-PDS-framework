package com.pds.pingou.camisa.service;

import com.pds.pingou.camisa.entity.*;
import com.pds.pingou.camisa.repository.CamisaRepository;
import com.pds.pingou.camisa.repository.HistoricoEnvioCamisaRepository;
import com.pds.pingou.camisa.repository.PerfilMorfologicoRepository;
import com.pds.pingou.security.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CuradoriaCamisaService {
    
    private final CamisaRepository camisaRepository;
    private final HistoricoEnvioCamisaRepository historicoRepository;
    private final PerfilMorfologicoRepository perfilRepository;
    
    /**
     * Realiza a curadoria personalizada de camisas para um usuário
     * Leva em consideração:
     * - Tamanho adequado ao perfil morfológico
     * - Time favorito do usuário
     * - Camisas já enviadas anteriormente
     * - Evita times rivais
     */
    @Transactional(readOnly = true)
    public List<Camisa> curarCamisasParaUsuario(User user, AssinaturaCamisa assinatura, int quantidade) {
        // Busca perfil morfológico
        PerfilMorfologico perfil = perfilRepository.findByUserId(user.getId())
            .orElseThrow(() -> new IllegalStateException("Usuário não possui perfil morfológico cadastrado"));
        
        // Busca histórico de camisas já enviadas
        List<Long> camisasJaEnviadas = historicoRepository.findByUserId(user.getId())
            .stream()
            .map(h -> h.getCamisa().getId())
            .collect(Collectors.toList());
        
        // Busca camisas disponíveis
        List<Camisa> camisasDisponiveis = camisaRepository.findByTamanhoAndEstoqueGreaterThan(
            perfil.getTamanhoRecomendado(), 0
        );
        
        // Aplica lógica de curadoria
        List<Camisa> camisasCuradas = camisasDisponiveis.stream()
            .filter(c -> !camisasJaEnviadas.contains(c.getId())) // Evita repetições
            .filter(c -> !c.getTime().equalsIgnoreCase(assinatura.getTimeRival())) // Evita time rival
            .sorted(new ComparadorCuradoria(assinatura.getTimeFavorito()))
            .limit(quantidade)
            .collect(Collectors.toList());
        
        if (camisasCuradas.size() < quantidade) {
            throw new IllegalStateException(
                String.format("Não há camisas suficientes disponíveis. Necessário: %d, Disponível: %d",
                    quantidade, camisasCuradas.size())
            );
        }
        
        return camisasCuradas;
    }
    
    /**
     * Calcula score de relevância para uma camisa
     */
    private int calcularScore(Camisa camisa, String timeFavorito, List<Long> camisasJaEnviadas) {
        int score = 0;
        
        // Prioriza time favorito
        if (camisa.getTime().equalsIgnoreCase(timeFavorito)) {
            score += 100;
        }
        
        // Prioriza camisas de jogador
        if (camisa.isDeJogador()) {
            score += 50;
        }
        
        // Prioriza camisas mais recentes
        int anoAtual = java.time.Year.now().getValue();
        score += (anoAtual - camisa.getAno()) * -5; // Quanto mais recente, maior o score
        
        // Penaliza se já foi enviada
        if (camisasJaEnviadas.contains(camisa.getId())) {
            score -= 1000;
        }
        
        return score;
    }
    
    /**
     * Gera observações sobre a curadoria realizada
     */
    public String gerarObservacoesCuradoria(List<Camisa> camisas, String timeFavorito) {
        long camisasTimeFavorito = camisas.stream()
            .filter(c -> c.getTime().equalsIgnoreCase(timeFavorito))
            .count();
        
        long camisasJogador = camisas.stream()
            .filter(Camisa::isDeJogador)
            .count();
        
        StringBuilder obs = new StringBuilder();
        obs.append(String.format("Pacote curado com %d camisas. ", camisas.size()));
        
        if (camisasTimeFavorito > 0) {
            obs.append(String.format("%d camisa(s) do seu time favorito (%s). ", 
                camisasTimeFavorito, timeFavorito));
        }
        
        if (camisasJogador > 0) {
            obs.append(String.format("%d camisa(s) versão jogador. ", camisasJogador));
        }
        
        obs.append("Todas as camisas foram selecionadas especialmente para você!");
        
        return obs.toString();
    }
    
    /**
     * Verifica se um usuário tem perfil morfológico cadastrado
     */
    public boolean usuarioPossuiPerfilMorfologico(Long userId) {
        return perfilRepository.findByUserId(userId).isPresent();
    }
    
    /**
     * Comparador personalizado para ordenação de camisas
     */
    private static class ComparadorCuradoria implements Comparator<Camisa> {
        private final String timeFavorito;
        
        public ComparadorCuradoria(String timeFavorito) {
            this.timeFavorito = timeFavorito;
        }
        
        @Override
        public int compare(Camisa c1, Camisa c2) {
            // Prioriza time favorito
            boolean c1TimeFavorito = c1.getTime().equalsIgnoreCase(timeFavorito);
            boolean c2TimeFavorito = c2.getTime().equalsIgnoreCase(timeFavorito);
            
            if (c1TimeFavorito && !c2TimeFavorito) return -1;
            if (!c1TimeFavorito && c2TimeFavorito) return 1;
            
            // Prioriza camisas de jogador
            if (c1.isDeJogador() && !c2.isDeJogador()) return -1;
            if (!c1.isDeJogador() && c2.isDeJogador()) return 1;
            
            // Prioriza camisas mais recentes
            return Integer.compare(c2.getAno(), c1.getAno());
        }
    }
}
