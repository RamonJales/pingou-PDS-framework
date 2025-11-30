package com.pds.pingou.framework.core.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller abstrato base para operações REST CRUD genéricas no framework.
 * 
 * Esta classe fornece endpoints padrão para operações comuns de CRUD,
 * reduzindo código boilerplate nas implementações específicas de domínio.
 * 
 * @param <T> Tipo da entidade
 * @param <DTO> Tipo do DTO de resposta
 * @param <REQ> Tipo do DTO de requisição
 * @param <S> Tipo do serviço
 * 
 * @author Pingou Framework Team
 * @version 1.0
 * @since 1.0
 */
public abstract class BaseRestController<T, DTO, REQ, S> {
    
    /**
     * Retorna o serviço utilizado por este controller.
     * Deve ser implementado pelas subclasses.
     */
    protected abstract S getService();
    
    /**
     * Lista todas as entidades.
     */
    @GetMapping
    public ResponseEntity<List<DTO>> listAll() {
        List<DTO> items = findAll();
        return ResponseEntity.ok(items);
    }
    
    /**
     * Busca uma entidade por ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DTO> findById(@PathVariable Long id) {
        DTO item = findEntityById(id);
        return ResponseEntity.ok(item);
    }
    
    /**
     * Cria uma nova entidade.
     */
    @PostMapping
    public ResponseEntity<DTO> create(@RequestBody REQ request) {
        DTO created = createEntity(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    
    /**
     * Atualiza uma entidade existente.
     */
    @PutMapping("/{id}")
    public ResponseEntity<DTO> update(@PathVariable Long id, @RequestBody REQ request) {
        DTO updated = updateEntity(id, request);
        return ResponseEntity.ok(updated);
    }
    
    /**
     * Deleta uma entidade por ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        deleteEntity(id);
        return ResponseEntity.noContent().build();
    }
    
    /**
     * Método abstrato para listar todas as entidades.
     * Deve ser implementado pelas subclasses.
     */
    protected abstract List<DTO> findAll();
    
    /**
     * Método abstrato para buscar uma entidade por ID.
     * Deve ser implementado pelas subclasses.
     */
    protected abstract DTO findEntityById(Long id);
    
    /**
     * Método abstrato para criar uma nova entidade.
     * Deve ser implementado pelas subclasses.
     */
    protected abstract DTO createEntity(REQ request);
    
    /**
     * Método abstrato para atualizar uma entidade.
     * Deve ser implementado pelas subclasses.
     */
    protected abstract DTO updateEntity(Long id, REQ request);
    
    /**
     * Método abstrato para deletar uma entidade.
     * Deve ser implementado pelas subclasses.
     */
    protected abstract void deleteEntity(Long id);
}
