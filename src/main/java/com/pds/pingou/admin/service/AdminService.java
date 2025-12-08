package com.pds.pingou.admin.service;

import com.pds.pingou.admin.dto.UserRequestDTO;
import com.pds.pingou.admin.dto.UserResponseDTO;
import com.pds.pingou.admin.dto.UserUpdateDTO;
import com.pds.pingou.admin.mapper.UserMapper;
import com.pds.pingou.framework.core.security.exception.UserDuplicatedException;
import com.pds.pingou.framework.core.security.exception.UserNotFoundException;
import com.pds.pingou.framework.core.security.user.UserRole;
import com.pds.pingou.security.user.User;
import com.pds.pingou.security.user.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdminService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserResponseDTO> findAll() {
        return userRepository.findAll().stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<UserResponseDTO> findById(Long id) {
        return userRepository.findById(id)
                .map(UserMapper::toDTO);
    }

    public Optional<UserResponseDTO> findByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(UserMapper::toDTO);
    }

    @Transactional
    public UserResponseDTO createUser(UserRequestDTO dto) {
        // Verifica se email já existe
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new UserDuplicatedException("Email já cadastrado");
        }
        
        User user = UserMapper.toEntity(dto, passwordEncoder);
        User savedUser = userRepository.save(user);
        return UserMapper.toDTO(savedUser);
    }

    @Transactional
    public UserResponseDTO updateUser(Long id, UserUpdateDTO dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));
        
        // Se está tentando alterar o email, verifica se já existe
        if (dto.getEmail() != null && !dto.getEmail().equals(user.getEmail())) {
            if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
                throw new UserDuplicatedException("Email já cadastrado");
            }
        }
        
        UserMapper.updateEntity(user, dto, passwordEncoder);
        User updatedUser = userRepository.save(user);
        return UserMapper.toDTO(updatedUser);
    }

    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));
        userRepository.delete(user);
    }

    @Transactional
    public void changeUserRole(Long userId, UserRole newRole) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));
        user.setRole(newRole);
        userRepository.save(user);
    }
}
