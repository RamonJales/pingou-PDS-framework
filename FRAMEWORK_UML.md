# Diagrama UML do Framework de Assinaturas Pingou

## Visão Geral da Arquitetura

```
┌─────────────────────────────────────────────────────────────────┐
│                    PINGOU FRAMEWORK CORE                        │
│                  (Camada Genérica Reutilizável)                 │
└─────────────────────────────────────────────────────────────────┘
                              │
                              │ extends/implements
                              ▼
┌─────────────────────────────────────────────────────────────────┐
│                   IMPLEMENTAÇÃO ESPECÍFICA                       │
│              (Cachaça, Vinho, Café, Livros, etc.)               │
└─────────────────────────────────────────────────────────────────┘
```

## Diagrama de Classes - Camada de Entidades

```
┌─────────────────────────────────────┐
│       <<abstract>>                  │
│       BaseProduct                   │
├─────────────────────────────────────┤
│ - id: Long                          │
│ - nome: String                      │
│ - descricao: String                 │
│ - preco: BigDecimal                 │
│ - urlImagem: String                 │
│ - ativo: Boolean                    │
├─────────────────────────────────────┤
│ + getShortDescription(): String     │
│ + getCategory(): String             │
│ + isAvailable(): boolean            │
│ + activate(): void                  │
│ + deactivate(): void                │
└─────────────────────────────────────┘
                  △
                  │ extends
                  │
        ┌─────────┴──────────┐
        │                    │
┌───────────────┐    ┌───────────────┐
│    Produto    │    │     Vinho     │
│  <<abstract>> │    │   (exemplo)   │
└───────────────┘    └───────────────┘
        △
        │ extends
        │
┌───────────────┐
│    Cachaca    │
│ (específico)  │
└───────────────┘


┌─────────────────────────────────────┐
│       <<abstract>>                  │
│       BasePlan<PKG>                 │
├─────────────────────────────────────┤
│ - id: Long                          │
│ - nome: String                      │
│ - descricao: String                 │
│ - preco: BigDecimal                 │
│ - maxProdutosPorPeriodo: Integer    │
│ - frequenciaEntrega: String         │
│ - ativo: Boolean                    │
├─────────────────────────────────────┤
│ + getPackages(): List<PKG>          │
│ + setPackages(List<PKG>): void      │
│ + addPackage(PKG): void             │
│ + removePackage(PKG): void          │
│ + isAvailable(): boolean            │
│ + activate(): void                  │
│ + deactivate(): void                │
└─────────────────────────────────────┘
                  △
                  │ extends
                  │
        ┌─────────┴──────────┐
        │                    │
┌───────────────┐    ┌───────────────┐
│     Plano     │    │  PlanoVinho   │
│              │    │   (exemplo)   │
└───────────────┘    └───────────────┘


┌─────────────────────────────────────┐
│       <<abstract>>                  │
│    BasePackage<PLN, ITM>            │
├─────────────────────────────────────┤
│ - id: Long                          │
│ - nome: String                      │
│ - descricao: String                 │
│ - dataEntrega: LocalDate            │
│ - periodo: Integer                  │
│ - ano: Integer                      │
│ - ativo: Boolean                    │
├─────────────────────────────────────┤
│ + getPlan(): PLN                    │
│ + setPlan(PLN): void                │
│ + getItems(): List<ITM>             │
│ + setItems(List<ITM>): void         │
│ + addItem(ITM): void                │
│ + removeItem(ITM): void             │
│ + isAvailable(): boolean            │
│ + isOverdue(): boolean              │
│ + isDeliveryDateToday(): boolean    │
└─────────────────────────────────────┘
                  △
                  │ extends
                  │
        ┌─────────┴──────────┐
        │                    │
┌───────────────┐    ┌────────────────┐
│    Pacote     │    │  PacoteVinho   │
│              │    │   (exemplo)    │
└───────────────┘    └────────────────┘


┌─────────────────────────────────────┐
│       <<abstract>>                  │
│   BasePackageItem<PKG, PRD>         │
├─────────────────────────────────────┤
│ - id: Long                          │
│ - quantidade: Integer               │
│ - observacoes: String               │
├─────────────────────────────────────┤
│ + getPackage(): PKG                 │
│ + setPackage(PKG): void             │
│ + getProduct(): PRD                 │
│ + setProduct(PRD): void             │
│ + incrementQuantity(): void         │
│ + decrementQuantity(): void         │
│ + isValidQuantity(): boolean        │
└─────────────────────────────────────┘
                  △
                  │ extends
                  │
        ┌─────────┴──────────┐
        │                    │
┌───────────────┐    ┌─────────────────┐
│  ItemPacote   │    │ ItemPacoteVinho │
│              │    │    (exemplo)    │
└───────────────┘    └─────────────────┘


┌─────────────────────────────────────┐
│       <<abstract>>                  │
│   BaseSubscription<U, P>            │
├─────────────────────────────────────┤
│ - id: Long                          │
│ - status: SubscriptionStatus        │
│ - dataInicio: LocalDate             │
│ - dataExpiracao: LocalDate          │
├─────────────────────────────────────┤
│ + getUser(): U                      │
│ + setUser(U): void                  │
│ + getPlan(): P                      │
│ + setPlan(P): void                  │
│ + isActive(): boolean               │
│ + activate(): void                  │
│ + deactivate(): void                │
│ + suspend(): void                   │
│ + cancel(): void                    │
└─────────────────────────────────────┘
                  △
                  │ extends
                  │
        ┌─────────┴──────────┐
        │                    │
┌───────────────┐    ┌──────────────────┐
│  Assinatura   │    │ AssinaturaVinho  │
│              │    │    (exemplo)     │
└───────────────┘    └──────────────────┘
```

## Diagrama de Classes - Camada de Serviços

```
┌─────────────────────────────────────────┐
│       <<abstract>>                      │
│   BaseCrudService<T, ID, R>             │
├─────────────────────────────────────────┤
│ # getRepository(): R                    │
├─────────────────────────────────────────┤
│ + findAll(): List<T>                    │
│ + findById(ID): Optional<T>             │
│ + findByIdOrThrow(ID): T                │
│ + save(T): T                            │
│ + update(T): T                          │
│ + deleteById(ID): void                  │
│ + delete(T): void                       │
│ + existsById(ID): boolean               │
│ + count(): long                         │
│ # validate(T): void                     │
│ # beforeSave(T): void                   │
│ # afterSave(T): void                    │
│ # beforeDelete(ID): void                │
│ # afterDelete(ID): void                 │
│ # createNotFoundException(ID): Exception│
└─────────────────────────────────────────┘
                     △
          ┌──────────┼──────────┐
          │          │          │
┌─────────────┐ ┌─────────┐ ┌──────────────┐
│BaseProduct  │ │BasePlan │ │BasePackage   │
│Service      │ │Service  │ │Service       │
└─────────────┘ └─────────┘ └──────────────┘
          △          △          △
          │          │          │
   ┌──────┴────┐     │    ┌─────┴──────┐
   │           │     │    │            │
┌────────┐ ┌────────┐ ┌───────┐ ┌──────────┐
│Cachaca │ │Vinho   │ │Plano  │ │Pacote    │
│Service │ │Service │ │Service│ │Service   │
└────────┘ └────────┘ └───────┘ └──────────┘


┌─────────────────────────────────────────┐
│       <<abstract>>                      │
│ BaseSubscriptionService<S,U,P,R>        │
├─────────────────────────────────────────┤
│ + activateSubscription(U,P): S          │
│ + deactivateSubscription(Long): S       │
│ + suspendSubscription(Long): S          │
│ + cancelSubscription(Long): S           │
│ + reactivateSubscription(Long): S       │
│ + findActiveSubscriptions(): List<S>    │
│ + findByStatus(Status): List<S>         │
│ # hasActiveSubscription(U): boolean     │
│ # findByUser(U): S                      │
│ # createSubscription(U,P): S            │
└─────────────────────────────────────────┘
                     △
                     │ extends
                     │
          ┌──────────┴──────────┐
          │                     │
┌─────────────────┐    ┌─────────────────────┐
│ Assinatura      │    │ AssinaturaVinho     │
│ Service         │    │ Service             │
└─────────────────┘    └─────────────────────┘
```

## Diagrama de Classes - Camada de Controllers

```
┌─────────────────────────────────────────┐
│       <<abstract>>                      │
│  BaseRestController<T,DTO,REQ,S>        │
├─────────────────────────────────────────┤
│ # getService(): S                       │
├─────────────────────────────────────────┤
│ + listAll(): ResponseEntity<List<DTO>>  │
│ + findById(Long): ResponseEntity<DTO>   │
│ + create(REQ): ResponseEntity<DTO>      │
│ + update(Long,REQ): ResponseEntity<DTO> │
│ + delete(Long): ResponseEntity<Void>    │
│ # findAll(): List<DTO>                  │
│ # findEntityById(Long): DTO             │
│ # createEntity(REQ): DTO                │
│ # updateEntity(Long,REQ): DTO           │
│ # deleteEntity(Long): void              │
└─────────────────────────────────────────┘
                     △
          ┌──────────┼──────────┐
          │          │          │
┌─────────────┐ ┌─────────┐ ┌──────────┐
│Cachaca      │ │Plano    │ │Pacote    │
│Controller   │ │Controller│ │Controller│
└─────────────┘ └─────────┘ └──────────┘
```

## Relacionamentos Entre Entidades

```
       User                    Plano
         │                       │
         │ 1                   * │
         │                       │
         └────── Assinatura ─────┘
                    │
                    │ *
                    │
                    │ 1
                  Plano
                    │
                    │ 1
                    │
                    │ *
                 Pacote
                    │
                    │ 1
                    │
                    │ *
              ItemPacote
                    │
                    │ *
                    │
                    │ 1
                 Produto
                    │
                    │
              ┌─────┴─────┐
              │           │
           Cachaca      Vinho
                       (exemplo)
```

## Fluxo de Herança Completo

```
Framework Core (Genérico)
         │
         ├── BaseProduct
         │      └── Produto (Abstrato do Pingou)
         │            └── Cachaca (Concreto)
         │
         ├── BasePlan<PKG>
         │      └── Plano (Concreto do Pingou)
         │
         ├── BasePackage<PLN,ITM>
         │      └── Pacote (Concreto do Pingou)
         │
         ├── BasePackageItem<PKG,PRD>
         │      └── ItemPacote (Concreto do Pingou)
         │
         └── BaseSubscription<U,P>
                └── Assinatura (Concreto do Pingou)
```

## Enumerações

```
┌─────────────────────────┐
│  SubscriptionStatus     │
│     <<enumeration>>     │
├─────────────────────────┤
│ ATIVA                   │
│ INATIVA                 │
│ SUSPENSA                │
│ CANCELADA               │
│ PENDENTE                │
└─────────────────────────┘

┌─────────────────────────┐
│  StatusAssinatura       │
│  <<enumeration>> (old)  │
├─────────────────────────┤
│ ATIVA                   │
│ INATIVA                 │
│ CANCELADA               │
│ EXPIRADA                │
└─────────────────────────┘
         │
         │ converts to
         ▼
   SubscriptionStatus
```

## Padrões de Design Aplicados

### 1. Template Method
```
BaseCrudService
    │
    ├── findAll() ────────────┐
    ├── save()                │ Template methods
    ├── update()              │ (implementados)
    └── delete() ─────────────┘
    │
    └── validate() ───────────┐
    └── createNotFoundException() │ Hook methods
                              │ (abstratos)
                              └─ (para subclasses)
```

### 2. Strategy
```
BaseProduct
    │
    └── getCategory() ────────┐
    └── getShortDescription() │ Estratégias específicas
                              │ implementadas por
                              └─ subclasses
```

### 3. Factory
```
BaseCrudService
    │
    └── createNotFoundException(ID)
            │
            └─ Subclasses criam suas próprias exceções
               (CachacaNotFoundException, VinhoNotFoundException, etc.)
```

## Camadas da Aplicação

```
┌─────────────────────────────────────────────┐
│           PRESENTATION LAYER                │
│  Controllers (REST API + Web UI)            │
│  - Validação de entrada                     │
│  - Mapeamento DTO ↔ Entity                  │
└─────────────────────────────────────────────┘
                    │
                    ▼
┌─────────────────────────────────────────────┐
│            SERVICE LAYER                    │
│  Services (Business Logic)                  │
│  - Regras de negócio                        │
│  - Orquestração de operações                │
│  - Transações                               │
└─────────────────────────────────────────────┘
                    │
                    ▼
┌─────────────────────────────────────────────┐
│          PERSISTENCE LAYER                  │
│  Repositories (Data Access)                 │
│  - CRUD operations                          │
│  - Queries customizadas                     │
│  - JPA/Hibernate                            │
└─────────────────────────────────────────────┘
                    │
                    ▼
┌─────────────────────────────────────────────┐
│            DATABASE LAYER                   │
│  PostgreSQL                                 │
│  - Schemas                                  │
│  - Tables                                   │
│  - Constraints                              │
└─────────────────────────────────────────────┘
```

## Diagrama de Sequência - Criar Assinatura

```
Cliente    Controller    Service       Repository    Database
  │           │            │              │             │
  │──POST─────>│            │              │             │
  │           │            │              │             │
  │           │──create────>│              │             │
  │           │            │              │             │
  │           │            │──validate────>│             │
  │           │            │              │             │
  │           │            │──save────────>│             │
  │           │            │              │             │
  │           │            │              │──INSERT─────>│
  │           │            │              │             │
  │           │            │              │<────OK──────│
  │           │            │              │             │
  │           │            │<────entity───│             │
  │           │            │              │             │
  │           │<────DTO────│              │             │
  │           │            │              │             │
  │<──201─────│            │              │             │
```

## Extensibilidade do Framework

### Adicionando Novo Domínio (ex: Café)

```
1. Criar Entidades:
   Cafe extends BaseProduct
   PlanoCafe extends BasePlan<PacoteCafe>
   PacoteCafe extends BasePackage<PlanoCafe, ItemPacoteCafe>
   ItemPacoteCafe extends BasePackageItem<PacoteCafe, Cafe>
   AssinaturaCafe extends BaseSubscription<User, PlanoCafe>

2. Criar Repositórios:
   CafeRepository extends JpaRepository<Cafe, Long>
   PlanoCafeRepository extends JpaRepository<PlanoCafe, Long>
   ...

3. Criar Serviços:
   CafeService extends BaseProductService<Cafe, CafeRepository>
   PlanoCafeService extends BasePlanService<PlanoCafe, PlanoCafeRepository>
   ...

4. Criar Controllers:
   CafeController extends BaseRestController<...>
   PlanoCafeController extends BaseRestController<...>
   ...

5. Configurar Rotas:
   /api/cafes
   /api/planos-cafe
   /api/assinaturas-cafe
   ...
```

## Observações Importantes

1. **Type Safety**: Uso extensivo de Generics para type safety em tempo de compilação
2. **JPA Inheritance**: Uso de @MappedSuperclass para entidades base
3. **Lazy Loading**: Relacionamentos configurados como LAZY por padrão
4. **Cascade Operations**: CascadeType.ALL em relacionamentos pai-filho
5. **Validations**: Bean Validation (@NotNull, @Size, etc.) nas entidades
6. **Transactions**: @Transactional nos métodos de serviço que modificam dados

---

**Versão do Diagrama**: 2.0  
**Data**: 30 de Novembro de 2025  
**Framework**: Pingou Subscription Framework
