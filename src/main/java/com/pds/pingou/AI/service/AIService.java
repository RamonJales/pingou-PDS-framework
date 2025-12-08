package com.pds.pingou.AI.service;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import com.pds.pingou.AI.dto.AIQuestionDTO;
import com.pds.pingou.AI.dto.AIResponseDTO;
import com.pds.pingou.AI.prompt.AIPromptProvider;
import com.pds.pingou.AI.prompt.AIPromptProviderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AIService {
    
    private static final Logger logger = LoggerFactory.getLogger(AIService.class);
    private static final String DEFAULT_MODEL = "gemini-2.0-flash-exp";
    
    private final AIPromptProviderFactory promptProviderFactory;
    private Client aiClient;
    private final String apiKey;
    private boolean initialized = false;
    
    @Value("${ai.model:gemini-2.0-flash-exp}")
    private String modelName;
    
    public AIService(AIPromptProviderFactory promptProviderFactory,
                     @Value("${google.ai.api.key:}") String apiKey) {
        this.promptProviderFactory = promptProviderFactory;
        this.apiKey = apiKey;
        
        // Inicialização preguiçosa - só inicializa quando for usar
        if (apiKey != null && !apiKey.isBlank()) {
            try {
                // Configurar a API key como variável de ambiente para a biblioteca
                System.setProperty("GOOGLE_API_KEY", apiKey);
                
                // Inicializar o Client sem parâmetros
                this.aiClient = new Client();
                this.initialized = true;
                
                logger.info("AIService inicializado com sucesso usando o modelo: {}", DEFAULT_MODEL);
                if (apiKey.length() >= 10) {
                    logger.info("API Key configurada (primeiros 10 caracteres): {}...", apiKey.substring(0, 10));
                }
            } catch (Exception e) {
                logger.warn("AIService não foi inicializado. Funcionalidades de IA estarão desabilitadas: {}", e.getMessage());
                this.initialized = false;
            }
        } else {
            logger.warn("API Key do Google AI não configurada. Funcionalidades de IA estarão desabilitadas.");
            this.initialized = false;
        }
    }

    public AIResponseDTO processQuestion(AIQuestionDTO questionDTO) {
        if (!initialized || aiClient == null) {
            logger.warn("AIService não está inicializado. Funcionalidades de IA desabilitadas.");
            return AIResponseDTO.error("Serviço de IA não está disponível. Verifique a configuração da API Key.");
        }
        
        try {
            // Validação da entrada
            if (questionDTO.getQuestion() == null || questionDTO.getQuestion().trim().isEmpty()) {
                logger.warn("Tentativa de enviar pergunta vazia para IA");
                return AIResponseDTO.error("Pergunta não pode estar vazia");
            }
            
            // Obter o provedor de prompts apropriado via Factory
            // Default é "futebol" para o módulo de camisas de futebol
            AIPromptProvider promptProvider = promptProviderFactory.createProvider(
                questionDTO.getContextType() != null ? questionDTO.getContextType() : "futebol"
            );
            
            // Construir o prompt completo
            String systemPrompt = promptProvider.buildSystemPrompt();
            String userPrompt = promptProvider.buildUserPrompt(questionDTO.getQuestion());
            String fullPrompt = systemPrompt + userPrompt;
            
            logger.info("Processando pergunta para IA: {}", questionDTO.getQuestion());
            logger.debug("Usando modelo: {}", modelName != null ? modelName : DEFAULT_MODEL);
            
            // Chamar a API do Gemini
            GenerateContentResponse response = aiClient.models.generateContent(
                modelName != null ? modelName : DEFAULT_MODEL,
                fullPrompt,
                null
            );
            
            String responseText = response.text();
            logger.info("Resposta da IA gerada com sucesso (tamanho: {} caracteres)", responseText.length());
            
            return new AIResponseDTO(responseText);
            
        } catch (IllegalArgumentException e) {
            logger.error("Tipo de contexto inválido: {}", e.getMessage());
            return AIResponseDTO.error("Tipo de contexto inválido: " + e.getMessage());
            
        } catch (Exception e) {
            logger.error("Erro ao processar pergunta na IA: {}", e.getMessage(), e);
            String errorMessage = "Desculpe, ocorreu um erro ao processar sua pergunta. ";
            
            // Mensagem mais amigável baseada no tipo de erro
            if (e.getMessage() != null && e.getMessage().contains("API key")) {
                errorMessage += "Verifique a configuração da chave da API.";
            } else if (e.getMessage() != null && e.getMessage().contains("quota")) {
                errorMessage += "Limite de uso da API excedido.";
            } else if (e.getMessage() != null && e.getMessage().contains("network") || 
                       e.getMessage() != null && e.getMessage().contains("timeout")) {
                errorMessage += "Problema de conexão. Tente novamente.";
            } else {
                errorMessage += "Tente novamente em alguns instantes.";
            }
            
            return AIResponseDTO.error(errorMessage);
        }
    }

    public String processSimpleQuestion(String question) {
        AIQuestionDTO dto = new AIQuestionDTO();
        dto.setQuestion(question);
        AIResponseDTO response = processQuestion(dto);
        
        if (response.isSuccess()) {
            return response.getAnswer();
        } else {
            return "Erro: " + response.getErrorMessage();
        }
    }
}

