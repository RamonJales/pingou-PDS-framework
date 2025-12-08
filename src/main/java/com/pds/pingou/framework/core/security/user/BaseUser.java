package com.pds.pingou.framework.core.security.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * Classe abstrata base para representar usuários no sistema.
 * 
 * Esta classe faz parte do framework de segurança e deve ser estendida por
 * implementações específicas de domínio. Ela encapsula a lógica comum de
 * autenticação e autorização usando Spring Security.
 * 
 * @author Pingou Framework Team
 * @version 1.0
 * @since 1.0
 */
@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
public abstract class BaseUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String sobrenome;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    protected BaseUser(String email, String nome, String sobrenome, String password, UserRole role) {
        this.email = email;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.password = password;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role == UserRole.ADMIN) {
            return List.of(
                    new SimpleGrantedAuthority("ROLE_ADMIN"),
                    new SimpleGrantedAuthority("ROLE_USER")
            );
        }
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    /**
     * Retorna o nome completo do usuário.
     */
    public String getFullName() {
        return nome + " " + sobrenome;
    }

    /**
     * Verifica se o usuário é um administrador.
     */
    public boolean isAdmin() {
        return this.role == UserRole.ADMIN;
    }
}
