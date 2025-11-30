package com.pds.pingou.framework.core.service;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Serviço abstrato base para operações CRUD genéricas no framework.
 * 
 * Esta classe fornece uma implementação padrão para operações comuns de CRUD,
 * reduzindo código boilerplate nas implementações específicas de domínio.
 * 
 * @param <T> Tipo da entidade
 * @param <ID> Tipo do identificador
 * @param <R> Tipo do repositório
 * 
 * @author Pingou Framework Team
 * @version 1.0
 * @since 1.0
 */
public abstract class BaseCrudService<T, ID, R extends JpaRepository<T, ID>> {
    
    /**
     * Retorna o repositório utilizado por este serviço.
     * Deve ser implementado pelas subclasses.
     */
    protected abstract R getRepository();
    
    /**
     * Lista todas as entidades.
     */
    public List<T> findAll() {
        return getRepository().findAll();
    }
    
    /**
     * Busca uma entidade por ID.
     */
    public Optional<T> findById(ID id) {
        return getRepository().findById(id);
    }
    
    /**
     * Busca uma entidade por ID ou lança exceção se não encontrada.
     */
    public T findByIdOrThrow(ID id) {
        return findById(id)
            .orElseThrow(() -> createNotFoundException(id));
    }
    
    /**
     * Salva uma entidade.
     */
    public T save(T entity) {
        return getRepository().save(entity);
    }
    
    /**
     * Atualiza uma entidade existente.
     */
    public T update(T entity) {
        return save(entity);
    }
    
    /**
     * Deleta uma entidade por ID.
     */
    public void deleteById(ID id) {
        getRepository().deleteById(id);
    }
    
    /**
     * Deleta uma entidade.
     */
    public void delete(T entity) {
        getRepository().delete(entity);
    }
    
    /**
     * Verifica se uma entidade existe por ID.
     */
    public boolean existsById(ID id) {
        return getRepository().existsById(id);
    }
    
    /**
     * Conta o número total de entidades.
     */
    public long count() {
        return getRepository().count();
    }
    
    /**
     * Cria uma exceção personalizada quando uma entidade não é encontrada.
     * Deve ser implementado pelas subclasses.
     */
    protected abstract RuntimeException createNotFoundException(ID id);
    
    /**
     * Valida uma entidade antes de salvar.
     * Pode ser sobrescrito pelas subclasses para adicionar validações específicas.
     */
    protected void validate(T entity) {
        // Implementação padrão vazia - pode ser sobrescrita
    }
    
    /**
     * Executa lógica antes de salvar.
     * Pode ser sobrescrito pelas subclasses.
     */
    protected void beforeSave(T entity) {
        validate(entity);
    }
    
    /**
     * Executa lógica depois de salvar.
     * Pode ser sobrescrito pelas subclasses.
     */
    protected void afterSave(T entity) {
        // Implementação padrão vazia - pode ser sobrescrita
    }
    
    /**
     * Executa lógica antes de deletar.
     * Pode ser sobrescrito pelas subclasses.
     */
    protected void beforeDelete(ID id) {
        // Implementação padrão vazia - pode ser sobrescrita
    }
    
    /**
     * Executa lógica depois de deletar.
     * Pode ser sobrescrito pelas subclasses.
     */
    protected void afterDelete(ID id) {
        // Implementação padrão vazia - pode ser sobrescrita
    }
}
