package com.pds.pingou.framework.core.service;

import com.pds.pingou.framework.core.entity.BasePlan;
import com.pds.pingou.framework.core.entity.BaseSubscription;
import com.pds.pingou.framework.core.enums.SubscriptionStatus;
import com.pds.pingou.security.user.User;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

/**
 * Testes para BaseSubscriptionService - serviço base de assinaturas do framework.
 */
@ExtendWith(MockitoExtension.class)
class BaseSubscriptionServiceTest {

    @Mock
    private JpaRepository<TestSubscription, Long> repository;

    private TestSubscriptionService service;
    private TestSubscription subscription;
    private User user;
    private TestPlan plan;

    @BeforeEach
    void setUp() {
        service = new TestSubscriptionService(repository);
        
        user = new User();
        user.setId(1L);
        user.setEmail("user@test.com");
        user.setNome("Test User");

        plan = new TestPlan();
        plan.setId(1L);
        plan.setNome("Plano Premium");

        subscription = new TestSubscription();
        subscription.setId(1L);
        subscription.setUser(user);
        subscription.setPlan(plan);
        subscription.setStatus(SubscriptionStatus.ATIVA);
        subscription.setDataInicio(LocalDate.now().minusMonths(1));
        subscription.setDataExpiracao(LocalDate.now().plusMonths(11));
    }

    @Test
    @DisplayName("Deve ativar assinatura com sucesso")
    void deveAtivarAssinaturaComSucesso() {
        // Arrange
        subscription.setStatus(SubscriptionStatus.INATIVA);
        when(repository.findById(anyLong())).thenReturn(Optional.of(subscription));
        when(repository.save(any(TestSubscription.class))).thenAnswer(i -> i.getArgument(0));

        // Act
        TestSubscription result = service.activateSubscription(1L);

        // Assert
        assertNotNull(result);
        assertEquals(SubscriptionStatus.ATIVA, result.getStatus());
        assertTrue(result.isActive());
        verify(repository).findById(1L);
        verify(repository).save(subscription);
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar ativar assinatura inexistente")
    void deveLancarExcecaoAoTentarAtivarAssinaturaInexistente() {
        // Arrange
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> service.activateSubscription(999L));
        verify(repository).findById(999L);
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Deve desativar assinatura com sucesso")
    void deveDesativarAssinaturaComSucesso() {
        // Arrange
        subscription.setStatus(SubscriptionStatus.ATIVA);
        when(repository.findById(anyLong())).thenReturn(Optional.of(subscription));
        when(repository.save(any(TestSubscription.class))).thenAnswer(i -> i.getArgument(0));

        // Act
        TestSubscription result = service.deactivateSubscription(1L);

        // Assert
        assertNotNull(result);
        assertEquals(SubscriptionStatus.INATIVA, result.getStatus());
        assertFalse(result.isActive());
        verify(repository).findById(1L);
        verify(repository).save(subscription);
    }

    @Test
    @DisplayName("Deve suspender assinatura com sucesso")
    void deveSuspenderAssinaturaComSucesso() {
        // Arrange
        subscription.setStatus(SubscriptionStatus.ATIVA);
        when(repository.findById(anyLong())).thenReturn(Optional.of(subscription));
        when(repository.save(any(TestSubscription.class))).thenAnswer(i -> i.getArgument(0));

        // Act
        TestSubscription result = service.suspendSubscription(1L);

        // Assert
        assertNotNull(result);
        assertEquals(SubscriptionStatus.SUSPENSA, result.getStatus());
        assertFalse(result.isActive());
        verify(repository).findById(1L);
        verify(repository).save(subscription);
    }

    @Test
    @DisplayName("Deve cancelar assinatura com sucesso")
    void deveCancelarAssinaturaComSucesso() {
        // Arrange
        subscription.setStatus(SubscriptionStatus.ATIVA);
        when(repository.findById(anyLong())).thenReturn(Optional.of(subscription));
        when(repository.save(any(TestSubscription.class))).thenAnswer(i -> i.getArgument(0));

        // Act
        TestSubscription result = service.cancelSubscription(1L);

        // Assert
        assertNotNull(result);
        assertEquals(SubscriptionStatus.CANCELADA, result.getStatus());
        assertFalse(result.isActive());
        verify(repository).findById(1L);
        verify(repository).save(subscription);
    }

    @Test
    @DisplayName("Deve buscar assinaturas ativas")
    void deveBuscarAssinaturasAtivas() {
        // Arrange
        TestSubscription subscription2 = new TestSubscription();
        subscription2.setId(2L);
        subscription2.setStatus(SubscriptionStatus.INATIVA);

        TestSubscription subscription3 = new TestSubscription();
        subscription3.setId(3L);
        subscription3.setStatus(SubscriptionStatus.ATIVA);

        when(repository.findAll()).thenReturn(Arrays.asList(subscription, subscription2, subscription3));

        // Act
        List<TestSubscription> result = service.findActiveSubscriptions();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(BaseSubscription::isActive));
        assertTrue(result.contains(subscription));
        assertTrue(result.contains(subscription3));
        assertFalse(result.contains(subscription2));
        verify(repository).findAll();
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não há assinaturas ativas")
    void deveRetornarListaVaziaQuandoNaoHaAssinaturasAtivas() {
        // Arrange
        subscription.setStatus(SubscriptionStatus.INATIVA);
        when(repository.findAll()).thenReturn(Arrays.asList(subscription));

        // Act
        List<TestSubscription> result = service.findActiveSubscriptions();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(repository).findAll();
    }

    @Test
    @DisplayName("Deve buscar assinaturas por status")
    void deveBuscarAssinaturasPorStatus() {
        // Arrange
        TestSubscription subscription2 = new TestSubscription();
        subscription2.setId(2L);
        subscription2.setStatus(SubscriptionStatus.SUSPENSA);

        TestSubscription subscription3 = new TestSubscription();
        subscription3.setId(3L);
        subscription3.setStatus(SubscriptionStatus.ATIVA);

        when(repository.findAll()).thenReturn(Arrays.asList(subscription, subscription2, subscription3));

        // Act
        List<TestSubscription> result = service.findByStatus(SubscriptionStatus.ATIVA);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(s -> s.getStatus() == SubscriptionStatus.ATIVA));
        verify(repository).findAll();
    }

    @Test
    @DisplayName("Deve verificar se assinatura está ativa")
    void deveVerificarSeAssinaturaEstaAtiva() {
        // Arrange
        subscription.setStatus(SubscriptionStatus.ATIVA);

        // Act & Assert
        assertTrue(subscription.isActive());
        
        // Arrange
        subscription.setStatus(SubscriptionStatus.INATIVA);

        // Act & Assert
        assertFalse(subscription.isActive());
    }

    @Test
    @DisplayName("Deve verificar diferentes status de assinatura")
    void deveVerificarDiferentesStatusDeAssinatura() {
        // ATIVA
        subscription.setStatus(SubscriptionStatus.ATIVA);
        assertTrue(subscription.isActive());

        // INATIVA
        subscription.setStatus(SubscriptionStatus.INATIVA);
        assertFalse(subscription.isActive());

        // SUSPENSA
        subscription.setStatus(SubscriptionStatus.SUSPENSA);
        assertFalse(subscription.isActive());

        // CANCELADA
        subscription.setStatus(SubscriptionStatus.CANCELADA);
        assertFalse(subscription.isActive());

        // PENDENTE
        subscription.setStatus(SubscriptionStatus.PENDENTE);
        assertFalse(subscription.isActive());
    }

    @Test
    @DisplayName("Deve gerenciar ciclo de vida completo da assinatura")
    void deveGerenciarCicloDeVidaCompletoDaAssinatura() {
        // Arrange
        when(repository.findById(anyLong())).thenReturn(Optional.of(subscription));
        when(repository.save(any(TestSubscription.class))).thenAnswer(i -> i.getArgument(0));

        // Pendente -> Ativa
        subscription.setStatus(SubscriptionStatus.PENDENTE);
        TestSubscription activated = service.activateSubscription(1L);
        assertEquals(SubscriptionStatus.ATIVA, activated.getStatus());

        // Ativa -> Suspensa
        TestSubscription suspended = service.suspendSubscription(1L);
        assertEquals(SubscriptionStatus.SUSPENSA, suspended.getStatus());

        // Suspensa -> Ativa (reativação)
        TestSubscription reactivated = service.activateSubscription(1L);
        assertEquals(SubscriptionStatus.ATIVA, reactivated.getStatus());

        // Ativa -> Cancelada
        TestSubscription cancelled = service.cancelSubscription(1L);
        assertEquals(SubscriptionStatus.CANCELADA, cancelled.getStatus());

        // Verificar chamadas
        verify(repository, times(4)).findById(1L);
        verify(repository, times(4)).save(subscription);
    }

    @Test
    @DisplayName("Deve validar datas de início e expiração")
    void deveValidarDatasDeInicioEExpiracao() {
        // Arrange
        LocalDate dataInicio = LocalDate.now();
        LocalDate dataExpiracao = LocalDate.now().plusYears(1);

        subscription.setDataInicio(dataInicio);
        subscription.setDataExpiracao(dataExpiracao);

        // Assert
        assertEquals(dataInicio, subscription.getDataInicio());
        assertEquals(dataExpiracao, subscription.getDataExpiracao());
        assertTrue(subscription.getDataExpiracao().isAfter(subscription.getDataInicio()));
    }

    // Classes auxiliares para teste
    @Entity
    static class TestSubscription extends BaseSubscription<User, TestPlan> {
        @ManyToOne
        private User user;

        @ManyToOne
        private TestPlan plan;

        @Override
        public User getUser() {
            return user;
        }

        @Override
        public void setUser(User user) {
            this.user = user;
        }

        @Override
        public TestPlan getPlan() {
            return plan;
        }

        @Override
        public void setPlan(TestPlan plan) {
            this.plan = plan;
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
    static class TestPackage {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne
        private TestPlan plan;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }
    }

    static class TestSubscriptionService extends BaseSubscriptionService<TestSubscription, User, TestPlan, JpaRepository<TestSubscription, Long>> {
        private final JpaRepository<TestSubscription, Long> repository;

        public TestSubscriptionService(JpaRepository<TestSubscription, Long> repository) {
            this.repository = repository;
        }

        @Override
        protected JpaRepository<TestSubscription, Long> getRepository() {
            return repository;
        }

        @Override
        protected RuntimeException createNotFoundException(Long id) {
            return new RuntimeException("Subscription not found with id: " + id);
        }

        @Override
        public boolean hasActiveSubscription(User user) {
            return false;
        }

        @Override
        public List<TestSubscription> findByUser(User user) {
            return new ArrayList<>();
        }
    }
}
