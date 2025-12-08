package com.pds.pingou.admin.controller;

import com.pds.pingou.admin.dto.UserRequestDTO;
import com.pds.pingou.admin.dto.UserResponseDTO;
import com.pds.pingou.admin.dto.UserUpdateDTO;
import com.pds.pingou.admin.service.AdminService;
import com.pds.pingou.framework.core.security.user.UserRole;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> users = adminService.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        return adminService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/users/search")
    public ResponseEntity<UserResponseDTO> getByEmail(@RequestParam String email) {
        return adminService.findByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/users")
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserRequestDTO dto) {
        UserResponseDTO createdUser = adminService.createUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserUpdateDTO dto) {
        UserResponseDTO updatedUser = adminService.updateUser(id, dto);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        adminService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/users/{id}/role")
    public ResponseEntity<Void> changeUserRole(@PathVariable Long id, @RequestParam String role) {
        try {
            UserRole newRole = UserRole.valueOf(role);
            adminService.changeUserRole(id, newRole);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().build();
        }
    }
}