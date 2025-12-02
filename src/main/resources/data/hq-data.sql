-- ===============================================
-- DADOS INICIAIS PARA SISTEMA DE HQS
-- ===============================================

BEGIN;

-- ========== PLANOS DE ASSINATURA DE HQ ==========

INSERT INTO planos_hq (nome, descricao, preco, max_produtos_por_periodo, frequencia_entrega, ativo, tipo_plano, percentual_classicas, percentual_modernas, multiplicador_pontos) VALUES
                                                                                                                                                                                     ('HQ Clássico', 'Apenas as melhores HQs clássicas dos anos 60-90. Ideal para nostálgicos e colecionadores.', 49.90, 2, 'MENSAL', TRUE, 'CLASSICO', 100, 0, 1.0),
                                                                                                                                                                                     ('HQ Moderno', 'As HQs mais recentes e inovadoras do mercado. Para quem gosta de acompanhar os lançamentos.', 59.90, 3, 'MENSAL', TRUE, 'MODERNO', 0, 100, 1.2),
                                                                                                                                                                                     ('HQ Misto Premium', 'Combinação perfeita de clássicos atemporais e lançamentos modernos.', 89.90, 4, 'MENSAL', TRUE, 'MISTO', 50, 50, 1.5),
                                                                                                                                                                                     ('HQ Colecionador', 'Plano premium com edições especiais, variantes e HQs de colecionador.', 149.90, 5, 'MENSAL', TRUE, 'MISTO', 40, 60, 2.0);

-- ========== HQS - MARVEL CLÁSSICAS ==========

INSERT INTO produtos (nome, descricao, preco, url_imagem, ativo) VALUES
                                                                     ('Amazing Spider-Man #1 (1963)', 'A primeira aparição do Homem-Aranha em revista própria', 299.90, NULL, TRUE),
                                                                     ('Fantastic Four #1 (1961)', 'A HQ que deu início ao Universo Marvel moderno', 399.90, NULL, TRUE),
                                                                     ('X-Men #1 (1963)', 'A formação original dos X-Men', 249.90, NULL, TRUE),
                                                                     ('Avengers #1 (1963)', 'Os Vingadores se reúnem pela primeira vez', 279.90, NULL, TRUE),
                                                                     ('Iron Man Tales of Suspense #39', 'Primeira aparição do Homem de Ferro', 189.90, NULL, TRUE);

-- Dados específicos das HQs Marvel Clássicas
INSERT INTO hqs (id, tipo_hq, categoria, editora, autor, desenhista, ano_publicacao, numero_edicao, numero_paginas, isbn, edicao_colecionador, sinopse, pontos_ganho)
SELECT p.id, 'CLASSICA', 'MARVEL', 'MARVEL', 'Stan Lee', 'Steve Ditko', 1963, 1, 32, NULL, TRUE,
       'A origem do Homem-Aranha! Peter Parker é picado por uma aranha radioativa e ganha poderes incríveis.', 50
FROM produtos p WHERE p.nome = 'Amazing Spider-Man #1 (1963)';

INSERT INTO hqs (id, tipo_hq, categoria, editora, autor, desenhista, ano_publicacao, numero_edicao, numero_paginas, isbn, edicao_colecionador, sinopse, pontos_ganho)
SELECT p.id, 'CLASSICA', 'MARVEL', 'MARVEL', 'Stan Lee', 'Jack Kirby', 1961, 1, 32, NULL, TRUE,
       'Reed Richards, Sue Storm, Johnny Storm e Ben Grimm ganham poderes cósmicos após uma missão espacial.', 60
FROM produtos p WHERE p.nome = 'Fantastic Four #1 (1961)';

INSERT INTO hqs (id, tipo_hq, categoria, editora, autor, desenhista, ano_publicacao, numero_edicao, numero_paginas, isbn, edicao_colecionador, sinopse, pontos_ganho)
SELECT p.id, 'CLASSICA', 'MARVEL', 'MARVEL', 'Stan Lee', 'Jack Kirby', 1963, 1, 32, NULL, TRUE,
       'Professor Xavier reúne cinco jovens mutantes para formar a primeira equipe de X-Men.', 45
FROM produtos p WHERE p.nome = 'X-Men #1 (1963)';

INSERT INTO hqs (id, tipo_hq, categoria, editora, autor, desenhista, ano_publicacao, numero_edicao, numero_paginas, isbn, edicao_colecionador, sinopse, pontos_ganho)
SELECT p.id, 'CLASSICA', 'MARVEL', 'MARVEL', 'Stan Lee', 'Jack Kirby', 1963, 1, 32, NULL, TRUE,
       'Iron Man, Thor, Hulk, Ant-Man e Wasp se unem para combater Loki.', 50
FROM produtos p WHERE p.nome = 'Avengers #1 (1963)';

INSERT INTO hqs (id, tipo_hq, categoria, editora, autor, desenhista, ano_publicacao, numero_edicao, numero_paginas, isbn, edicao_colecionador, sinopse, pontos_ganho)
SELECT p.id, 'CLASSICA', 'MARVEL', 'MARVEL', 'Stan Lee', 'Don Heck', 1963, 39, 32, NULL, TRUE,
       'Tony Stark cria a primeira armadura do Homem de Ferro para escapar do cativeiro.', 40
FROM produtos p WHERE p.nome = 'Iron Man Tales of Suspense #39';

-- ========== HQS - DC CLÁSSICAS ==========

INSERT INTO produtos (nome, descricao, preco, url_imagem, ativo) VALUES
                                                                     ('Batman: The Killing Joke', 'A história definitiva do Coringa por Alan Moore', 79.90, NULL, TRUE),
                                                                     ('Watchmen #1', 'A revolucionária HQ de Alan Moore e Dave Gibbons', 89.90, NULL, TRUE),
                                                                     ('Crisis on Infinite Earths #1', 'O evento que mudou o Universo DC para sempre', 69.90, NULL, TRUE),
                                                                     ('The Dark Knight Returns #1', 'Frank Miller reinventa Batman', 99.90, NULL, TRUE);

INSERT INTO hqs (id, tipo_hq, categoria, editora, autor, desenhista, ano_publicacao, numero_edicao, numero_paginas, isbn, edicao_colecionador, sinopse, pontos_ganho)
SELECT p.id, 'CLASSICA', 'DC', 'DC', 'Alan Moore', 'Brian Bolland', 1988, 1, 48, '978-1401216672', TRUE,
       'Uma piada mortal que explora a complexa relação entre Batman e o Coringa.', 55
FROM produtos p WHERE p.nome = 'Batman: The Killing Joke';

INSERT INTO hqs (id, tipo_hq, categoria, editora, autor, desenhista, ano_publicacao, numero_edicao, numero_paginas, isbn, edicao_colecionador, sinopse, pontos_ganho)
SELECT p.id, 'CLASSICA', 'DC', 'DC', 'Alan Moore', 'Dave Gibbons', 1986, 1, 32, '978-1401245252', TRUE,
       'Quem vigia os vigilantes? A obra-prima que desconstruiu o gênero de super-heróis.', 60
FROM produtos p WHERE p.nome = 'Watchmen #1';

INSERT INTO hqs (id, tipo_hq, categoria, editora, autor, desenhista, ano_publicacao, numero_edicao, numero_paginas, isbn, edicao_colecionador, sinopse, pontos_ganho)
SELECT p.id, 'CLASSICA', 'DC', 'DC', 'Marv Wolfman', 'George Pérez', 1985, 1, 32, NULL, TRUE,
       'O Anti-Monitor ameaça destruir todo o multiverso. Os heróis devem se unir para sobreviver.', 50
FROM produtos p WHERE p.nome = 'Crisis on Infinite Earths #1';

INSERT INTO hqs (id, tipo_hq, categoria, editora, autor, desenhista, ano_publicacao, numero_edicao, numero_paginas, isbn, edicao_colecionador, sinopse, pontos_ganho)
SELECT p.id, 'CLASSICA', 'DC', 'DC', 'Frank Miller', 'Frank Miller', 1986, 1, 48, '978-1563893421', TRUE,
       'Bruce Wayne sai da aposentadoria em uma Gotham City distópica.', 65
FROM produtos p WHERE p.nome = 'The Dark Knight Returns #1';

-- ========== HQS - MARVEL MODERNAS ==========

INSERT INTO produtos (nome, descricao, preco, url_imagem, ativo) VALUES
                                                                     ('Ultimate Spider-Man #1 (2024)', 'Nova era do Homem-Aranha no Universo Ultimate', 34.90, NULL, TRUE),
                                                                     ('Immortal Hulk #1', 'Al Ewing reinventa o Hulk como terror', 39.90, NULL, TRUE),
                                                                     ('House of X #1', 'Jonathan Hickman revoluciona os X-Men', 44.90, NULL, TRUE),
                                                                     ('Thor (2020) #1', 'Donny Cates assume o Deus do Trovão', 37.90, NULL, TRUE),
                                                                     ('Venom (2023) #1', 'Nova fase do simbionte alienígena', 35.90, NULL, TRUE);

INSERT INTO hqs (id, tipo_hq, categoria, editora, autor, desenhista, ano_publicacao, numero_edicao, numero_paginas, isbn, edicao_colecionador, sinopse, pontos_ganho)
SELECT p.id, 'MODERNA', 'MARVEL', 'MARVEL', 'Jonathan Hickman', 'Marco Checchetto', 2024, 1, 32, NULL, FALSE,
       'Uma nova abordagem do Homem-Aranha em um universo reimaginado.', 25
FROM produtos p WHERE p.nome = 'Ultimate Spider-Man #1 (2024)';

INSERT INTO hqs (id, tipo_hq, categoria, editora, autor, desenhista, ano_publicacao, numero_edicao, numero_paginas, isbn, edicao_colecionador, sinopse, pontos_ganho)
SELECT p.id, 'MODERNA', 'MARVEL', 'MARVEL', 'Al Ewing', 'Joe Bennett', 2018, 1, 32, NULL, FALSE,
       'Você está com medo? Deveria estar. O Hulk imortal está de volta.', 30
FROM produtos p WHERE p.nome = 'Immortal Hulk #1';

INSERT INTO hqs (id, tipo_hq, categoria, editora, autor, desenhista, ano_publicacao, numero_edicao, numero_paginas, isbn, edicao_colecionador, sinopse, pontos_ganho)
SELECT p.id, 'MODERNA', 'MARVEL', 'MARVEL', 'Jonathan Hickman', 'Pepe Larraz', 2019, 1, 40, NULL, FALSE,
       'Os mutantes finalmente tem um lar. Mas a que custo? A era Krakoa começa aqui.', 35
FROM produtos p WHERE p.nome = 'House of X #1';

INSERT INTO hqs (id, tipo_hq, categoria, editora, autor, desenhista, ano_publicacao, numero_edicao, numero_paginas, isbn, edicao_colecionador, sinopse, pontos_ganho)
SELECT p.id, 'MODERNA', 'MARVEL', 'MARVEL', 'Donny Cates', 'Nic Klein', 2020, 1, 32, NULL, FALSE,
       'Thor enfrenta a ira de Galactus, o Devorador de Mundos, em uma batalha épica.', 28
FROM produtos p WHERE p.nome = 'Thor (2020) #1';

INSERT INTO hqs (id, tipo_hq, categoria, editora, autor, desenhista, ano_publicacao, numero_edicao, numero_paginas, isbn, edicao_colecionador, sinopse, pontos_ganho)
SELECT p.id, 'MODERNA', 'MARVEL', 'MARVEL', 'Torunn Grønbekk', 'Cafu', 2023, 1, 32, NULL, FALSE,
       'Eddie Brock e o simbionte enfrentam novos desafios em uma era renovada.', 26
FROM produtos p WHERE p.nome = 'Venom (2023) #1';

-- ========== HQS - DC MODERNAS ==========

INSERT INTO produtos (nome, descricao, preco, url_imagem, ativo) VALUES
                                                                     ('Batman (2024) #1', 'Chip Zdarsky assume o Cavaleiro das Trevas', 38.90, NULL, TRUE),
                                                                     ('Superman (2023) #1', 'Joshua Williamson redefine o Homem de Aço', 36.90, NULL, TRUE),
                                                                     ('Wonder Woman (2024) #1', 'Tom King traz nova perspectiva à Amazona', 37.90, NULL, TRUE),
                                                                     ('The Flash (2023) #1', 'O Velocista Escarlate em nova velocidade', 34.90, NULL, TRUE);

INSERT INTO hqs (id, tipo_hq, categoria, editora, autor, desenhista, ano_publicacao, numero_edicao, numero_paginas, isbn, edicao_colecionador, sinopse, pontos_ganho)
SELECT p.id, 'MODERNA', 'DC', 'DC', 'Chip Zdarsky', 'Jorge Jiménez', 2024, 1, 32, NULL, FALSE,
       'Uma nova ameaça surge em Gotham. Batman deve enfrentar seu passado para proteger o futuro.', 27
FROM produtos p WHERE p.nome = 'Batman (2024) #1';

INSERT INTO hqs (id, tipo_hq, categoria, editora, autor, desenhista, ano_publicacao, numero_edicao, numero_paginas, isbn, edicao_colecionador, sinopse, pontos_ganho)
SELECT p.id, 'MODERNA', 'DC', 'DC', 'Joshua Williamson', 'Jamal Campbell', 2023, 1, 32, NULL, FALSE,
       'Superman enfrenta uma conspiração intergaláctica que ameaça toda a Terra.', 29
FROM produtos p WHERE p.nome = 'Superman (2023) #1';

INSERT INTO hqs (id, tipo_hq, categoria, editora, autor, desenhista, ano_publicacao, numero_edicao, numero_paginas, isbn, edicao_colecionador, sinopse, pontos_ganho)
SELECT p.id, 'MODERNA', 'DC', 'DC', 'Tom King', 'Daniel Sampere', 2024, 1, 32, NULL, FALSE,
       'Diana Prince deve escolher entre ser Wonder Woman ou viver uma vida normal.', 30
FROM produtos p WHERE p.nome = 'Wonder Woman (2024) #1';

INSERT INTO hqs (id, tipo_hq, categoria, editora, autor, desenhista, ano_publicacao, numero_edicao, numero_paginas, isbn, edicao_colecionador, sinopse, pontos_ganho)
SELECT p.id, 'MODERNA', 'DC', 'DC', 'Simon Spurrier', 'Mike Deodato Jr', 2023, 1, 32, NULL, FALSE,
       'Barry Allen descobre que sua velocidade está causando rachaduras na Força de Aceleração.', 26
FROM produtos p WHERE p.nome = 'The Flash (2023) #1';

-- ========== PACOTES DE EXEMPLO ==========

INSERT INTO pacotes_hq (nome, descricao, data_entrega, periodo, mes, ano, plano_id, ativo, tema_mes)
SELECT 'Pacote Clássico - Janeiro 2025', 'Especial Origens Marvel', '2025-01-15', 1, 1, 2025, id, TRUE, 'Origens dos Heróis'
FROM planos_hq WHERE nome = 'HQ Clássico';

INSERT INTO pacotes_hq (nome, descricao, data_entrega, periodo, mes, ano, plano_id, ativo, tema_mes)
SELECT 'Pacote Moderno - Janeiro 2025', 'Novidades de 2024', '2025-01-15', 1, 1, 2025, id, TRUE, 'O Melhor de 2024'
FROM planos_hq WHERE nome = 'HQ Moderno';

INSERT INTO pacotes_hq (nome, descricao, data_entrega, periodo, mes, ano, plano_id, ativo, tema_mes)
SELECT 'Pacote Misto - Janeiro 2025', 'Clássicos e Modernos', '2025-01-15', 1, 1, 2025, id, TRUE, 'Melhor dos Dois Mundos'
FROM planos_hq WHERE nome = 'HQ Misto Premium';

-- Itens do Pacote Clássico
INSERT INTO item_pacote_hq (pacote_id, hq_id, quantidade, observacoes, edicao_especial)
SELECT pac.id, p.id, 1, 'Edição de colecionador', TRUE
FROM pacotes_hq pac, produtos p
WHERE pac.nome = 'Pacote Clássico - Janeiro 2025'
  AND p.nome = 'Amazing Spider-Man #1 (1963)';

INSERT INTO item_pacote_hq (pacote_id, hq_id, quantidade, observacoes, edicao_especial)
SELECT pac.id, p.id, 1, 'Primeira edição rara', TRUE
FROM pacotes_hq pac, produtos p
WHERE pac.nome = 'Pacote Clássico - Janeiro 2025'
  AND p.nome = 'X-Men #1 (1963)';

-- Itens do Pacote Moderno
INSERT INTO item_pacote_hq (pacote_id, hq_id, quantidade, observacoes, edicao_especial)
SELECT pac.id, p.id, 1, 'Lançamento recente', FALSE
FROM pacotes_hq pac, produtos p
WHERE pac.nome = 'Pacote Moderno - Janeiro 2025'
  AND p.nome = 'Ultimate Spider-Man #1 (2024)';

INSERT INTO item_pacote_hq (pacote_id, hq_id, quantidade, observacoes, edicao_especial)
SELECT pac.id, p.id, 1, 'Nova fase aclamada', FALSE
FROM pacotes_hq pac, produtos p
WHERE pac.nome = 'Pacote Moderno - Janeiro 2025'
  AND p.nome = 'Batman (2024) #1';

INSERT INTO item_pacote_hq (pacote_id, hq_id, quantidade, observacoes, edicao_especial)
SELECT pac.id, p.id, 1, 'Série premiada', FALSE
FROM pacotes_hq pac, produtos p
WHERE pac.nome = 'Pacote Moderno - Janeiro 2025'
  AND p.nome = 'House of X #1';

-- Itens do Pacote Misto
INSERT INTO item_pacote_hq (pacote_id, hq_id, quantidade, observacoes, edicao_especial)
SELECT pac.id, p.id, 1, 'Clássico atemporal', TRUE
FROM pacotes_hq pac, produtos p
WHERE pac.nome = 'Pacote Misto - Janeiro 2025'
  AND p.nome = 'Batman: The Killing Joke';

INSERT INTO item_pacote_hq (pacote_id, hq_id, quantidade, observacoes, edicao_especial)
SELECT pac.id, p.id, 1, 'Clássico revolucionário', TRUE
FROM pacotes_hq pac, produtos p
WHERE pac.nome = 'Pacote Misto - Janeiro 2025'
  AND p.nome = 'Watchmen #1';

INSERT INTO item_pacote_hq (pacote_id, hq_id, quantidade, observacoes, edicao_especial)
SELECT pac.id, p.id, 1, 'Moderno aclamado', FALSE
FROM pacotes_hq pac, produtos p
WHERE pac.nome = 'Pacote Misto - Janeiro 2025'
  AND p.nome = 'Immortal Hulk #1';

INSERT INTO item_pacote_hq (pacote_id, hq_id, quantidade, observacoes, edicao_especial)
SELECT pac.id, p.id, 1, 'Lançamento recente', FALSE
FROM pacotes_hq pac, produtos p
WHERE pac.nome = 'Pacote Misto - Janeiro 2025'
  AND p.nome = 'Superman (2023) #1';

COMMIT;

-- Verificar dados inseridos
SELECT 'PLANOS_HQ' as tabela, COUNT(*) as total FROM planos_hq
UNION ALL SELECT 'HQS', COUNT(*) FROM hqs
UNION ALL SELECT 'PACOTES_HQ', COUNT(*) FROM pacotes_hq
UNION ALL SELECT 'ITEM_PACOTE_HQ', COUNT(*) FROM item_pacote_hq
ORDER BY tabela;