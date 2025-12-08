package com.pds.pingou.admin.service;

import com.pds.pingou.admin.dto.UserRequestDTO;
import com.pds.pingou.admin.dto.UserResponseDTO;
import com.pds.pingou.admin.dto.UserUpdateDTO;
import com.pds.pingou.framework.core.security.exception.UserDuplicatedException;
import com.pds.pingou.framework.core.security.exception.UserNotFoundException;
import com.pds.pingou.framework.core.security.user.UserRole;
import com.pds.pingou.security.user.User;
import com.pds.pingou.security.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AdminService adminService;

    private User userAdmin;
    private User userRegular;

    @BeforeEach
    void setUp() {
        userAdmin = new User();
        userAdmin.setId(1L);
        userAdmin.setEmail("admin@email.com");
        userAdmin.setNome("Admin");
        userAdmin.setSobrenome("Sistema");
        userAdmin.setRole(UserRole.ADMIN);

        userRegular = new User();
        userRegular.setId(2L);
        userRegular.setEmail("user@email.com");
        userRegular.setNome("Usuário");
        userRegular.setSobrenome("Regular");
        userRegular.setRole(UserRole.USER);
    }

    @Test
    @DisplayName("Deve encontrar usuário por email com sucesso")
    void deveEncontrarUsuarioPorEmailComSucesso() {
        // Arrange
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(userAdmin));

        // Act
        Optional<UserResponseDTO> result = adminService.findByEmail("admin@email.com");

        // Assert
        assertTrue(result.isPresent());
        assertEquals("admin@email.com", result.get().getEmail());
        assertEquals(UserRole.ADMIN, result.get().getRole());
        verify(userRepository).findByEmail("admin@email.com");
    }

    @Test
    @DisplayName("Deve retornar empty quando usuário não é encontrado por email")
    void deveRetornarEmptyQuandoUsuarioNaoEncontrado() {
        // Arrange
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        // Act
        Optional<UserResponseDTO> result = adminService.findByEmail("inexistente@email.com");

        // Assert
        assertTrue(result.isEmpty());
        verify(userRepository).findByEmail("inexistente@email.com");
    }

    @Test
    @DisplayName("Deve alterar role do usuário com sucesso")
    void deveAlterarRoleDoUsuarioComSucesso() {
        // Arrange
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(userRegular));
        when(userRepository.save(any(User.class))).thenReturn(userRegular);

        // Act
        assertDoesNotThrow(() -> adminService.changeUserRole(2L, UserRole.ADMIN));

        // Assert
        assertEquals(UserRole.ADMIN, userRegular.getRole());
        verify(userRepository).findById(2L);
        verify(userRepository).save(userRegular);
    }

    @Test
    @DisplayName("Deve lançar exceção ao alterar role de usuário inexistente")
    void deveLancarExcecaoAoAlterarRoleUsuarioInexistente() {
        // Arrange
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> 
                adminService.changeUserRole(999L, UserRole.ADMIN));
        
        verify(userRepository).findById(999L);
        verify(userRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve alterar role de ADMIN para USER com sucesso")
    void deveAlterarRoleDeAdminParaUserComSucesso() {
        // Arrange
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(userAdmin));
        when(userRepository.save(any(User.class))).thenReturn(userAdmin);

        // Act
        assertDoesNotThrow(() -> adminService.changeUserRole(1L, UserRole.USER));

        // Assert
        assertEquals(UserRole.USER, userAdmin.getRole());
        verify(userRepository).findById(1L);
        verify(userRepository).save(userAdmin);
    }

    @Test
    @DisplayName("Deve manter role quando já é a mesma")
    void deveManterRoleQuandoJaEAMesma() {
        // Arrange
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(userAdmin));
        when(userRepository.save(any(User.class))).thenReturn(userAdmin);

        // Act
        assertDoesNotThrow(() -> adminService.changeUserRole(1L, UserRole.ADMIN));

        // Assert
        assertEquals(UserRole.ADMIN, userAdmin.getRole());
        verify(userRepository).findById(1L);
        verify(userRepository).save(userAdmin);
    }

    @Test
    @DisplayName("Deve listar todos os usuários com sucesso")
    void deveListarTodosUsuariosComSucesso() {
        // Arrange
        List<User> usuarios = Arrays.asList(userAdmin, userRegular);
        when(userRepository.findAll()).thenReturn(usuarios);

        // Act
        List<UserResponseDTO> result = adminService.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("admin@email.com", result.get(0).getEmail());
        assertEquals("user@email.com", result.get(1).getEmail());
        verify(userRepository).findAll();
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não há usuários")
    void deveRetornarListaVaziaQuandoNaoHaUsuarios() {
        // Arrange
        when(userRepository.findAll()).thenReturn(Arrays.asList());

        // Act
        List<UserResponseDTO> result = adminService.findAll();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(userRepository).findAll();
    }

    @Test
    @DisplayName("Deve distinguir diferentes tipos de usuários na listagem")
    void deveDistinguirDiferentesTiposUsuariosNaListagem() {
        // Arrange
        User outroAdmin = new User();
        outroAdmin.setId(3L);
        outroAdmin.setEmail("admin2@email.com");
        outroAdmin.setRole(UserRole.ADMIN);

        List<User> usuarios = Arrays.asList(userAdmin, userRegular, outroAdmin);
        when(userRepository.findAll()).thenReturn(usuarios);

        // Act
        List<UserResponseDTO> result = adminService.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(3, result.size());
        
        long adminCount = result.stream()
                .filter(user -> user.getRole() == UserRole.ADMIN)
                .count();
        long userCount = result.stream()
                .filter(user -> user.getRole() == UserRole.USER)
                .count();
        
        assertEquals(2, adminCount);
        assertEquals(1, userCount);
        verify(userRepository).findAll();
    }

    @Test
    @DisplayName("Deve verificar se serviço mantém integridade dos dados do usuário")
    void deveVerificarSeServicoMantemIntegridadeDados() {
        // Arrange
        String emailOriginal = userRegular.getEmail();
        String nomeOriginal = userRegular.getNome();
        UserRole roleOriginal = userRegular.getRole();
        
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(userRegular));
        when(userRepository.save(any(User.class))).thenReturn(userRegular);

        // Act
        adminService.changeUserRole(2L, UserRole.ADMIN);

        // Assert
        assertEquals(emailOriginal, userRegular.getEmail()); // Email não deve mudar
        assertEquals(nomeOriginal, userRegular.getNome()); // Nome não deve mudar
        assertNotEquals(roleOriginal, userRegular.getRole()); // Role deve mudar
        assertEquals(UserRole.ADMIN, userRegular.getRole()); // Nova role deve ser ADMIN
        
        verify(userRepository).findById(2L);
        verify(userRepository).save(userRegular);
    }

    // Testes para CREATE
    @Test
    @DisplayName("Deve criar um novo usuário com sucesso")
    void deveCriarNovoUsuarioComSucesso() {
        // Arrange
        UserRequestDTO requestDTO = new UserRequestDTO();
        requestDTO.setEmail("novo@email.com");
        requestDTO.setNome("Novo");
        requestDTO.setSobrenome("Usuario");
        requestDTO.setPassword("senha123");
        requestDTO.setRole(UserRole.USER);

        User newUser = new User();
        newUser.setId(3L);
        newUser.setEmail(requestDTO.getEmail());
        newUser.setNome(requestDTO.getNome());
        newUser.setSobrenome(requestDTO.getSobrenome());
        newUser.setPassword("encoded_password");
        newUser.setRole(requestDTO.getRole());

        when(userRepository.findByEmail(requestDTO.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("encoded_password");
        when(userRepository.save(any(User.class))).thenReturn(newUser);

        // Act
        UserResponseDTO result = adminService.createUser(requestDTO);

        // Assert
        assertNotNull(result);
        assertEquals("novo@email.com", result.getEmail());
        assertEquals("Novo", result.getNome());
        assertEquals("Usuario", result.getSobrenome());
        assertEquals(UserRole.USER, result.getRole());
        verify(userRepository).findByEmail(requestDTO.getEmail());
        verify(passwordEncoder).encode("senha123");
        verify(userRepository).save(any(User.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao criar usuário com email duplicado")
    void deveLancarExcecaoAoCriarUsuarioComEmailDuplicado() {
        // Arrange
        UserRequestDTO requestDTO = new UserRequestDTO();
        requestDTO.setEmail("admin@email.com");
        requestDTO.setNome("Teste");
        requestDTO.setSobrenome("Usuario");
        requestDTO.setPassword("senha123");
        requestDTO.setRole(UserRole.USER);

        when(userRepository.findByEmail(requestDTO.getEmail())).thenReturn(Optional.of(userAdmin));

        // Act & Assert
        assertThrows(UserDuplicatedException.class, () -> adminService.createUser(requestDTO));
        
        verify(userRepository).findByEmail(requestDTO.getEmail());
        verify(userRepository, never()).save(any());
    }

    // Testes para READ by ID
    @Test
    @DisplayName("Deve encontrar usuário por ID com sucesso")
    void deveEncontrarUsuarioPorIdComSucesso() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.of(userAdmin));

        // Act
        Optional<UserResponseDTO> result = adminService.findById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        assertEquals("admin@email.com", result.get().getEmail());
        assertEquals(UserRole.ADMIN, result.get().getRole());
        verify(userRepository).findById(1L);
    }

    @Test
    @DisplayName("Deve retornar empty quando usuário não é encontrado por ID")
    void deveRetornarEmptyQuandoUsuarioNaoEncontradoPorId() {
        // Arrange
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        // Act
        Optional<UserResponseDTO> result = adminService.findById(999L);

        // Assert
        assertTrue(result.isEmpty());
        verify(userRepository).findById(999L);
    }

    // Testes para UPDATE
    @Test
    @DisplayName("Deve atualizar usuário com sucesso")
    void deveAtualizarUsuarioComSucesso() {
        // Arrange
        UserUpdateDTO updateDTO = new UserUpdateDTO();
        updateDTO.setNome("Nome Atualizado");
        updateDTO.setSobrenome("Sobrenome Atualizado");

        when(userRepository.findById(2L)).thenReturn(Optional.of(userRegular));
        when(userRepository.save(any(User.class))).thenReturn(userRegular);

        // Act
        UserResponseDTO result = adminService.updateUser(2L, updateDTO);

        // Assert
        assertNotNull(result);
        verify(userRepository).findById(2L);
        verify(userRepository).save(userRegular);
    }

    @Test
    @DisplayName("Deve atualizar senha do usuário com sucesso")
    void deveAtualizarSenhaDoUsuarioComSucesso() {
        // Arrange
        UserUpdateDTO updateDTO = new UserUpdateDTO();
        updateDTO.setPassword("novaSenha123");

        when(userRepository.findById(2L)).thenReturn(Optional.of(userRegular));
        when(passwordEncoder.encode("novaSenha123")).thenReturn("encoded_nova_senha");
        when(userRepository.save(any(User.class))).thenReturn(userRegular);

        // Act
        UserResponseDTO result = adminService.updateUser(2L, updateDTO);

        // Assert
        assertNotNull(result);
        verify(userRepository).findById(2L);
        verify(passwordEncoder).encode("novaSenha123");
        verify(userRepository).save(userRegular);
    }

    @Test
    @DisplayName("Deve lançar exceção ao atualizar usuário inexistente")
    void deveLancarExcecaoAoAtualizarUsuarioInexistente() {
        // Arrange
        UserUpdateDTO updateDTO = new UserUpdateDTO();
        updateDTO.setNome("Teste");

        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> adminService.updateUser(999L, updateDTO));
        
        verify(userRepository).findById(999L);
        verify(userRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar exceção ao atualizar email para um já existente")
    void deveLancarExcecaoAoAtualizarEmailParaJaExistente() {
        // Arrange
        UserUpdateDTO updateDTO = new UserUpdateDTO();
        updateDTO.setEmail("admin@email.com");

        when(userRepository.findById(2L)).thenReturn(Optional.of(userRegular));
        when(userRepository.findByEmail("admin@email.com")).thenReturn(Optional.of(userAdmin));

        // Act & Assert
        assertThrows(UserDuplicatedException.class, () -> adminService.updateUser(2L, updateDTO));
        
        verify(userRepository).findById(2L);
        verify(userRepository).findByEmail("admin@email.com");
        verify(userRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve permitir atualizar email para o mesmo email do próprio usuário")
    void devePermitirAtualizarEmailParaMesmoEmailDoProprioUsuario() {
        // Arrange
        UserUpdateDTO updateDTO = new UserUpdateDTO();
        updateDTO.setEmail("user@email.com"); // Mesmo email do userRegular

        when(userRepository.findById(2L)).thenReturn(Optional.of(userRegular));
        when(userRepository.save(any(User.class))).thenReturn(userRegular);

        // Act
        UserResponseDTO result = adminService.updateUser(2L, updateDTO);

        // Assert
        assertNotNull(result);
        verify(userRepository).findById(2L);
        verify(userRepository, never()).findByEmail(anyString()); // Não deve verificar duplicação
        verify(userRepository).save(userRegular);
    }

    // Testes para DELETE
    @Test
    @DisplayName("Deve deletar usuário com sucesso")
    void deveDeletarUsuarioComSucesso() {
        // Arrange
        when(userRepository.findById(2L)).thenReturn(Optional.of(userRegular));

        // Act
        assertDoesNotThrow(() -> adminService.deleteUser(2L));

        // Assert
        verify(userRepository).findById(2L);
        verify(userRepository).delete(userRegular);
    }

    @Test
    @DisplayName("Deve lançar exceção ao deletar usuário inexistente")
    void deveLancarExcecaoAoDeletarUsuarioInexistente() {
        // Arrange
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> adminService.deleteUser(999L));
        
        verify(userRepository).findById(999L);
        verify(userRepository, never()).delete(any());
    }
}