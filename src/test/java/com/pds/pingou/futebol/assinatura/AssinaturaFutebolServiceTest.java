package com.pds.pingou.futebol.assinatura;

import com.pds.pingou.enums.StatusAssinatura;
import com.pds.pingou.futebol.assinatura.dto.AssinaturaFutebolRequestDTO;
import com.pds.pingou.futebol.assinatura.dto.AssinaturaFutebolResponseDTO;
import com.pds.pingou.futebol.assinatura.dto.MembroAssinaturaRequestDTO;
import com.pds.pingou.futebol.enums.TamanhoCamisa;
import com.pds.pingou.futebol.enums.TipoPlanoFutebol;
import com.pds.pingou.futebol.plano.PlanoFutebol;
import com.pds.pingou.futebol.plano.PlanoFutebolRepository;
import com.pds.pingou.security.user.User;
import com.pds.pingou.security.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Testes unitários para AssinaturaFutebolService.
 * 
 * Testa a lógica central do sistema de assinaturas familiares:
 * - Criação de assinaturas
 * - Gestão de membros com tamanhos diferentes
 * - Validações de regras de negócio
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("AssinaturaFutebolService")
class AssinaturaFutebolServiceTest {

    @Mock
    private AssinaturaFutebolRepository assinaturaRepository;

    @Mock
    private MembroAssinaturaRepository membroRepository;

    @Mock
    private PlanoFutebolRepository planoRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AssinaturaFutebolService assinaturaService;

    private User user;
    private PlanoFutebol planoIndividual;
    private PlanoFutebol planoFamilia;
    private AssinaturaFutebol assinaturaAtiva;

    @BeforeEach
    void setUp() {
        // Usuário
        user = new User();
        user.setId(1L);
        user.setEmail("torcedor@email.com");
        user.setNome("João Torcedor");

        // Plano Individual
        planoIndividual = new PlanoFutebol();
        planoIndividual.setId(1L);
        planoIndividual.setNome("Torcedor");
        planoIndividual.setDescricao("Plano individual");
        planoIndividual.setPreco(new BigDecimal("89.90"));
        planoIndividual.setAtivo(true);
        planoIndividual.setTipoPlano(TipoPlanoFutebol.INDIVIDUAL);
        planoIndividual.setCamisasPorMembro(1);

        // Plano Família
        planoFamilia = new PlanoFutebol();
        planoFamilia.setId(2L);
        planoFamilia.setNome("Família Unida");
        planoFamilia.setDescricao("Plano para até 5 pessoas");
        planoFamilia.setPreco(new BigDecimal("289.90"));
        planoFamilia.setAtivo(true);
        planoFamilia.setTipoPlano(TipoPlanoFutebol.FAMILIA);
        planoFamilia.setCamisasPorMembro(1);

        // Assinatura Ativa
        assinaturaAtiva = new AssinaturaFutebol();
        assinaturaAtiva.setId(1L);
        assinaturaAtiva.setUser(user);
        assinaturaAtiva.setPlano(planoIndividual);
        assinaturaAtiva.setStatus(StatusAssinatura.ATIVA);
        assinaturaAtiva.setDataInicio(LocalDate.now());
        assinaturaAtiva.setMembros(new ArrayList<>());
        
        MembroAssinatura titular = new MembroAssinatura();
        titular.setId(1L);
        titular.setNome("João Torcedor");
        titular.setTamanho(TamanhoCamisa.M);
        titular.setTitular(true);
        titular.setAssinatura(assinaturaAtiva);
        assinaturaAtiva.getMembros().add(titular);
    }

    @Nested
    @DisplayName("Busca de assinaturas")
    class BuscaAssinaturasTest {

        @Test
        @DisplayName("Deve encontrar assinatura por ID")
        void deveEncontrarAssinaturaPorId() {
            when(assinaturaRepository.findById(1L)).thenReturn(Optional.of(assinaturaAtiva));

            AssinaturaFutebolResponseDTO resultado = assinaturaService.buscarPorId(1L);

            assertThat(resultado).isNotNull();
            assertThat(resultado.getId()).isEqualTo(1L);
        }

        @Test
        @DisplayName("Deve lançar exceção para assinatura inexistente")
        void deveLancarExcecaoParaAssinaturaInexistente() {
            when(assinaturaRepository.findById(999L)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> assinaturaService.buscarPorId(999L))
                    .isInstanceOf(RuntimeException.class)
                    .hasMessageContaining("não encontrada");
        }

        @Test
        @DisplayName("Deve encontrar assinatura por email")
        void deveEncontrarAssinaturaPorEmail() {
            when(assinaturaRepository.findByUserEmail("torcedor@email.com"))
                    .thenReturn(Optional.of(assinaturaAtiva));

            AssinaturaFutebolResponseDTO resultado = assinaturaService.buscarPorEmail("torcedor@email.com");

            assertThat(resultado).isNotNull();
        }

        @Test
        @DisplayName("Deve listar assinaturas ativas")
        void deveListarAssinaturasAtivas() {
            when(assinaturaRepository.findByStatus(StatusAssinatura.ATIVA))
                    .thenReturn(Arrays.asList(assinaturaAtiva));

            List<AssinaturaFutebolResponseDTO> resultado = assinaturaService.listarAtivas();

            assertThat(resultado).hasSize(1);
        }
    }

    @Nested
    @DisplayName("Criação de assinaturas")
    class CriacaoAssinaturasTest {

        @Test
        @DisplayName("Deve criar assinatura individual com sucesso")
        void deveCriarAssinaturaIndividualComSucesso() {
            AssinaturaFutebolRequestDTO requestDTO = new AssinaturaFutebolRequestDTO();
            requestDTO.setPlanoId(1L);
            requestDTO.setTimeFavoritoPrincipal("Flamengo");
            
            MembroAssinaturaRequestDTO membroDTO = new MembroAssinaturaRequestDTO();
            membroDTO.setNome("João");
            membroDTO.setTamanho(TamanhoCamisa.M);
            membroDTO.setTitular(true);
            requestDTO.setMembros(Arrays.asList(membroDTO));

            when(userRepository.findByEmail("torcedor@email.com"))
                    .thenReturn(Optional.of(user));
            when(assinaturaRepository.existsByUser(user)).thenReturn(false);
            when(planoRepository.findById(1L)).thenReturn(Optional.of(planoIndividual));
            when(assinaturaRepository.save(any(AssinaturaFutebol.class))).thenAnswer(invocation -> {
                AssinaturaFutebol assinatura = invocation.getArgument(0);
                assinatura.setId(2L);
                return assinatura;
            });

            AssinaturaFutebolResponseDTO resultado = assinaturaService.criar("torcedor@email.com", requestDTO);

            assertThat(resultado).isNotNull();
            verify(assinaturaRepository).save(any(AssinaturaFutebol.class));
        }

        @Test
        @DisplayName("Deve rejeitar assinatura duplicada")
        void deveRejeitarAssinaturaDuplicada() {
            AssinaturaFutebolRequestDTO requestDTO = new AssinaturaFutebolRequestDTO();
            requestDTO.setPlanoId(1L);

            when(userRepository.findByEmail("torcedor@email.com"))
                    .thenReturn(Optional.of(user));
            when(assinaturaRepository.existsByUser(user)).thenReturn(true);

            assertThatThrownBy(() -> assinaturaService.criar("torcedor@email.com", requestDTO))
                    .isInstanceOf(RuntimeException.class)
                    .hasMessageContaining("já possui");
        }

        @Test
        @DisplayName("Deve rejeitar assinatura sem membros")
        void deveRejeitarAssinaturaSemMembros() {
            AssinaturaFutebolRequestDTO requestDTO = new AssinaturaFutebolRequestDTO();
            requestDTO.setPlanoId(1L);
            requestDTO.setMembros(Arrays.asList()); // vazio

            when(userRepository.findByEmail("torcedor@email.com"))
                    .thenReturn(Optional.of(user));
            when(assinaturaRepository.existsByUser(user)).thenReturn(false);
            when(planoRepository.findById(1L)).thenReturn(Optional.of(planoIndividual));

            assertThatThrownBy(() -> assinaturaService.criar("torcedor@email.com", requestDTO))
                    .isInstanceOf(RuntimeException.class)
                    .hasMessageContaining("pelo menos um membro");
        }

        @Test
        @DisplayName("Deve rejeitar assinatura família com membros excedendo limite")
        void deveRejeitarAssinaturaFamiliaComMembrosExcedentes() {
            AssinaturaFutebolRequestDTO requestDTO = new AssinaturaFutebolRequestDTO();
            requestDTO.setPlanoId(1L); // Plano Individual (1 membro máximo)
            
            List<MembroAssinaturaRequestDTO> membros = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                MembroAssinaturaRequestDTO membro = new MembroAssinaturaRequestDTO();
                membro.setNome("Membro " + i);
                membro.setTamanho(TamanhoCamisa.M);
                membro.setTitular(i == 0);
                membros.add(membro);
            }
            requestDTO.setMembros(membros);

            when(userRepository.findByEmail("torcedor@email.com"))
                    .thenReturn(Optional.of(user));
            when(assinaturaRepository.existsByUser(user)).thenReturn(false);
            when(planoRepository.findById(1L)).thenReturn(Optional.of(planoIndividual));

            assertThatThrownBy(() -> assinaturaService.criar("torcedor@email.com", requestDTO))
                    .isInstanceOf(RuntimeException.class)
                    .hasMessageContaining("no máximo");
        }
    }

    @Nested
    @DisplayName("Gestão de membros família")
    class GestaoMembrosFamiliaTest {

        @Test
        @DisplayName("Deve criar assinatura família com tamanhos diferentes")
        void deveCriarAssinaturaFamiliaComTamanhosDiferentes() {
            AssinaturaFutebolRequestDTO requestDTO = new AssinaturaFutebolRequestDTO();
            requestDTO.setPlanoId(2L); // Plano Família
            requestDTO.setTimeFavoritoPrincipal("Corinthians");
            
            List<MembroAssinaturaRequestDTO> membros = new ArrayList<>();
            
            // Pai - G
            MembroAssinaturaRequestDTO pai = new MembroAssinaturaRequestDTO();
            pai.setNome("João Pai");
            pai.setTamanho(TamanhoCamisa.G);
            pai.setTitular(true);
            membros.add(pai);
            
            // Mãe - M
            MembroAssinaturaRequestDTO mae = new MembroAssinaturaRequestDTO();
            mae.setNome("Maria Mãe");
            mae.setTamanho(TamanhoCamisa.M);
            mae.setTitular(false);
            membros.add(mae);
            
            // Filho - Infantil 10
            MembroAssinaturaRequestDTO filho = new MembroAssinaturaRequestDTO();
            filho.setNome("Joãozinho");
            filho.setTamanho(TamanhoCamisa.INF_10);
            filho.setTitular(false);
            membros.add(filho);
            
            requestDTO.setMembros(membros);

            when(userRepository.findByEmail("torcedor@email.com"))
                    .thenReturn(Optional.of(user));
            when(assinaturaRepository.existsByUser(user)).thenReturn(false);
            when(planoRepository.findById(2L)).thenReturn(Optional.of(planoFamilia));
            when(assinaturaRepository.save(any(AssinaturaFutebol.class))).thenAnswer(invocation -> {
                AssinaturaFutebol assinatura = invocation.getArgument(0);
                assinatura.setId(3L);
                return assinatura;
            });

            AssinaturaFutebolResponseDTO resultado = assinaturaService.criar("torcedor@email.com", requestDTO);

            assertThat(resultado).isNotNull();
            verify(assinaturaRepository).save(any(AssinaturaFutebol.class));
        }
    }
}
