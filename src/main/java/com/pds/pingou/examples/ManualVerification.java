package com.pds.pingou.examples;

import com.pds.pingou.AI.service.strategy.AntiRivalCurationStrategy;
import com.pds.pingou.enums.TipoPlano;
import com.pds.pingou.futebol.enums.TipoCamisa;
import com.pds.pingou.futebol.produto.CamisaFutebol;
import com.pds.pingou.futebol.user.CamisaUserProfile;
import com.pds.pingou.pacote.ItemPacoteRequestDTO;
import com.pds.pingou.planos.Plano;
import com.pds.pingou.produto.Produto;
import com.pds.pingou.produto.ProdutoRepository;
import com.pds.pingou.security.user.User;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;



/**
 * Classe de verificação manual para testar a estratégia Anti-Rival.
 * 
 * Executa um cenário onde um torcedor do Flamengo odeia o Vasco.
 */
public class ManualVerification {

    public static void main(String[] args) {
        System.out.println("=== INICIANDO VERIFICAÇÃO ANTI-RIVAL ===");

        // 1. Setup Mock Repository usando Mockito
        ProdutoRepository mockRepo = mock(ProdutoRepository.class);
        when(mockRepo.findByAtivoTrue()).thenReturn(criarCamisasTeste());

        // 2. Setup Strategy
        AntiRivalCurationStrategy strategy = new AntiRivalCurationStrategy(mockRepo);

        // 3. Setup User (Flamenguista, Odeia Vasco)
        User user = new User();
        CamisaUserProfile profile = new CamisaUserProfile("Flamengo", "G");
        profile.adicionarRestricao("Vasco");
        user.addProfile(TipoPlano.FUTEBOL, profile);

        // 4. Setup Plano
        Plano plano = new Plano("Plano Torcedor", "Desc", BigDecimal.TEN, 5);

        // 5. Executar Sugestão
        List<ItemPacoteRequestDTO> sugestoes = strategy.suggestPackageItems(user, plano, 5);

        // 6. Verificar Resultados
        verificarResultados(sugestoes);

        System.out.println("=== FIM DA VERIFICAÇÃO ===");
    }

    private static List<Produto> criarCamisasTeste() {
        List<Produto> lista = new ArrayList<>();

        // Camisa 1: Flamengo (Coração)
        CamisaFutebol flamengo = new CamisaFutebol("Manto Sagrado", "Camisa 1", BigDecimal.valueOf(300),
                "Flamengo", "2024", TipoCamisa.PRINCIPAL, "Adidas");
        flamengo.setId(1L);
        lista.add(flamengo);

        // Camisa 2: Vasco (Rival)
        CamisaFutebol vasco = new CamisaFutebol("Camisa Cruzmaltina", "Camisa 1", BigDecimal.valueOf(300),
                "Vasco", "2024", TipoCamisa.PRINCIPAL, "Kappa");
        vasco.setId(2L);
        lista.add(vasco);

        // Camisa 3: Real Madrid (Neutro)
        CamisaFutebol real = new CamisaFutebol("Camisa Real", "Camisa 1", BigDecimal.valueOf(400),
                "Real Madrid", "2024", TipoCamisa.PRINCIPAL, "Adidas");
        real.setId(3L);
        lista.add(real);

        return lista;
    }

    private static void verificarResultados(List<ItemPacoteRequestDTO> sugestoes) {
        System.out.println("Sugestões geradas: " + sugestoes.size());
        
        boolean temVasco = sugestoes.stream().anyMatch(item -> item.getProdutoId() == 2L);
        boolean temFlamengo = sugestoes.stream().anyMatch(item -> item.getProdutoId() == 1L);

        sugestoes.forEach(item -> System.out.println("Item ID Sugerido: " + item.getProdutoId()));

        if (temVasco) {
            System.out.println("❌ FALHA: Camisa do Vasco foi sugerida!");
        } else {
            System.out.println("✅ SUCESSO: Nenhuma camisa do Vasco sugerida.");
        }

        if (temFlamengo) {
            System.out.println("✅ SUCESSO: Camisa do Flamengo foi sugerida (prioridade).");
        } else {
            System.out.println("⚠️ AVISO: Camisa do Flamengo não foi sugerida (verificar lógica de score).");
        }
    }
}

