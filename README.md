# 🎽 Camisa Club - Sistema de Assinatura de Camisas de Futebol

Sistema completo de assinatura de camisas de futebol desenvolvido com o **Framework PDS** para gerenciamento genérico de assinaturas.

## 📋 Sobre o Projeto

O **Camisa Club** é uma aplicação completa que permite aos usuários assinar planos para receber camisas de futebol mensalmente. O sistema oferece diferentes categorias de planos:

- **Clássicos Brasileiros**: Camisas dos principais times do Brasil
- **Internacionais Premium**: Camisas das maiores ligas europeias
- **Retrô Nostálgico**: Camisas históricas e colecionáveis
- **Nacionais Completo**: Plano premium com múltiplas camisas brasileiras
- **Libertadores Especial**: Camisas de times da Copa Libertadores
- **Champions Collection**: Exclusivo com camisas da Champions League

## 🏗️ Arquitetura

O projeto utiliza o **Framework PDS**, um framework genérico e reutilizável para sistemas de assinatura, estendendo as seguintes classes base:

### Classes do Framework Core
- `BaseProduct` → Produto genérico
- `BasePlan` → Plano de assinatura
- `BasePackage` → Pacote de produtos
- `BasePackageItem` → Item de pacote
- `BaseSubscription` → Assinatura
- `BaseProductService` → Serviço de produtos
- `BasePlanService` → Serviço de planos
- `BaseRestController` → Controller REST

### Implementação para Camisas
- `Camisa` extends `BaseProduct`
- `PlanoCamisa` extends `BasePlan`
- `PacoteCamisa` extends `BasePackage`
- `ItemPacoteCamisa` extends `BasePackageItem`
- `AssinaturaCamisa` extends `BaseSubscription`

## 🚀 Tecnologias Utilizadas

- **Java 21**
- **Spring Boot 3.5.5**
- **Spring Data JPA**
- **PostgreSQL**
- **Lombok**
- **Spring Security**
- **JWT Authentication**
- **Swagger/OpenAPI**
- **Docker & Docker Compose**

## 📦 Estrutura do Projeto

```
src/main/java/com/pds/pingou/
├── framework/core/          # Framework genérico reutilizável
│   ├── entity/              # Entidades base abstratas
│   ├── service/             # Serviços base abstratos
│   ├── controller/          # Controllers base abstratos
│   └── enums/               # Enumerações do framework
├── camisa/                  # Implementação de camisas
│   ├── Camisa.java
│   ├── CamisaRepository.java
│   ├── CamisaService.java
│   ├── controller/
│   ├── dto/
│   ├── enums/
│   ├── exception/
│   ├── planos/              # Planos de assinatura
│   ├── pacote/              # Pacotes mensais
│   └── assinatura/          # Assinaturas
├── security/                # Configuração de segurança
│   ├── auth/
│   ├── config/
│   └── user/
└── admin/                   # Gerenciamento de usuários
```

## 🔧 Configuração e Instalação

### Pré-requisitos
- Java 21+
- Docker e Docker Compose
- Maven

### 1. Clone o repositório
```bash
git clone <repository-url>
cd pingou-PDS-framework
```

### 2. Inicie o banco de dados PostgreSQL
```bash
docker-compose up -d
```

### 3. Configure as variáveis de ambiente (opcional)
```bash
export JWT_SECRET=sua-chave-secreta
export GOOGLE_AI_API_KEY=sua-api-key
```

### 4. Execute a aplicação
```bash
mvn spring-boot:run
```

A aplicação estará disponível em: `http://localhost:8080`

## 📚 API Endpoints

### Camisas
- `GET /api/camisas` - Lista todas as camisas
- `GET /api/camisas/{id}` - Busca camisa por ID
- `POST /api/camisas` - Cria nova camisa
- `PUT /api/camisas/{id}` - Atualiza camisa
- `DELETE /api/camisas/{id}` - Remove camisa
- `GET /api/camisas/time/{time}` - Busca por time
- `GET /api/camisas/liga/{liga}` - Busca por liga
- `GET /api/camisas/tipo/{tipo}` - Busca por tipo
- `GET /api/camisas/search?termo={termo}` - Busca por termo
- `GET /api/camisas/estoque` - Camisas em estoque
- `GET /api/camisas/edicoes-limitadas` - Edições limitadas

### Planos
- `GET /api/planos` - Lista todos os planos
- `GET /api/planos/{id}` - Busca plano por ID
- `POST /api/planos` - Cria novo plano
- `PUT /api/planos/{id}` - Atualiza plano
- `DELETE /api/planos/{id}` - Remove plano
- `GET /api/planos/ativos` - Lista planos ativos
- `GET /api/planos/categoria/{categoria}` - Busca por categoria
- `PATCH /api/planos/{id}/ativar` - Ativa plano
- `PATCH /api/planos/{id}/desativar` - Desativa plano

### Assinaturas
- `GET /api/assinaturas` - Lista todas as assinaturas
- `GET /api/assinaturas/{id}` - Busca assinatura por ID
- `POST /api/assinaturas` - Cria nova assinatura
- `PUT /api/assinaturas/{id}` - Atualiza assinatura
- `DELETE /api/assinaturas/{id}` - Remove assinatura
- `GET /api/assinaturas/usuario/{userId}` - Busca por usuário
- `GET /api/assinaturas/ativas` - Lista assinaturas ativas
- `PATCH /api/assinaturas/{id}/ativar` - Ativa assinatura
- `PATCH /api/assinaturas/{id}/desativar` - Desativa assinatura
- `PATCH /api/assinaturas/{id}/suspender` - Suspende assinatura
- `PATCH /api/assinaturas/{id}/cancelar` - Cancela assinatura

### Autenticação
- `POST /api/auth/login` - Login
- `POST /api/auth/register` - Registro
- `POST /api/auth/refresh` - Renovar token

## 🗄️ Banco de Dados

O banco de dados é inicializado automaticamente com dados de exemplo incluindo:

- **20 Camisas**: Brasileiras, Internacionais e Retrô
- **6 Planos**: Diferentes categorias e preços
- Times incluídos: Flamengo, Palmeiras, Real Madrid, Barcelona, Manchester United, Liverpool e muitos outros

Para popular o banco com dados iniciais:
```bash
psql -U admin -d pingou -f src/main/resources/data/camisas-data.sql
```

## 🔐 Segurança

O sistema utiliza Spring Security com JWT para autenticação e autorização:

- Tokens JWT com expiração configurável
- Refresh tokens para renovação
- Endpoints públicos e protegidos
- Roles de usuário (ADMIN, USER)

## 📖 Documentação da API

Acesse a documentação interativa da API via Swagger UI:
```
http://localhost:8080/swagger-ui.html
```

## 🎯 Funcionalidades Principais

- ✅ CRUD completo de camisas de futebol
- ✅ Gestão de planos de assinatura com múltiplas categorias
- ✅ Sistema de assinaturas com ciclo de vida completo
- ✅ Autenticação e autorização com JWT
- ✅ Busca avançada de camisas (time, liga, tipo, ano)
- ✅ Controle de estoque
- ✅ Edições limitadas e personalizações
- ✅ API REST documentada com Swagger
- ✅ Validações de negócio integradas
- ✅ Tratamento de exceções centralizado

## 🧪 Testes

Execute os testes com:
```bash
mvn test
```

## 📝 Licença

Este projeto foi desenvolvido como exemplo de uso do Framework PDS.

## 👥 Autores

Desenvolvido usando o **Framework PDS** - Sistema genérico para assinaturas.

---

**Versão**: 1.0.0  
**Última Atualização**: Dezembro 2025
