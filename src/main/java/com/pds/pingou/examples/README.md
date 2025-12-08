# Exemplos de Uso do Framework Pingou

Esta pasta contém exemplos de como estender o framework para criar seus próprios produtos de assinatura.

## Estrutura de Exemplos

### 1. `produto/` - Exemplos de Produtos
- `ExemploProdutoCachaca.java` - Como criar um produto de cachaça
- `ExemploProdutoVinho.java` - Como criar um produto de vinho
- `ExemploProdutoCafe.java` - Como criar um produto de café

### 2. `security/` - Exemplos de Segurança
- `ExemploUser.java` - Como estender o usuário base

## Como Usar

### Criando um Novo Tipo de Produto

1. Estenda a classe `Produto`:

```java
@Entity
@Table(name = "vinhos")
public class Vinho extends Produto {
    
    private String uva;
    private Integer safra;
    private String regiao;
    
    // Construtores, getters, setters...
}
```

2. Crie o Repository:

```java
@Repository
public interface VinhoRepository extends JpaRepository<Vinho, Long> {
    List<Vinho> findByUvaAndAtivoTrue(String uva);
    List<Vinho> findBySafraAndAtivoTrue(Integer safra);
}
```

3. Crie DTOs, Service e Controller seguindo o padrão do framework.

### Criando um Novo Tipo de Usuário

1. Estenda `BaseUser`:

```java
@Entity
public class MeuUsuario extends BaseUser {
    
    private String cpf;
    private LocalDate dataNascimento;
    
    // Relacionamentos específicos...
}
```

2. Implemente `BaseAuthenticationService` com seu tipo de usuário.

## Módulos Disponíveis

O framework fornece os seguintes módulos prontos para uso:

- **Assinatura** - Gerenciamento de assinaturas de usuários
- **Pacote** - Criação de pacotes de produtos
- **Plano** - Definição de planos de assinatura
- **Produto** - Base para qualquer tipo de produto
- **Security** - Autenticação JWT completa
- **AI** - Geração de pacotes com IA
