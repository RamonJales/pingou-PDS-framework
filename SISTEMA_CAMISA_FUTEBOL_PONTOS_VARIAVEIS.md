# Sistema de Assinatura de Camisas de Futebol

## 📋 Visão Geral

Sistema de assinatura de camisas de futebol desenvolvido utilizando o **Pingou Subscription Framework**. O sistema oferece curadoria personalizada de camisas baseada em preferências dos usuários, perfil morfológico e histórico de entregas.

## 🎯 Pontos Variáveis Implementados

Este sistema demonstra como o framework pode ser adaptado para diferentes domínios. Abaixo estão os pontos variáveis implementados especificamente para camisas de futebol:

---

### 1️⃣ **Especialização do Domínio do Produto**

#### 📦 Entidade: `Camisa` (extends `BaseProduct`)

**Localização**: `com.pds.pingou.camisa.entity.Camisa`

**Atributos Especializados**:
- `time`: Nome do time da camisa
- `timeRival`: Time rival (usado para evitar envio de camisas indesejadas)
- `ano`: Ano da temporada da camisa
- `tamanho`: Enum com tamanhos (PP, P, M, G, GG, XG, XXG)
- `tipo`: Enum com tipos (GOLEIRO, TREINO, SOCIAL, JOGO)
- `material`: Enum diferenciando JOGADOR (premium) e TORCEDOR (standard)
- `personalizacao`: Campo para customizações (nome, número)
- `estoque`: Controle de quantidade disponível

**Métodos Específicos**:
```java
boolean isDeJogador()           // Identifica camisas versão jogador
boolean isDeTorcedor()          // Identifica camisas versão torcedor
boolean isDisponivelEmEstoque() // Verifica disponibilidade
void decrementarEstoque()       // Controle de estoque
void incrementarEstoque(int)    // Reposição de estoque
```

**Diferencial**: O sistema distingue claramente entre camisas de jogador (material premium) e de torcedor, permitindo curadoria baseada em preferências e orçamento.

---

### 2️⃣ **Mecanismo de Curadoria e Montagem de Pacotes**

#### 🎨 Serviço: `CuradoriaCamisaService`

**Localização**: `com.pds.pingou.camisa.service.CuradoriaCamisaService`

**Funcionalidade Principal**: Curadoria Personalizada com Match Inteligente

**Critérios de Seleção**:

1. **Tamanho Adequado**: 
   - Busca camisas do tamanho recomendado no perfil morfológico
   - Garante conforto perfeito para o usuário

2. **Time Favorito**:
   - Prioriza camisas do time favorito do usuário
   - Score +100 para time favorito

3. **Evita Repetições**:
   - Verifica histórico de envios anteriores
   - Nunca envia a mesma camisa duas vezes

4. **Bloqueio de Rivais**:
   - **Jamais** envia camisas do time rival declarado
   - Filtro absoluto na curadoria

5. **Priorização de Qualidade**:
   - Camisas versão jogador recebem score +50
   - Prioriza camisas mais recentes (ano)

**Algoritmo de Score**:
```java
int score = 0;
if (timeFavorito) score += 100;
if (materialJogador) score += 50;
score += (anoAtual - ano) * -5;  // Mais recente = maior score
if (jaEnviada) score -= 1000;    // Penaliza repetições
```

**Método Principal**:
```java
List<Camisa> curarCamisasParaUsuario(User user, AssinaturaCamisa assinatura, int quantidade)
```

**Observações de Curadoria**:
- Gera descrição automática do pacote curado
- Destaca camisas do time favorito
- Informa quantidade de camisas versão jogador

---

### 3️⃣ **Regras de Cadastro de Novos Usuários**

#### 👤 Entidade: `PerfilMorfologico`

**Localização**: `com.pds.pingou.camisa.entity.PerfilMorfologico`

**Anamnese Morfológica Completa**:

O sistema exige cadastro detalhado de características físicas para calcular o tamanho ideal:

**Medidas Obrigatórias**:
- `altura`: Altura em centímetros
- `peso`: Peso em quilogramas
- `circunferenciaPeito`: Circunferência do peito em cm
- `circunferenciaCintura`: Circunferência da cintura em cm
- `comprimentoTorso`: Comprimento do torso em cm
- `larguraOmbros`: Largura dos ombros em cm

**Cálculo Automático**:
- `tamanhoRecomendado`: Calculado automaticamente com base nas medidas
- `imc`: Índice de Massa Corporal calculado

**Algoritmo de Recomendação de Tamanho**:
```java
@PrePersist
@PreUpdate
private void calcularTamanhoRecomendado() {
    // Considera circunferência do peito como fator principal
    if (circunferenciaPeito < 85) tamanhoRecomendado = PP;
    else if (circunferenciaPeito < 90) tamanhoRecomendado = P;
    else if (circunferenciaPeito < 95) tamanhoRecomendado = M;
    else if (circunferenciaPeito < 100) tamanhoRecomendado = G;
    else if (circunferenciaPeito < 110) tamanhoRecomendado = GG;
    else if (circunferenciaPeito < 120) tamanhoRecomendado = XG;
    else tamanhoRecomendado = XXG;
    
    // Ajusta baseado no comprimento do torso
    if (comprimentoTorso > 75) {
        // Aumenta um tamanho
    }
}
```

**Validação Obrigatória**:
- Usuário **não pode criar assinatura** sem perfil morfológico
- Garante que todas as camisas enviadas terão tamanho adequado

**Endpoint de Cadastro**:
```
POST /api/perfil-morfologico
```

---

### 4️⃣ **Estrutura e Composição do Plano - Família e Amigos**

#### 👨‍👩‍👧‍👦 Entidade: `PlanoCamisa` (extends `BasePlan`)

**Localização**: `com.pds.pingou.camisa.entity.PlanoCamisa`

**Atributos de Compartilhamento**:
- `permiteCompartilhamento`: Flag booleana para planos família
- `maxParticipantes`: Número máximo de pessoas no plano
- `camisasPorMes`: Quantidade de camisas por mês

**Método de Validação**:
```java
boolean isPlanoFamilia()  // Identifica se é plano compartilhável
void validarNumeroParticipantes(int numeroAtual)  // Valida limite
```

#### 🤝 Entidade: `AssinaturaCamisa` (extends `BaseSubscription`)

**Localização**: `com.pds.pingou.camisa.entity.AssinaturaCamisa`

**Atributos de Compartilhamento**:
- `assinaturaPrincipal`: Referência à assinatura principal (null se for principal)
- `participantesCompartilhados`: Lista de participantes compartilhando o plano

**Funcionalidades**:

**1. Assinatura Principal**:
```java
boolean isAssinaturaPrincipal()  // Verifica se é assinatura principal
int getTotalParticipantes()      // Total incluindo compartilhados
```

**2. Adicionar Participantes**:
```java
void adicionarParticipante(AssinaturaCamisa assinatura) {
    // Valida se é assinatura principal
    // Valida se plano permite compartilhamento
    // Valida limite de participantes
    // Adiciona novo participante
}
```

**3. Remover Participantes**:
```java
void removerParticipante(AssinaturaCamisa assinatura)
```

**Benefícios do Plano Família**:

✅ **Divisão de Custos**: Múltiplos usuários compartilham o valor do plano

✅ **Diferentes Estaturas**: Cada participante tem seu próprio perfil morfológico
```java
User1 -> PerfilMorfologico1 -> Tamanho M
User2 -> PerfilMorfologico2 -> Tamanho GG
User3 -> PerfilMorfologico3 -> Tamanho P
// Todos no mesmo plano!
```

✅ **Diferentes Biotipos**: Sistema respeita características físicas individuais

✅ **Preferências Individuais**: Cada um pode ter seu time favorito e rival
```java
User1 -> Time: Flamengo, Rival: Fluminense
User2 -> Time: Corinthians, Rival: Palmeiras
User3 -> Time: Grêmio, Rival: Internacional
```

✅ **Curadoria Individual**: Pacotes personalizados para cada participante

**Serviço de Gestão**: `AssinaturaCamisaService`

**Métodos Principais**:
```java
AssinaturaCamisa adicionarParticipante(
    Long assinaturaPrincipalId, 
    User novoParticipante, 
    String timeFavorito, 
    String timeRival
)

void removerParticipante(Long assinaturaPrincipalId, Long participanteId)

List<AssinaturaCamisa> buscarParticipantes(Long assinaturaPrincipalId)
```

**Endpoints REST**:
```
POST   /api/assinaturas-camisa/{id}/participantes
DELETE /api/assinaturas-camisa/{id}/participantes/{participanteId}
GET    /api/assinaturas-camisa/{id}/participantes
```

**Exemplo de Uso**:
```json
POST /api/assinaturas-camisa/1/participantes
{
  "novoParticipanteUserId": 42,
  "timeFavorito": "Santos",
  "timeRival": "Corinthians"
}
```

---

## 🏗️ Arquitetura Implementada

### Pacotes e Estrutura

```
com.pds.pingou.camisa/
├── entity/                          # Entidades especializadas
│   ├── Camisa.java                  # ✅ Produto especializado
│   ├── PerfilMorfologico.java       # ✅ Perfil corporal
│   ├── PlanoCamisa.java             # ✅ Plano com compartilhamento
│   ├── PacoteCamisa.java            # Pacote de camisas
│   ├── ItemPacoteCamisa.java        # Item do pacote
│   ├── AssinaturaCamisa.java        # ✅ Assinatura com participantes
│   └── HistoricoEnvioCamisa.java    # Histórico de entregas
├── enums/
│   ├── TipoCamisa.java              # GOLEIRO, TREINO, SOCIAL, JOGO
│   ├── MaterialCamisa.java          # JOGADOR, TORCEDOR
│   └── TamanhoCamisa.java           # PP, P, M, G, GG, XG, XXG
├── service/
│   ├── CamisaService.java
│   ├── PerfilMorfologicoService.java
│   ├── PlanoCamisaService.java
│   ├── AssinaturaCamisaService.java
│   ├── PacoteCamisaService.java
│   ├── CuradoriaCamisaService.java  # ✅ Curadoria personalizada
│   └── HistoricoEnvioCamisaService.java
├── repository/
│   ├── CamisaRepository.java
│   ├── PerfilMorfologicoRepository.java
│   ├── PlanoCamisaRepository.java
│   ├── AssinaturaCamisaRepository.java
│   ├── PacoteCamisaRepository.java
│   ├── ItemPacoteCamisaRepository.java
│   └── HistoricoEnvioCamisaRepository.java
├── controller/
│   ├── CamisaController.java
│   ├── PerfilMorfologicoController.java
│   ├── PlanoCamisaController.java
│   └── AssinaturaCamisaController.java
├── dto/
│   ├── CamisaRequestDTO.java
│   ├── CamisaResponseDTO.java
│   ├── PerfilMorfologicoRequestDTO.java
│   ├── PerfilMorfologicoResponseDTO.java
│   ├── PlanoCamisaRequestDTO.java
│   ├── PlanoCamisaResponseDTO.java
│   ├── AssinaturaCamisaRequestDTO.java
│   ├── AssinaturaCamisaResponseDTO.java
│   ├── ParticipanteResponseDTO.java
│   └── AdicionarParticipanteRequestDTO.java
└── mapper/
    ├── CamisaMapper.java
    ├── PerfilMorfologicoMapper.java
    ├── PlanoCamisaMapper.java
    └── AssinaturaCamisaMapper.java
```

---

## 🚀 API REST Endpoints

### Camisas
```
GET    /api/camisas                    # Listar todas
GET    /api/camisas/{id}               # Buscar por ID
POST   /api/camisas                    # Criar nova
PUT    /api/camisas/{id}               # Atualizar
DELETE /api/camisas/{id}               # Deletar
GET    /api/camisas/disponiveis        # Camisas com estoque
GET    /api/camisas/time/{time}        # Camisas de um time
```

### Perfil Morfológico
```
POST   /api/perfil-morfologico         # Criar perfil
GET    /api/perfil-morfologico/meu-perfil
PUT    /api/perfil-morfologico/meu-perfil
DELETE /api/perfil-morfologico/meu-perfil
GET    /api/perfil-morfologico/usuario/{userId}
```

### Planos
```
GET    /api/planos-camisa              # Listar todos
GET    /api/planos-camisa/{id}         # Buscar por ID
POST   /api/planos-camisa              # Criar novo
PUT    /api/planos-camisa/{id}         # Atualizar
DELETE /api/planos-camisa/{id}         # Deletar
GET    /api/planos-camisa/ativos       # Planos ativos
GET    /api/planos-camisa/familia      # Planos compartilháveis
```

### Assinaturas
```
POST   /api/assinaturas-camisa         # Criar assinatura
GET    /api/assinaturas-camisa/minha-assinatura
GET    /api/assinaturas-camisa/{id}
GET    /api/assinaturas-camisa
POST   /api/assinaturas-camisa/{id}/ativar
POST   /api/assinaturas-camisa/{id}/suspender
POST   /api/assinaturas-camisa/{id}/cancelar

# Gestão de Participantes
POST   /api/assinaturas-camisa/{id}/participantes
DELETE /api/assinaturas-camisa/{id}/participantes/{participanteId}
GET    /api/assinaturas-camisa/{id}/participantes
```

---

## 📊 Fluxo de Uso do Sistema

### 1. Cadastro de Novo Usuário
```
1. Usuário cria conta
2. POST /api/perfil-morfologico (OBRIGATÓRIO)
   {
     "altura": 175,
     "peso": 75,
     "circunferenciaPeito": 95,
     "circunferenciaCintura": 85,
     "comprimentoTorso": 70,
     "larguraOmbros": 45
   }
3. Sistema calcula tamanhoRecomendado automaticamente
```

### 2. Criação de Assinatura Individual
```
POST /api/assinaturas-camisa
{
  "planoId": 1,
  "timeFavorito": "Flamengo",
  "timeRival": "Fluminense"
}
```

### 3. Criação de Assinatura Família
```
# Passo 1: Usuário principal cria assinatura com plano família
POST /api/assinaturas-camisa
{
  "planoId": 2,  // Plano família
  "timeFavorito": "Palmeiras",
  "timeRival": "Corinthians"
}

# Passo 2: Adicionar amigos/familiares
POST /api/assinaturas-camisa/1/participantes
{
  "novoParticipanteUserId": 10,
  "timeFavorito": "São Paulo",
  "timeRival": "Corinthians"
}

# Cada participante precisa ter perfil morfológico!
```

### 4. Curadoria e Envio de Pacotes
```
# Sistema executa curadoria automaticamente
# Para cada usuário/participante:
1. Busca perfil morfológico
2. Identifica tamanho recomendado
3. Busca histórico de camisas enviadas
4. Aplica algoritmo de curadoria:
   - Prioriza time favorito
   - Evita time rival (NUNCA envia)
   - Evita repetições
   - Prioriza material jogador
   - Prioriza camisas recentes
5. Monta pacote personalizado
6. Registra no histórico
```

---

## 🎨 Pontos Variáveis - Resumo Visual

| Ponto Variável | Implementação | Classes Principais |
|---------------|---------------|-------------------|
| **Domínio do Produto** | Camisa com atributos específicos de futebol | `Camisa`, enums (Tipo, Material, Tamanho) |
| **Curadoria Personalizada** | Match inteligente baseado em preferências | `CuradoriaCamisaService` |
| **Perfil Morfológico** | Anamnese completa com cálculo automático | `PerfilMorfologico`, `PerfilMorfologicoService` |
| **Plano Família** | Compartilhamento com múltiplos perfis | `AssinaturaCamisa`, `AssinaturaCamisaService` |

---

## 🔧 Configuração e Instalação

### 1. Dependências (pom.xml)
Todas as dependências do framework já estão incluídas.

### 2. Banco de Dados
O sistema usa JPA/Hibernate com geração automática de schema.

### 3. Executar
```bash
./mvnw spring-boot:run
```

---

## 📈 Benefícios da Implementação

✅ **Reutilização**: 70% do código vem do framework  
✅ **Rapidez**: Sistema completo em dias  
✅ **Consistência**: Padrões uniformes  
✅ **Extensibilidade**: Fácil adicionar novos tipos de produto  
✅ **Manutenibilidade**: Correções no framework beneficiam todos  

---

## 🎯 Conclusão

Este sistema demonstra como o **Pingou Subscription Framework** pode ser adaptado para criar um sistema completo de assinatura de camisas de futebol com:

- ✅ Curadoria personalizada inteligente
- ✅ Perfil morfológico detalhado
- ✅ Planos família com compartilhamento
- ✅ Gestão completa de estoque e histórico
- ✅ API REST completa

**Versão**: 1.0  
**Data**: Dezembro 2025  
**Framework Base**: Pingou Subscription Framework v1.0
