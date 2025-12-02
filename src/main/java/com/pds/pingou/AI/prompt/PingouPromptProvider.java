package com.pds.pingou.AI.prompt;

import com.pds.pingou.camisa.planos.PlanoCamisa;
import com.pds.pingou.camisa.planos.PlanoCamisaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PingouPromptProvider implements AIPromptProvider {
    
    private final PlanoCamisaRepository planoRepository;
    
    public PingouPromptProvider(PlanoCamisaRepository planoRepository) {
        this.planoRepository = planoRepository;
    }
    
    @Override
    public String buildSystemPrompt() {
        StringBuilder prompt = new StringBuilder();
        
        // Introdução e contexto
        prompt.append("Você é a assistente oficial do aplicativo *Camisa Club*, um app de assinatura de camisas de futebol.\n");
        prompt.append("Sua função é exclusivamente fornecer informações sobre o Camisa Club, seus planos de assinatura e\n");
        prompt.append("curiosidades sobre o mundo do futebol e camisas de times ligadas ao aplicativo.\n");
        prompt.append("Não responda perguntas que não estejam relacionadas ao Camisa Club.\n");
        prompt.append("Se for questionada sobre outros assuntos, deixe claro que só pode falar sobre os planos e serviços do aplicativo.\n\n");
        
        // Descrição do aplicativo
        prompt.append("O *Camisa Club* é uma plataforma que conecta torcedores e colecionadores a experiências únicas,\n");
        prompt.append("recebendo mensalmente camisas de futebol de times nacionais e internacionais. Os planos disponíveis atualmente são:\n\n");
        
        // Buscar planos do banco de dados
        List<PlanoCamisa> planos = planoRepository.findAll();
        
        if (planos.isEmpty()) {
            prompt.append("No momento, não temos planos ativos cadastrados. Por favor, verifique mais tarde.\n\n");
        } else {
            int contador = 1;
            for (PlanoCamisa plano : planos) {
                if (plano.getAtivo()) {
                    prompt.append(contador).append(". **").append(plano.getNome()).append("**\n");
                    prompt.append("   ").append(plano.getDescricao()).append("\n");
                    prompt.append("   Preço: R$ ").append(plano.getPreco()).append(" por mês\n");
                    prompt.append("   Máximo de camisas por mês: ").append(plano.getMaxProdutosPorPeriodo()).append("\n");
                    prompt.append("   Frequência de entrega: ").append(plano.getFrequenciaEntrega()).append("\n");
                    prompt.append("   Categoria: ").append(plano.getCategoriaPlano()).append("\n");
                    if (plano.getLigaFoco() != null) {
                        prompt.append("   Liga em foco: ").append(plano.getLigaFoco()).append("\n");
                    }
                    if (plano.getPermitePersonalizacao() != null && plano.getPermitePersonalizacao()) {
                        prompt.append("   Permite personalização: Sim\n");
                    }
                    if (plano.getPrioridadeLancamentos() != null && plano.getPrioridadeLancamentos()) {
                        prompt.append("   Prioridade em lançamentos: Sim\n");
                    }
                    if (plano.getDescontoLoja() != null && plano.getDescontoLoja().compareTo(java.math.BigDecimal.ZERO) > 0) {
                        prompt.append("   Desconto na loja: ").append(plano.getDescontoLoja()).append("%\n");
                    }
                    prompt.append("\n");
                    contador++;
                }
            }
        }
        
        // Instruções finais
        prompt.append("Lembre-se:\n");
        prompt.append("- Você só deve responder perguntas relacionadas ao aplicativo *Camisa Club* e seus planos.\n");
        prompt.append("- Se alguém perguntar algo fora desse contexto, diga:\n");
        prompt.append("  \"Sou a assistente do Camisa Club e só posso responder perguntas sobre nossos planos de assinatura de camisas de futebol.\"\n");
        prompt.append("- Seja sempre educada, prestativa e entusiasta sobre futebol e cultura de camisas de times.\n");
        prompt.append("- Se for perguntado sobre valores, sempre mencione os preços atualizados dos planos.\n");
        prompt.append("- As camisas podem ser de times nacionais, internacionais, clássicos, retrô e edições especiais.\n");
        
        return prompt.toString();
    }
    
    @Override
    public String buildUserPrompt(String userQuestion) {
        return "\n\nPergunta do usuário: " + userQuestion;
    }
}

