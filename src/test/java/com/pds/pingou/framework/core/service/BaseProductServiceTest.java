package com.pds.pingou.framework.core.service;

import com.pds.pingou.framework.core.entity.BaseProduct;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.repository.JpaRepository;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

/**
 * Testes para BaseProductService - serviço base de produtos do framework.
 */
@ExtendWith(MockitoExtension.class)
class BaseProductServiceTest {

    @Mock
    private JpaRepository<TestProduct, Long> repository;

    private TestProductService service;
    private TestProduct product1;
    private TestProduct product2;
    private TestProduct product3;

    @BeforeEach
    void setUp() {
        service = new TestProductService(repository);
        
        product1 = new TestProduct();
        product1.setId(1L);
        product1.setNome("Produto A");
        product1.setDescricao("Descrição A");
        product1.setPreco(new BigDecimal("100.00"));
        product1.setAtivo(true);

        product2 = new TestProduct();
        product2.setId(2L);
        product2.setNome("Produto B");
        product2.setDescricao("Descrição B");
        product2.setPreco(new BigDecimal("200.00"));
        product2.setAtivo(false);

        product3 = new TestProduct();
        product3.setId(3L);
        product3.setNome("Produto C");
        product3.setDescricao("Descrição C");
        product3.setPreco(new BigDecimal("150.00"));
        product3.setAtivo(true);
    }

    @Test
    @DisplayName("Deve listar apenas produtos ativos")
    void deveListarApenasProdutosAtivos() {
        // Arrange
        List<TestProduct> products = Arrays.asList(product1, product2, product3);
        when(repository.findAll()).thenReturn(products);

        // Act
        List<TestProduct> result = service.findActiveProducts();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(TestProduct::isAvailable));
        assertTrue(result.contains(product1));
        assertTrue(result.contains(product3));
        assertFalse(result.contains(product2));
        verify(repository).findAll();
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não há produtos ativos")
    void deveRetornarListaVaziaQuandoNaoHaProdutosAtivos() {
        // Arrange
        product1.setAtivo(false);
        product3.setAtivo(false);
        List<TestProduct> products = Arrays.asList(product1, product2, product3);
        when(repository.findAll()).thenReturn(products);

        // Act
        List<TestProduct> result = service.findActiveProducts();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(repository).findAll();
    }

    @Test
    @DisplayName("Deve buscar produtos por nome contendo texto")
    void deveBuscarProdutosPorNomeContendoTexto() {
        // Arrange
        List<TestProduct> products = Arrays.asList(product1, product2, product3);
        when(repository.findAll()).thenReturn(products);

        // Act
        List<TestProduct> result = service.findByNameContaining("Produto");

        // Assert
        assertNotNull(result);
        assertEquals(3, result.size());
        verify(repository).findAll();
    }

    @Test
    @DisplayName("Deve buscar produtos por nome parcial (case insensitive)")
    void deveBuscarProdutosPorNomeParcialCaseInsensitive() {
        // Arrange
        List<TestProduct> products = Arrays.asList(product1, product2, product3);
        when(repository.findAll()).thenReturn(products);

        // Act
        List<TestProduct> result = service.findByNameContaining("produto a");

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Produto A", result.get(0).getNome());
        verify(repository).findAll();
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando nome não encontrado")
    void deveRetornarListaVaziaQuandoNomeNaoEncontrado() {
        // Arrange
        List<TestProduct> products = Arrays.asList(product1, product2, product3);
        when(repository.findAll()).thenReturn(products);

        // Act
        List<TestProduct> result = service.findByNameContaining("Inexistente");

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(repository).findAll();
    }

    @Test
    @DisplayName("Deve buscar produtos por categoria")
    void deveBuscarProdutosPorCategoria() {
        // Arrange
        product1.setCategory("Eletrônicos");
        product2.setCategory("Livros");
        product3.setCategory("Eletrônicos");
        List<TestProduct> products = Arrays.asList(product1, product2, product3);
        when(repository.findAll()).thenReturn(products);

        // Act
        List<TestProduct> result = service.findByCategory("Eletrônicos");

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(p -> "Eletrônicos".equals(p.getCategory())));
        verify(repository).findAll();
    }

    @Test
    @DisplayName("Deve buscar produtos por categoria (case insensitive)")
    void deveBuscarProdutosPorCategoriaCaseInsensitive() {
        // Arrange
        product1.setCategory("Eletrônicos");
        product2.setCategory("Livros");
        List<TestProduct> products = Arrays.asList(product1, product2);
        when(repository.findAll()).thenReturn(products);

        // Act
        List<TestProduct> result = service.findByCategory("eletrônicos");

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Eletrônicos", result.get(0).getCategory());
        verify(repository).findAll();
    }

    @Test
    @DisplayName("Deve ativar produto com sucesso")
    void deveAtivarProdutoComSucesso() {
        // Arrange
        product2.setAtivo(false);
        when(repository.findById(anyLong())).thenReturn(Optional.of(product2));
        when(repository.save(any(TestProduct.class))).thenAnswer(i -> i.getArgument(0));

        // Act
        TestProduct result = service.activateProduct(2L);

        // Assert
        assertNotNull(result);
        assertTrue(result.isAtivo());
        verify(repository).findById(2L);
        verify(repository).save(product2);
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar ativar produto inexistente")
    void deveLancarExcecaoAoTentarAtivarProdutoInexistente() {
        // Arrange
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> service.activateProduct(999L));
        verify(repository).findById(999L);
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Deve desativar produto com sucesso")
    void deveDesativarProdutoComSucesso() {
        // Arrange
        product1.setAtivo(true);
        when(repository.findById(anyLong())).thenReturn(Optional.of(product1));
        when(repository.save(any(TestProduct.class))).thenAnswer(i -> i.getArgument(0));

        // Act
        TestProduct result = service.deactivateProduct(1L);

        // Assert
        assertNotNull(result);
        assertFalse(result.isAtivo());
        verify(repository).findById(1L);
        verify(repository).save(product1);
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar desativar produto inexistente")
    void deveLancarExcecaoAoTentarDesativarProdutoInexistente() {
        // Arrange
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> service.deactivateProduct(999L));
        verify(repository).findById(999L);
        verify(repository, never()).save(any());
    }

    // Classes auxiliares para teste
    @Entity
    static class TestProduct extends BaseProduct {
        private String category;

        @Override
        public String getShortDescription() {
            return getNome();
        }

        @Override
        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }
    }

    static class TestProductService extends BaseProductService<TestProduct, JpaRepository<TestProduct, Long>> {
        private final JpaRepository<TestProduct, Long> repository;

        public TestProductService(JpaRepository<TestProduct, Long> repository) {
            this.repository = repository;
        }

        @Override
        protected JpaRepository<TestProduct, Long> getRepository() {
            return repository;
        }

        @Override
        protected RuntimeException createNotFoundException(Long id) {
            return new RuntimeException("Product not found with id: " + id);
        }
    }
}
