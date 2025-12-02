package com.pds.pingou.admin.mapper;

import com.pds.pingou.admin.dto.UserRequestDTO;
import com.pds.pingou.admin.dto.UserResponseDTO;
import com.pds.pingou.admin.dto.UserUpdateDTO;
import com.pds.pingou.camisa.assinatura.dto.AssinaturaCamisaMapper;
import com.pds.pingou.security.user.User;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserMapper {
    
    public static UserResponseDTO toDTO(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setNome(user.getNome());
        dto.setSobrenome(user.getSobrenome());
        dto.setRole(user.getRole());
        
        if (user.getAssinaturaCamisa() != null) {
            dto.setAssinaturaCamisa(AssinaturaCamisaMapper.toResponseDTO(user.getAssinaturaCamisa()));
        }
        
        return dto;
    }
    
    public static User toEntity(UserRequestDTO dto, PasswordEncoder passwordEncoder) {
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setNome(dto.getNome());
        user.setSobrenome(dto.getSobrenome());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(dto.getRole());
        return user;
    }
    
    public static void updateEntity(User user, UserUpdateDTO dto, PasswordEncoder passwordEncoder) {
        if (dto.getEmail() != null && !dto.getEmail().isBlank()) {
            user.setEmail(dto.getEmail());
        }
        if (dto.getNome() != null && !dto.getNome().isBlank()) {
            user.setNome(dto.getNome());
        }
        if (dto.getSobrenome() != null && !dto.getSobrenome().isBlank()) {
            user.setSobrenome(dto.getSobrenome());
        }
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        if (dto.getRole() != null) {
            user.setRole(dto.getRole());
        }
    }
}

