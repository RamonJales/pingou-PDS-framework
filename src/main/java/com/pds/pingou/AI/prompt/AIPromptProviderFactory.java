package com.pds.pingou.AI.prompt;

import org.springframework.stereotype.Component;

/**
 * Factory para criação de provedores de prompts de IA.
 * 
 * Este factory implementa o padrão Factory Method, permitindo
 * a criação flexível de diferentes tipos de provedores de prompts
 * conforme necessário.
 * 
 * Tipos suportados:
 * - "futebol" ou "camisa10": Prompts para o módulo de camisas de futebol
 * - "pingou" ou "cachaca": Prompts para o módulo original de cachaça (legado)
 * 
 * @author Pingou Team
 * @version 2.0
 * @since 1.0
 */
@Component
public class AIPromptProviderFactory {
    
    private final FutebolPromptProvider futebolPromptProvider;
    private final PingouPromptProvider pingouPromptProvider;
    
    public AIPromptProviderFactory(FutebolPromptProvider futebolPromptProvider,
                                   PingouPromptProvider pingouPromptProvider) {
        this.futebolPromptProvider = futebolPromptProvider;
        this.pingouPromptProvider = pingouPromptProvider;
    }
    
    /**
     * Cria um provedor de prompts baseado no tipo solicitado.
     * 
     * @param type Tipo do provedor ("futebol", "camisa10", "pingou", "cachaca")
     * @return Instância do provedor de prompts
     * @throws IllegalArgumentException se o tipo não for reconhecido
     */
    public AIPromptProvider createProvider(String type) {
        if (type == null) {
            return createDefaultProvider();
        }
        
        return switch (type.toLowerCase()) {
            case "futebol", "camisa10", "camisas", "jersey" -> futebolPromptProvider;
            case "pingou", "cachaca", "cachaça" -> pingouPromptProvider;
            default -> throw new IllegalArgumentException("Tipo de provedor não reconhecido: " + type + 
                ". Tipos válidos: futebol, camisa10, pingou, cachaca");
        };
    }
    
    /**
     * Retorna o provedor padrão (Futebol - o módulo atual).
     * 
     * @return Provedor de prompts padrão para camisas de futebol
     */
    public AIPromptProvider createDefaultProvider() {
        return futebolPromptProvider;
    }
}

