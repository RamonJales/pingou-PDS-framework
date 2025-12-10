package com.pds.pingou.hq.service;

import com.pds.pingou.hq.entity.HistoricoHQUsuario;
import com.pds.pingou.hq.entity.PreferenciaUsuario;
import com.pds.pingou.hq.entity.Quadrinho;
import com.pds.pingou.hq.enums.CategoriaHQ;
import com.pds.pingou.hq.enums.EditoraHQ;
import com.pds.pingou.hq.enums.TipoHQ;
import com.pds.pingou.hq.exception.PreferenciaNotFoundException;
import com.pds.pingou.hq.repository.HistoricoHQUsuarioRepository;
import com.pds.pingou.hq.repository.PreferenciaUsuarioRepository;
import com.pds.pingou.hq.repository.QuadrinhoRepository;
import com.pds.pingou.security.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Serviço especializado em curadoria de HQs
 * Implementa algoritmo de seleção personalizada baseado em preferências e histórico
 */
@Service
@Transactional
public class CuradoriaService {

    @Autowired
    private QuadrinhoRepository quadrinhoRepository;

    @Autowired
    private PreferenciaUsuarioRepository preferenciaRepository;

    @Autowired
    private HistoricoHQUsuarioRepository historicoRepository;

    /**
     * Curadoria principal: seleciona HQs personalizadas evitando duplicatas
     */
    public List<Quadrinho> curarPacotePersonalizado(User user, int quantidadeClassicas, int quantidadeModernas, boolean incluirEdicoesColecionador) {
        
        // Busca preferências do usuário
        PreferenciaUsuario preferencias = preferenciaRepository.findByUser(user)
            .orElseThrow(() -> new PreferenciaNotFoundException("Usuário sem preferências cadastradas"));

        // Busca IDs de HQs já recebidas pelo usuário
        List<Long> hqsJaRecebidas = historicoRepository.findQuadrinhosIdsRecebidosPorUser(user);

        // Lista final de quadrinhos selecionados
        List<Quadrinho> selecionados = new ArrayList<>();

        // Seleciona HQs clássicas
        if (quantidadeClassicas > 0) {
            List<Quadrinho> classicas = selecionarPorPreferencias(
                TipoHQ.CLASSICA,
                preferencias.getCategoriasFavoritas(),
                preferencias.getEditorasFavoritas(),
                hqsJaRecebidas,
                incluirEdicoesColecionador
            );
            selecionados.addAll(limitarSelecao(classicas, quantidadeClassicas));
        }

        // Seleciona HQs modernas
        if (quantidadeModernas > 0) {
            List<Quadrinho> modernas = selecionarPorPreferencias(
                TipoHQ.MODERNA,
                preferencias.getCategoriasFavoritas(),
                preferencias.getEditorasFavoritas(),
                hqsJaRecebidas,
                incluirEdicoesColecionador
            );
            selecionados.addAll(limitarSelecao(modernas, quantidadeModernas));
        }

        return selecionados;
    }

    /**
     * Seleciona quadrinhos baseado nas preferências do usuário
     */
    private List<Quadrinho> selecionarPorPreferencias(
        TipoHQ tipo,
        Set<CategoriaHQ> categoriasFavoritas,
        Set<EditoraHQ> editorasFavoritas,
        List<Long> hqsJaRecebidas,
        boolean incluirEdicoesColecionador
    ) {
        
        List<Quadrinho> candidatos;

        // Se tem preferências, busca por elas
        if (!categoriasFavoritas.isEmpty() || !editorasFavoritas.isEmpty()) {
            List<CategoriaHQ> categorias = categoriasFavoritas.isEmpty() ? null : new ArrayList<>(categoriasFavoritas);
            List<EditoraHQ> editoras = editorasFavoritas.isEmpty() ? null : new ArrayList<>(editorasFavoritas);
            
            candidatos = quadrinhoRepository.findParaCuradoria(tipo, categorias, editoras);
        } else {
            // Sem preferências específicas, busca todos do tipo
            candidatos = quadrinhoRepository.findByTipoComEstoque(tipo);
        }

        // Remove HQs já recebidas
        candidatos = candidatos.stream()
            .filter(q -> !hqsJaRecebidas.contains(q.getId()))
            .collect(Collectors.toList());

        // Filtra ou prioriza edições de colecionador
        if (incluirEdicoesColecionador) {
            // Prioriza edições de colecionador
            List<Quadrinho> edicoesColecionador = candidatos.stream()
                .filter(q -> Boolean.TRUE.equals(q.getEdicaoColecionador()))
                .collect(Collectors.toList());
            
            List<Quadrinho> normais = candidatos.stream()
                .filter(q -> !Boolean.TRUE.equals(q.getEdicaoColecionador()))
                .collect(Collectors.toList());
            
            // Mistura: 60% colecionador, 40% normais
            Collections.shuffle(edicoesColecionador);
            Collections.shuffle(normais);
            
            List<Quadrinho> resultado = new ArrayList<>();
            int totalColecionador = (int) Math.ceil(candidatos.size() * 0.6);
            
            resultado.addAll(edicoesColecionador.stream().limit(totalColecionador).toList());
            resultado.addAll(normais.stream().limit(candidatos.size() - totalColecionador).toList());
            
            return resultado;
        }

        // Embaralha para variedade
        Collections.shuffle(candidatos);
        return candidatos;
    }

    /**
     * Limita a seleção à quantidade desejada
     */
    private List<Quadrinho> limitarSelecao(List<Quadrinho> candidatos, int quantidade) {
        return candidatos.stream()
            .limit(quantidade)
            .collect(Collectors.toList());
    }

    /**
     * Verifica se o usuário já recebeu um quadrinho
     */
    public boolean usuarioJaRecebeu(User user, Quadrinho quadrinho) {
        return historicoRepository.existsByUserAndQuadrinho(user, quadrinho);
    }

    /**
     * Registra quadrinhos recebidos no histórico
     */
    public void registrarRecebimento(User user, List<Quadrinho> quadrinhos, Long pacoteId) {
        for (Quadrinho quadrinho : quadrinhos) {
            if (!usuarioJaRecebeu(user, quadrinho)) {
                HistoricoHQUsuario historico = new HistoricoHQUsuario(user, quadrinho, pacoteId);
                historicoRepository.save(historico);
            }
        }
    }

    /**
     * Busca recomendações baseadas no histórico de avaliações
     */
    public List<Quadrinho> recomendarBaseadoEmAvaliacoes(User user, int quantidade) {
        // Busca histórico com avaliações
        List<HistoricoHQUsuario> historico = historicoRepository.findByUserAndAvaliacaoIsNotNull(user);
        
        if (historico.isEmpty()) {
            // Sem histórico, retorna aleatórios com estoque
            List<Quadrinho> aleatorios = quadrinhoRepository.findComEstoqueDisponivel();
            Collections.shuffle(aleatorios);
            return aleatorios.stream().limit(quantidade).collect(Collectors.toList());
        }

        // Identifica categorias e editoras melhor avaliadas (4 ou 5 estrelas)
        Set<CategoriaHQ> categoriasPreferidas = historico.stream()
            .filter(h -> h.getAvaliacao() >= 4)
            .map(h -> h.getQuadrinho().getCategoria())
            .collect(Collectors.toSet());

        Set<EditoraHQ> editorasPreferidas = historico.stream()
            .filter(h -> h.getAvaliacao() >= 4)
            .map(h -> h.getQuadrinho().getEditora())
            .collect(Collectors.toSet());

        // Busca HQs similares às bem avaliadas
        List<Long> hqsJaRecebidas = historicoRepository.findQuadrinhosIdsRecebidosPorUser(user);
        
        List<Quadrinho> recomendados = new ArrayList<>();
        
        if (!categoriasPreferidas.isEmpty()) {
            recomendados.addAll(
                quadrinhoRepository.findByCategoriasComEstoque(new ArrayList<>(categoriasPreferidas))
                    .stream()
                    .filter(q -> !hqsJaRecebidas.contains(q.getId()))
                    .collect(Collectors.toList())
            );
        }

        Collections.shuffle(recomendados);
        return recomendados.stream().limit(quantidade).collect(Collectors.toList());
    }

    /**
     * Estatísticas de curadoria para um usuário
     */
    public String gerarEstatisticasCuradoria(User user) {
        long totalRecebidas = historicoRepository.countByUser(user);
        List<HistoricoHQUsuario> avaliadas = historicoRepository.findByUserAndAvaliacaoIsNotNull(user);
        
        double mediaAvaliacoes = avaliadas.isEmpty() ? 0.0 :
            avaliadas.stream()
                .mapToInt(HistoricoHQUsuario::getAvaliacao)
                .average()
                .orElse(0.0);

        return String.format(
            "Total recebidas: %d | Avaliadas: %d | Média avaliações: %.1f/5",
            totalRecebidas,
            avaliadas.size(),
            mediaAvaliacoes
        );
    }
}
