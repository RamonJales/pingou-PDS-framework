package com.pds.pingou.examples.security;

import com.pds.pingou.framework.core.security.auth.BaseAuthenticationService;
import com.pds.pingou.framework.core.security.config.JwtService;
import com.pds.pingou.framework.core.security.dto.RegisterRequestDTO;
import com.pds.pingou.framework.core.security.user.UserRole;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

/**
 * EXEMPLO: Como implementar AuthenticationService para seu usuário personalizado.
 * 
 * Demonstra como estender BaseAuthenticationService para usar seu próprio
 * tipo de usuário com campos adicionais.
 * 
 * Para usar em produção:
 * 1. Copie esta classe para seu pacote de domínio
 * 2. Adicione @Service
 * 3. Injete seu UserRepository
 * 4. Ajuste a lógica de createAndSaveUser conforme necessário
 * 
 * @author Pingou Framework Team
 * @version 2.0
 */
// @Service  // Descomente para usar em produção
public class ExemploAuthenticationService extends BaseAuthenticationService<ExemploUser> {

    // private final ExemploUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public ExemploAuthenticationService(
            // ExemploUserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            AuthenticationManager authenticationManager) {
        super(jwtService, authenticationManager);
        // this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected Optional<ExemploUser> findUserByEmail(String email) {
        // return userRepository.findByEmail(email);
        return Optional.empty(); // Placeholder - substitua pela implementação real
    }

    @Override
    protected boolean userExistsByEmail(String email) {
        // return userRepository.findByEmail(email).isPresent();
        return false; // Placeholder - substitua pela implementação real
    }

    @Override
    protected ExemploUser createAndSaveUser(RegisterRequestDTO request) {
        ExemploUser user = new ExemploUser(
                request.email(),
                request.nome(),
                request.sobrenome(),
                passwordEncoder.encode(request.password()),
                UserRole.USER
        );
        
        // Aqui você pode adicionar lógica para preencher campos adicionais
        // Por exemplo, se o RegisterRequestDTO for estendido:
        // user.setCpf(request.cpf());
        // user.setTelefone(request.telefone());
        
        // return userRepository.save(user);
        return user; // Placeholder - substitua pela implementação real
    }
}
