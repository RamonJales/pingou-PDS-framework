-- ===============================================
-- SCHEMA PARA SISTEMA DE HQS
-- ===============================================

-- Tabela: hqs (extends produtos via JOINED)
CREATE TABLE IF NOT EXISTS hqs (
                                   id BIGINT PRIMARY KEY REFERENCES produtos(id) ON DELETE CASCADE,
    tipo_hq VARCHAR(50) NOT NULL,
    categoria VARCHAR(50) NOT NULL,
    editora VARCHAR(50) NOT NULL,
    autor VARCHAR(255) NOT NULL,
    desenhista VARCHAR(255) NOT NULL,
    ano_publicacao INTEGER NOT NULL,
    numero_edicao INTEGER,
    numero_paginas INTEGER,
    isbn VARCHAR(20),
    edicao_colecionador BOOLEAN DEFAULT FALSE,
    sinopse VARCHAR(2000),
    pontos_ganho INTEGER DEFAULT 10
    );

-- Tabela: planos_hq
CREATE TABLE IF NOT EXISTS planos_hq (
                                         id BIGSERIAL PRIMARY KEY,
                                         nome VARCHAR(255) NOT NULL UNIQUE,
    descricao VARCHAR(2000) NOT NULL,
    preco NUMERIC(10,2) NOT NULL,
    max_produtos_por_periodo INTEGER NOT NULL,
    frequencia_entrega VARCHAR(50) NOT NULL,
    ativo BOOLEAN NOT NULL DEFAULT TRUE,
    tipo_plano VARCHAR(50) NOT NULL,
    percentual_classicas INTEGER,
    percentual_modernas INTEGER,
    multiplicador_pontos NUMERIC(5,2) DEFAULT 1.0
    );

-- Tabela: pacotes_hq
CREATE TABLE IF NOT EXISTS pacotes_hq (
                                          id BIGSERIAL PRIMARY KEY,
                                          nome VARCHAR(255) NOT NULL,
    descricao VARCHAR(1000),
    data_entrega DATE NOT NULL,
    periodo INTEGER NOT NULL,
    mes INTEGER NOT NULL,
    ano INTEGER NOT NULL,
    plano_id BIGINT NOT NULL REFERENCES planos_hq(id),
    ativo BOOLEAN NOT NULL DEFAULT TRUE,
    tema_mes VARCHAR(255)
    );

CREATE INDEX IF NOT EXISTS ix_pacotes_hq_plano ON pacotes_hq(plano_id);
CREATE INDEX IF NOT EXISTS ix_pacotes_hq_mes_ano ON pacotes_hq(mes, ano);

-- Tabela: item_pacote_hq
CREATE TABLE IF NOT EXISTS item_pacote_hq (
                                              id BIGSERIAL PRIMARY KEY,
                                              pacote_id BIGINT NOT NULL REFERENCES pacotes_hq(id) ON DELETE CASCADE,
    hq_id BIGINT NOT NULL REFERENCES produtos(id),
    quantidade INTEGER NOT NULL,
    observacoes VARCHAR(500),
    edicao_especial BOOLEAN DEFAULT FALSE
    );

CREATE INDEX IF NOT EXISTS ix_item_pacote_hq_pacote ON item_pacote_hq(pacote_id);
CREATE INDEX IF NOT EXISTS ix_item_pacote_hq_hq ON item_pacote_hq(hq_id);

-- Tabela: assinaturas_hq
CREATE TABLE IF NOT EXISTS assinaturas_hq (
                                              id BIGSERIAL PRIMARY KEY,
                                              user_id BIGINT NOT NULL UNIQUE REFERENCES users(id) ON DELETE CASCADE,
    plano_id BIGINT NOT NULL REFERENCES planos_hq(id),
    status VARCHAR(50) NOT NULL,
    data_inicio DATE NOT NULL,
    data_expiracao DATE,
    preferencias_categorias VARCHAR(500),
    preferencias_editoras VARCHAR(500)
    );

CREATE INDEX IF NOT EXISTS ix_assinaturas_hq_plano ON assinaturas_hq(plano_id);
CREATE INDEX IF NOT EXISTS ix_assinaturas_hq_status ON assinaturas_hq(status);

-- Tabela: pontuacao
CREATE TABLE IF NOT EXISTS pontuacao (
                                         id BIGSERIAL PRIMARY KEY,
                                         user_id BIGINT NOT NULL UNIQUE REFERENCES users(id) ON DELETE CASCADE,
    pontos_totais INTEGER NOT NULL DEFAULT 0,
    pontos_disponiveis INTEGER NOT NULL DEFAULT 0,
    pontos_utilizados INTEGER NOT NULL DEFAULT 0,
    ultima_atualizacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
    );

CREATE INDEX IF NOT EXISTS ix_pontuacao_user ON pontuacao(user_id);

-- Tabela: historico_pontos
CREATE TABLE IF NOT EXISTS historico_pontos (
                                                id BIGSERIAL PRIMARY KEY,
                                                user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    tipo VARCHAR(50) NOT NULL,
    pontos INTEGER NOT NULL,
    descricao VARCHAR(500),
    hq_id BIGINT,
    data_hora TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
    );

CREATE INDEX IF NOT EXISTS ix_historico_pontos_user ON historico_pontos(user_id);
CREATE INDEX IF NOT EXISTS ix_historico_pontos_tipo ON historico_pontos(tipo);
CREATE INDEX IF NOT EXISTS ix_historico_pontos_data ON historico_pontos(data_hora);