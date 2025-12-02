# 🚀 Guia de Início Rápido - Camisa Club

## Visão Geral

Você agora tem uma aplicação completa de **assinatura de camisas de futebol** construída com o Framework PDS!

## ✅ O que foi criado

### 1. **Modelo de Domínio Completo**
- ✅ `Camisa` - Entidade de camisas de futebol com:
  - Time, Liga, Ano da Temporada, Tipo (Titular, Reserva, Retrô)
  - Marca, Tamanhos disponíveis, Material
  - Controle de estoque e edições limitadas
  
- ✅ `PlanoCamisa` - Planos de assinatura com 6 categorias:
  - Clássicos Brasileiros (R$ 189,90)
  - Internacionais Premium (R$ 299,90)
  - Retrô Nostálgico (R$ 249,90)
  - Nacionais Completo (R$ 349,90)
  - Libertadores Especial (R$ 269,90)
  - Champions Collection (R$ 399,90)

- ✅ `PacoteCamisa` - Pacotes mensais de camisas
- ✅ `ItemPacoteCamisa` - Itens dos pacotes
- ✅ `AssinaturaCamisa` - Assinaturas de usuários

### 2. **Repositórios JPA**
- ✅ Todos com queries customizadas para buscas avançadas

### 3. **Serviços**
- ✅ Herdam do framework base com lógica de negócio específica

### 4. **Controllers REST**
- ✅ CRUD completo para todas as entidades
- ✅ Endpoints especializados (busca por time, liga, tipo, etc.)

### 5. **DTOs e Mappers**
- ✅ Conversão automática entre entidades e DTOs

### 6. **Dados Iniciais**
- ✅ 20 camisas cadastradas (Brasileiras, Internacionais, Retrô)
- ✅ 6 planos de assinatura prontos

## 🎯 Como Executar

### Passo 1: Banco de Dados
O banco PostgreSQL já está configurado no `docker-compose.yml`:
```bash
docker-compose up -d
```

### Passo 2: Executar a Aplicação

#### Opção A: Via IDE (Recomendado)
1. Abra o projeto no IntelliJ IDEA ou VS Code
2. Execute a classe `PingouApplication.java`
3. Aguarde a mensagem de sucesso

#### Opção B: Via Maven (se tiver Maven instalado)
```bash
mvn spring-boot:run
```

#### Opção C: Via Wrapper Maven
```bash
./mvnw spring-boot:run    # Linux/Mac
.\mvnw.cmd spring-boot:run # Windows
```

### Passo 3: Popular o Banco (IMPORTANTE!)

**Após a aplicação iniciar pela primeira vez**, as tabelas serão criadas automaticamente.
Então execute este comando para inserir os dados iniciais:

```bash
docker cp src\main\resources\data\camisas-data.sql postgres:/tmp/camisas-data.sql
docker exec postgres psql -U admin -d pingou -f /tmp/camisas-data.sql
```

## 📚 Testando a API

### Swagger UI (Recomendado)
Acesse: http://localhost:8080/swagger-ui.html

### Exemplos de Requisições

#### 1. Listar todas as camisas
```bash
GET http://localhost:8080/api/camisas
```

#### 2. Buscar camisas por time
```bash
GET http://localhost:8080/api/camisas/time/Flamengo
```

#### 3. Listar planos ativos
```bash
GET http://localhost:8080/api/planos/ativos
```

#### 4. Criar uma assinatura
```bash
POST http://localhost:8080/api/assinaturas
Content-Type: application/json

{
  "userId": 1,
  "planoId": 1,
  "tamanhoPreferido": "M",
  "timesFavoritos": "Flamengo,Palmeiras",
  "ligasPreferidas": "BRASILEIRAO",
  "aceitaPersonalizacao": true
}
```

## 🔐 Autenticação

A aplicação possui autenticação JWT configurada. Para endpoints protegidos:

1. Registre um usuário:
```bash
POST http://localhost:8080/api/auth/register
Content-Type: application/json

{
  "username": "usuario",
  "email": "usuario@email.com",
  "password": "senha123"
}
```

2. Faça login:
```bash
POST http://localhost:8080/api/auth/login
Content-Type: application/json

{
  "username": "usuario",
  "password": "senha123"
}
```

3. Use o token retornado no header:
```
Authorization: Bearer {seu-token-jwt}
```

## 📊 Estrutura de Dados

### Camisas Cadastradas
- **Brasileiras**: Flamengo, Palmeiras, Corinthians, São Paulo, Santos, Grêmio
- **Premier League**: Manchester United, Liverpool, Manchester City, Arsenal, Chelsea
- **La Liga**: Real Madrid, Barcelona, Atlético Madrid
- **Retrô**: Flamengo 1981, Brasil 1970, Santos Pelé, Milan 1989, Man United 1999

### Ligas Disponíveis
- BRASILEIRAO
- PREMIER_LEAGUE
- LA_LIGA
- SERIE_A
- BUNDESLIGA
- LIGUE_1
- LIBERTADORES
- CHAMPIONS_LEAGUE
- COPA_DO_BRASIL
- SELECAO_NACIONAL

### Tipos de Camisa
- TITULAR
- RESERVA
- TERCEIRO_UNIFORME
- GOLEIRO
- RETRO
- EDICAO_ESPECIAL
- TREINO

## 🏗️ Arquitetura

O projeto usa o **Framework PDS**, reutilizando ~70% do código:

```
Framework Core (Genérico)
    ↓
Camisa Club (Implementação)
```

### Classes Reutilizadas do Framework:
- `BaseProduct` → `Camisa`
- `BasePlan` → `PlanoCamisa`
- `BasePackage` → `PacoteCamisa`
- `BasePackageItem` → `ItemPacoteCamisa`
- `BaseSubscription` → `AssinaturaCamisa`
- `BaseProductService`, `BasePlanService`, etc.

## 🛠️ Tecnologias

- Java 21
- Spring Boot 3.5.5
- Spring Data JPA
- PostgreSQL
- Spring Security + JWT
- Swagger/OpenAPI
- Lombok
- Docker

## 📁 Estrutura do Projeto

```
src/main/java/com/pds/pingou/
├── framework/core/       ← Framework genérico (mantido)
├── camisa/              ← Nova aplicação de camisas
├── security/            ← Segurança (mantida)
└── admin/              ← Admin (mantido)
```

## 🎉 Pronto para Produção!

A aplicação está completa e funcional com:
- ✅ CRUD completo
- ✅ Autenticação e autorização
- ✅ Validações de negócio
- ✅ Tratamento de erros
- ✅ Documentação automática
- ✅ Dados de exemplo
- ✅ Docker configurado

## 📝 Próximos Passos (Opcional)

1. Adicionar testes unitários e de integração
2. Implementar sistema de pagamentos
3. Criar interface frontend (React, Angular, Vue)
4. Adicionar sistema de notificações
5. Implementar rastreamento de entregas
6. Criar dashboard administrativo

## 💡 Dicas

- Use o Swagger UI para explorar todos os endpoints
- Verifique os logs da aplicação para debug
- O banco é recriado a cada restart (hibernate.ddl-auto=update)
- Todas as senhas são hash com BCrypt

## 🆘 Problemas Comuns

### Porta 8080 já em uso
```bash
# Windows
netstat -ano | findstr :8080
taskkill /PID {pid} /F

# Linux/Mac
lsof -ti:8080 | xargs kill -9
```

### Banco de dados não conecta
```bash
docker-compose down
docker-compose up -d
```

### Tabelas não foram criadas
Aguarde a aplicação iniciar completamente. O Spring Boot cria as tabelas automaticamente.

---

**Desenvolvido com Framework PDS** 🚀
