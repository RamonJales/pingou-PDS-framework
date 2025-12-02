package com.pds.pingou.security.user;

import com.pds.pingou.camisa.assinatura.AssinaturaCamisa;
import com.pds.pingou.camisa.planos.PlanoCamisa;
import com.pds.pingou.framework.core.enums.SubscriptionStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Table(name = "users")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String email;

    @NonNull
    private String nome;

    @NonNull
    private String sobrenome;

    @NonNull
    private String password;

    @Enumerated(EnumType.STRING)
    @NonNull
    private UserRole role;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private AssinaturaCamisa assinaturaCamisa;

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

    public AssinaturaCamisa getAssinaturaAtiva(PlanoCamisa plano) {
        if (this.assinaturaCamisa != null 
            && this.assinaturaCamisa.getStatus() == SubscriptionStatus.ATIVA 
            && this.assinaturaCamisa.getPlano().equals(plano)) {
            return this.assinaturaCamisa;
        }
        return null;
    }
}
