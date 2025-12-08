package com.pds.pingou.AI;

import com.pds.pingou.AI.dto.AIQuestionDTO;
import com.pds.pingou.AI.dto.AIResponseDTO;
import com.pds.pingou.AI.prompt.AIPromptProvider;
import com.pds.pingou.AI.prompt.AIPromptProviderFactory;
import com.pds.pingou.AI.prompt.FutebolPromptProvider;
import com.pds.pingou.AI.prompt.PingouPromptProvider;
import com.pds.pingou.futebol.plano.PlanoFutebol;
import com.pds.pingou.futebol.plano.PlanoFutebolRepository;
import com.pds.pingou.futebol.produto.CamisaFutebol;
import com.pds.pingou.futebol.produto.CamisaFutebolRepository;
import com.pds.pingou.futebol.enums.TipoPlanoFutebol;
import com.pds.pingou.futebol.enums.TipoCamisa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

/**
 * Testes unitários para o módulo de IA.
 * 
 * Testa:
 * - FutebolPromptProvider
 * - AIPromptProviderFactory
 * - AIQuestionDTO
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Módulo de IA")
class AIModuleTest {

    @Mock
    private PlanoFutebolRepository planoFutebolRepository;

    @Mock
    private CamisaFutebolRepository camisaFutebolRepository;

    @Mock
    private com.pds.pingou.planos.PlanoRepository planoRepository;

    @Nested
    @DisplayName("FutebolPromptProvider")
    class FutebolPromptProviderTest {

        private FutebolPromptProvider futebolPromptProvider;

        @BeforeEach
        void setUp() {
            futebolPromptProvider = new FutebolPromptProvider(
                    planoFutebolRepository, 
                    camisaFutebolRepository
            );
        }

        @Test
        @DisplayName("Deve gerar prompt do sistema com informações do Camisa10")
        void deveGerarPromptDoSistema() {
            when(planoFutebolRepository.findByAtivoTrue()).thenReturn(List.of());
            when(camisaFutebolRepository.findByAtivoTrue()).thenReturn(List.of());

            String prompt = futebolPromptProvider.buildSystemPrompt();

            assertThat(prompt).isNotBlank();
            assertThat(prompt).contains("Camisa10");
            assertThat(prompt).contains("camisas de futebol");
        }

        @Test
        @DisplayName("Deve incluir planos ativos no prompt")
        void deveIncluirPlanosAtivos() {
            PlanoFutebol plano = new PlanoFutebol();
            plano.setId(1L);
            plano.setNome("Torcedor");
            plano.setDescricao("Plano individual");
            plano.setPreco(new BigDecimal("89.90"));
            plano.setTipoPlano(TipoPlanoFutebol.INDIVIDUAL);
            plano.setCamisasPorMembro(1);
            plano.setFrequenciaEntrega("MENSAL");
            plano.setPersonalizacaoInclusa(false);
            plano.setPrioridadeEdicaoLimitada(false);
            plano.setIncluiSelecoes(false);
            plano.setFreteGratis(true);

            when(planoFutebolRepository.findByAtivoTrue()).thenReturn(List.of(plano));
            when(camisaFutebolRepository.findByAtivoTrue()).thenReturn(List.of());

            String prompt = futebolPromptProvider.buildSystemPrompt();

            assertThat(prompt).contains("Torcedor");
            assertThat(prompt).contains("89.90");
            assertThat(prompt).contains("Individual");
        }

        @Test
        @DisplayName("Deve incluir camisas disponíveis no prompt")
        void deveIncluirCamisasDisponiveis() {
            CamisaFutebol camisa = new CamisaFutebol();
            camisa.setId(1L);
            camisa.setNome("Camisa Flamengo");
            camisa.setTime("Flamengo");
            camisa.setTemporada("2024/2025");
            camisa.setTipoCamisa(TipoCamisa.PRINCIPAL);
            camisa.setMarca("Adidas");
            camisa.setPreco(new BigDecimal("299.90"));

            when(planoFutebolRepository.findByAtivoTrue()).thenReturn(List.of());
            when(camisaFutebolRepository.findByAtivoTrue()).thenReturn(List.of(camisa));

            String prompt = futebolPromptProvider.buildSystemPrompt();

            assertThat(prompt).contains("Flamengo");
        }

        @Test
        @DisplayName("Deve incluir tipos de planos família no prompt")
        void deveIncluirTiposPlanosFamilia() {
            when(planoFutebolRepository.findByAtivoTrue()).thenReturn(List.of());
            when(camisaFutebolRepository.findByAtivoTrue()).thenReturn(List.of());

            String prompt = futebolPromptProvider.buildSystemPrompt();

            assertThat(prompt).contains("Individual");
            assertThat(prompt).contains("Casal");
            assertThat(prompt).contains("Família");
            assertThat(prompt).contains("Torcida");
        }

        @Test
        @DisplayName("Deve incluir informações sobre tamanhos")
        void deveIncluirInformacoesSobreTamanhos() {
            when(planoFutebolRepository.findByAtivoTrue()).thenReturn(List.of());
            when(camisaFutebolRepository.findByAtivoTrue()).thenReturn(List.of());

            String prompt = futebolPromptProvider.buildSystemPrompt();

            assertThat(prompt).contains("Tamanhos");
            assertThat(prompt).contains("Infantil");
            assertThat(prompt).contains("Adulto");
        }

        @Test
        @DisplayName("Deve construir prompt do usuário corretamente")
        void deveConstruirPromptDoUsuario() {
            String pergunta = "Quais planos vocês têm?";
            
            String prompt = futebolPromptProvider.buildUserPrompt(pergunta);

            assertThat(prompt).contains(pergunta);
            // O prompt menciona "Pergunta do torcedor"
            assertThat(prompt.toLowerCase()).contains("torcedor");
        }
    }

    @Nested
    @DisplayName("AIPromptProviderFactory")
    class AIPromptProviderFactoryTest {

        private AIPromptProviderFactory factory;
        private FutebolPromptProvider futebolProvider;
        private PingouPromptProvider pingouProvider;

        @BeforeEach
        void setUp() {
            futebolProvider = new FutebolPromptProvider(planoFutebolRepository, camisaFutebolRepository);
            pingouProvider = new PingouPromptProvider(planoRepository);
            factory = new AIPromptProviderFactory(futebolProvider, pingouProvider);
        }

        @Test
        @DisplayName("Deve retornar FutebolPromptProvider para tipo 'futebol'")
        void deveRetornarFutebolProviderParaTipoFutebol() {
            AIPromptProvider provider = factory.createProvider("futebol");
            
            assertThat(provider).isInstanceOf(FutebolPromptProvider.class);
        }

        @Test
        @DisplayName("Deve retornar FutebolPromptProvider para tipo 'camisa10'")
        void deveRetornarFutebolProviderParaTipoCamisa10() {
            AIPromptProvider provider = factory.createProvider("camisa10");
            
            assertThat(provider).isInstanceOf(FutebolPromptProvider.class);
        }

        @Test
        @DisplayName("Deve retornar PingouPromptProvider para tipo 'pingou'")
        void deveRetornarPingouProviderParaTipoPingou() {
            AIPromptProvider provider = factory.createProvider("pingou");
            
            assertThat(provider).isInstanceOf(PingouPromptProvider.class);
        }

        @Test
        @DisplayName("Deve retornar PingouPromptProvider para tipo 'cachaca'")
        void deveRetornarPingouProviderParaTipoCachaca() {
            AIPromptProvider provider = factory.createProvider("cachaca");
            
            assertThat(provider).isInstanceOf(PingouPromptProvider.class);
        }

        @Test
        @DisplayName("Deve lançar exceção para tipo desconhecido")
        void deveLancarExcecaoParaTipoDesconhecido() {
            assertThatThrownBy(() -> factory.createProvider("desconhecido"))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("não reconhecido");
        }

        @Test
        @DisplayName("Default provider deve ser FutebolPromptProvider")
        void defaultProviderDeveSerFutebol() {
            AIPromptProvider provider = factory.createDefaultProvider();
            
            assertThat(provider).isInstanceOf(FutebolPromptProvider.class);
        }

        @Test
        @DisplayName("Deve retornar default quando tipo é null")
        void deveRetornarDefaultQuandoTipoNull() {
            AIPromptProvider provider = factory.createProvider(null);
            
            assertThat(provider).isInstanceOf(FutebolPromptProvider.class);
        }

        @Test
        @DisplayName("Deve ser case-insensitive")
        void deveSerCaseInsensitive() {
            assertThat(factory.createProvider("FUTEBOL")).isInstanceOf(FutebolPromptProvider.class);
            assertThat(factory.createProvider("Futebol")).isInstanceOf(FutebolPromptProvider.class);
            assertThat(factory.createProvider("PINGOU")).isInstanceOf(PingouPromptProvider.class);
        }
    }

    @Nested
    @DisplayName("AIQuestionDTO")
    class AIQuestionDTOTest {

        @Test
        @DisplayName("Default contextType deve ser 'futebol'")
        void defaultContextTypeDeveSerFutebol() {
            AIQuestionDTO dto = new AIQuestionDTO();
            
            assertThat(dto.getContextType()).isEqualTo("futebol");
        }

        @Test
        @DisplayName("Deve permitir alterar contextType")
        void devePermitirAlterarContextType() {
            AIQuestionDTO dto = new AIQuestionDTO();
            dto.setContextType("pingou");
            
            assertThat(dto.getContextType()).isEqualTo("pingou");
        }

        @Test
        @DisplayName("Deve armazenar pergunta corretamente")
        void deveArmazenarPerguntaCorretamente() {
            AIQuestionDTO dto = new AIQuestionDTO();
            dto.setQuestion("Quais camisas do Flamengo vocês têm?");
            
            assertThat(dto.getQuestion()).isEqualTo("Quais camisas do Flamengo vocês têm?");
        }
    }

    @Nested
    @DisplayName("AIResponseDTO")
    class AIResponseDTOTest {

        @Test
        @DisplayName("Deve criar resposta de sucesso")
        void deveCriarRespostaDeSucesso() {
            AIResponseDTO response = new AIResponseDTO("Resposta da IA");
            
            assertThat(response.isSuccess()).isTrue();
            assertThat(response.getAnswer()).isEqualTo("Resposta da IA");
            assertThat(response.getErrorMessage()).isNull();
        }

        @Test
        @DisplayName("Deve criar resposta de erro")
        void deveCriarRespostaDeErro() {
            AIResponseDTO response = AIResponseDTO.error("Erro ao processar");
            
            assertThat(response.isSuccess()).isFalse();
            assertThat(response.getErrorMessage()).isEqualTo("Erro ao processar");
        }
    }
}
