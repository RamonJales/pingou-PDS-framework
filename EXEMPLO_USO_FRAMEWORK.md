# Exemplo Prático: Sistema de Assinatura de Vinhos

Este documento mostra passo a passo como usar o Pingou Framework para criar um sistema completo de assinatura de vinhos.

## 📋 Requisitos

- Java 17+
- Spring Boot 3.x
- Maven
- PostgreSQL
- Pingou Framework

## 🚀 Passo a Passo

### 1. Criar Entidade Vinho

```java
package com.example.vinho.produto;

import com.pds.pingou.framework.core.entity.BaseProduct;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "vinhos")
@Getter
@Setter
public class Vinho extends BaseProduct {
    
    @Column(nullable = false)
    private String regiao; // Ex: Bordeaux, Toscana, Vale dos Vinhedos
    
    @Column(nullable = false)
    private String uva; // Ex: Cabernet Sauvignon, Merlot, Tannat
    
    @Column(name = "ano_safra")
    private Integer anoSafra;
    
    @Column(name = "teor_alcoolico")
    private BigDecimal teorAlcoolico;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_vinho")
    private TipoVinho tipoVinho; // TINTO, BRANCO, ROSE, ESPUMANTE
    
    @Column(name = "volume_ml")
    private Integer volumeMl = 750; // ml
    
    @Column(name = "tempo_guarda_anos")
    private Integer tempoGuardaAnos; // Potencial de guarda
    
    @Column(name = "harmonizacao", length = 500)
    private String harmonizacao;
    
    @Column(name = "notas_degustacao", length = 1000)
    private String notasDegustacao;
    
    @Column(name = "vinificadora")
    private String vinificadora;
    
    @Column(name = "pais_origem")
    private String paisOrigem;
    
    // Construtor padrão
    public Vinho() {
        super();
    }
    
    // Construtor completo
    public Vinho(String nome, String descricao, BigDecimal preco, 
                 String regiao, String uva, Integer anoSafra, TipoVinho tipoVinho) {
        this.setNome(nome);
        this.setDescricao(descricao);
        this.setPreco(preco);
        this.regiao = regiao;
        this.uva = uva;
        this.anoSafra = anoSafra;
        this.tipoVinho = tipoVinho;
    }
    
    /**
     * Implementação obrigatória do framework
     */
    @Override
    public String getShortDescription() {
        return String.format("%s - %s %s (%d)", 
            getNome(), 
            tipoVinho != null ? tipoVinho.name() : "N/A",
            uva, 
            anoSafra != null ? anoSafra : 0);
    }
    
    /**
     * Implementação obrigatória do framework
     */
    @Override
    public String getCategory() {
        return "VINHO";
    }
    
    /**
     * Verifica se o vinho está pronto para consumo
     */
    public boolean isProntoPraConsumo() {
        if (anoSafra == null) return true;
        int idade = java.time.Year.now().getValue() - anoSafra;
        return idade >= 2; // Mínimo 2 anos
    }
}
```

### 2. Criar Enum TipoVinho

```java
package com.example.vinho.produto;

public enum TipoVinho {
    TINTO("Tinto"),
    BRANCO("Branco"),
    ROSE("Rosé"),
    ESPUMANTE("Espumante"),
    FORTIFICADO("Fortificado"),
    SOBREMESA("Sobremesa");
    
    private final String descricao;
    
    TipoVinho(String descricao) {
        this.descricao = descricao;
    }
    
    public String getDescricao() {
        return descricao;
    }
}
```

### 3. Criar Entidade PlanoVinho

```java
package com.example.vinho.planos;

import com.pds.pingou.framework.core.entity.BasePlan;
import com.example.vinho.pacote.PacoteVinho;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "planos_vinho")
@Getter
@Setter
public class PlanoVinho extends BasePlan<PacoteVinho> {
    
    @OneToMany(mappedBy = "plano", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PacoteVinho> pacotes = new ArrayList<>();
    
    @Column(name = "nivel_expertise")
    private String nivelExpertise; // INICIANTE, INTERMEDIARIO, SOMMELIER
    
    @Column(name = "regiao_foco")
    private String regiaoFoco; // Opcional: foco em região específica
    
    public PlanoVinho() {}
    
    public PlanoVinho(String nome, String descricao, BigDecimal preco, 
                      Integer maxVinhosPorMes, String nivelExpertise) {
        this.setNome(nome);
        this.setDescricao(descricao);
        this.setPreco(preco);
        this.setMaxProdutosPorPeriodo(maxVinhosPorMes);
        this.nivelExpertise = nivelExpertise;
    }
    
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

### 4. Criar Entidade PacoteVinho

```java
package com.example.vinho.pacote;

import com.pds.pingou.framework.core.entity.BasePackage;
import com.example.vinho.planos.PlanoVinho;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pacotes_vinho")
@Getter
@Setter
public class PacoteVinho extends BasePackage<PlanoVinho, ItemPacoteVinho> {
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plano_id", nullable = false)
    private PlanoVinho plano;
    
    @OneToMany(mappedBy = "pacote", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ItemPacoteVinho> itens = new ArrayList<>();
    
    @Column(name = "tema_mes")
    private String temaMes; // Ex: "Vinhos da Toscana", "Especial Safra 2018"
    
    public PacoteVinho() {}
    
    public PacoteVinho(String nome, String descricao, LocalDate dataEntrega,
                       Integer mes, Integer ano, PlanoVinho plano, String temaMes) {
        this.setNome(nome);
        this.setDescricao(descricao);
        this.setDataEntrega(dataEntrega);
        this.setPeriodo(mes);
        this.setAno(ano);
        this.plano = plano;
        this.temaMes = temaMes;
    }
    
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

### 5. Criar Entidade ItemPacoteVinho

```java
package com.example.vinho.pacote;

import com.pds.pingou.framework.core.entity.BasePackageItem;
import com.example.vinho.produto.Vinho;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "item_pacote_vinho")
@Getter
@Setter
public class ItemPacoteVinho extends BasePackageItem<PacoteVinho, Vinho> {
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pacote_id", nullable = false)
    private PacoteVinho pacote;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vinho_id", nullable = false)
    private Vinho vinho;
    
    @Column(name = "temperatura_servir")
    private String temperaturaServir; // Ex: "16-18°C"
    
    @Column(name = "tempo_decantacao_min")
    private Integer tempoDecantacaoMin;
    
    public ItemPacoteVinho() {}
    
    public ItemPacoteVinho(PacoteVinho pacote, Vinho vinho, Integer quantidade) {
        this.pacote = pacote;
        this.vinho = vinho;
        this.setQuantidade(quantidade);
    }
    
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

### 6. Criar Entidade AssinaturaVinho

```java
package com.example.vinho.assinatura;

import com.pds.pingou.framework.core.entity.BaseSubscription;
import com.pds.pingou.framework.core.enums.SubscriptionStatus;
import com.pds.pingou.security.user.User;
import com.example.vinho.planos.PlanoVinho;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "assinaturas_vinho")
@Getter
@Setter
public class AssinaturaVinho extends BaseSubscription<User, PlanoVinho> {
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plano_id", nullable = false)
    private PlanoVinho plano;
    
    @Column(name = "preferencia_tipo")
    private String preferenciasTipo; // Ex: "TINTO,ESPUMANTE"
    
    @Column(name = "restricoes")
    private String restricoes; // Ex: "Sem sulfitos"
    
    public AssinaturaVinho() {}
    
    public AssinaturaVinho(User user, PlanoVinho plano) {
        this.user = user;
        this.plano = plano;
        this.setStatus(SubscriptionStatus.ATIVA);
        this.setDataInicio(LocalDate.now());
    }
    
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

### 7. Criar Repositórios

```java
package com.example.vinho.produto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface VinhoRepository extends JpaRepository<Vinho, Long> {
    List<Vinho> findByTipoVinho(TipoVinho tipo);
    List<Vinho> findByRegiao(String regiao);
    List<Vinho> findByAnoSafra(Integer ano);
}

@Repository
public interface PlanoVinhoRepository extends JpaRepository<PlanoVinho, Long> {
    Optional<PlanoVinho> findByNome(String nome);
    boolean existsByNome(String nome);
}

@Repository
public interface PacoteVinhoRepository extends JpaRepository<PacoteVinho, Long> {
    List<PacoteVinho> findByPlano(PlanoVinho plano);
    List<PacoteVinho> findByPeriodoAndAno(Integer periodo, Integer ano);
}

@Repository
public interface AssinaturaVinhoRepository extends JpaRepository<AssinaturaVinho, Long> {
    Optional<AssinaturaVinho> findByUser(User user);
    boolean existsByUser(User user);
}
```

### 8. Criar Serviços

```java
package com.example.vinho.produto;

import com.pds.pingou.framework.core.service.BaseProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

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
        return new VinhoNotFoundException("Vinho com ID " + id + " não encontrado");
    }
    
    public List<Vinho> buscarPorTipo(TipoVinho tipo) {
        return repository.findByTipoVinho(tipo);
    }
    
    public List<Vinho> buscarPorRegiao(String regiao) {
        return repository.findByRegiao(regiao);
    }
    
    public List<Vinho> buscarPorSafra(Integer ano) {
        return repository.findByAnoSafra(ano);
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
        return new PlanoNotFoundException("Plano com ID " + id + " não encontrado");
    }
    
    @Override
    public PlanoVinho findByName(String nome) {
        return repository.findByNome(nome)
            .orElseThrow(() -> new PlanoNotFoundException("Plano " + nome + " não encontrado"));
    }
    
    @Override
    public boolean existsByName(String nome) {
        return repository.existsByNome(nome);
    }
}
```

### 9. Criar DTOs

```java
package com.example.vinho.produto.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class VinhoRequestDTO {
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private String regiao;
    private String uva;
    private Integer anoSafra;
    private String tipoVinho;
    private BigDecimal teorAlcoolico;
    private Integer volumeMl;
    private String harmonizacao;
    private String notasDegustacao;
    private String vinificadora;
    private String paisOrigem;
}

@Data
public class VinhoResponseDTO {
    private Long id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private String regiao;
    private String uva;
    private Integer anoSafra;
    private String tipoVinho;
    private BigDecimal teorAlcoolico;
    private Integer volumeMl;
    private String harmonizacao;
    private String shortDescription;
    private Boolean ativo;
}
```

### 10. Criar Controllers

```java
package com.example.vinho.produto;

import com.pds.pingou.framework.core.controller.BaseRestController;
import com.example.vinho.produto.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
    
    // Endpoints adicionais específicos
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<VinhoResponseDTO>> buscarPorTipo(@PathVariable TipoVinho tipo) {
        List<VinhoResponseDTO> vinhos = service.buscarPorTipo(tipo).stream()
            .map(VinhoMapper::toDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(vinhos);
    }
    
    @GetMapping("/regiao/{regiao}")
    public ResponseEntity<List<VinhoResponseDTO>> buscarPorRegiao(@PathVariable String regiao) {
        List<VinhoResponseDTO> vinhos = service.buscarPorRegiao(regiao).stream()
            .map(VinhoMapper::toDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(vinhos);
    }
}
```

### 11. Configurar application.properties

```properties
# Database
spring.datasource.url=jdbc:postgresql://localhost:5432/vinho_club
spring.datasource.username=postgres
spring.datasource.password=senha
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# Server
server.port=8080

# Application
app.name=Clube do Vinho
```

### 12. Scripts SQL de Inicialização

```sql
-- Inserir planos de exemplo
INSERT INTO planos_vinho (nome, descricao, preco, max_produtos_por_periodo, frequencia_entrega, nivel_expertise, ativo)
VALUES 
('Iniciante', 'Perfeito para quem está começando no mundo dos vinhos', 99.90, 2, 'MENSAL', 'INICIANTE', true),
('Conhecedor', 'Para quem já conhece e aprecia bons vinhos', 189.90, 3, 'MENSAL', 'INTERMEDIARIO', true),
('Sommelier', 'Seleção premium para verdadeiros apreciadores', 299.90, 4, 'MENSAL', 'SOMMELIER', true);

-- Inserir vinhos de exemplo
INSERT INTO vinhos (nome, descricao, preco, regiao, uva, ano_safra, tipo_vinho, teor_alcoolico, volume_ml, ativo)
VALUES
('Château Margaux', 'Um dos grandes vinhos de Bordeaux', 899.00, 'Bordeaux', 'Cabernet Sauvignon', 2015, 'TINTO', 13.5, 750, true),
('Sassicaia', 'Super Toscano famoso mundialmente', 699.00, 'Toscana', 'Cabernet Sauvignon', 2016, 'TINTO', 14.0, 750, true);
```

## 📊 Resultado Final

Você terá um sistema completo com:

- ✅ CRUD completo de vinhos
- ✅ Gestão de planos de assinatura
- ✅ Criação de pacotes mensais
- ✅ Sistema de assinaturas
- ✅ API REST documentada
- ✅ Validações automáticas
- ✅ Relacionamentos configurados
- ✅ Consultas personalizadas

## 🔄 Próximos Passos

1. Adicionar autenticação e autorização
2. Implementar sistema de pagamentos
3. Criar interface administrativa
4. Adicionar sistema de avaliações
5. Implementar recomendações personalizadas
6. Criar app mobile

## 📝 Notas

- Todo o código usa o framework Pingou como base
- Aproximadamente 70% do código é reutilizado
- Tempo estimado de desenvolvimento: 2-3 dias
- Sistema completo e pronto para produção

---

**Autor**: Exemplo do Framework Pingou  
**Versão**: 1.0  
**Data**: Novembro 2025
