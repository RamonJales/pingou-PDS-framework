-- ============================================================================
-- SCRIPT DE SEED PARA MÓDULO DE CAMISAS DE FUTEBOL
-- ============================================================================
-- Este script popula o banco de dados com dados de exemplo para testes
-- do sistema de assinatura de camisas de futebol.
-- 
-- Executar após a aplicação criar as tabelas automaticamente (ddl-auto=update)
-- ============================================================================

-- ============================================================================
-- 1. USUÁRIOS DE TESTE
-- ============================================================================

-- Senha padrão para todos: 'senha123' (hash BCrypt)
INSERT INTO users (email, nome, sobrenome, password, role)
VALUES 
    ('admin@camisa10.com', 'Admin', 'Sistema', '$2a$10$N9qo8uLOickgx2ZMRZoMy.MqrxPhkNmUK/rMnxpbYqdP3qQ5Q6eNm', 'ADMIN'),
    ('joao@email.com', 'João', 'Silva', '$2a$10$N9qo8uLOickgx2ZMRZoMy.MqrxPhkNmUK/rMnxpbYqdP3qQ5Q6eNm', 'USER'),
    ('maria@email.com', 'Maria', 'Santos', '$2a$10$N9qo8uLOickgx2ZMRZoMy.MqrxPhkNmUK/rMnxpbYqdP3qQ5Q6eNm', 'USER'),
    ('pedro@email.com', 'Pedro', 'Oliveira', '$2a$10$N9qo8uLOickgx2ZMRZoMy.MqrxPhkNmUK/rMnxpbYqdP3qQ5Q6eNm', 'USER'),
    ('ana@email.com', 'Ana', 'Costa', '$2a$10$N9qo8uLOickgx2ZMRZoMy.MqrxPhkNmUK/rMnxpbYqdP3qQ5Q6eNm', 'USER'),
    ('familia@email.com', 'Carlos', 'Família', '$2a$10$N9qo8uLOickgx2ZMRZoMy.MqrxPhkNmUK/rMnxpbYqdP3qQ5Q6eNm', 'USER')
ON CONFLICT (email) DO NOTHING;

-- ============================================================================
-- 2. PLANOS DE ASSINATURA DE FUTEBOL
-- ============================================================================

INSERT INTO planos_futebol (
    nome, descricao, preco, ativo, max_produtos_por_periodo, frequencia_entrega,
    tipo_plano, camisas_por_membro, personalizacao_inclusa, prioridade_edicao_limitada,
    inclui_selecoes, trocas_tamanho_ano, permite_escolha_time, desconto_camisa_adicional, frete_gratis
) VALUES 
-- Plano Individual Básico
(
    'Torcedor',
    'Plano individual perfeito para quem quer começar sua coleção. Receba 1 camisa por mês do seu time favorito.',
    89.90,
    true,
    1,
    'MENSAL',
    'INDIVIDUAL',
    1,
    false,
    false,
    false,
    1,
    true,
    0.00,
    false
),
-- Plano Individual Premium
(
    'Craque',
    'Para o torcedor apaixonado! 1 camisa por mês com personalização inclusa (nome e número) e frete grátis.',
    149.90,
    true,
    1,
    'MENSAL',
    'INDIVIDUAL',
    1,
    true,
    true,
    false,
    2,
    true,
    10.00,
    true
),
-- Plano Casal
(
    'Dupla de Ataque',
    'Plano para casais apaixonados por futebol! 2 camisas por mês com tamanhos diferentes para cada um.',
    169.90,
    true,
    2,
    'MENSAL',
    'CASAL',
    1,
    true,
    true,
    true,
    3,
    true,
    15.00,
    true
),
-- Plano Família Pequena
(
    'Família Pequena',
    'Ideal para famílias de até 3 pessoas. Cada membro recebe seu tamanho de camisa.',
    239.90,
    true,
    3,
    'MENSAL',
    'FAMILIA_PEQUENA',
    1,
    true,
    true,
    true,
    4,
    true,
    20.00,
    true
),
-- Plano Família
(
    'Família Unida',
    'Para famílias de até 5 pessoas! Todos vestidos com a camisa do time do coração. Tamanhos adultos e infantis.',
    369.90,
    true,
    5,
    'MENSAL',
    'FAMILIA',
    1,
    true,
    true,
    true,
    5,
    true,
    25.00,
    true
),
-- Plano Família Grande
(
    'Família Completa',
    'Para famílias grandes de até 8 pessoas. Cada membro com seu tamanho ideal.',
    529.90,
    true,
    8,
    'MENSAL',
    'FAMILIA_GRANDE',
    1,
    true,
    true,
    true,
    8,
    true,
    30.00,
    true
),
-- Plano Torcida
(
    'Torcida Organizada',
    'Para grupos de até 12 pessoas! Perfeito para amigos, escritórios ou torcidas organizadas.',
    799.90,
    true,
    12,
    'MENSAL',
    'TORCIDA',
    1,
    true,
    true,
    true,
    12,
    true,
    35.00,
    true
);

-- ============================================================================
-- 3. CAMISAS DE FUTEBOL (CATÁLOGO)
-- ============================================================================

INSERT INTO camisas_futebol (
    nome, descricao, preco, ativo, time, pais, temporada, tipo_camisa,
    competicao, marca, permite_personalizacao, custo_personalizacao,
    material, versao_jogador, oficial, estoque_total, url_imagem
) VALUES
-- BRASILEIRÃO - SÉRIE A
-- Flamengo
(
    'Camisa Flamengo 2024/2025 Principal',
    'Camisa oficial do Flamengo temporada 2024/2025. Vermelho e preto, as cores da paixão rubro-negra.',
    299.90,
    true,
    'Flamengo',
    'Brasil',
    '2024/2025',
    'PRINCIPAL',
    'BRASILEIRAO_SERIE_A',
    'Adidas',
    true,
    49.90,
    'Dri-FIT',
    false,
    true,
    500,
    'https://images.camisa10.com/flamengo-principal-2425.jpg'
),
(
    'Camisa Flamengo 2024/2025 Reserva',
    'Camisa reserva do Flamengo, branca com detalhes em vermelho e preto.',
    289.90,
    true,
    'Flamengo',
    'Brasil',
    '2024/2025',
    'RESERVA',
    'BRASILEIRAO_SERIE_A',
    'Adidas',
    true,
    49.90,
    'Dri-FIT',
    false,
    true,
    300,
    'https://images.camisa10.com/flamengo-reserva-2425.jpg'
),
(
    'Camisa Flamengo 2024/2025 Terceira',
    'Terceira camisa do Flamengo, edição especial com design exclusivo.',
    329.90,
    true,
    'Flamengo',
    'Brasil',
    '2024/2025',
    'TERCEIRA',
    'LIBERTADORES',
    'Adidas',
    true,
    59.90,
    'Dri-FIT Premium',
    false,
    true,
    200,
    'https://images.camisa10.com/flamengo-terceira-2425.jpg'
),
-- Palmeiras
(
    'Camisa Palmeiras 2024/2025 Principal',
    'Camisa oficial do Palmeiras, verde Alviverde. O manto do Verdão!',
    299.90,
    true,
    'Palmeiras',
    'Brasil',
    '2024/2025',
    'PRINCIPAL',
    'BRASILEIRAO_SERIE_A',
    'Puma',
    true,
    49.90,
    'Dri-FIT',
    false,
    true,
    450,
    'https://images.camisa10.com/palmeiras-principal-2425.jpg'
),
(
    'Camisa Palmeiras 2024/2025 Reserva',
    'Camisa reserva do Palmeiras, branca com detalhes verdes.',
    289.90,
    true,
    'Palmeiras',
    'Brasil',
    '2024/2025',
    'RESERVA',
    'BRASILEIRAO_SERIE_A',
    'Puma',
    true,
    49.90,
    'Dri-FIT',
    false,
    true,
    280,
    'https://images.camisa10.com/palmeiras-reserva-2425.jpg'
),
-- Corinthians
(
    'Camisa Corinthians 2024/2025 Principal',
    'Camisa oficial do Corinthians, branca tradicional. O manto alvinegro!',
    289.90,
    true,
    'Corinthians',
    'Brasil',
    '2024/2025',
    'PRINCIPAL',
    'BRASILEIRAO_SERIE_A',
    'Nike',
    true,
    49.90,
    'Dri-FIT',
    false,
    true,
    400,
    'https://images.camisa10.com/corinthians-principal-2425.jpg'
),
(
    'Camisa Corinthians 2024/2025 Reserva',
    'Camisa reserva do Corinthians, preta com detalhes brancos.',
    279.90,
    true,
    'Corinthians',
    'Brasil',
    '2024/2025',
    'RESERVA',
    'BRASILEIRAO_SERIE_A',
    'Nike',
    true,
    49.90,
    'Dri-FIT',
    false,
    true,
    320,
    'https://images.camisa10.com/corinthians-reserva-2425.jpg'
),
-- São Paulo
(
    'Camisa São Paulo 2024/2025 Principal',
    'Camisa oficial do São Paulo FC, tricolor paulista. O manto do Soberano!',
    289.90,
    true,
    'São Paulo',
    'Brasil',
    '2024/2025',
    'PRINCIPAL',
    'BRASILEIRAO_SERIE_A',
    'New Balance',
    true,
    49.90,
    'Dri-FIT',
    false,
    true,
    380,
    'https://images.camisa10.com/saopaulo-principal-2425.jpg'
),
-- Santos
(
    'Camisa Santos 2024/2025 Principal',
    'Camisa oficial do Santos FC, branca clássica. O manto do Peixe!',
    269.90,
    true,
    'Santos',
    'Brasil',
    '2024/2025',
    'PRINCIPAL',
    'BRASILEIRAO_SERIE_A',
    'Umbro',
    true,
    49.90,
    'Dri-FIT',
    false,
    true,
    300,
    'https://images.camisa10.com/santos-principal-2425.jpg'
),
-- Botafogo
(
    'Camisa Botafogo 2024/2025 Principal',
    'Camisa oficial do Botafogo FR, listrada preto e branco. Camisa do Glorioso!',
    279.90,
    true,
    'Botafogo',
    'Brasil',
    '2024/2025',
    'PRINCIPAL',
    'BRASILEIRAO_SERIE_A',
    'Reebok',
    true,
    49.90,
    'Dri-FIT',
    false,
    true,
    280,
    'https://images.camisa10.com/botafogo-principal-2425.jpg'
),
-- Fluminense
(
    'Camisa Fluminense 2024/2025 Principal',
    'Camisa oficial do Fluminense FC, tricolor carioca. O manto do Flu!',
    279.90,
    true,
    'Fluminense',
    'Brasil',
    '2024/2025',
    'PRINCIPAL',
    'BRASILEIRAO_SERIE_A',
    'Umbro',
    true,
    49.90,
    'Dri-FIT',
    false,
    true,
    260,
    'https://images.camisa10.com/fluminense-principal-2425.jpg'
),
-- TIMES EUROPEUS
-- Real Madrid
(
    'Camisa Real Madrid 2024/2025 Principal',
    'Camisa oficial do Real Madrid CF, branca tradicional. O manto merengue!',
    399.90,
    true,
    'Real Madrid',
    'Espanha',
    '2024/2025',
    'PRINCIPAL',
    'LA_LIGA',
    'Adidas',
    true,
    59.90,
    'Dri-FIT Premium',
    false,
    true,
    200,
    'https://images.camisa10.com/realmadrid-principal-2425.jpg'
),
-- Barcelona
(
    'Camisa Barcelona 2024/2025 Principal',
    'Camisa oficial do FC Barcelona, blaugrana. O manto culé!',
    399.90,
    true,
    'Barcelona',
    'Espanha',
    '2024/2025',
    'PRINCIPAL',
    'LA_LIGA',
    'Nike',
    true,
    59.90,
    'Dri-FIT Premium',
    false,
    true,
    180,
    'https://images.camisa10.com/barcelona-principal-2425.jpg'
),
-- Manchester United
(
    'Camisa Manchester United 2024/2025 Principal',
    'Camisa oficial do Manchester United FC, vermelha clássica. O manto dos Red Devils!',
    389.90,
    true,
    'Manchester United',
    'Inglaterra',
    '2024/2025',
    'PRINCIPAL',
    'PREMIER_LEAGUE',
    'Adidas',
    true,
    59.90,
    'Dri-FIT Premium',
    false,
    true,
    220,
    'https://images.camisa10.com/manchesterunited-principal-2425.jpg'
),
-- Liverpool
(
    'Camisa Liverpool 2024/2025 Principal',
    'Camisa oficial do Liverpool FC, vermelha. Youll Never Walk Alone!',
    379.90,
    true,
    'Liverpool',
    'Inglaterra',
    '2024/2025',
    'PRINCIPAL',
    'PREMIER_LEAGUE',
    'Nike',
    true,
    59.90,
    'Dri-FIT Premium',
    false,
    true,
    190,
    'https://images.camisa10.com/liverpool-principal-2425.jpg'
),
-- SELEÇÕES
-- Brasil
(
    'Camisa Seleção Brasileira 2024 Principal',
    'Camisa oficial da Seleção Brasileira, amarela canarinho. Rumo ao Hexa!',
    449.90,
    true,
    'Brasil',
    'Brasil',
    '2024',
    'PRINCIPAL',
    'COPA_DO_MUNDO',
    'Nike',
    true,
    69.90,
    'Dri-FIT Elite',
    false,
    true,
    500,
    'https://images.camisa10.com/brasil-principal-24.jpg'
),
(
    'Camisa Seleção Brasileira 2024 Reserva',
    'Camisa reserva da Seleção Brasileira, azul royal. A segunda pele da torcida!',
    429.90,
    true,
    'Brasil',
    'Brasil',
    '2024',
    'RESERVA',
    'COPA_DO_MUNDO',
    'Nike',
    true,
    69.90,
    'Dri-FIT Elite',
    false,
    true,
    350,
    'https://images.camisa10.com/brasil-reserva-24.jpg'
),
-- Argentina
(
    'Camisa Argentina 2024 Principal',
    'Camisa oficial da Seleção Argentina, celeste e branca. A camisa dos campeões!',
    449.90,
    true,
    'Argentina',
    'Argentina',
    '2024',
    'PRINCIPAL',
    'COPA_DO_MUNDO',
    'Adidas',
    true,
    69.90,
    'Dri-FIT Elite',
    false,
    true,
    250,
    'https://images.camisa10.com/argentina-principal-24.jpg'
);

-- ============================================================================
-- FIM DO SCRIPT DE SEED
-- ============================================================================
-- Para verificar os dados inseridos:
-- SELECT COUNT(*) FROM users;
-- SELECT COUNT(*) FROM planos_futebol;
-- SELECT COUNT(*) FROM camisas_futebol;
-- ============================================================================
