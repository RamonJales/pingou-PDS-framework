package com.pds.pingou.security.auth;

import com.pds.pingou.framework.core.security.config.JwtService;
import com.pds.pingou.framework.core.security.dto.AuthenticationResponseDTO;
import com.pds.pingou.framework.core.security.dto.LoginRequestDTO;
import com.pds.pingou.framework.core.security.dto.RegisterRequestDTO;
import com.pds.pingou.framework.core.security.exception.UserDuplicatedException;
import com.pds.pingou.framework.core.security.exception.UserNotFoundException;
import com.pds.pingou.framework.core.security.user.UserRole;
import com.pds.pingou.security.user.User;
import com.pds.pingou.security.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private PasswordEncoder passwordEncoder;

    private AuthenticationService authenticationService;

    private RegisterRequestDTO registerRequestDTO;
    private LoginRequestDTO loginRequestDTO;
    private User user;

    @BeforeEach
    void setUp() {
        authenticationService = new AuthenticationService(
                userRepository,
                passwordEncoder,
                jwtService,
                authenticationManager
        );

        registerRequestDTO = new RegisterRequestDTO(
                "João",
                "Silva",
                "joao@email.com",
                "password123"
        );

        loginRequestDTO = new LoginRequestDTO(
                "joao@email.com",
                "password123"
        );

        user = new User(
                "joao@email.com",
                "João",
                "Silva",
                "encodedPassword",
                UserRole.USER
        );
        user.setId(1L);
    }

    @Test
    @DisplayName("Deve registrar um novo usuário com sucesso")
    void deveRegistrarNovoUsuarioComSucesso() {
        // Arrange
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(jwtService.generateToken(any(User.class))).thenReturn("accessToken");
        when(jwtService.generateRefreshToken(any(User.class))).thenReturn("refreshToken");

        // Act
        AuthenticationResponseDTO response = authenticationService.register(registerRequestDTO);

        // Assert
        assertNotNull(response);
        assertEquals("accessToken", response.accessToken());
        assertEquals("refreshToken", response.refreshToken());
        verify(userRepository).findByEmail("joao@email.com");
        verify(userRepository).save(any(User.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando email já existe no registro")
    void deveLancarExcecaoQuandoEmailJaExiste() {
        // Arrange
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        // Act & Assert
        assertThrows(UserDuplicatedException.class, () -> 
                authenticationService.register(registerRequestDTO));
        
        verify(userRepository).findByEmail("joao@email.com");
        verify(userRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve fazer login com sucesso")
    void deveFazerLoginComSucesso() {
        // Arrange
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(jwtService.generateToken(any(User.class))).thenReturn("accessToken");
        when(jwtService.generateRefreshToken(any(User.class))).thenReturn("refreshToken");

        // Act
        AuthenticationResponseDTO response = authenticationService.login(loginRequestDTO);

        // Assert
        assertNotNull(response);
        assertEquals("accessToken", response.accessToken());
        assertEquals("refreshToken", response.refreshToken());
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userRepository).findByEmail("joao@email.com");
    }

    @Test
    @DisplayName("Deve lançar exceção quando credenciais são inválidas")
    void deveLancarExcecaoQuandoCredenciaisSaoInvalidas() {
        // Arrange
        doThrow(new BadCredentialsException("Invalid credentials"))
                .when(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));

        // Act & Assert
        assertThrows(BadCredentialsException.class, () -> 
                authenticationService.login(loginRequestDTO));
        
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando usuário não é encontrado no login")
    void deveLancarExcecaoQuandoUsuarioNaoEncontrado() {
        // Arrange
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> 
                authenticationService.login(loginRequestDTO));
        
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userRepository).findByEmail("joao@email.com");
    }
}