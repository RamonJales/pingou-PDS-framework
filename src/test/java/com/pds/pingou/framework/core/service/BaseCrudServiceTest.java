package com.pds.pingou.framework.core.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

/**
 * Testes para BaseCrudService - serviço base de CRUD do framework.
 */
@ExtendWith(MockitoExtension.class)
class BaseCrudServiceTest {

    @Mock
    private JpaRepository<TestEntity, Long> repository;

    private TestCrudService service;
    private TestEntity entity;

    @BeforeEach
    void setUp() {
        service = new TestCrudService(repository);
        
        entity = new TestEntity();
        entity.setId(1L);
        entity.setName("Test Entity");
    }

    @Test
    @DisplayName("Deve listar todas as entidades com sucesso")
    void deveListarTodasEntidadesComSucesso() {
        // Arrange
        List<TestEntity> entities = Arrays.asList(entity);
        when(repository.findAll()).thenReturn(entities);

        // Act
        List<TestEntity> result = service.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Entity", result.get(0).getName());
        verify(repository).findAll();
    }

    @Test
    @DisplayName("Deve buscar entidade por ID com sucesso")
    void deveBuscarEntidadePorIdComSucesso() {
        // Arrange
        when(repository.findById(anyLong())).thenReturn(Optional.of(entity));

        // Act
        Optional<TestEntity> result = service.findById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        assertEquals("Test Entity", result.get().getName());
        verify(repository).findById(1L);
    }

    @Test
    @DisplayName("Deve retornar Optional vazio quando entidade não existe")
    void deveRetornarOptionalVazioQuandoEntidadeNaoExiste() {
        // Arrange
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        // Act
        Optional<TestEntity> result = service.findById(999L);

        // Assert
        assertFalse(result.isPresent());
        verify(repository).findById(999L);
    }

    @Test
    @DisplayName("Deve buscar entidade por ID ou lançar exceção quando encontrada")
    void deveBuscarEntidadePorIdOrThrowQuandoEncontrada() {
        // Arrange
        when(repository.findById(anyLong())).thenReturn(Optional.of(entity));

        // Act
        TestEntity result = service.findByIdOrThrow(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(repository).findById(1L);
    }

    @Test
    @DisplayName("Deve lançar exceção quando entidade não é encontrada no findByIdOrThrow")
    void deveLancarExcecaoQuandoEntidadeNaoEncontradaNoFindByIdOrThrow() {
        // Arrange
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, 
            () -> service.findByIdOrThrow(999L));
        
        assertEquals("Entity not found with id: 999", exception.getMessage());
        verify(repository).findById(999L);
    }

    @Test
    @DisplayName("Deve salvar entidade com sucesso")
    void deveSalvarEntidadeComSucesso() {
        // Arrange
        when(repository.save(any(TestEntity.class))).thenReturn(entity);

        // Act
        TestEntity result = service.save(entity);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test Entity", result.getName());
        verify(repository).save(entity);
    }

    @Test
    @DisplayName("Deve atualizar entidade com sucesso")
    void deveAtualizarEntidadeComSucesso() {
        // Arrange
        entity.setName("Updated Entity");
        when(repository.save(any(TestEntity.class))).thenReturn(entity);

        // Act
        TestEntity result = service.update(entity);

        // Assert
        assertNotNull(result);
        assertEquals("Updated Entity", result.getName());
        verify(repository).save(entity);
    }

    @Test
    @DisplayName("Deve deletar entidade por ID com sucesso")
    void deveDeletarEntidadePorIdComSucesso() {
        // Arrange
        doNothing().when(repository).deleteById(anyLong());

        // Act
        service.deleteById(1L);

        // Assert
        verify(repository).deleteById(1L);
    }

    @Test
    @DisplayName("Deve deletar entidade com sucesso")
    void deveDeletarEntidadeComSucesso() {
        // Arrange
        doNothing().when(repository).delete(any(TestEntity.class));

        // Act
        service.delete(entity);

        // Assert
        verify(repository).delete(entity);
    }

    @Test
    @DisplayName("Deve verificar se entidade existe por ID")
    void deveVerificarSeEntidadeExistePorId() {
        // Arrange
        when(repository.existsById(anyLong())).thenReturn(true);

        // Act
        boolean result = service.existsById(1L);

        // Assert
        assertTrue(result);
        verify(repository).existsById(1L);
    }

    @Test
    @DisplayName("Deve verificar que entidade não existe por ID")
    void deveVerificarQueEntidadeNaoExistePorId() {
        // Arrange
        when(repository.existsById(anyLong())).thenReturn(false);

        // Act
        boolean result = service.existsById(999L);

        // Assert
        assertFalse(result);
        verify(repository).existsById(999L);
    }

    @Test
    @DisplayName("Deve contar total de entidades")
    void deveContarTotalDeEntidades() {
        // Arrange
        when(repository.count()).thenReturn(5L);

        // Act
        long result = service.count();

        // Assert
        assertEquals(5L, result);
        verify(repository).count();
    }

    // Classes auxiliares para teste
    static class TestEntity {
        private Long id;
        private String name;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    static class TestCrudService extends BaseCrudService<TestEntity, Long, JpaRepository<TestEntity, Long>> {
        private final JpaRepository<TestEntity, Long> repository;

        public TestCrudService(JpaRepository<TestEntity, Long> repository) {
            this.repository = repository;
        }

        @Override
        protected JpaRepository<TestEntity, Long> getRepository() {
            return repository;
        }

        @Override
        protected RuntimeException createNotFoundException(Long id) {
            return new RuntimeException("Entity not found with id: " + id);
        }
    }
}
