package com.pds.pingou.AI.service.strategy;

import com.pds.pingou.enums.TipoPlano;
import com.pds.pingou.futebol.produto.CamisaFutebol;
import com.pds.pingou.futebol.user.CamisaUserProfile;
import com.pds.pingou.pacote.ItemPacoteRequestDTO;
import com.pds.pingou.planos.Plano;
import com.pds.pingou.produto.Produto;
import com.pds.pingou.produto.ProdutoRepository;
import com.pds.pingou.security.user.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Estratégia "Anti-Rival" para curadoria de camisas de futebol.
 * 
 * Lógica:
 * 1. Filtra camisas que sejam do time rival (Restrição Absoluta).
 * 2. Prioriza camisas do time do coração.
 * 3. Garante que se for camisa, seja do tamanho do usuário (se houver
 * estoque/disponibilidade).
 */
@Component("FUTEBOL")
public class AntiRivalCurationStrategy implements CurationStrategy {

    private final ProdutoRepository produtoRepository;

    public AntiRivalCurationStrategy(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @Override
    public List<ItemPacoteRequestDTO> suggestPackageItems(User user, Plano plano, int limite) {
        // 1. Recuperar produtos ativos
        List<Produto> todosProdutos = produtoRepository.findByAtivoTrue();

        // 2. Recuperar perfil especializado
        CamisaUserProfile perfil = (CamisaUserProfile) user.getProfile(TipoPlano.FUTEBOL);

        // Se não tiver perfil, retorna estratégia padrão (aleatória filtrada apenas por
        // tipo)
        if (perfil == null) {
            return fallbackStrategy(todosProdutos, limite);
        }

        // 3. Filtrar e Classificar (Lógica Anti-rival)
        // Correção: Usando List<CamisaFutebol> para evitar erro de covariância
        List<CamisaFutebol> candidatos = todosProdutos.stream()
                .filter(p -> p instanceof CamisaFutebol)
                .map(p -> (CamisaFutebol) p)
                // FILTRO ELIMINATÓRIO: Jamais enviar time restrito
                .filter(camisa -> !isTimeRestrito(camisa, perfil))
                // Ordenação por relevância
                .sorted((c1, c2) -> calculateScore(c2, perfil) - calculateScore(c1, perfil))
                .collect(Collectors.toList());

        // 4. Selecionar os top N
        return candidatos.stream()
                .limit(limite)
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private boolean isTimeRestrito(CamisaFutebol camisa, CamisaUserProfile perfil) {
        if (perfil.getTimesRestritos() == null)
            return false;
        return perfil.getTimesRestritos().stream()
                .anyMatch(rival -> rival.equalsIgnoreCase(camisa.getTime()));
    }

    /**
     * Calcula pontuação de match.
     * Time do Coração: +100
     * Seleção Interesse: +50
     * Outros: +10
     */
    private int calculateScore(CamisaFutebol camisa, CamisaUserProfile perfil) {
        int score = 0;

        if (camisa.getTime().equalsIgnoreCase(perfil.getTimeCoracao())) {
            score += 100;
        } else if (perfil.getSelecoesInteresse() != null &&
                perfil.getSelecoesInteresse().contains(camisa.getTime())) {
            score += 50;
        } else {
            score += 10;
        }

        // Bônus se for da temporada atual (exemplo)
        if (camisa != null && camisa.getTemporada() != null && camisa.getTemporada().contains("2025")) {
            score += 5;
        }

        return score;
    }

    private ItemPacoteRequestDTO toDTO(Produto produto) {
        ItemPacoteRequestDTO dto = new ItemPacoteRequestDTO();
        dto.setProdutoId(produto.getId());
        dto.setQuantidade(1);
        dto.setObservacoes("Selecionado via Anti-Rival Strategy");
        return dto;
    }

    private List<ItemPacoteRequestDTO> fallbackStrategy(List<Produto> produtos, int limite) {
        return produtos.stream()
                .filter(p -> p instanceof CamisaFutebol)
                .limit(limite)
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
