-- =====================================================
-- Script de População do Banco de Dados - Sistema de HQs
-- =====================================================

-- Limpar dados existentes (cuidado em produção!)
-- TRUNCATE TABLE item_pacote_hq CASCADE;
-- TRUNCATE TABLE pacotes_hq CASCADE;
-- TRUNCATE TABLE assinaturas_hq CASCADE;
-- TRUNCATE TABLE historico_hq_usuario CASCADE;
-- TRUNCATE TABLE preferencias_categorias CASCADE;
-- TRUNCATE TABLE preferencias_editoras CASCADE;
-- TRUNCATE TABLE preferencias_usuario CASCADE;
-- TRUNCATE TABLE planos_hq CASCADE;
-- TRUNCATE TABLE quadrinhos CASCADE;

-- =====================================================
-- 1. QUADRINHOS
-- =====================================================

-- Marvel Clássicas
INSERT INTO quadrinhos (nome, descricao, preco, url_imagem, editora, tipo_hq, categoria, edicao_colecionador, numero_edicao, ano_publicacao, titulo_serie, autor, ilustrador, estoque, ativo, pontos_ganho) VALUES
('Amazing Spider-Man #1', 'Primeira aparição do Homem-Aranha', 2999.90, 'spider-man-1.jpg', 'MARVEL', 'CLASSICA', 'SUPER_HEROI', true, 1, 1963, 'Amazing Spider-Man', 'Stan Lee', 'Steve Ditko', 3, true, 200),
('X-Men #1', 'Primeira aparição dos X-Men', 1999.90, 'xmen-1.jpg', 'MARVEL', 'CLASSICA', 'SUPER_HEROI', true, 1, 1963, 'X-Men', 'Stan Lee', 'Jack Kirby', 2, true, 200),
('Fantastic Four #1', 'Origem do Quarteto Fantástico', 2499.90, 'ff-1.jpg', 'MARVEL', 'CLASSICA', 'SUPER_HEROI', true, 1, 1961, 'Fantastic Four', 'Stan Lee', 'Jack Kirby', 1, true, 200),
('Avengers #1', 'Formação dos Vingadores', 1799.90, 'avengers-1.jpg', 'MARVEL', 'CLASSICA', 'SUPER_HEROI', true, 1, 1963, 'Avengers', 'Stan Lee', 'Jack Kirby', 4, true, 200),
('Iron Man #1', 'Primeira HQ solo do Homem de Ferro', 1299.90, 'ironman-1.jpg', 'MARVEL', 'CLASSICA', 'SUPER_HEROI', false, 1, 1968, 'Iron Man', 'Stan Lee', 'Don Heck', 5, true, 100),
('Thor #1', 'Primeira aparição de Thor', 1199.90, 'thor-1.jpg', 'MARVEL', 'CLASSICA', 'SUPER_HEROI', false, 1, 1962, 'Thor', 'Stan Lee', 'Jack Kirby', 6, true, 100),
('Daredevil #1', 'Origem do Demolidor', 999.90, 'daredevil-1.jpg', 'MARVEL', 'CLASSICA', 'SUPER_HEROI', false, 1, 1964, 'Daredevil', 'Stan Lee', 'Bill Everett', 7, true, 100),
('Hulk #1', 'Primeira aparição do Hulk', 1599.90, 'hulk-1.jpg', 'MARVEL', 'CLASSICA', 'SUPER_HEROI', true, 1, 1962, 'The Incredible Hulk', 'Stan Lee', 'Jack Kirby', 3, true, 200);

-- DC Clássicas
INSERT INTO quadrinhos (nome, descricao, preco, url_imagem, editora, tipo_hq, categoria, edicao_colecionador, numero_edicao, ano_publicacao, titulo_serie, autor, ilustrador, estoque, ativo, pontos_ganho) VALUES
('Action Comics #1', 'Primeira aparição do Superman', 9999.90, 'action-1.jpg', 'DC', 'CLASSICA', 'SUPER_HEROI', true, 1, 1938, 'Action Comics', 'Jerry Siegel', 'Joe Shuster', 1, true, 200),
('Detective Comics #27', 'Primeira aparição do Batman', 7999.90, 'detective-27.jpg', 'DC', 'CLASSICA', 'SUPER_HEROI', true, 27, 1939, 'Detective Comics', 'Bill Finger', 'Bob Kane', 1, true, 200),
('Flash #1', 'Primeira aparição do Flash', 1499.90, 'flash-1.jpg', 'DC', 'CLASSICA', 'SUPER_HEROI', true, 1, 1940, 'Flash Comics', 'Gardner Fox', 'Harry Lampert', 2, true, 200),
('Wonder Woman #1', 'Primeira HQ solo da Mulher Maravilha', 1899.90, 'ww-1.jpg', 'DC', 'CLASSICA', 'SUPER_HEROI', true, 1, 1942, 'Wonder Woman', 'William Moulton', 'Harry Peter', 3, true, 200),
('Green Lantern #1', 'Primeira aparição do Lanterna Verde', 1299.90, 'gl-1.jpg', 'DC', 'CLASSICA', 'SUPER_HEROI', false, 1, 1941, 'Green Lantern', 'Bill Finger', 'Martin Nodell', 4, true, 100),
('Justice League #1', 'Formação da Liga da Justiça', 899.90, 'jl-1.jpg', 'DC', 'CLASSICA', 'SUPER_HEROI', false, 1, 1960, 'Justice League', 'Gardner Fox', 'Mike Sekowsky', 6, true, 100);

-- Marvel Modernas
INSERT INTO quadrinhos (nome, descricao, preco, url_imagem, editora, tipo_hq, categoria, edicao_colecionador, numero_edicao, ano_publicacao, titulo_serie, autor, ilustrador, estoque, ativo, pontos_ganho) VALUES
('Civil War #1', 'Início da Guerra Civil Marvel', 149.90, 'civil-war-1.jpg', 'MARVEL', 'MODERNA', 'SUPER_HEROI', true, 1, 2006, 'Civil War', 'Mark Millar', 'Steve McNiven', 10, true, 100),
('Infinity Gauntlet #1', 'Saga da Manopla do Infinito', 179.90, 'infinity-1.jpg', 'MARVEL', 'MODERNA', 'SUPER_HEROI', true, 1, 1991, 'Infinity Gauntlet', 'Jim Starlin', 'George Perez', 8, true, 100),
('Spider-Man: Blue #1', 'Romance de Peter e Gwen', 99.90, 'spider-blue-1.jpg', 'MARVEL', 'MODERNA', 'SUPER_HEROI', false, 1, 2002, 'Spider-Man: Blue', 'Jeph Loeb', 'Tim Sale', 12, true, 50),
('Old Man Logan #1', 'Wolverine no futuro distópico', 119.90, 'old-logan-1.jpg', 'MARVEL', 'MODERNA', 'SUPER_HEROI', false, 1, 2008, 'Old Man Logan', 'Mark Millar', 'Steve McNiven', 15, true, 50),
('Planet Hulk #1', 'Hulk exilado no espaço', 109.90, 'planet-hulk-1.jpg', 'MARVEL', 'MODERNA', 'SUPER_HEROI', false, 1, 2006, 'Planet Hulk', 'Greg Pak', 'Carlo Pagulayan', 10, true, 50),
('Ms. Marvel #1', 'Nova Ms. Marvel (Kamala Khan)', 89.90, 'ms-marvel-1.jpg', 'MARVEL', 'MODERNA', 'SUPER_HEROI', false, 1, 2014, 'Ms. Marvel', 'G. Willow Wilson', 'Adrian Alphona', 20, true, 50),
('Black Panther #1', 'Pantera Negra de Ta-Nehisi Coates', 99.90, 'bp-1.jpg', 'MARVEL', 'MODERNA', 'SUPER_HEROI', false, 1, 2016, 'Black Panther', 'Ta-Nehisi Coates', 'Brian Stelfreeze', 18, true, 50);

-- DC Modernas
INSERT INTO quadrinhos (nome, descricao, preco, url_imagem, editora, tipo_hq, categoria, edicao_colecionador, numero_edicao, ano_publicacao, titulo_serie, autor, ilustrador, estoque, ativo, pontos_ganho) VALUES
('Batman: Year One #1', 'Origem moderna do Batman', 139.90, 'year-one-1.jpg', 'DC', 'MODERNA', 'SUPER_HEROI', true, 1, 1987, 'Batman: Year One', 'Frank Miller', 'David Mazzucchelli', 8, true, 100),
('The Killing Joke', 'Clássico do Coringa', 159.90, 'killing-joke.jpg', 'DC', 'MODERNA', 'SUPER_HEROI', true, 1, 1988, 'Batman: The Killing Joke', 'Alan Moore', 'Brian Bolland', 6, true, 100),
('All-Star Superman #1', 'Superman de Grant Morrison', 119.90, 'allstar-supes-1.jpg', 'DC', 'MODERNA', 'SUPER_HEROI', false, 1, 2005, 'All-Star Superman', 'Grant Morrison', 'Frank Quitely', 12, true, 50),
('Flashpoint #1', 'Evento que reiniciou a DC', 129.90, 'flashpoint-1.jpg', 'DC', 'MODERNA', 'SUPER_HEROI', false, 1, 2011, 'Flashpoint', 'Geoff Johns', 'Andy Kubert', 10, true, 50),
('Dark Nights: Metal #1', 'Batmans do Multiverso Sombrio', 109.90, 'dark-metal-1.jpg', 'DC', 'MODERNA', 'SUPER_HEROI', false, 1, 2017, 'Dark Nights: Metal', 'Scott Snyder', 'Greg Capullo', 15, true, 50),
('Rebirth #1', 'Reinício do universo DC', 99.90, 'rebirth-1.jpg', 'DC', 'MODERNA', 'SUPER_HEROI', false, 1, 2016, 'DC Universe: Rebirth', 'Geoff Johns', 'Gary Frank', 20, true, 50);

-- Mangás
INSERT INTO quadrinhos (nome, descricao, preco, url_imagem, editora, tipo_hq, categoria, edicao_colecionador, numero_edicao, ano_publicacao, titulo_serie, autor, ilustrador, estoque, ativo, pontos_ganho) VALUES
('Naruto #1', 'Início da saga ninja', 29.90, 'naruto-1.jpg', 'OUTROS', 'MODERNA', 'MANGA', false, 1, 1999, 'Naruto', 'Masashi Kishimoto', 'Masashi Kishimoto', 25, true, 50),
('One Piece #1', 'Romance Dawn - O Começo', 29.90, 'onepiece-1.jpg', 'OUTROS', 'MODERNA', 'MANGA', false, 1, 1997, 'One Piece', 'Eiichiro Oda', 'Eiichiro Oda', 30, true, 50),
('Death Note #1', 'O caderno da morte', 34.90, 'deathnote-1.jpg', 'OUTROS', 'MODERNA', 'MANGA', false, 1, 2003, 'Death Note', 'Tsugumi Ohba', 'Takeshi Obata', 20, true, 50),
('Attack on Titan #1', 'Humanidade vs Titãs', 34.90, 'aot-1.jpg', 'OUTROS', 'MODERNA', 'MANGA', false, 1, 2009, 'Attack on Titan', 'Hajime Isayama', 'Hajime Isayama', 22, true, 50);

-- =====================================================
-- 2. PLANOS
-- =====================================================

INSERT INTO planos_hq (nome, descricao, preco, max_produtos_por_periodo, frequencia_entrega, percentual_classicas, percentual_modernas, pontos_bonus_mensal, inclui_edicoes_colecionador, nivel_curadoria, ativo) VALUES
('Plano Iniciante', 'Perfeito para quem está começando no mundo dos quadrinhos', 79.90, 2, 'MENSAL', 30, 70, 50, false, 'BASICO', true),
('Plano Colecionador', 'Para verdadeiros colecionadores de HQs clássicas', 159.90, 3, 'MENSAL', 70, 30, 150, true, 'INTERMEDIARIO', true),
('Plano Premium', 'Curadoria exclusiva com edições raras', 249.90, 4, 'MENSAL', 50, 50, 300, true, 'PREMIUM', true),
('Plano Moderno', 'Focado em lançamentos e HQs recentes', 99.90, 3, 'MENSAL', 20, 80, 80, false, 'INTERMEDIARIO', true),
('Plano Clássico', 'Para os amantes das HQs vintage', 189.90, 3, 'MENSAL', 100, 0, 200, true, 'PREMIUM', true);

-- =====================================================
-- 3. DADOS DE EXEMPLO (Opcional - comentado)
-- =====================================================

-- Exemplo de preferência de usuário (requer user_id válido)
-- INSERT INTO preferencias_usuario (user_id, prefere_classicas, prefere_modernas, interesse_edicoes_colecionador, quiz_completo, data_criacao) 
-- VALUES (1, true, false, true, true, NOW());

-- Exemplo de categorias favoritas (requer preferencia_id válido)
-- INSERT INTO preferencias_categorias (preferencia_id, categoria) VALUES 
-- (1, 'SUPER_HEROI'),
-- (1, 'MANGA');

-- Exemplo de editoras favoritas (requer preferencia_id válido)
-- INSERT INTO preferencias_editoras (preferencia_id, editora) VALUES 
-- (1, 'MARVEL'),
-- (1, 'DC');

-- =====================================================
-- CONSULTAS ÚTEIS
-- =====================================================

-- Ver todos os quadrinhos com estoque
SELECT nome, editora, tipo_hq, categoria, pontos_ganho, estoque, preco 
FROM quadrinhos 
WHERE estoque > 0 AND ativo = true 
ORDER BY tipo_hq, editora, nome;

-- Ver planos disponíveis
SELECT nome, descricao, preco, max_produtos_por_periodo, percentual_classicas, percentual_modernas, pontos_bonus_mensal 
FROM planos_hq 
WHERE ativo = true 
ORDER BY preco;

-- Contar quadrinhos por tipo
SELECT tipo_hq, COUNT(*) as total, SUM(estoque) as estoque_total 
FROM quadrinhos 
WHERE ativo = true 
GROUP BY tipo_hq;

-- Contar quadrinhos por editora
SELECT editora, COUNT(*) as total, SUM(estoque) as estoque_total 
FROM quadrinhos 
WHERE ativo = true 
GROUP BY editora;

-- Ver edições de colecionador
SELECT nome, editora, tipo_hq, ano_publicacao, pontos_ganho, preco 
FROM quadrinhos 
WHERE edicao_colecionador = true AND ativo = true 
ORDER BY preco DESC;
