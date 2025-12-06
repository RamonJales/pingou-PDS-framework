package com.pds.pingou.framework.core.service;

import com.pds.pingou.framework.core.entity.BasePackage;
import com.pds.pingou.framework.core.entity.BasePlan;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.repository.JpaRepository;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

/**
 * Testes para BasePlanService - serviço base de planos do framework.
 */
@ExtendWith(MockitoExtension.class)
class BasePlanServiceTest {

    @Mock
    private JpaRepository<TestPlan, Long> repository;

    private TestPlanService service;
    private TestPlan plan;

    @BeforeEach
    void setUp() {
        service = new TestPlanService(repository);
        
        plan = new TestPlan();
        plan.setId(1L);
        plan.setNome("Plano Premium");
        plan.setDescricao("Plano com benefícios premium");
        plan.setPreco(new BigDecimal("99.90"));
        plan.setMaxProdutosPorPeriodo(5);
        plan.setFrequenciaEntrega("MENSAL");
        plan.setAtivo(true);
    }

    @Test
    @DisplayName("Deve ativar plano com sucesso")
    void deveAtivarPlanoComSucesso() {
        // Arrange
        plan.setAtivo(false);
        when(repository.findById(anyLong())).thenReturn(Optional.of(plan));
        when(repository.save(any(TestPlan.class))).thenAnswer(i -> i.getArgument(0));

        // Act
        TestPlan result = service.activatePlan(1L);

        // Assert
        assertNotNull(result);
        assertTrue(result.isAtivo());
        verify(repository).findById(1L);
        verify(repository).save(plan);
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar ativar plano inexistente")
    void deveLancarExcecaoAoTentarAtivarPlanoInexistente() {
        // Arrange
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> service.activatePlan(999L));
        verify(repository).findById(999L);
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Deve desativar plano com sucesso")
    void deveDesativarPlanoComSucesso() {
        // Arrange
        plan.setAtivo(true);
        when(repository.findById(anyLong())).thenReturn(Optional.of(plan));
        when(repository.save(any(TestPlan.class))).thenAnswer(i -> i.getArgument(0));

        // Act
        TestPlan result = service.deactivatePlan(1L);

        // Assert
        assertNotNull(result);
        assertFalse(result.isAtivo());
        verify(repository).findById(1L);
        verify(repository).save(plan);
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar desativar plano inexistente")
    void deveLancarExcecaoAoTentarDesativarPlanoInexistente() {
        // Arrange
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> service.deactivatePlan(999L));
        verify(repository).findById(999L);
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Deve validar quantidade máxima de produtos")
    void deveValidarQuantidadeMaximaDeProdutos() {
        // Arrange
        plan.setMaxProdutosPorPeriodo(3);

        // Act & Assert
        assertTrue(plan.isValidProductQuantity(1));
        assertTrue(plan.isValidProductQuantity(3));
        assertFalse(plan.isValidProductQuantity(4));
        assertFalse(plan.isValidProductQuantity(0));
    }

    @Test
    @DisplayName("Deve adicionar pacote ao plano")
    void deveAdicionarPacoteAoPlano() {
        // Arrange
        TestPackage package1 = new TestPackage();
        package1.setId(1L);
        package1.setNome("Pacote 1");

        // Act
        plan.addPackage(package1);

        // Assert
        assertEquals(1, plan.getPackages().size());
        assertTrue(plan.getPackages().contains(package1));
    }

    @Test
    @DisplayName("Deve remover pacote do plano")
    void deveRemoverPacoteDoPlano() {
        // Arrange
        TestPackage package1 = new TestPackage();
        package1.setId(1L);
        package1.setNome("Pacote 1");
        plan.addPackage(package1);

        // Act
        plan.removePackage(package1);

        // Assert
        assertEquals(0, plan.getPackages().size());
        assertFalse(plan.getPackages().contains(package1));
    }

    @Test
    @DisplayName("Deve verificar se plano está ativo")
    void deveVerificarSePlanoEstaAtivo() {
        // Arrange
        plan.setAtivo(true);

        // Act & Assert
        assertTrue(plan.isAtivo());
        
        // Arrange
        plan.setAtivo(false);

        // Act & Assert
        assertFalse(plan.isAtivo());
    }

    @Test
    @DisplayName("Deve criar plano com valores padrão corretos")
    void deveCriarPlanoComValoresPadraoCorretos() {
        // Arrange & Act
        TestPlan newPlan = new TestPlan();
        newPlan.setNome("Novo Plano");
        newPlan.setPreco(new BigDecimal("49.90"));
        newPlan.setMaxProdutosPorPeriodo(2);

        // Assert
        assertNotNull(newPlan);
        assertEquals("Novo Plano", newPlan.getNome());
        assertEquals(new BigDecimal("49.90"), newPlan.getPreco());
        assertEquals(2, newPlan.getMaxProdutosPorPeriodo());
        assertNotNull(newPlan.getPackages());
        assertTrue(newPlan.getPackages().isEmpty());
    }

    // Classes auxiliares para teste
    @Entity
    static class TestPlan extends BasePlan<TestPackage> {
        @OneToMany(mappedBy = "plan")
        private java.util.List<TestPackage> packages = new ArrayList<>();

        @Override
        public java.util.List<TestPackage> getPackages() {
            return packages;
        }

        @Override
        public void setPackages(java.util.List<TestPackage> packages) {
            this.packages = packages;
        }
    }

    @Entity
    static class TestPackage extends BasePackage<TestPlan, TestPackageItem> {
        @ManyToOne
        private TestPlan plan;

        @OneToMany
        private java.util.List<TestPackageItem> items = new ArrayList<>();

        @Override
        public TestPlan getPlan() {
            return plan;
        }

        @Override
        public void setPlan(TestPlan plan) {
            this.plan = plan;
        }

        @Override
        public java.util.List<TestPackageItem> getItems() {
            return items;
        }

        @Override
        public void setItems(java.util.List<TestPackageItem> items) {
            this.items = items;
        }
    }

    @Entity
    static class TestPackageItem {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }
    }

    static class TestPlanService extends BasePlanService<TestPlan, JpaRepository<TestPlan, Long>> {
        private final JpaRepository<TestPlan, Long> repository;

        public TestPlanService(JpaRepository<TestPlan, Long> repository) {
            this.repository = repository;
        }

        @Override
        protected JpaRepository<TestPlan, Long> getRepository() {
            return repository;
        }

        @Override
        protected RuntimeException createNotFoundException(Long id) {
            return new RuntimeException("Plan not found with id: " + id);
        }

        @Override
        public Optional<TestPlan> findByName(String name) {
            return Optional.empty();
        }

        @Override
        public boolean existsByName(String name) {
            return false;
        }
    }
}
