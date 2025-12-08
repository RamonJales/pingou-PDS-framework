package com.pds.pingou.assinatura;

import com.pds.pingou.assinatura.exception.AssinaturaDuplicadaException;
import com.pds.pingou.assinatura.exception.AssinaturaNotFoundException;
import com.pds.pingou.enums.StatusAssinatura;
import com.pds.pingou.framework.core.security.exception.UserNotFoundException;
import com.pds.pingou.framework.core.security.user.UserRole;
import com.pds.pingou.planos.Plano;
import com.pds.pingou.planos.PlanoRepository;
import com.pds.pingou.planos.exception.PlanoNotFoundException;
import com.pds.pingou.security.user.User;
import com.pds.pingou.security.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AssinaturaServiceTest {

    @Mock
    private AssinaturaRepository assinaturaRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PlanoRepository planoRepository;

    @InjectMocks
    private AssinaturaService assinaturaService;

    private User user;
    private Plano plano;
    private Assinatura assinatura;
    private AssinaturaRequestDTO assinaturaRequestDTO;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setEmail("user@email.com");
        user.setNome("Usuário");
        user.setSobrenome("Teste");
        user.setRole(UserRole.USER);

        plano = new Plano();
        plano.setId(1L);
        plano.setNome("Plano Básico");
        plano.setPreco(new BigDecimal("29.90"));
        plano.setMaxProdutosPorMes(2);

        assinatura = new Assinatura();
        assinatura.setId(1L);
        assinatura.setUser(user);
        assinatura.setPlano(plano);
        assinatura.setStatus(StatusAssinatura.ATIVA);
        assinatura.setDataInicio(LocalDate.now());

        assinaturaRequestDTO = new AssinaturaRequestDTO();
        assinaturaRequestDTO.setUserId(1L);
        assinaturaRequestDTO.setPlanoId(1L);
    }

    @Test
    @DisplayName("Deve ativar assinatura com sucesso")
    void deveAtivarAssinaturaComSucesso() {
        // Arrange
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(planoRepository.findById(anyLong())).thenReturn(Optional.of(plano));
        when(assinaturaRepository.findByUser(any(User.class))).thenReturn(Optional.empty());
        when(assinaturaRepository.save(any(Assinatura.class))).thenReturn(assinatura);

        // Act
        Assinatura result = assinaturaService.ativarAssinatura(1L, 1L);

        // Assert
        assertNotNull(result);
        assertEquals(StatusAssinatura.ATIVA, result.getStatus());
        assertNotNull(result.getDataInicio());
        verify(userRepository).findById(1L);
        verify(planoRepository).findById(1L);
        verify(assinaturaRepository).findByUser(user);
        verify(assinaturaRepository).save(any(Assinatura.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao ativar assinatura para usuário inexistente")
    void deveLancarExcecaoAoAtivarAssinaturaUsuarioInexistente() {
        // Arrange
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> 
                assinaturaService.ativarAssinatura(999L, 1L));
        
        verify(userRepository).findById(999L);
        verify(planoRepository, never()).findById(anyLong());
    }

    @Test
    @DisplayName("Deve lançar exceção ao ativar assinatura para plano inexistente")
    void deveLancarExcecaoAoAtivarAssinaturaPlanoInexistente() {
        // Arrange
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(planoRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(PlanoNotFoundException.class, () -> 
                assinaturaService.ativarAssinatura(1L, 999L));
        
        verify(userRepository).findById(1L);
        verify(planoRepository).findById(999L);
    }

    @Test
    @DisplayName("Deve lançar exceção ao ativar assinatura para usuário que já possui assinatura")
    void deveLancarExcecaoAoAtivarAssinaturaUsuarioJaPossuiAssinatura() {
        // Arrange
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(planoRepository.findById(anyLong())).thenReturn(Optional.of(plano));
        when(assinaturaRepository.findByUser(any(User.class))).thenReturn(Optional.of(assinatura));

        // Act & Assert
        assertThrows(AssinaturaDuplicadaException.class, () -> 
                assinaturaService.ativarAssinatura(1L, 1L));
        
        verify(assinaturaRepository).findByUser(user);
        verify(assinaturaRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve desativar assinatura com sucesso")
    void deveDesativarAssinaturaComSucesso() {
        // Arrange
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(assinaturaRepository.findByUser(any(User.class))).thenReturn(Optional.of(assinatura));
        when(assinaturaRepository.save(any(Assinatura.class))).thenReturn(assinatura);

        // Act
        Assinatura result = assinaturaService.desativarAssinatura(1L);

        // Assert
        assertNotNull(result);
        assertEquals(StatusAssinatura.INATIVA, result.getStatus());
        assertNotNull(result.getDataExpiracao());
        verify(userRepository).findById(1L);
        verify(assinaturaRepository).findByUser(user);
        verify(assinaturaRepository).save(assinatura);
    }

    @Test
    @DisplayName("Deve lançar exceção ao desativar assinatura inexistente")
    void deveLancarExcecaoAoDesativarAssinaturaInexistente() {
        // Arrange
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(assinaturaRepository.findByUser(any(User.class))).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(AssinaturaNotFoundException.class, () -> 
                assinaturaService.desativarAssinatura(1L));
        
        verify(userRepository).findById(1L);
        verify(assinaturaRepository).findByUser(user);
    }

    @Test
    @DisplayName("Deve listar todas as assinaturas com sucesso")
    void deveListarTodasAssinaturasComSucesso() {
        // Arrange
        List<Assinatura> assinaturas = Arrays.asList(assinatura);
        when(assinaturaRepository.findAll()).thenReturn(assinaturas);

        // Act
        List<Assinatura> result = assinaturaService.listarAssinaturas();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(assinaturaRepository).findAll();
    }

    @Test
    @DisplayName("Deve buscar assinatura por ID com sucesso")
    void deveBuscarAssinaturaPorIdComSucesso() {
        // Arrange
        when(assinaturaRepository.findById(anyLong())).thenReturn(Optional.of(assinatura));

        // Act
        Assinatura result = assinaturaService.buscarPorId(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(assinaturaRepository).findById(1L);
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar assinatura por ID inexistente")
    void deveLancarExcecaoAoBuscarAssinaturaPorIdInexistente() {
        // Arrange
        when(assinaturaRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(AssinaturaNotFoundException.class, () -> 
                assinaturaService.buscarPorId(999L));
        
        verify(assinaturaRepository).findById(999L);
    }

    @Test
    @DisplayName("Deve criar assinatura com sucesso")
    void deveCriarAssinaturaComSucesso() {
        // Arrange
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(planoRepository.findById(anyLong())).thenReturn(Optional.of(plano));
        when(assinaturaRepository.existsByUser(any(User.class))).thenReturn(false);
        when(assinaturaRepository.save(any(Assinatura.class))).thenReturn(assinatura);

        // Act
        Assinatura result = assinaturaService.criarAssinatura(assinaturaRequestDTO);

        // Assert
        assertNotNull(result);
        assertEquals(StatusAssinatura.ATIVA, result.getStatus());
        verify(userRepository).findById(1L);
        verify(planoRepository).findById(1L);
        verify(assinaturaRepository).existsByUser(user);
        verify(assinaturaRepository).save(any(Assinatura.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao criar assinatura para usuário que já possui assinatura")
    void deveLancarExcecaoAoCriarAssinaturaUsuarioJaPossuiAssinatura() {
        // Arrange
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(planoRepository.findById(anyLong())).thenReturn(Optional.of(plano));
        when(assinaturaRepository.existsByUser(any(User.class))).thenReturn(true);

        // Act & Assert
        assertThrows(AssinaturaDuplicadaException.class, () -> 
                assinaturaService.criarAssinatura(assinaturaRequestDTO));
        
        verify(assinaturaRepository).existsByUser(user);
        verify(assinaturaRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve editar assinatura com sucesso")
    void deveEditarAssinaturaComSucesso() {
        // Arrange
        when(assinaturaRepository.findById(anyLong())).thenReturn(Optional.of(assinatura));
        when(planoRepository.findById(anyLong())).thenReturn(Optional.of(plano));
        when(assinaturaRepository.save(any(Assinatura.class))).thenReturn(assinatura);

        // Act
        Assinatura result = assinaturaService.editarAssinatura(1L, assinaturaRequestDTO);

        // Assert
        assertNotNull(result);
        verify(assinaturaRepository).findById(1L);
        verify(planoRepository).findById(1L);
        verify(assinaturaRepository).save(assinatura);
    }

    @Test
    @DisplayName("Deve deletar assinatura com sucesso")
    void deveDeletarAssinaturaComSucesso() {
        // Arrange
        when(assinaturaRepository.findById(anyLong())).thenReturn(Optional.of(assinatura));

        // Act
        assertDoesNotThrow(() -> assinaturaService.deletarAssinatura(1L));

        // Assert
        verify(assinaturaRepository).findById(1L);
        verify(assinaturaRepository).delete(assinatura);
    }

    @Test
    @DisplayName("Deve lançar exceção ao deletar assinatura inexistente")
    void deveLancarExcecaoAoDeletarAssinaturaInexistente() {
        // Arrange
        when(assinaturaRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(AssinaturaNotFoundException.class, () -> 
                assinaturaService.deletarAssinatura(999L));
        
        verify(assinaturaRepository).findById(999L);
        verify(assinaturaRepository, never()).delete(any());
    }
}