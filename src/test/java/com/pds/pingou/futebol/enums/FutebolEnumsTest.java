package com.pds.pingou.futebol.enums;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testes unitários para os Enums do módulo de Futebol.
 * 
 * Garante que os enums estejam configurados corretamente com
 * seus valores e comportamentos esperados.
 */
@DisplayName("Enums do Módulo de Futebol")
class FutebolEnumsTest {

    @Nested
    @DisplayName("TamanhoCamisa")
    class TamanhoCamisaTest {

        @Test
        @DisplayName("Deve ter 14 tamanhos disponíveis")
        void deveTer14TamanhosDisponiveis() {
            assertThat(TamanhoCamisa.values()).hasSize(14);
        }

        @Test
        @DisplayName("Tamanhos infantis devem ter siglas numéricas")
        void tamanhosInfantisDevemTerSiglasNumericas() {
            assertThat(TamanhoCamisa.INF_2.getSigla()).isEqualTo("2");
            assertThat(TamanhoCamisa.INF_4.getSigla()).isEqualTo("4");
            assertThat(TamanhoCamisa.INF_6.getSigla()).isEqualTo("6");
            assertThat(TamanhoCamisa.INF_8.getSigla()).isEqualTo("8");
            assertThat(TamanhoCamisa.INF_10.getSigla()).isEqualTo("10");
            assertThat(TamanhoCamisa.INF_12.getSigla()).isEqualTo("12");
            assertThat(TamanhoCamisa.INF_14.getSigla()).isEqualTo("14");
        }

        @Test
        @DisplayName("Tamanhos adultos devem ter siglas corretas")
        void tamanhosAdultosDevemTerSiglasCorretas() {
            assertThat(TamanhoCamisa.PP.getSigla()).isEqualTo("PP");
            assertThat(TamanhoCamisa.P.getSigla()).isEqualTo("P");
            assertThat(TamanhoCamisa.M.getSigla()).isEqualTo("M");
            assertThat(TamanhoCamisa.G.getSigla()).isEqualTo("G");
            assertThat(TamanhoCamisa.GG.getSigla()).isEqualTo("GG");
            assertThat(TamanhoCamisa.XGG.getSigla()).isEqualTo("XGG");
            assertThat(TamanhoCamisa.XXGG.getSigla()).isEqualTo("XXGG");
        }

        @ParameterizedTest
        @EnumSource(value = TamanhoCamisa.class, names = {"INF_2", "INF_4", "INF_6", "INF_8", "INF_10", "INF_12", "INF_14"})
        @DisplayName("Todos os tamanhos INF devem ser infantis")
        void todosTamanhosInfDevemSerInfantis(TamanhoCamisa tamanho) {
            assertThat(tamanho.isInfantil()).isTrue();
            assertThat(tamanho.isAdulto()).isFalse();
        }

        @ParameterizedTest
        @EnumSource(value = TamanhoCamisa.class, names = {"PP", "P", "M", "G", "GG", "XGG", "XXGG"})
        @DisplayName("Todos os tamanhos não-INF devem ser adultos")
        void todosTamanhosNaoInfDevemSerAdultos(TamanhoCamisa tamanho) {
            assertThat(tamanho.isAdulto()).isTrue();
            assertThat(tamanho.isInfantil()).isFalse();
        }

        @Test
        @DisplayName("getTamanhosInfantis deve retornar apenas infantis")
        void getTamanhosInfantisDeveRetornarApenasInfantis() {
            TamanhoCamisa[] infantis = TamanhoCamisa.getTamanhosInfantis();
            
            assertThat(infantis).hasSize(7);
            for (TamanhoCamisa t : infantis) {
                assertThat(t.isInfantil()).isTrue();
            }
        }

        @Test
        @DisplayName("getTamanhosAdultos deve retornar apenas adultos")
        void getTamanhosAdultosDeveRetornarApenasAdultos() {
            TamanhoCamisa[] adultos = TamanhoCamisa.getTamanhosAdultos();
            
            assertThat(adultos).hasSize(7);
            for (TamanhoCamisa t : adultos) {
                assertThat(t.isAdulto()).isTrue();
            }
        }
    }

    @Nested
    @DisplayName("TipoCamisa")
    class TipoCamisaTest {

        @Test
        @DisplayName("Deve ter 7 tipos de camisa")
        void deveTer7TiposDeCamisa() {
            assertThat(TipoCamisa.values()).hasSize(7);
        }

        @Test
        @DisplayName("Multiplicadores de preço devem estar corretos")
        void multiplicadoresDevemEstarCorretos() {
            assertThat(TipoCamisa.PRINCIPAL.getMultiplicadorPreco()).isEqualTo(1.0);
            assertThat(TipoCamisa.RESERVA.getMultiplicadorPreco()).isEqualTo(1.0);
            assertThat(TipoCamisa.TERCEIRA.getMultiplicadorPreco()).isEqualTo(1.1);
            assertThat(TipoCamisa.GOLEIRO.getMultiplicadorPreco()).isEqualTo(1.15);
            assertThat(TipoCamisa.TREINO.getMultiplicadorPreco()).isEqualTo(0.7);
            assertThat(TipoCamisa.RETRO.getMultiplicadorPreco()).isEqualTo(1.3);
            assertThat(TipoCamisa.EDICAO_ESPECIAL.getMultiplicadorPreco()).isEqualTo(1.5);
        }

        @Test
        @DisplayName("Camisas de jogo devem ser identificadas corretamente")
        void camisasDeJogoDevemSerIdentificadas() {
            // Camisas de jogo
            assertThat(TipoCamisa.PRINCIPAL.isCamisaDeJogo()).isTrue();
            assertThat(TipoCamisa.RESERVA.isCamisaDeJogo()).isTrue();
            assertThat(TipoCamisa.TERCEIRA.isCamisaDeJogo()).isTrue();
            assertThat(TipoCamisa.GOLEIRO.isCamisaDeJogo()).isTrue();
            
            // Não são camisas de jogo
            assertThat(TipoCamisa.TREINO.isCamisaDeJogo()).isFalse();
            assertThat(TipoCamisa.RETRO.isCamisaDeJogo()).isFalse();
            assertThat(TipoCamisa.EDICAO_ESPECIAL.isCamisaDeJogo()).isFalse();
        }

        @Test
        @DisplayName("Nomes devem ser descritivos")
        void nomesDevemSerDescritivos() {
            assertThat(TipoCamisa.PRINCIPAL.getNome()).isEqualTo("Camisa Principal");
            assertThat(TipoCamisa.RESERVA.getNome()).isEqualTo("Camisa Reserva");
            assertThat(TipoCamisa.RETRO.getNome()).isEqualTo("Camisa Retrô");
        }
    }

    @Nested
    @DisplayName("TipoPlanoFutebol")
    class TipoPlanoFutebolTest {

        @Test
        @DisplayName("Deve ter 6 tipos de plano")
        void deveTer6TiposDePlano() {
            assertThat(TipoPlanoFutebol.values()).hasSize(6);
        }

        @Test
        @DisplayName("Máximo de membros deve estar correto para cada tipo")
        void maximoMembrosDeveEstarCorreto() {
            assertThat(TipoPlanoFutebol.INDIVIDUAL.getMaxMembros()).isEqualTo(1);
            assertThat(TipoPlanoFutebol.CASAL.getMaxMembros()).isEqualTo(2);
            assertThat(TipoPlanoFutebol.FAMILIA_PEQUENA.getMaxMembros()).isEqualTo(3);
            assertThat(TipoPlanoFutebol.FAMILIA.getMaxMembros()).isEqualTo(5);
            assertThat(TipoPlanoFutebol.FAMILIA_GRANDE.getMaxMembros()).isEqualTo(8);
            assertThat(TipoPlanoFutebol.TORCIDA.getMaxMembros()).isEqualTo(12);
        }

        @Test
        @DisplayName("Multiplicadores de preço devem fazer sentido econômico")
        void multiplicadoresDevemFazerSentido() {
            // Quanto mais membros, menor o custo por pessoa
            double custoIndividual = TipoPlanoFutebol.INDIVIDUAL.getMultiplicadorPreco() / 1;
            double custoCasal = TipoPlanoFutebol.CASAL.getMultiplicadorPreco() / 2;
            double custoFamilia = TipoPlanoFutebol.FAMILIA.getMultiplicadorPreco() / 5;
            double custoTorcida = TipoPlanoFutebol.TORCIDA.getMultiplicadorPreco() / 12;
            
            // Custo por pessoa deve diminuir conforme o plano cresce
            assertThat(custoIndividual).isGreaterThan(custoCasal);
            assertThat(custoCasal).isGreaterThan(custoFamilia);
            assertThat(custoFamilia).isGreaterThan(custoTorcida);
        }

        @Test
        @DisplayName("permiteAdicionarMembro deve funcionar corretamente")
        void permiteAdicionarMembroDeveFuncionar() {
            // Individual - apenas 1
            assertThat(TipoPlanoFutebol.INDIVIDUAL.permiteAdicionarMembro(0)).isTrue();
            assertThat(TipoPlanoFutebol.INDIVIDUAL.permiteAdicionarMembro(1)).isFalse();
            
            // Casal - até 2
            assertThat(TipoPlanoFutebol.CASAL.permiteAdicionarMembro(1)).isTrue();
            assertThat(TipoPlanoFutebol.CASAL.permiteAdicionarMembro(2)).isFalse();
            
            // Família - até 5
            assertThat(TipoPlanoFutebol.FAMILIA.permiteAdicionarMembro(4)).isTrue();
            assertThat(TipoPlanoFutebol.FAMILIA.permiteAdicionarMembro(5)).isFalse();
            
            // Torcida - até 12
            assertThat(TipoPlanoFutebol.TORCIDA.permiteAdicionarMembro(11)).isTrue();
            assertThat(TipoPlanoFutebol.TORCIDA.permiteAdicionarMembro(12)).isFalse();
        }

        @Test
        @DisplayName("getPlanoParaMembros deve sugerir plano adequado")
        void getPlanoParaMembrosDeveRetornarPlanoAdequado() {
            assertThat(TipoPlanoFutebol.getPlanoParaMembros(1))
                    .isEqualTo(TipoPlanoFutebol.INDIVIDUAL);
            assertThat(TipoPlanoFutebol.getPlanoParaMembros(2))
                    .isEqualTo(TipoPlanoFutebol.CASAL);
            assertThat(TipoPlanoFutebol.getPlanoParaMembros(3))
                    .isEqualTo(TipoPlanoFutebol.FAMILIA_PEQUENA);
            assertThat(TipoPlanoFutebol.getPlanoParaMembros(5))
                    .isEqualTo(TipoPlanoFutebol.FAMILIA);
            assertThat(TipoPlanoFutebol.getPlanoParaMembros(7))
                    .isEqualTo(TipoPlanoFutebol.FAMILIA_GRANDE);
            assertThat(TipoPlanoFutebol.getPlanoParaMembros(10))
                    .isEqualTo(TipoPlanoFutebol.TORCIDA);
        }
    }

    @Nested
    @DisplayName("Competicao")
    class CompeticaoTest {

        @Test
        @DisplayName("Deve ter competições brasileiras, europeias e de seleções")
        void deveTerCompeticoesVariadas() {
            assertThat(Competicao.values().length).isGreaterThanOrEqualTo(10);
        }

        @Test
        @DisplayName("Competições brasileiras devem ter região Brasil")
        void competicoesBrasileirasDevemTerRegiaoBrasil() {
            assertThat(Competicao.BRASILEIRAO_SERIE_A.getRegiao()).isEqualTo("Brasil");
            assertThat(Competicao.BRASILEIRAO_SERIE_B.getRegiao()).isEqualTo("Brasil");
        }

        @Test
        @DisplayName("Competições de clubes devem ser identificadas")
        void competicoesDeClubesDevemSerIdentificadas() {
            // Clubes
            assertThat(Competicao.BRASILEIRAO_SERIE_A.isClubes()).isTrue();
            assertThat(Competicao.PREMIER_LEAGUE.isClubes()).isTrue();
            assertThat(Competicao.CHAMPIONS_LEAGUE.isClubes()).isTrue();
            assertThat(Competicao.LIBERTADORES.isClubes()).isTrue();
            
            // Seleções
            assertThat(Competicao.COPA_DO_MUNDO.isClubes()).isFalse();
            assertThat(Competicao.COPA_AMERICA.isClubes()).isFalse();
            assertThat(Competicao.EUROCOPA.isClubes()).isFalse();
        }

        @Test
        @DisplayName("Competições europeias devem ter regiões corretas")
        void competicoesEuropeiasDevemTerRegioes() {
            assertThat(Competicao.PREMIER_LEAGUE.getRegiao()).isEqualTo("Inglaterra");
            assertThat(Competicao.LA_LIGA.getRegiao()).isEqualTo("Espanha");
            assertThat(Competicao.SERIE_A_ITALIA.getRegiao()).isEqualTo("Itália");
            assertThat(Competicao.BUNDESLIGA.getRegiao()).isEqualTo("Alemanha");
            assertThat(Competicao.LIGUE_1.getRegiao()).isEqualTo("França");
        }

        @Test
        @DisplayName("Competições continentais devem ter regiões amplas")
        void competicoesContinentaisDevemTerRegioesAmplas() {
            assertThat(Competicao.CHAMPIONS_LEAGUE.getRegiao()).isEqualTo("Europa");
            assertThat(Competicao.EUROPA_LEAGUE.getRegiao()).isEqualTo("Europa");
            assertThat(Competicao.LIBERTADORES.getRegiao()).isEqualTo("América do Sul");
            assertThat(Competicao.SULAMERICANA.getRegiao()).isEqualTo("América do Sul");
        }
    }
}
