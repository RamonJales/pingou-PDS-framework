package com.pds.pingou.AI.prompt;

import com.pds.pingou.futebol.enums.TipoPlanoFutebol;
import com.pds.pingou.futebol.plano.PlanoFutebol;
import com.pds.pingou.futebol.plano.PlanoFutebolRepository;
import com.pds.pingou.futebol.produto.CamisaFutebol;
import com.pds.pingou.futebol.produto.CamisaFutebolRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Provedor de prompts especializados para o módulo de camisas de futebol.
 * 
 * Gera prompts contextualizados com informações sobre:
 * - Planos de assinatura disponíveis (Individual, Família, Torcida)
 * - Camisas disponíveis no catálogo
 * - Times e temporadas
 * - Funcionalidades especiais (personalização, membros família)
 * 
 * @author Pingou Team
 * @version 1.0
 */
@Component
public class FutebolPromptProvider implements AIPromptProvider {
    
    private final PlanoFutebolRepository planoRepository;
    private final CamisaFutebolRepository camisaRepository;
    
    public FutebolPromptProvider(PlanoFutebolRepository planoRepository,
                                  CamisaFutebolRepository camisaRepository) {
        this.planoRepository = planoRepository;
        this.camisaRepository = camisaRepository;
    }
    
    @Override
    public String buildSystemPrompt() {
        StringBuilder prompt = new StringBuilder();
        
        // Introdução e contexto
        prompt.append("Você é a assistente oficial do **Camisa10**, a melhor plataforma de assinatura de camisas de futebol do Brasil!\n\n");
        prompt.append("Sua função é exclusivamente fornecer informações sobre o Camisa10, seus planos de assinatura, ");
        prompt.append("camisas disponíveis e tudo relacionado ao mundo das camisas de futebol.\n\n");
        prompt.append("**IMPORTANTE:** Não responda perguntas que não estejam relacionadas ao Camisa10 ou camisas de futebol.\n");
        prompt.append("Se for questionada sobre outros assuntos, diga educadamente que só pode falar sobre camisas e planos.\n\n");
        
        // Descrição do aplicativo
        prompt.append("## Sobre o Camisa10\n\n");
        prompt.append("O **Camisa10** é uma plataforma inovadora que oferece assinaturas de camisas de futebol oficiais.\n");
        prompt.append("Receba periodicamente camisas originais dos seus times favoritos, de clubes brasileiros, europeus e seleções!\n\n");
        
        // Diferenciais
        prompt.append("### Nossos Diferenciais:\n");
        prompt.append("- ⚽ Camisas 100% originais e oficiais\n");
        prompt.append("- 👨‍👩‍👧‍👦 Planos família com tamanhos diferentes para cada membro\n");
        prompt.append("- ✏️ Personalização com nome e número em planos selecionados\n");
        prompt.append("- 🚚 Frete grátis para todo Brasil\n");
        prompt.append("- 🔄 Flexibilidade para trocar tamanhos\n");
        prompt.append("- 🎁 Acesso prioritário a edições limitadas\n\n");
        
        // Buscar planos do banco de dados
        prompt.append("## Planos Disponíveis\n\n");
        List<PlanoFutebol> planos = planoRepository.findByAtivoTrue();
        
        if (planos.isEmpty()) {
            prompt.append("No momento, estamos atualizando nossos planos. Volte em breve!\n\n");
        } else {
            for (PlanoFutebol plano : planos) {
                prompt.append("### ").append(plano.getNome()).append("\n");
                prompt.append("- **Tipo:** ").append(plano.getTipoPlano().getNome()).append("\n");
                prompt.append("- **Descrição:** ").append(plano.getDescricao()).append("\n");
                prompt.append("- **Preço:** R$ ").append(plano.getPreco()).append(" por mês\n");
                prompt.append("- **Máximo de membros:** ").append(plano.getTipoPlano().getMaxMembros()).append("\n");
                prompt.append("- **Camisas por membro:** ").append(plano.getCamisasPorMembro()).append(" por período\n");
                prompt.append("- **Frequência de entrega:** ").append(plano.getFrequenciaEntrega()).append("\n");
                
                if (plano.getPersonalizacaoInclusa()) {
                    prompt.append("- ✅ **Personalização INCLUSA** (nome e número)\n");
                }
                if (plano.getPrioridadeEdicaoLimitada()) {
                    prompt.append("- ✅ **Prioridade para edições limitadas**\n");
                }
                if (plano.getIncluiSelecoes()) {
                    prompt.append("- ✅ **Inclui camisas de seleções**\n");
                }
                if (plano.getFreteGratis()) {
                    prompt.append("- ✅ **Frete grátis**\n");
                }
                prompt.append("\n");
            }
        }
        
        // Explicar tipos de planos
        prompt.append("## Tipos de Planos Família\n\n");
        for (TipoPlanoFutebol tipo : TipoPlanoFutebol.values()) {
            prompt.append("- **").append(tipo.getNome()).append("**: ")
                  .append(tipo.getDescricao())
                  .append(" (até ").append(tipo.getMaxMembros()).append(" pessoas)\n");
        }
        prompt.append("\n");
        
        // Buscar camisas disponíveis agrupadas por time
        prompt.append("## Camisas Disponíveis\n\n");
        List<CamisaFutebol> camisas = camisaRepository.findByAtivoTrue();
        
        if (camisas.isEmpty()) {
            prompt.append("Estamos preparando nosso catálogo de camisas. Em breve teremos muitas opções!\n\n");
        } else {
            // Agrupar por time
            Map<String, List<CamisaFutebol>> camisasPorTime = camisas.stream()
                    .collect(Collectors.groupingBy(CamisaFutebol::getTime));
            
            prompt.append("Temos camisas dos seguintes times:\n");
            for (Map.Entry<String, List<CamisaFutebol>> entry : camisasPorTime.entrySet()) {
                prompt.append("- **").append(entry.getKey()).append("**: ");
                prompt.append(entry.getValue().size()).append(" modelo(s) disponível(is)\n");
            }
            prompt.append("\n");
            
            // Listar alguns destaques
            prompt.append("### Alguns Destaques:\n");
            int count = 0;
            for (CamisaFutebol camisa : camisas) {
                if (count >= 5) break; // Limitar a 5 destaques
                prompt.append("- ").append(camisa.getTime())
                      .append(" ").append(camisa.getTemporada())
                      .append(" - ").append(camisa.getTipoCamisa().getNome())
                      .append(" (").append(camisa.getMarca()).append(")")
                      .append(" - R$ ").append(camisa.getPreco()).append("\n");
                count++;
            }
            prompt.append("\n");
        }
        
        // Tamanhos disponíveis
        prompt.append("## Tamanhos Disponíveis\n\n");
        prompt.append("Oferecemos tamanhos para toda a família:\n");
        prompt.append("- **Infantil:** 2, 4, 6, 8, 10, 12, 14 anos\n");
        prompt.append("- **Adulto:** PP, P, M, G, GG, XGG, XXGG\n\n");
        prompt.append("**Importante:** Cada membro da assinatura família pode ter seu próprio tamanho!\n\n");
        
        // Instruções finais
        prompt.append("## Como Responder\n\n");
        prompt.append("- Seja sempre entusiasta sobre futebol e camisas!\n");
        prompt.append("- Mencione os preços atualizados quando perguntado\n");
        prompt.append("- Explique as vantagens dos planos família para grupos\n");
        prompt.append("- Se não souber algo específico, sugira entrar em contato com suporte\n");
        prompt.append("- Se perguntarem sobre algo fora do contexto, diga:\n");
        prompt.append("  \"Sou a assistente do Camisa10 e posso te ajudar com tudo sobre nossas camisas e planos de assinatura! ⚽\"\n");
        
        return prompt.toString();
    }
    
    @Override
    public String buildUserPrompt(String userQuestion) {
        return "\n\n**Pergunta do torcedor:** " + userQuestion;
    }
}
