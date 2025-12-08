# 🎽 Módulo de Assinatura de Camisas de Futebol

## 📋 Visão Geral

Este módulo implementa um sistema completo de **assinatura de camisas de futebol** utilizando o framework Pingou Subscription. A principal inovação é o suporte a **planos família** onde cada membro pode ter seu próprio tamanho de camisa.

## 🎯 Características Principais

### ✅ Planos Flexíveis
- **Individual**: 1 membro
- **Casal**: 2 membros
- **Família Pequena**: até 3 membros
- **Família**: até 5 membros
- **Família Grande**: até 8 membros
- **Torcida**: até 12 membros (grupos)

### ✅ Tamanhos Diferenciados por Membro
A grande inovação: cada membro da família pode ter seu próprio tamanho!

```
Exemplo de Assinatura Família:
├── Pai: Tamanho G
├── Mãe: Tamanho M
├── Filho (12 anos): Tamanho INF_12
└── Filha (8 anos): Tamanho INF_8
```

### ✅ Tamanhos Disponíveis
- **Infantis**: 2, 4, 6, 8, 10, 12, 14 anos
- **Adultos**: PP, P, M, G, GG, XGG, XXGG

### ✅ Tipos de Camisas
- Principal (Casa)
- Reserva (Fora)
- Terceira (Alternativa)
- Goleiro
- Treino
- Retrô (Comemorativa)
- Edição Especial

### ✅ Funcionalidades Avançadas
- Personalização de nome/número
- Preferências por time
- Limite de trocas de tamanho por ano
- Pacotes temáticos e de edição limitada
- Prioridade para edições especiais

## 🏗️ Arquitetura

### Estrutura de Pacotes

```
com.pds.pingou.futebol/
├── enums/
│   ├── TamanhoCamisa.java          # Tamanhos adulto e infantil
│   ├── TipoCamisa.java             # Tipos de camisa
│   ├── TipoPlanoFutebol.java       # Tipos de plano família
│   └── Competicao.java             # Ligas e competições
├── produto/
│   ├── CamisaFutebol.java          # Entidade de produto
│   ├── CamisaFutebolRepository.java
│   ├── CamisaFutebolService.java
│   ├── CamisaFutebolController.java
│   ├── CamisaFutebolMapper.java
│   └── dto/
│       ├── CamisaFutebolRequestDTO.java
│       └── CamisaFutebolResponseDTO.java
├── plano/
│   ├── PlanoFutebol.java           # Plano com suporte família
│   ├── PlanoFutebolRepository.java
│   ├── PlanoFutebolService.java
│   ├── PlanoFutebolController.java
│   ├── PlanoFutebolMapper.java
│   └── dto/
│       ├── PlanoFutebolRequestDTO.java
│       └── PlanoFutebolResponseDTO.java
├── assinatura/
│   ├── AssinaturaFutebol.java      # Assinatura com membros
│   ├── MembroAssinatura.java       # Membro com tamanho
│   ├── AssinaturaFutebolRepository.java
│   ├── MembroAssinaturaRepository.java
│   ├── AssinaturaFutebolService.java
│   ├── AssinaturaFutebolController.java
│   ├── AssinaturaFutebolMapper.java
│   └── dto/
│       ├── AssinaturaFutebolRequestDTO.java
│       ├── AssinaturaFutebolResponseDTO.java
│       ├── MembroAssinaturaRequestDTO.java
│       └── MembroAssinaturaResponseDTO.java
└── pacote/
    ├── PacoteFutebol.java          # Pacote temático
    ├── ItemPacoteFutebol.java      # Item com TAMANHO!
    ├── PacoteFutebolRepository.java
    ├── ItemPacoteFutebolRepository.java
    ├── PacoteFutebolService.java
    ├── PacoteFutebolController.java
    ├── PacoteFutebolMapper.java
    └── dto/
        ├── PacoteFutebolRequestDTO.java
        ├── PacoteFutebolResponseDTO.java
        ├── ItemPacoteFutebolRequestDTO.java
        └── ItemPacoteFutebolResponseDTO.java
```

## 🔑 Conceitos-Chave

### 1. MembroAssinatura
Cada membro de uma assinatura família tem:
- **Nome**: Identificação do membro
- **Tamanho**: Tamanho da camisa (chave!)
- **Time Favorito**: Para curadoria
- **Jogador Favorito**: Para personalização
- **Número Favorito**: Para personalização

### 2. ItemPacoteFutebol
O item do pacote agora tem tamanho específico:
```java
// Antes (genérico):
ItemPacote item = new ItemPacote(pacote, produto, 1);

// Agora (com tamanho por membro):
ItemPacoteFutebol item = new ItemPacoteFutebol(
    pacote, 
    camisaFlamengo, 
    TamanhoCamisa.G,      // Tamanho específico!
    membroPai             // Para quem é esta camisa
);
```

### 3. Geração de Pacotes Família
O método `gerarPacoteParaAssinatura()` é a magia:

```java
// Para cada camisa do pacote base...
for (ItemPacoteFutebol itemBase : itensBase) {
    CamisaFutebol camisa = itemBase.getCamisa();
    
    // ...cria um item para CADA membro com SEU tamanho!
    for (MembroAssinatura membro : assinatura.getMembrosAtivos()) {
        ItemPacoteFutebol itemMembro = new ItemPacoteFutebol();
        itemMembro.setCamisa(camisa);
        itemMembro.setTamanho(membro.getTamanho()); // TAMANHO DO MEMBRO!
        itemMembro.setMembroDestino(membro);
        // ... personalização se disponível
    }
}
```

## 📡 API Endpoints

### Camisas
```
GET    /api/futebol/camisas              # Lista todas
GET    /api/futebol/camisas/{id}         # Busca por ID
GET    /api/futebol/camisas/time/{time}  # Busca por time
GET    /api/futebol/camisas/times        # Lista times
POST   /api/futebol/camisas              # Cria camisa
PUT    /api/futebol/camisas/{id}         # Atualiza
DELETE /api/futebol/camisas/{id}         # Deleta
```

### Planos
```
GET    /api/futebol/planos                    # Lista todos
GET    /api/futebol/planos/{id}               # Busca por ID
GET    /api/futebol/planos/familiares         # Lista planos família
GET    /api/futebol/planos/sugerir/{membros}  # Sugere plano ideal
POST   /api/futebol/planos                    # Cria plano
PUT    /api/futebol/planos/{id}               # Atualiza
DELETE /api/futebol/planos/{id}               # Deleta
```

### Assinaturas
```
GET    /api/futebol/assinaturas/minha                    # Minha assinatura
GET    /api/futebol/assinaturas/{id}                     # Busca por ID
GET    /api/futebol/assinaturas/{id}/membros             # Lista membros
GET    /api/futebol/assinaturas/{id}/tamanhos            # Tamanhos necessários
POST   /api/futebol/assinaturas                          # Cria com membros
POST   /api/futebol/assinaturas/{id}/membros             # Adiciona membro
PUT    /api/futebol/assinaturas/membros/{id}             # Atualiza membro
PATCH  /api/futebol/assinaturas/membros/{id}/tamanho     # Troca tamanho
DELETE /api/futebol/assinaturas/membros/{id}             # Remove membro
PATCH  /api/futebol/assinaturas/{id}/suspender           # Suspende
PATCH  /api/futebol/assinaturas/{id}/reativar            # Reativa
PATCH  /api/futebol/assinaturas/{id}/cancelar            # Cancela
POST   /api/futebol/assinaturas/{id}/renovar             # Renova
```

### Pacotes
```
GET    /api/futebol/pacotes                                       # Lista todos
GET    /api/futebol/pacotes/{id}                                  # Busca por ID
GET    /api/futebol/pacotes/meus                                  # Meus pacotes
GET    /api/futebol/pacotes/edicoes-limitadas                     # Edições limitadas
POST   /api/futebol/pacotes                                       # Cria pacote
POST   /api/futebol/pacotes/{id}/gerar-para-assinatura/{assId}    # Gera para família
POST   /api/futebol/pacotes/{id}/gerar-para-todos                 # Gera para todos
POST   /api/futebol/pacotes/{id}/itens                            # Adiciona item
PATCH  /api/futebol/pacotes/itens/{id}/status                     # Atualiza status
DELETE /api/futebol/pacotes/{id}                                  # Deleta
```

## 💡 Exemplos de Uso

### 1. Criar Assinatura Família
```json
POST /api/futebol/assinaturas
{
  "planoId": 2,
  "membros": [
    {
      "nome": "João Silva",
      "tamanho": "G",
      "titular": true,
      "timeFavorito": "Flamengo",
      "jogadorFavorito": "GABIGOL",
      "numeroFavorito": 9
    },
    {
      "nome": "Maria Silva",
      "tamanho": "M",
      "timeFavorito": "Flamengo"
    },
    {
      "nome": "Pedro Silva",
      "tamanho": "INF_12",
      "timeFavorito": "Flamengo",
      "jogadorFavorito": "ARRASCAETA",
      "numeroFavorito": 14
    },
    {
      "nome": "Ana Silva",
      "tamanho": "INF_8",
      "timeFavorito": "Flamengo"
    }
  ],
  "timeFavoritoPrincipal": "Flamengo",
  "timesSecundarios": "Brasil,Real Madrid",
  "aceitaTimesRivais": false
}
```

### 2. Gerar Pacote para Família
```
POST /api/futebol/pacotes/1/gerar-para-assinatura/5
```

Resultado: 4 itens criados automaticamente:
- Camisa Flamengo (G) → João Silva
- Camisa Flamengo (M) → Maria Silva  
- Camisa Flamengo (INF_12) → Pedro Silva
- Camisa Flamengo (INF_8) → Ana Silva

### 3. Trocar Tamanho de Membro
```
PATCH /api/futebol/assinaturas/membros/3/tamanho?novoTamanho=INF_14
```

O Pedro cresceu! Troca de INF_12 para INF_14 (limitado a X trocas/ano).

## 🔧 Extensibilidade

### Adicionando Novo Tipo de Plano
```java
// Em TipoPlanoFutebol.java
ENTERPRISE("Empresarial", "Para empresas", 50, 15.0);
```

### Adicionando Nova Competição
```java
// Em Competicao.java  
MLS("Major League Soccer", "EUA", true);
```

### Adicionando Novo Tamanho
```java
// Em TamanhoCamisa.java
INF_16("Infantil 16 anos", "16", true);
```

## 📊 Fluxo de Negócio

```
1. Cliente escolhe plano (Individual ou Família)
           ↓
2. Cadastra membros com tamanhos
           ↓
3. Admin cria pacote base do mês
           ↓
4. Sistema gera pacotes personalizados
   para cada assinatura:
   - Mesmo pacote
   - Tamanhos diferentes por membro
           ↓
5. Itens são separados, enviados, entregues
           ↓
6. Próximo mês: repete processo
```

## 🎨 Diferenciais do Framework

| Recurso | Framework Base | Módulo Futebol |
|---------|----------------|----------------|
| Produtos | Genéricos | Camisas com time, temporada, tipo |
| Planos | Quantidade de produtos | Quantidade de membros |
| Pacotes | Itens simples | Itens com tamanho por membro |
| Assinaturas | 1 usuário | Múltiplos membros família |
| Personalização | - | Nome/número do jogador |

## 📝 Notas Técnicas

- O tamanho NÃO fica na camisa (CamisaFutebol) - fica no ITEM do pacote
- Isso permite que a mesma camisa seja enviada em tamanhos diferentes
- Cada ItemPacoteFutebol conhece seu membro destino
- Trocas de tamanho são limitadas por ano (configurável no plano)
- Edições limitadas controlam quantidade disponível
