package com.pds.pingou.futebol;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pds.pingou.futebol.enums.TamanhoCamisa;
import com.pds.pingou.futebol.enums.TipoCamisa;
import com.pds.pingou.futebol.enums.TipoPlanoFutebol;
import com.pds.pingou.futebol.plano.dto.PlanoFutebolRequestDTO;
import com.pds.pingou.futebol.produto.dto.CamisaFutebolRequestDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Testes de integração para os controllers do módulo futebol.
 * 
 * Testes end-to-end que verificam:
 * - Endpoints REST estão funcionando
 * - Segurança está configurada
 * - Serialização JSON está correta
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("Controllers Futebol - Integração")
class FutebolControllersIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Nested
    @DisplayName("PlanoFutebolController")
    class PlanoFutebolControllerTest {

        @Test
        @DisplayName("GET /api/planos-futebol deve retornar 200")
        @WithMockUser
        void listarPlanosDeveRetornar200() throws Exception {
            mockMvc.perform(get("/api/planos-futebol"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        }

        @Test
        @DisplayName("GET /api/planos-futebol sem auth deve retornar 401")
        void listarPlanosSemAuthDeveRetornar401() throws Exception {
            mockMvc.perform(get("/api/planos-futebol"))
                    .andExpect(status().isUnauthorized());
        }

        @Test
        @DisplayName("GET /api/planos-futebol/tipos deve listar tipos de planos")
        @WithMockUser
        void listarTiposDeveRetornar200() throws Exception {
            mockMvc.perform(get("/api/planos-futebol/tipos"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        }
    }

    @Nested
    @DisplayName("CamisaFutebolController")
    class CamisaFutebolControllerTest {

        @Test
        @DisplayName("GET /api/camisas deve retornar 200")
        @WithMockUser
        void listarCamisasDeveRetornar200() throws Exception {
            mockMvc.perform(get("/api/camisas"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        }

        @Test
        @DisplayName("GET /api/camisas/tamanhos deve listar tamanhos disponíveis")
        @WithMockUser
        void listarTamanhosDeveRetornar200() throws Exception {
            mockMvc.perform(get("/api/camisas/tamanhos"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        }

        @Test
        @DisplayName("GET /api/camisas/tipos deve listar tipos de camisas")
        @WithMockUser
        void listarTiposDeveRetornar200() throws Exception {
            mockMvc.perform(get("/api/camisas/tipos"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        }
    }

    @Nested
    @DisplayName("AssinaturaFutebolController")
    class AssinaturaFutebolControllerTest {

        @Test
        @DisplayName("GET /api/assinaturas-futebol/minha sem auth deve retornar 401")
        void minhaAssinaturaSemAuthDeveRetornar401() throws Exception {
            mockMvc.perform(get("/api/assinaturas-futebol/minha"))
                    .andExpect(status().isUnauthorized());
        }

        @Test
        @DisplayName("GET /api/assinaturas-futebol/minha com auth deve funcionar")
        @WithMockUser(username = "test@test.com")
        void minhaAssinaturaComAuthDeveFuncionar() throws Exception {
            // Mesmo se não encontrar assinatura, o endpoint deve funcionar
            mockMvc.perform(get("/api/assinaturas-futebol/minha"))
                    .andExpect(status().is4xxClientError()); // 404 é esperado para usuário sem assinatura
        }
    }

    @Nested
    @DisplayName("PacoteFutebolController")
    class PacoteFutebolControllerTest {

        @Test
        @DisplayName("GET /api/pacotes-futebol sem auth deve retornar 401")
        void listarPacotesSemAuthDeveRetornar401() throws Exception {
            mockMvc.perform(get("/api/pacotes-futebol"))
                    .andExpect(status().isUnauthorized());
        }

        @Test
        @DisplayName("GET /api/pacotes-futebol/assinatura/1 com auth deve funcionar")
        @WithMockUser
        void buscarPacotesPorAssinaturaDeveFuncionar() throws Exception {
            mockMvc.perform(get("/api/pacotes-futebol/assinatura/1"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        }
    }
}
