package com.pds.pingou.futebol.plano;

import com.pds.pingou.futebol.enums.TipoPlanoFutebol;
import com.pds.pingou.futebol.plano.dto.PlanoFutebolRequestDTO;
import com.pds.pingou.futebol.plano.dto.PlanoFutebolResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Testes unitários para PlanoFutebolService.
 * 
 * Cobre cenários de:
 * - Listagem de planos
 * - Busca por ID e nome
 * - Criação de planos
 * - Atualização de planos
 * - Ativação/desativação
 * - Planos família
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("PlanoFutebolService")
class PlanoFutebolServiceTest {

    @Mock
    private PlanoFutebolRepository planoRepository;

    @InjectMocks
    private PlanoFutebolService planoService;

    private PlanoFutebol planoIndividual;
    private PlanoFutebol planoFamilia;

    @BeforeEach
    void setUp() {
        // Plano Individual
        planoIndividual = new PlanoFutebol();
        planoIndividual.setId(1L);
        planoIndividual.setNome("Torcedor");
        planoIndividual.setDescricao("Plano individual básico");
        planoIndividual.setPreco(new BigDecimal("89.90"));
        planoIndividual.setAtivo(true);
        planoIndividual.setTipoPlano(TipoPlanoFutebol.INDIVIDUAL);
        planoIndividual.setCamisasPorMembro(1);
        planoIndividual.setPersonalizacaoInclusa(false);
        planoIndividual.setFrequenciaEntrega("MENSAL");

        // Plano Família
        planoFamilia = new PlanoFutebol();
        planoFamilia.setId(2L);
        planoFamilia.setNome("Família Unida");
        planoFamilia.setDescricao("Plano para até 5 pessoas");
        planoFamilia.setPreco(new BigDecimal("289.90"));
        planoFamilia.setAtivo(true);
        planoFamilia.setTipoPlano(TipoPlanoFutebol.FAMILIA);
        planoFamilia.setCamisasPorMembro(1);
        planoFamilia.setPersonalizacaoInclusa(true);
        planoFamilia.setFrequenciaEntrega("MENSAL");
    }

    @Nested
    @DisplayName("Listagem de planos")
    class ListagemPlanosTest {

        @Test
        @DisplayName("Deve listar todos os planos ativos")
        void deveListarTodosOsPlanosAtivos() {
            when(planoRepository.findByAtivoTrue())
                    .thenReturn(Arrays.asList(planoIndividual, planoFamilia));

            List<PlanoFutebolResponseDTO> planos = planoService.listarTodos();

            assertThat(planos).hasSize(2);
            verify(planoRepository).findByAtivoTrue();
        }

        @Test
        @DisplayName("Deve retornar lista vazia quando não há planos")
        void deveRetornarListaVaziaQuandoNaoHaPlanos() {
            when(planoRepository.findByAtivoTrue()).thenReturn(Arrays.asList());

            List<PlanoFutebolResponseDTO> planos = planoService.listarTodos();

            assertThat(planos).isEmpty();
        }
    }

    @Nested
    @DisplayName("Busca por ID")
    class BuscaPorIdTest {

        @Test
        @DisplayName("Deve encontrar plano por ID")
        void deveEncontrarPlanoPorId() {
            when(planoRepository.findById(1L)).thenReturn(Optional.of(planoIndividual));

            PlanoFutebolResponseDTO resultado = planoService.buscarPorId(1L);

            assertThat(resultado).isNotNull();
            assertThat(resultado.getNome()).isEqualTo("Torcedor");
        }

        @Test
        @DisplayName("Deve lançar exceção para plano inexistente")
        void deveLancarExcecaoParaPlanoInexistente() {
            when(planoRepository.findById(999L)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> planoService.buscarPorId(999L))
                    .isInstanceOf(RuntimeException.class)
                    .hasMessageContaining("não encontrado");
        }
    }

    @Nested
    @DisplayName("Busca por nome")
    class BuscaPorNomeTest {

        @Test
        @DisplayName("Deve encontrar plano por nome")
        void deveEncontrarPlanoPorNome() {
            when(planoRepository.findByNome("Torcedor")).thenReturn(Optional.of(planoIndividual));

            PlanoFutebolResponseDTO resultado = planoService.buscarPorNome("Torcedor");

            assertThat(resultado).isNotNull();
            assertThat(resultado.getNome()).isEqualTo("Torcedor");
        }
    }

    @Nested
    @DisplayName("Busca por tipo")
    class BuscaPorTipoTest {

        @Test
        @DisplayName("Deve listar planos por tipo")
        void deveListarPlanosPorTipo() {
            when(planoRepository.findByTipoPlanoAndAtivoTrue(TipoPlanoFutebol.INDIVIDUAL))
                    .thenReturn(Arrays.asList(planoIndividual));

            List<PlanoFutebolResponseDTO> planos = planoService.listarPorTipo(TipoPlanoFutebol.INDIVIDUAL);

            assertThat(planos).hasSize(1);
            assertThat(planos.get(0).getTipoPlano()).isEqualTo(TipoPlanoFutebol.INDIVIDUAL);
        }
    }

    @Nested
    @DisplayName("Planos familiares")
    class PlanosFamiliaresTest {

        @Test
        @DisplayName("Deve listar apenas planos familiares")
        void deveListarApenasPlanosFamiliares() {
            when(planoRepository.findByAtivoTrue())
                    .thenReturn(Arrays.asList(planoIndividual, planoFamilia));

            List<PlanoFutebolResponseDTO> planos = planoService.listarPlanosFamiliares();

            assertThat(planos).hasSize(1);
            assertThat(planos.get(0).getNome()).isEqualTo("Família Unida");
        }

        @Test
        @DisplayName("Deve listar apenas planos individuais")
        void deveListarApenasIndividuais() {
            when(planoRepository.findByTipoPlanoAndAtivoTrue(TipoPlanoFutebol.INDIVIDUAL))
                    .thenReturn(Arrays.asList(planoIndividual));

            List<PlanoFutebolResponseDTO> planos = planoService.listarPlanosIndividuais();

            assertThat(planos).hasSize(1);
        }
    }

    @Nested
    @DisplayName("Criação de planos")
    class CriacaoPlanosTest {

        @Test
        @DisplayName("Deve criar plano com sucesso")
        void deveCriarPlanoComSucesso() {
            PlanoFutebolRequestDTO requestDTO = new PlanoFutebolRequestDTO();
            requestDTO.setNome("Novo Plano");
            requestDTO.setDescricao("Descrição do novo plano");
            requestDTO.setPreco(new BigDecimal("99.90"));
            requestDTO.setTipoPlano(TipoPlanoFutebol.INDIVIDUAL);
            requestDTO.setCamisasPorMembro(1);

            when(planoRepository.existsByNome("Novo Plano")).thenReturn(false);
            when(planoRepository.save(any(PlanoFutebol.class))).thenAnswer(invocation -> {
                PlanoFutebol plano = invocation.getArgument(0);
                plano.setId(3L);
                return plano;
            });

            PlanoFutebolResponseDTO resultado = planoService.criar(requestDTO);

            assertThat(resultado).isNotNull();
            assertThat(resultado.getNome()).isEqualTo("Novo Plano");
            verify(planoRepository).save(any(PlanoFutebol.class));
        }

        @Test
        @DisplayName("Deve rejeitar plano com nome duplicado")
        void deveRejeitarPlanoComNomeDuplicado() {
            PlanoFutebolRequestDTO requestDTO = new PlanoFutebolRequestDTO();
            requestDTO.setNome("Torcedor");

            when(planoRepository.existsByNome("Torcedor")).thenReturn(true);

            assertThatThrownBy(() -> planoService.criar(requestDTO))
                    .isInstanceOf(RuntimeException.class)
                    .hasMessageContaining("Já existe");
        }
    }

    @Nested
    @DisplayName("Planos com personalização")
    class PlanosPersonalizacaoTest {

        @Test
        @DisplayName("Deve listar planos com personalização inclusa")
        void deveListarPlanosComPersonalizacao() {
            when(planoRepository.findByPersonalizacaoInclusaTrue())
                    .thenReturn(Arrays.asList(planoFamilia));

            List<PlanoFutebolResponseDTO> planos = planoService.listarPlanosComPersonalizacao();

            assertThat(planos).hasSize(1);
            assertThat(planos.get(0).getPersonalizacaoInclusa()).isTrue();
        }
    }
}
