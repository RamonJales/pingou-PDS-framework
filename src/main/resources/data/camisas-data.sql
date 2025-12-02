-- Script de dados iniciais para o sistema de assinatura de camisas de futebol
-- Criado para popular banco de dados com camisas, planos e configurações iniciais

-- ========================================
-- CAMISAS DE FUTEBOL
-- ========================================

-- Camisas Brasileiras Clássicas
INSERT INTO camisas (nome, descricao, preco, time, liga, ano_temporada, tipo_camisa, marca, tamanhos_disponiveis, pais_time, material, numeracao_disponivel, nome_personalizacao_disponivel, edicao_limitada, estoque_quantidade, ativo)
VALUES 
('Flamengo 2024 Titular', 'Camisa oficial do Flamengo para a temporada 2024', 299.90, 'Flamengo', 'BRASILEIRAO', 2024, 'TITULAR', 'Adidas', 'P,M,G,GG,XG', 'Brasil', 'Poliéster Dry-Fit', true, true, false, 100, true),
('Palmeiras 2024 Titular', 'Camisa oficial do Palmeiras para a temporada 2024', 299.90, 'Palmeiras', 'BRASILEIRAO', 2024, 'TITULAR', 'Puma', 'P,M,G,GG,XG', 'Brasil', 'Poliéster Dry-Fit', true, true, false, 100, true),
('Corinthians 2024 Titular', 'Camisa oficial do Corinthians para a temporada 2024', 299.90, 'Corinthians', 'BRASILEIRAO', 2024, 'TITULAR', 'Nike', 'P,M,G,GG,XG', 'Brasil', 'Poliéster Dry-Fit', true, true, false, 100, true),
('São Paulo 2024 Titular', 'Camisa oficial do São Paulo para a temporada 2024', 299.90, 'São Paulo', 'BRASILEIRAO', 2024, 'TITULAR', 'Adidas', 'P,M,G,GG,XG', 'Brasil', 'Poliéster Dry-Fit', true, true, false, 100, true),
('Santos 2024 Titular', 'Camisa oficial do Santos para a temporada 2024', 279.90, 'Santos', 'BRASILEIRAO', 2024, 'TITULAR', 'Umbro', 'P,M,G,GG', 'Brasil', 'Poliéster', true, true, false, 80, true),
('Grêmio 2024 Titular', 'Camisa oficial do Grêmio para a temporada 2024', 289.90, 'Grêmio', 'BRASILEIRAO', 2024, 'TITULAR', 'Umbro', 'P,M,G,GG', 'Brasil', 'Poliéster', true, true, false, 80, true);

-- Camisas Europeias - Premier League
INSERT INTO camisas (nome, descricao, preco, time, liga, ano_temporada, tipo_camisa, marca, tamanhos_disponiveis, pais_time, material, numeracao_disponivel, nome_personalizacao_disponivel, edicao_limitada, estoque_quantidade, ativo)
VALUES 
('Manchester United 2024 Titular', 'Camisa oficial do Manchester United 2024', 399.90, 'Manchester United', 'PREMIER_LEAGUE', 2024, 'TITULAR', 'Adidas', 'P,M,G,GG,XG', 'Inglaterra', 'Poliéster Climacool', true, true, false, 120, true),
('Liverpool 2024 Titular', 'Camisa oficial do Liverpool 2024', 399.90, 'Liverpool', 'PREMIER_LEAGUE', 2024, 'TITULAR', 'Nike', 'P,M,G,GG,XG', 'Inglaterra', 'Poliéster Dri-FIT', true, true, false, 120, true),
('Manchester City 2024 Titular', 'Camisa oficial do Manchester City 2024', 399.90, 'Manchester City', 'PREMIER_LEAGUE', 2024, 'TITULAR', 'Puma', 'P,M,G,GG,XG', 'Inglaterra', 'Poliéster', true, true, false, 120, true),
('Arsenal 2024 Titular', 'Camisa oficial do Arsenal 2024', 399.90, 'Arsenal', 'PREMIER_LEAGUE', 2024, 'TITULAR', 'Adidas', 'P,M,G,GG,XG', 'Inglaterra', 'Poliéster', true, true, false, 100, true),
('Chelsea 2024 Titular', 'Camisa oficial do Chelsea 2024', 399.90, 'Chelsea', 'PREMIER_LEAGUE', 2024, 'TITULAR', 'Nike', 'P,M,G,GG,XG', 'Inglaterra', 'Poliéster', true, true, false, 100, true);

-- Camisas Europeias - La Liga
INSERT INTO camisas (nome, descricao, preco, time, liga, ano_temporada, tipo_camisa, marca, tamanhos_disponiveis, pais_time, material, numeracao_disponivel, nome_personalizacao_disponivel, edicao_limitada, estoque_quantidade, ativo)
VALUES 
('Real Madrid 2024 Titular', 'Camisa oficial do Real Madrid 2024', 449.90, 'Real Madrid', 'LA_LIGA', 2024, 'TITULAR', 'Adidas', 'P,M,G,GG,XG', 'Espanha', 'Poliéster Climacool', true, true, false, 150, true),
('Barcelona 2024 Titular', 'Camisa oficial do Barcelona 2024', 449.90, 'Barcelona', 'LA_LIGA', 2024, 'TITULAR', 'Nike', 'P,M,G,GG,XG', 'Espanha', 'Poliéster Dri-FIT', true, true, false, 150, true),
('Atlético Madrid 2024 Titular', 'Camisa oficial do Atlético Madrid 2024', 399.90, 'Atlético Madrid', 'LA_LIGA', 2024, 'TITULAR', 'Nike', 'P,M,G,GG', 'Espanha', 'Poliéster', true, true, false, 90, true);

-- Camisas Retrô
INSERT INTO camisas (nome, descricao, preco, time, liga, ano_temporada, tipo_camisa, marca, tamanhos_disponiveis, pais_time, material, numeracao_disponivel, nome_personalizacao_disponivel, edicao_limitada, estoque_quantidade, ativo)
VALUES 
('Flamengo 1981 Retrô', 'Camisa retrô do Flamengo campeão mundial de 1981', 349.90, 'Flamengo', 'BRASILEIRAO', 1981, 'RETRO', 'Adidas', 'P,M,G,GG', 'Brasil', 'Algodão', false, false, true, 50, true),
('Brasil 1970 Retrô', 'Camisa retrô da Seleção Brasileira tricampeã mundial', 399.90, 'Brasil', 'SELECAO_NACIONAL', 1970, 'RETRO', 'Topper', 'P,M,G,GG', 'Brasil', 'Algodão', false, false, true, 40, true),
('Santos 1962 Retrô Pelé', 'Camisa retrô do Santos época de Pelé', 379.90, 'Santos', 'BRASILEIRAO', 1962, 'RETRO', 'Umbro', 'P,M,G', 'Brasil', 'Algodão', true, false, true, 30, true),
('Milan 1989 Retrô', 'Camisa retrô do Milan campeão europeu', 449.90, 'Milan', 'SERIE_A', 1989, 'RETRO', 'Adidas', 'P,M,G,GG', 'Itália', 'Algodão', false, false, true, 35, true),
('Manchester United 1999 Retrô', 'Camisa retrô do Manchester United tríplice coroa', 449.90, 'Manchester United', 'PREMIER_LEAGUE', 1999, 'RETRO', 'Umbro', 'P,M,G,GG', 'Inglaterra', 'Algodão', false, false, true, 40, true);

-- ========================================
-- PLANOS DE ASSINATURA
-- ========================================

-- Plano Clássicos Brasileiros
INSERT INTO planos_camisa (nome, descricao, preco, max_produtos_por_periodo, frequencia_entrega, categoria_plano, liga_foco, permite_personalizacao, prioridade_lancamentos, desconto_loja, ativo)
VALUES 
('Clássicos Brasileiros', 'Receba mensalmente camisas dos principais times brasileiros. Ideal para o torcedor apaixonado pelo futebol nacional.', 189.90, 1, 'MENSAL', 'CLASSICOS', 'BRASILEIRAO', true, false, 10.00, true);

-- Plano Internacionais Premium
INSERT INTO planos_camisa (nome, descricao, preco, max_produtos_por_periodo, frequencia_entrega, categoria_plano, liga_foco, permite_personalizacao, prioridade_lancamentos, desconto_loja, ativo)
VALUES 
('Internacionais Premium', 'As melhores camisas das principais ligas europeias. Premier League, La Liga, Serie A e mais.', 299.90, 1, 'MENSAL', 'INTERNACIONAIS', NULL, true, true, 15.00, true);

-- Plano Retrô Nostálgico
INSERT INTO planos_camisa (nome, descricao, preco, max_produtos_por_periodo, frequencia_entrega, categoria_plano, liga_foco, permite_personalizacao, prioridade_lancamentos, desconto_loja, ativo)
VALUES 
('Retrô Nostálgico', 'Camisas históricas que marcaram época. Edições limitadas e colecionáveis.', 249.90, 1, 'MENSAL', 'RETRO', NULL, false, false, 5.00, true);

-- Plano Nacionais Completo
INSERT INTO planos_camisa (nome, descricao, preco, max_produtos_por_periodo, frequencia_entrega, categoria_plano, liga_foco, permite_personalizacao, prioridade_lancamentos, desconto_loja, ativo)
VALUES 
('Nacionais Completo', 'Plano premium com 2 camisas mensais de times brasileiros. Personalização incluída.', 349.90, 2, 'MENSAL', 'NACIONAIS', 'BRASILEIRAO', true, true, 20.00, true);

-- Plano Libertadores Especial
INSERT INTO planos_camisa (nome, descricao, preco, max_produtos_por_periodo, frequencia_entrega, categoria_plano, liga_foco, permite_personalizacao, prioridade_lancamentos, desconto_loja, ativo)
VALUES 
('Libertadores Especial', 'Camisas dos times que disputam a Copa Libertadores. Edições especiais da competição.', 269.90, 1, 'MENSAL', 'INTERNACIONAIS', 'LIBERTADORES', true, true, 12.00, true);

-- Plano Champions Collection
INSERT INTO planos_camisa (nome, descricao, preco, max_produtos_por_periodo, frequencia_entrega, categoria_plano, liga_foco, permite_personalizacao, prioridade_lancamentos, desconto_loja, ativo)
VALUES 
('Champions Collection', 'Exclusivo com camisas dos participantes da UEFA Champions League. Acesso antecipado a lançamentos.', 399.90, 2, 'MENSAL', 'INTERNACIONAIS', 'CHAMPIONS_LEAGUE', true, true, 25.00, true);

-- ========================================
-- INFORMAÇÕES ADICIONAIS
-- ========================================

-- Total de Camisas: 20 (6 Brasileiras + 8 Internacionais + 6 Retrô)
-- Total de Planos: 6 (Clássicos, Internacionais Premium, Retrô, Nacionais Completo, Libertadores, Champions)

-- Categorias de Planos:
-- - CLASSICOS: Foco em times tradicionais
-- - NACIONAIS: Times brasileiros
-- - INTERNACIONAIS: Times europeus e internacionais
-- - RETRO: Camisas históricas e colecionáveis

COMMIT;
