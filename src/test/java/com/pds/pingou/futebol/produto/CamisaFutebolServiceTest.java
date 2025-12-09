package com.pds.pingou.futebol.produto;

import com.pds.pingou.futebol.enums.TipoCamisa;
import com.pds.pingou.futebol.produto.dto.CamisaFutebolRequestDTO;
import com.pds.pingou.futebol.produto.dto.CamisaFutebolResponseDTO;
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
 * Testes unitários para CamisaFutebolService.
 * 
 * Cobre cenários de:
 * - Listagem de camisas
 * - Busca por time, temporada, tipo
 * - Criação e atualização
 * - Ativação/desativação
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("CamisaFutebolService")
class CamisaFutebolServiceTest {

    @Mock
    private CamisaFutebolRepository camisaRepository;

    @InjectMocks
    private CamisaFutebolService camisaService;

    private CamisaFutebol camisaPrincipal;
    private CamisaFutebol camisaReserva;

    @BeforeEach
    void setUp() {
        // Camisa Principal Flamengo
        camisaPrincipal = new CamisaFutebol();
        camisaPrincipal.setId(1L);
        camisaPrincipal.setNome("Camisa Flamengo 2024/2025 Principal");
        camisaPrincipal.setDescricao("Camisa oficial do Flamengo temporada 2024/2025");
        camisaPrincipal.setPreco(new BigDecimal("299.90"));
        camisaPrincipal.setTime("Flamengo");
        camisaPrincipal.setPais("Brasil");
        camisaPrincipal.setTemporada("2024/2025");
        camisaPrincipal.setTipoCamisa(TipoCamisa.PRINCIPAL);
        camisaPrincipal.setMarca("Adidas");
        camisaPrincipal.setOficial(true);
        camisaPrincipal.setAtivo(true);

        // Camisa Reserva Palmeiras
        camisaReserva = new CamisaFutebol();
        camisaReserva.setId(2L);
        camisaReserva.setNome("Camisa Palmeiras 2024/2025 Reserva");
        camisaReserva.setDescricao("Camisa reserva do Palmeiras");
        camisaReserva.setPreco(new BigDecimal("279.90"));
        camisaReserva.setTime("Palmeiras");
        camisaReserva.setPais("Brasil");
        camisaReserva.setTemporada("2024/2025");
        camisaReserva.setTipoCamisa(TipoCamisa.RESERVA);
        camisaReserva.setMarca("Puma");
        camisaReserva.setOficial(true);
        camisaReserva.setAtivo(true);
    }

    @Nested
    @DisplayName("Listagem de camisas")
    class ListagemCamisasTest {

        @Test
        @DisplayName("Deve listar todas as camisas ativas")
        void deveListarTodasAsCamisasAtivas() {
            when(camisaRepository.findByAtivoTrue())
                    .thenReturn(Arrays.asList(camisaPrincipal, camisaReserva));

            List<CamisaFutebolResponseDTO> camisas = camisaService.listarTodas();

            assertThat(camisas).hasSize(2);
            verify(camisaRepository).findByAtivoTrue();
        }

        @Test
        @DisplayName("Deve retornar lista vazia quando não há camisas")
        void deveRetornarListaVaziaQuandoNaoHaCamisas() {
            when(camisaRepository.findByAtivoTrue()).thenReturn(Arrays.asList());

            List<CamisaFutebolResponseDTO> camisas = camisaService.listarTodas();

            assertThat(camisas).isEmpty();
        }
    }

    @Nested
    @DisplayName("Busca por ID")
    class BuscaPorIdTest {

        @Test
        @DisplayName("Deve encontrar camisa por ID")
        void deveEncontrarCamisaPorId() {
            when(camisaRepository.findById(1L)).thenReturn(Optional.of(camisaPrincipal));

            CamisaFutebolResponseDTO resultado = camisaService.buscarPorId(1L);

            assertThat(resultado).isNotNull();
            assertThat(resultado.getId()).isEqualTo(1L);
            assertThat(resultado.getTime()).isEqualTo("Flamengo");
        }

        @Test
        @DisplayName("Deve lançar exceção para camisa inexistente")
        void deveLancarExcecaoParaCamisaInexistente() {
            when(camisaRepository.findById(999L)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> camisaService.buscarPorId(999L))
                    .isInstanceOf(RuntimeException.class)
                    .hasMessageContaining("não encontrada");
        }
    }

    @Nested
    @DisplayName("Busca por time")
    class BuscaPorTimeTest {

        @Test
        @DisplayName("Deve buscar camisas por time")
        void deveBuscarCamisasPorTime() {
            when(camisaRepository.findByTimeContainingIgnoreCase("Flamengo"))
                    .thenReturn(Arrays.asList(camisaPrincipal));

            List<CamisaFutebolResponseDTO> camisas = camisaService.buscarPorTime("Flamengo");

            assertThat(camisas).hasSize(1);
            assertThat(camisas.get(0).getTime()).isEqualTo("Flamengo");
        }

        @Test
        @DisplayName("Deve retornar lista vazia para time sem camisas")
        void deveRetornarListaVaziaParaTimeSemCamisas() {
            when(camisaRepository.findByTimeContainingIgnoreCase("Santos"))
                    .thenReturn(Arrays.asList());

            List<CamisaFutebolResponseDTO> camisas = camisaService.buscarPorTime("Santos");

            assertThat(camisas).isEmpty();
        }
    }

    @Nested
    @DisplayName("Busca por tipo")
    class BuscaPorTipoTest {

        @Test
        @DisplayName("Deve buscar camisas por tipo")
        void deveBuscarCamisasPorTipo() {
            when(camisaRepository.findByTipoCamisa(TipoCamisa.PRINCIPAL))
                    .thenReturn(Arrays.asList(camisaPrincipal));

            List<CamisaFutebolResponseDTO> camisas = camisaService.buscarPorTipo(TipoCamisa.PRINCIPAL);

            assertThat(camisas).hasSize(1);
            assertThat(camisas.get(0).getTipoCamisa()).isEqualTo(TipoCamisa.PRINCIPAL);
        }
    }

    @Nested
    @DisplayName("Busca por temporada")
    class BuscaPorTemporadaTest {

        @Test
        @DisplayName("Deve buscar camisas por temporada")
        void deveBuscarCamisasPorTemporada() {
            when(camisaRepository.findByTemporada("2024/2025"))
                    .thenReturn(Arrays.asList(camisaPrincipal, camisaReserva));

            List<CamisaFutebolResponseDTO> camisas = camisaService.buscarPorTemporada("2024/2025");

            assertThat(camisas).hasSize(2);
        }
    }

    @Nested
    @DisplayName("Criação de camisas")
    class CriacaoCamisasTest {

        @Test
        @DisplayName("Deve criar camisa com sucesso")
        void deveCriarCamisaComSucesso() {
            CamisaFutebolRequestDTO requestDTO = new CamisaFutebolRequestDTO();
            requestDTO.setNome("Camisa Corinthians 2024/2025");
            requestDTO.setDescricao("Camisa oficial do Corinthians");
            requestDTO.setPreco(new BigDecimal("289.90"));
            requestDTO.setTime("Corinthians");
            requestDTO.setPais("Brasil");
            requestDTO.setTemporada("2024/2025");
            requestDTO.setTipoCamisa(TipoCamisa.PRINCIPAL);
            requestDTO.setMarca("Nike");

            when(camisaRepository.existsByTimeAndTemporadaAndTipoCamisa(
                    "Corinthians", "2024/2025", TipoCamisa.PRINCIPAL))
                    .thenReturn(false);
            when(camisaRepository.save(any(CamisaFutebol.class))).thenAnswer(invocation -> {
                CamisaFutebol camisa = invocation.getArgument(0);
                camisa.setId(3L);
                return camisa;
            });

            CamisaFutebolResponseDTO resultado = camisaService.criar(requestDTO);

            assertThat(resultado).isNotNull();
            assertThat(resultado.getTime()).isEqualTo("Corinthians");
            verify(camisaRepository).save(any(CamisaFutebol.class));
        }

        @Test
        @DisplayName("Deve rejeitar camisa duplicada")
        void deveRejeitarCamisaDuplicada() {
            CamisaFutebolRequestDTO requestDTO = new CamisaFutebolRequestDTO();
            requestDTO.setTime("Flamengo");
            requestDTO.setTemporada("2024/2025");
            requestDTO.setTipoCamisa(TipoCamisa.PRINCIPAL);

            when(camisaRepository.existsByTimeAndTemporadaAndTipoCamisa(
                    "Flamengo", "2024/2025", TipoCamisa.PRINCIPAL))
                    .thenReturn(true);

            assertThatThrownBy(() -> camisaService.criar(requestDTO))
                    .isInstanceOf(RuntimeException.class)
                    .hasMessageContaining("Já existe");
        }
    }

    @Nested
    @DisplayName("Atualização de camisas")
    class AtualizacaoCamisasTest {

        @Test
        @DisplayName("Deve atualizar camisa existente")
        void deveAtualizarCamisaExistente() {
            CamisaFutebolRequestDTO requestDTO = new CamisaFutebolRequestDTO();
            requestDTO.setNome("Camisa Flamengo 2024/2025 - Atualizada");
            requestDTO.setPreco(new BigDecimal("319.90"));
            requestDTO.setTime("Flamengo");
            requestDTO.setTemporada("2024/2025");
            requestDTO.setTipoCamisa(TipoCamisa.PRINCIPAL);

            when(camisaRepository.findById(1L)).thenReturn(Optional.of(camisaPrincipal));
            when(camisaRepository.save(any(CamisaFutebol.class))).thenReturn(camisaPrincipal);

            CamisaFutebolResponseDTO resultado = camisaService.atualizar(1L, requestDTO);

            assertThat(resultado).isNotNull();
            verify(camisaRepository).save(any(CamisaFutebol.class));
        }

        @Test
        @DisplayName("Deve lançar exceção ao atualizar camisa inexistente")
        void deveLancarExcecaoAoAtualizarCamisaInexistente() {
            CamisaFutebolRequestDTO requestDTO = new CamisaFutebolRequestDTO();
            
            when(camisaRepository.findById(999L)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> camisaService.atualizar(999L, requestDTO))
                    .isInstanceOf(RuntimeException.class);
        }
    }
}
