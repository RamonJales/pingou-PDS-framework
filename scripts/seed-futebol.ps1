# ============================================================================
# Script de Seed do Banco de Dados - Módulo de Futebol
# ============================================================================
# Este script popula o banco PostgreSQL com dados de exemplo para o módulo
# de assinatura de camisas de futebol.
#
# Uso: .\scripts\seed-futebol.ps1
# ============================================================================

param(
    [string]$Host = "localhost",
    [int]$Port = 5432,
    [string]$Database = "pingou",
    [string]$User = "admin",
    [string]$Password = "admin"
)

$ErrorActionPreference = "Stop"

Write-Host ""
Write-Host "=============================================" -ForegroundColor Cyan
Write-Host "  SEED DATABASE - MODULO CAMISAS DE FUTEBOL  " -ForegroundColor Cyan
Write-Host "=============================================" -ForegroundColor Cyan
Write-Host ""

# Verificar se psql está disponível
$psqlPath = $null

# Tentar encontrar psql
$possiblePaths = @(
    "psql",
    "C:\Program Files\PostgreSQL\15\bin\psql.exe",
    "C:\Program Files\PostgreSQL\14\bin\psql.exe",
    "C:\Program Files\PostgreSQL\16\bin\psql.exe"
)

foreach ($path in $possiblePaths) {
    try {
        $null = & $path --version 2>$null
        $psqlPath = $path
        break
    } catch {
        continue
    }
}

if (-not $psqlPath) {
    Write-Host "[AVISO] psql nao encontrado. Tentando via Docker..." -ForegroundColor Yellow
    
    # Verificar se Docker está rodando
    try {
        $dockerContainer = docker ps --filter "ancestor=postgres" --format "{{.Names}}" 2>$null | Select-Object -First 1
        
        if ($dockerContainer) {
            Write-Host "[INFO] Usando container Docker: $dockerContainer" -ForegroundColor Green
            
            $scriptPath = Join-Path $PSScriptRoot "..\src\main\resources\data\futebol-seed-data.sql"
            $scriptContent = Get-Content $scriptPath -Raw
            
            # Executar via Docker
            $scriptContent | docker exec -i $dockerContainer psql -U $User -d $Database
            
            Write-Host ""
            Write-Host "[SUCESSO] Seed executado via Docker!" -ForegroundColor Green
            exit 0
        }
    } catch {
        Write-Host "[ERRO] Docker nao disponivel" -ForegroundColor Red
    }
    
    Write-Host ""
    Write-Host "[ALTERNATIVA] Execute manualmente o SQL:" -ForegroundColor Yellow
    Write-Host "  Arquivo: src\main\resources\data\futebol-seed-data.sql" -ForegroundColor White
    Write-Host ""
    Write-Host "  Opcoes:" -ForegroundColor White
    Write-Host "  1. Via pgAdmin: Abra o arquivo e execute" -ForegroundColor Gray
    Write-Host "  2. Via DBeaver: Abra o arquivo e execute" -ForegroundColor Gray
    Write-Host "  3. Via linha de comando:" -ForegroundColor Gray
    Write-Host "     psql -h $Host -p $Port -U $User -d $Database -f src\main\resources\data\futebol-seed-data.sql" -ForegroundColor Gray
    Write-Host ""
    exit 1
}

# Definir variável de ambiente para senha
$env:PGPASSWORD = $Password

Write-Host "[INFO] Conectando ao PostgreSQL..." -ForegroundColor White
Write-Host "  Host: $Host" -ForegroundColor Gray
Write-Host "  Port: $Port" -ForegroundColor Gray
Write-Host "  Database: $Database" -ForegroundColor Gray
Write-Host "  User: $User" -ForegroundColor Gray
Write-Host ""

# Caminho do script SQL
$scriptPath = Join-Path $PSScriptRoot "..\src\main\resources\data\futebol-seed-data.sql"

if (-not (Test-Path $scriptPath)) {
    Write-Host "[ERRO] Arquivo SQL nao encontrado: $scriptPath" -ForegroundColor Red
    exit 1
}

Write-Host "[INFO] Executando script de seed..." -ForegroundColor White

try {
    & $psqlPath -h $Host -p $Port -U $User -d $Database -f $scriptPath
    
    if ($LASTEXITCODE -eq 0) {
        Write-Host ""
        Write-Host "[SUCESSO] Seed executado com sucesso!" -ForegroundColor Green
        Write-Host ""
        
        # Mostrar resumo
        Write-Host "Resumo dos dados inseridos:" -ForegroundColor Cyan
        & $psqlPath -h $Host -p $Port -U $User -d $Database -c "SELECT 'Planos' as tabela, COUNT(*) as total FROM planos_futebol UNION ALL SELECT 'Camisas', COUNT(*) FROM camisas_futebol UNION ALL SELECT 'Usuarios', COUNT(*) FROM users;"
    } else {
        Write-Host "[ERRO] Falha ao executar seed" -ForegroundColor Red
        exit 1
    }
} catch {
    Write-Host "[ERRO] $($_.Exception.Message)" -ForegroundColor Red
    exit 1
} finally {
    $env:PGPASSWORD = $null
}

Write-Host ""
Write-Host "=============================================" -ForegroundColor Cyan
Write-Host "  SEED FINALIZADO COM SUCESSO!               " -ForegroundColor Cyan
Write-Host "=============================================" -ForegroundColor Cyan
Write-Host ""
