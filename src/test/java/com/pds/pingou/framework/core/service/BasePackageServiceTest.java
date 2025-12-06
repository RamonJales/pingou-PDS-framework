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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Testes para BasePackageService - serviço base de pacotes do framework.
 */
@ExtendWith(MockitoExtension.class)
class BasePackageServiceTest {

    @Mock
    private JpaRepository<TestPackage, Long> repository;

    private TestPackageService service;
    private TestPackage package1;
    private TestPackage package2;
    private TestPackage package3;
    private TestPlan plan;

    @BeforeEach
    void setUp() {
        service = new TestPackageService(repository);
        
        plan = new TestPlan();
        plan.setId(1L);
        plan.setNome("Plano Teste");

        package1 = new TestPackage();
        package1.setId(1L);
        package1.setNome("Pacote Janeiro");
        package1.setPlan(plan);
        package1.setPeriodo("Janeiro");
        package1.setAno(2025);
        package1.setDataEntrega(LocalDate.of(2025, 1, 15));

        package2 = new TestPackage();
        package2.setId(2L);
        package2.setNome("Pacote Fevereiro");
        package2.setPlan(plan);
        package2.setPeriodo("Fevereiro");
        package2.setAno(2025);
        package2.setDataEntrega(LocalDate.of(2025, 2, 15));

        package3 = new TestPackage();
        package3.setId(3L);
        package3.setNome("Pacote Março");
        package3.setPlan(plan);
        package3.setPeriodo("Março");
        package3.setAno(2025);
        package3.setDataEntrega(LocalDate.now());
    }

    @Test
    @DisplayName("Deve buscar pacotes por plano")
    void deveBuscarPacotesPorPlano() {
        // Arrange
        List<TestPackage> packages = Arrays.asList(package1, package2);
        when(repository.findAll()).thenReturn(packages);

        // Act
        List<TestPackage> result = service.findByPlan(plan);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(p -> p.getPlan().equals(plan)));
        verify(repository).findAll();
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando plano não tem pacotes")
    void deveRetornarListaVaziaQuandoPlanoNaoTemPacotes() {
        // Arrange
        TestPlan planSemPacotes = new TestPlan();
        planSemPacotes.setId(999L);
        when(repository.findAll()).thenReturn(Arrays.asList(package1, package2));

        // Act
        List<TestPackage> result = service.findByPlan(planSemPacotes);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(repository).findAll();
    }

    @Test
    @DisplayName("Deve buscar pacotes por período e ano")
    void deveBuscarPacotesPorPeriodoEAno() {
        // Arrange
        when(repository.findAll()).thenReturn(Arrays.asList(package1, package2, package3));

        // Act
        List<TestPackage> result = service.findByPeriodAndYear("Janeiro", 2025);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Pacote Janeiro", result.get(0).getNome());
        assertEquals("Janeiro", result.get(0).getPeriodo());
        assertEquals(2025, result.get(0).getAno());
        verify(repository).findAll();
    }

    @Test
    @DisplayName("Deve buscar pacotes por intervalo de datas")
    void deveBuscarPacotesPorIntervaloDeDatas() {
        // Arrange
        LocalDate startDate = LocalDate.of(2025, 1, 1);
        LocalDate endDate = LocalDate.of(2025, 2, 28);
        when(repository.findAll()).thenReturn(Arrays.asList(package1, package2, package3));

        // Act
        List<TestPackage> result = service.findByDeliveryDateRange(startDate, endDate);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(package1));
        assertTrue(result.contains(package2));
        assertFalse(result.contains(package3));
        verify(repository).findAll();
    }

    @Test
    @DisplayName("Deve buscar pacotes para entrega hoje")
    void deveBuscarPacotesParaEntregaHoje() {
        // Arrange
        package3.setDataEntrega(LocalDate.now());
        when(repository.findAll()).thenReturn(Arrays.asList(package1, package2, package3));

        // Act
        List<TestPackage> result = service.findPackagesForDeliveryToday();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Pacote Março", result.get(0).getNome());
        assertTrue(result.get(0).isDeliveryDateToday());
        verify(repository).findAll();
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não há entregas para hoje")
    void deveRetornarListaVaziaQuandoNaoHaEntregasParaHoje() {
        // Arrange
        when(repository.findAll()).thenReturn(Arrays.asList(package1, package2));

        // Act
        List<TestPackage> result = service.findPackagesForDeliveryToday();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(repository).findAll();
    }

    @Test
    @DisplayName("Deve buscar pacotes atrasados")
    void deveBuscarPacotesAtrasados() {
        // Arrange
        package1.setDataEntrega(LocalDate.now().minusDays(5));
        package2.setDataEntrega(LocalDate.now().minusDays(10));
        package3.setDataEntrega(LocalDate.now().plusDays(5));
        when(repository.findAll()).thenReturn(Arrays.asList(package1, package2, package3));

        // Act
        List<TestPackage> result = service.findOverduePackages();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(package1));
        assertTrue(result.contains(package2));
        assertFalse(result.contains(package3));
        assertTrue(result.stream().allMatch(BasePackage::isOverdue));
        verify(repository).findAll();
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não há pacotes atrasados")
    void deveRetornarListaVaziaQuandoNaoHaPacotesAtrasados() {
        // Arrange
        package1.setDataEntrega(LocalDate.now().plusDays(1));
        package2.setDataEntrega(LocalDate.now().plusDays(5));
        when(repository.findAll()).thenReturn(Arrays.asList(package1, package2));

        // Act
        List<TestPackage> result = service.findOverduePackages();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(repository).findAll();
    }

    @Test
    @DisplayName("Deve verificar se data de entrega é hoje")
    void deveVerificarSeDataDeEntregaEHoje() {
        // Arrange
        package3.setDataEntrega(LocalDate.now());

        // Act & Assert
        assertTrue(package3.isDeliveryDateToday());
        assertFalse(package1.isDeliveryDateToday());
        assertFalse(package2.isDeliveryDateToday());
    }

    @Test
    @DisplayName("Deve verificar se pacote está atrasado")
    void deveVerificarSePacoteEstaAtrasado() {
        // Arrange
        package1.setDataEntrega(LocalDate.now().minusDays(1));
        package2.setDataEntrega(LocalDate.now());
        package3.setDataEntrega(LocalDate.now().plusDays(1));

        // Act & Assert
        assertTrue(package1.isOverdue());
        assertFalse(package2.isOverdue());
        assertFalse(package3.isOverdue());
    }

    // Classes auxiliares para teste
    @Entity
    static class TestPackage extends BasePackage<TestPlan, TestPackageItem> {
        @ManyToOne
        private TestPlan plan;

        @OneToMany
        private List<TestPackageItem> items = new ArrayList<>();

        @Override
        public TestPlan getPlan() {
            return plan;
        }

        @Override
        public void setPlan(TestPlan plan) {
            this.plan = plan;
        }

        @Override
        public List<TestPackageItem> getItems() {
            return items;
        }

        @Override
        public void setItems(List<TestPackageItem> items) {
            this.items = items;
        }
    }

    @Entity
    static class TestPlan extends BasePlan<TestPackage> {
        @OneToMany(mappedBy = "plan")
        private List<TestPackage> packages = new ArrayList<>();

        @Override
        public List<TestPackage> getPackages() {
            return packages;
        }

        @Override
        public void setPackages(List<TestPackage> packages) {
            this.packages = packages;
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

    static class TestPackageService extends BasePackageService<TestPackage, TestPlan, JpaRepository<TestPackage, Long>> {
        private final JpaRepository<TestPackage, Long> repository;

        public TestPackageService(JpaRepository<TestPackage, Long> repository) {
            this.repository = repository;
        }

        @Override
        protected JpaRepository<TestPackage, Long> getRepository() {
            return repository;
        }

        @Override
        protected RuntimeException createNotFoundException(Long id) {
            return new RuntimeException("Package not found with id: " + id);
        }
    }
}
