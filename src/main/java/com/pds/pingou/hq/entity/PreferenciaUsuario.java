package com.pds.pingou.hq.entity;

import com.pds.pingou.hq.enums.CategoriaHQ;
import com.pds.pingou.hq.enums.EditoraHQ;
import com.pds.pingou.security.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Entidade que armazena as preferências do usuário
 * Utilizada para curadoria e recomendações personalizadas
 */
@Entity
@Table(name = "preferencias_usuario")
@Getter
@Setter
public class PreferenciaUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "preferencias_categorias", 
                     joinColumns = @JoinColumn(name = "preferencia_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "categoria")
    private Set<CategoriaHQ> categoriasFavoritas = new HashSet<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "preferencias_editoras", 
                     joinColumns = @JoinColumn(name = "preferencia_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "editora")
    private Set<EditoraHQ> editorasFavoritas = new HashSet<>();

    @Column(name = "prefere_classicas")
    private Boolean prefereClassicas = false;

    @Column(name = "prefere_modernas")
    private Boolean prefereModernas = false;

    @Column(name = "interesse_edicoes_colecionador")
    private Boolean interesseEdicoesColecionador = false;

    @Column(name = "quiz_completo")
    private Boolean quizCompleto = false;

    @Column(name = "data_criacao", nullable = false)
    private LocalDateTime dataCriacao;

    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    /**
     * Construtor padrão
     */
    public PreferenciaUsuario() {
        this.dataCriacao = LocalDateTime.now();
        this.categoriasFavoritas = new HashSet<>();
        this.editorasFavoritas = new HashSet<>();
    }

    /**
     * Construtor com usuário
     */
    public PreferenciaUsuario(User user) {
        this();
        this.user = user;
    }

    /**
     * Adiciona uma categoria favorita
     */
    public void adicionarCategoria(CategoriaHQ categoria) {
        this.categoriasFavoritas.add(categoria);
        this.dataAtualizacao = LocalDateTime.now();
    }

    /**
     * Remove uma categoria favorita
     */
    public void removerCategoria(CategoriaHQ categoria) {
        this.categoriasFavoritas.remove(categoria);
        this.dataAtualizacao = LocalDateTime.now();
    }

    /**
     * Adiciona uma editora favorita
     */
    public void adicionarEditora(EditoraHQ editora) {
        this.editorasFavoritas.add(editora);
        this.dataAtualizacao = LocalDateTime.now();
    }

    /**
     * Remove uma editora favorita
     */
    public void removerEditora(EditoraHQ editora) {
        this.editorasFavoritas.remove(editora);
        this.dataAtualizacao = LocalDateTime.now();
    }

    /**
     * Verifica se o usuário tem preferências configuradas
     */
    public boolean temPreferenciasConfiguradas() {
        return !categoriasFavoritas.isEmpty() || !editorasFavoritas.isEmpty();
    }

    /**
     * Marca o quiz como completo
     */
    public void marcarQuizCompleto() {
        this.quizCompleto = true;
        this.dataAtualizacao = LocalDateTime.now();
    }

    /**
     * Verifica se uma HQ corresponde às preferências do usuário
     */
    public boolean correspondePreferencias(Quadrinho quadrinho) {
        boolean correspondeCategoria = categoriasFavoritas.isEmpty() || 
                                      categoriasFavoritas.contains(quadrinho.getCategoria());
        
        boolean correspondeEditora = editorasFavoritas.isEmpty() || 
                                    editorasFavoritas.contains(quadrinho.getEditora());
        
        boolean correspondeTipo = (!prefereClassicas && !prefereModernas) ||
                                 (prefereClassicas && quadrinho.isClassica()) ||
                                 (prefereModernas && quadrinho.isModerna());
        
        return correspondeCategoria && correspondeEditora && correspondeTipo;
    }

    @PreUpdate
    private void preUpdate() {
        this.dataAtualizacao = LocalDateTime.now();
    }
}
