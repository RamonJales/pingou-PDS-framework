# Pingou Subscription Framework

## 📋 Visão Geral

O **Pingou Subscription Framework** é um framework genérico e reutilizável para criação de sistemas de assinatura de produtos. Originalmente desenvolvido para assinaturas de cachaça, o framework foi projetado para ser facilmente adaptado a qualquer tipo de produto (vinhos, cafés, livros, cosméticos, etc.).

## 🎯 Características Principais

- **Arquitetura Genérica**: Classes base abstratas que podem ser estendidas para qualquer domínio
- **Gerenciamento de Assinaturas**: Sistema completo de ciclo de vida (ativação, suspensão, cancelamento)
- **Gestão de Planos**: Suporte a múltiplos planos com diferentes características
- **Pacotes Personalizáveis**: Sistema de pacotes periódicos com produtos configuráveis
- **Histórico de Entregas**: Rastreamento completo de envios aos assinantes
- **API REST**: Endpoints padronizados com Spring Boot
- **Validações Integradas**: Validações de negócio embutidas no framework

## 🏗️ Arquitetura do Framework

### Estrutura de Pacotes

```
com.pds.pingou.framework.core/
├── entity/              # Entidades base do framework
│   ├── BaseProduct.java
│   ├── BasePlan.java
│   ├── BasePackage.java
│   ├── BasePackageItem.java
│   └── BaseSubscription.java
├── service/             # Serviços base do framework
│   ├── BaseCrudService.java
│   ├── BaseProductService.java
│   ├── BasePlanService.java
│   ├── BasePackageService.java
│   └── BaseSubscriptionService.java
├── controller/          # Controllers base do framework
│   └── BaseRestController.java
└── enums/              # Enumerações do framework
    └── SubscriptionStatus.java
```

## 🚀 Como Usar o Framework

### 1. Criando um Novo Produto

Para criar um novo tipo de produto, estenda a classe `BaseProduct`:

```java
@Entity
@Table(name = "vinhos")
public class Vinho extends BaseProduct {
    
    @Column(nullable = false)
    private String regiao;
    
    @Column(nullable = false)
    private String uva;
    
    @Column(nullable = false)
    private Integer anoSafra;
    
    @Override
    public String getShortDescription() {
        return String.format("%s - %s %d", getNome(), uva, anoSafra);
    }
    
    @Override
    public String getCategory() {
        return "VINHO";
    }
}
```

### 2. Criando um Plano de Assinatura

Estenda `BasePlan` para criar seus planos:

```java
@Entity
@Table(name = "planos_vinho")
public class PlanoVinho extends BasePlan<PacoteVinho> {
    
    @OneToMany(mappedBy = "plano", cascade = CascadeType.ALL)
    private List<PacoteVinho> pacotes = new ArrayList<>();
    
    @Override
    public List<PacoteVinho> getPackages() {
        return pacotes;
    }
    
    @Override
    public void setPackages(List<PacoteVinho> packages) {
        this.pacotes = packages;
    }
}
```

### 3. Criando Pacotes de Produtos

Estenda `BasePackage` para criar pacotes personalizados:

```java
@Entity
@Table(name = "pacotes_vinho")
public class PacoteVinho extends BasePackage<PlanoVinho, ItemPacoteVinho> {
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plano_id", nullable = false)
    private PlanoVinho plano;
    
    @OneToMany(mappedBy = "pacote", cascade = CascadeType.ALL)
    private List<ItemPacoteVinho> itens = new ArrayList<>();
    
    @Override
    public PlanoVinho getPlan() {
        return plano;
    }
    
    @Override
    public void setPlan(PlanoVinho plan) {
        this.plano = plan;
    }
    
    @Override
    public List<ItemPacoteVinho> getItems() {
        return itens;
    }
    
    @Override
    public void setItems(List<ItemPacoteVinho> items) {
        this.itens = items;
    }
}
```

### 4. Criando Itens de Pacote

Estenda `BasePackageItem`:

```java
@Entity
@Table(name = "item_pacote_vinho")
public class ItemPacoteVinho extends BasePackageItem<PacoteVinho, Vinho> {
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pacote_id", nullable = false)
    private PacoteVinho pacote;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vinho_id", nullable = false)
    private Vinho vinho;
    
    @Override
    public PacoteVinho getPackage() {
        return pacote;
    }
    
    @Override
    public void setPackage(PacoteVinho pkg) {
        this.pacote = pkg;
    }
    
    @Override
    public Vinho getProduct() {
        return vinho;
    }
    
    @Override
    public void setProduct(Vinho product) {
        this.vinho = product;
    }
}
```

### 5. Criando Assinaturas

Estenda `BaseSubscription`:

```java
@Entity
@Table(name = "assinaturas_vinho")
public class AssinaturaVinho extends BaseSubscription<User, PlanoVinho> {
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plano_id", nullable = false)
    private PlanoVinho plano;
    
    @Override
    public User getUser() {
        return user;
    }
    
    @Override
    public void setUser(User user) {
        this.user = user;
    }
    
    @Override
    public PlanoVinho getPlan() {
        return plano;
    }
    
    @Override
    public void setPlan(PlanoVinho plan) {
        this.plano = plan;
    }
}
```

### 6. Criando Serviços

Estenda os serviços base:

```java
@Service
public class VinhoService extends BaseProductService<Vinho, VinhoRepository> {
    
    @Autowired
    private VinhoRepository repository;
    
    @Override
    protected VinhoRepository getRepository() {
        return repository;
    }
    
    @Override
    protected RuntimeException createNotFoundException(Long id) {
        return new VinhoNotFoundException(id);
    }
}

@Service
public class PlanoVinhoService extends BasePlanService<PlanoVinho, PlanoVinhoRepository> {
    
    @Autowired
    private PlanoVinhoRepository repository;
    
    @Override
    protected PlanoVinhoRepository getRepository() {
        return repository;
    }
    
    @Override
    protected RuntimeException createNotFoundException(Long id) {
        return new PlanoNotFoundException(id);
    }
    
    @Override
    public PlanoVinho findByName(String nome) {
        return repository.findByNome(nome)
            .orElseThrow(() -> new PlanoNotFoundException(nome));
    }
    
    @Override
    public boolean existsByName(String nome) {
        return repository.existsByNome(nome);
    }
}
```

### 7. Criando Controllers

Estenda `BaseRestController`:

```java
@RestController
@RequestMapping("/api/vinhos")
public class VinhoController extends BaseRestController<Vinho, VinhoResponseDTO, VinhoRequestDTO, VinhoService> {
    
    @Autowired
    private VinhoService service;
    
    @Override
    protected VinhoService getService() {
        return service;
    }
    
    @Override
    protected List<VinhoResponseDTO> findAll() {
        return service.findAll().stream()
            .map(VinhoMapper::toDTO)
            .collect(Collectors.toList());
    }
    
    @Override
    protected VinhoResponseDTO findEntityById(Long id) {
        Vinho vinho = service.findByIdOrThrow(id);
        return VinhoMapper.toDTO(vinho);
    }
    
    @Override
    protected VinhoResponseDTO createEntity(VinhoRequestDTO request) {
        Vinho vinho = VinhoMapper.toEntity(request);
        return VinhoMapper.toDTO(service.save(vinho));
    }
    
    @Override
    protected VinhoResponseDTO updateEntity(Long id, VinhoRequestDTO request) {
        Vinho vinho = service.findByIdOrThrow(id);
        VinhoMapper.updateEntity(vinho, request);
        return VinhoMapper.toDTO(service.update(vinho));
    }
    
    @Override
    protected void deleteEntity(Long id) {
        service.deleteById(id);
    }
}
```

## 📦 Funcionalidades Prontas

### Gestão de Status de Assinatura

O framework fornece métodos prontos para gerenciar o ciclo de vida das assinaturas:

```java
// Ativar uma assinatura
subscription.activate();

// Desativar uma assinatura
subscription.deactivate();

// Suspender temporariamente
subscription.suspend();

// Cancelar definitivamente
subscription.cancel();

// Verificar se está ativa
boolean isActive = subscription.isActive();
```

### Validações Automáticas

Todas as classes base incluem validações:

- Produtos devem ter nome e preço válidos
- Planos devem ter nome único e configurações válidas
- Pacotes devem ter data de entrega e estar vinculados a um plano
- Assinaturas devem ter usuário e plano válidos

### Operações CRUD Simplificadas

Os serviços base fornecem:

- `findAll()` - Lista todas as entidades
- `findById(id)` - Busca por ID
- `save(entity)` - Cria ou atualiza
- `deleteById(id)` - Remove por ID
- `existsById(id)` - Verifica existência

## 🔧 Configuração

### Dependências Necessárias

```xml
<dependencies>
    <!-- Spring Boot Starter Data JPA -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    
    <!-- Spring Boot Starter Web -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    
    <!-- Lombok -->
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>
</dependencies>
```

## 📚 Exemplo Completo: Sistema de Assinatura de Cachaça

O projeto atual (Pingou) é uma implementação completa do framework para assinatura de cachaças. Você pode usar como referência:

- **Produto**: `Cachaca` estende `BaseProduct`
- **Plano**: `Plano` estende `BasePlan<Pacote>`
- **Pacote**: `Pacote` estende `BasePackage<Plano, ItemPacote>`
- **Item**: `ItemPacote` estende `BasePackageItem<Pacote, Produto>`
- **Assinatura**: `Assinatura` estende `BaseSubscription<User, Plano>`

## 🎨 Benefícios do Framework

1. **Reutilização de Código**: 70-80% do código é reutilizado
2. **Consistência**: Padrões uniformes em todo o sistema
3. **Manutenibilidade**: Correções e melhorias no framework beneficiam todas as implementações
4. **Rapidez no Desenvolvimento**: Novos sistemas em dias, não semanas
5. **Validações Prontas**: Regras de negócio já implementadas
6. **Extensibilidade**: Fácil adicionar funcionalidades específicas

## 🔐 Segurança

O framework integra-se com Spring Security. As implementações específicas devem configurar:

- Autenticação de usuários
- Autorização de endpoints
- Proteção CSRF
- JWT ou sessões

## 📊 Testes

O framework foi projetado para ser facilmente testável:

```java
@SpringBootTest
public class VinhoServiceTest {
    
    @Autowired
    private VinhoService service;
    
    @Test
    public void deveValidarPrecoPositivo() {
        Vinho vinho = new Vinho();
        vinho.setNome("Vinho Teste");
        vinho.setPreco(BigDecimal.valueOf(-10));
        
        assertThrows(IllegalArgumentException.class, 
            () -> service.save(vinho));
    }
}
```

**Versão**: 1.0  
**Última Atualização**: Novembro 2025  
**Autores**: Pingou Framework Team
