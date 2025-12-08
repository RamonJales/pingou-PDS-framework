package com.pds.pingou.examples.security;

import com.pds.pingou.assinatura.Assinatura;
import com.pds.pingou.framework.core.security.user.BaseUser;
import com.pds.pingou.framework.core.security.user.UserRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * EXEMPLO: Como estender BaseUser para criar seu próprio usuário.
 * 
 * Demonstra como adicionar campos específicos ao usuário base do framework,
 * como CPF, data de nascimento, endereço, etc.
 * 
 * Para usar em produção:
 * 1. Copie esta classe para seu pacote de domínio
 * 2. Ajuste os campos conforme sua necessidade
 * 3. Atualize o AuthenticationService para usar seu tipo de usuário
 * 
 * @author Pingou Framework Team
 * @version 2.0
 */
@Entity
@Table(name = "exemplo_usuarios")
@Getter
@Setter
@NoArgsConstructor
public class ExemploUser extends BaseUser {

    /** CPF do usuário (apenas números) */
    @Column(name = "cpf", unique = true, length = 11)
    private String cpf;

    /** Data de nascimento */
    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    /** Telefone com DDD */
    @Column(name = "telefone", length = 15)
    private String telefone;

    // ===== Endereço =====
    
    @Column(name = "cep", length = 8)
    private String cep;

    @Column(name = "logradouro")
    private String logradouro;

    @Column(name = "numero", length = 10)
    private String numero;

    @Column(name = "complemento")
    private String complemento;

    @Column(name = "bairro")
    private String bairro;

    @Column(name = "cidade")
    private String cidade;

    @Column(name = "estado", length = 2)
    private String estado;

    // ===== Preferências =====

    /** Aceita receber emails promocionais */
    @Column(name = "aceita_emails")
    private Boolean aceitaEmails = false;

    /** Aceita receber notificações push */
    @Column(name = "aceita_notificacoes")
    private Boolean aceitaNotificacoes = true;

    // ===== Relacionamentos =====

    /**
     * NOTA: Se você precisar de uma relação com Assinatura, 
     * crie sua própria classe de Assinatura que referencie ExemploUser.
     * 
     * Exemplo:
     * @OneToOne(mappedBy = "exemploUser", cascade = CascadeType.ALL, orphanRemoval = true)
     * private MinhaAssinatura assinatura;
     */

    /**
     * Construtor com campos obrigatórios.
     */
    public ExemploUser(String email, String nome, String sobrenome, String password, UserRole role) {
        super(email, nome, sobrenome, password, role);
    }

    /**
     * Construtor completo.
     */
    public ExemploUser(String email, String nome, String sobrenome, String password, 
                       UserRole role, String cpf, LocalDate dataNascimento) {
        super(email, nome, sobrenome, password, role);
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
    }

    /**
     * Retorna o endereço formatado.
     */
    public String getEnderecoCompleto() {
        StringBuilder sb = new StringBuilder();
        if (logradouro != null) sb.append(logradouro);
        if (numero != null) sb.append(", ").append(numero);
        if (complemento != null) sb.append(" - ").append(complemento);
        if (bairro != null) sb.append(", ").append(bairro);
        if (cidade != null) sb.append(", ").append(cidade);
        if (estado != null) sb.append("/").append(estado);
        if (cep != null) sb.append(" - CEP: ").append(formatarCep(cep));
        return sb.toString();
    }

    private String formatarCep(String cep) {
        if (cep != null && cep.length() == 8) {
            return cep.substring(0, 5) + "-" + cep.substring(5);
        }
        return cep;
    }

    /**
     * Verifica se o usuário é maior de idade.
     */
    public boolean isMaiorDeIdade() {
        if (dataNascimento == null) return false;
        return dataNascimento.plusYears(18).isBefore(LocalDate.now()) ||
               dataNascimento.plusYears(18).isEqual(LocalDate.now());
    }
}
