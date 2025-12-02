# Script para popular o banco de dados com os dados iniciais de camisas
# Execute este script após iniciar o banco de dados com docker-compose up

Write-Host "=========================================" -ForegroundColor Cyan
Write-Host "  Populando Banco de Dados - Camisa Club" -ForegroundColor Cyan
Write-Host "=========================================" -ForegroundColor Cyan
Write-Host ""

# Configurações do banco
$dbName = "pingou"
$dbUser = "admin"
$dbPassword = "admin"
$dbHost = "localhost"
$dbPort = "5432"
$sqlFile = "src\main\resources\data\camisas-data.sql"

# Verifica se o arquivo SQL existe
if (-not (Test-Path $sqlFile)) {
    Write-Host "❌ Erro: Arquivo SQL não encontrado: $sqlFile" -ForegroundColor Red
    exit 1
}

Write-Host "📁 Arquivo SQL encontrado: $sqlFile" -ForegroundColor Green
Write-Host "🔌 Conectando ao PostgreSQL..." -ForegroundColor Yellow
Write-Host "   Host: $dbHost:$dbPort" -ForegroundColor Gray
Write-Host "   Database: $dbName" -ForegroundColor Gray
Write-Host ""

# Executa o script SQL usando docker exec
try {
    # Copia o arquivo SQL para dentro do container
    Write-Host "📋 Copiando arquivo SQL para o container..." -ForegroundColor Yellow
    docker cp $sqlFile postgres:/tmp/camisas-data.sql
    
    # Executa o script SQL
    Write-Host "🔄 Executando script SQL..." -ForegroundColor Yellow
    $env:PGPASSWORD = $dbPassword
    docker exec postgres psql -U $dbUser -d $dbName -f /tmp/camisas-data.sql
    
    Write-Host ""
    Write-Host "✅ Dados inseridos com sucesso!" -ForegroundColor Green
    Write-Host ""
    Write-Host "📊 Dados inseridos:" -ForegroundColor Cyan
    Write-Host "   • 20 Camisas (Brasileiras, Internacionais e Retrô)" -ForegroundColor White
    Write-Host "   • 6 Planos de Assinatura" -ForegroundColor White
    Write-Host ""
    Write-Host "🎯 Você pode agora:" -ForegroundColor Yellow
    Write-Host "   1. Iniciar a aplicação Spring Boot" -ForegroundColor White
    Write-Host "   2. Acessar a API em http://localhost:8080" -ForegroundColor White
    Write-Host "   3. Ver a documentação em http://localhost:8080/swagger-ui.html" -ForegroundColor White
    Write-Host ""
    
} catch {
    Write-Host ""
    Write-Host "❌ Erro ao executar script SQL" -ForegroundColor Red
    Write-Host $_.Exception.Message -ForegroundColor Red
    Write-Host ""
    Write-Host "💡 Dica: Certifique-se de que o container PostgreSQL está rodando:" -ForegroundColor Yellow
    Write-Host "   docker-compose up -d" -ForegroundColor Gray
    exit 1
}

Write-Host "=========================================" -ForegroundColor Cyan
