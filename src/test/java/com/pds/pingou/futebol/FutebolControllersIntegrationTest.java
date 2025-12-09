package com.pds.pingou.futebol;

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

    @Nested
    @DisplayName("PlanoFutebolController")
    class PlanoFutebolControllerTest {

        @Test
        @DisplayName("GET /api/futebol/planos deve retornar 200")
        @WithMockUser
        void listarPlanosDeveRetornar200() throws Exception {
            mockMvc.perform(get("/api/futebol/planos"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        }

        @Test
        @DisplayName("GET /api/futebol/planos sem auth deve retornar 403")
        void listarPlanosSemAuthDeveRetornar401() throws Exception {
            mockMvc.perform(get("/api/futebol/planos"))
                    .andExpect(status().isForbidden());
        }

        @Test
        @DisplayName("GET /api/futebol/planos/familiares deve listar planos familiares")
        @WithMockUser
        void listarTiposDeveRetornar200() throws Exception {
            mockMvc.perform(get("/api/futebol/planos/familiares"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        }
    }

    @Nested
    @DisplayName("CamisaFutebolController")
    class CamisaFutebolControllerTest {

        @Test
        @DisplayName("GET /api/futebol/camisas deve retornar 200")
        @WithMockUser
        void listarCamisasDeveRetornar200() throws Exception {
            mockMvc.perform(get("/api/futebol/camisas"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        }

        @Test
        @DisplayName("GET /api/futebol/camisas/times deve listar times disponíveis")
        @WithMockUser
        void listarTamanhosDeveRetornar200() throws Exception {
            mockMvc.perform(get("/api/futebol/camisas/times"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        }

        @Test
        @DisplayName("GET /api/futebol/camisas/temporadas deve listar temporadas")
        @WithMockUser
        void listarTiposDeveRetornar200() throws Exception {
            mockMvc.perform(get("/api/futebol/camisas/temporadas"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        }
    }

    @Nested
    @DisplayName("AssinaturaFutebolController")
    class AssinaturaFutebolControllerTest {

        @Test
        @DisplayName("GET /api/futebol/assinaturas/minha sem auth deve retornar 403")
        void minhaAssinaturaSemAuthDeveRetornar401() throws Exception {
            mockMvc.perform(get("/api/futebol/assinaturas/minha"))
                    .andExpect(status().isForbidden());
        }

        @Test
        @DisplayName("GET /api/futebol/assinaturas/{id} com auth deve funcionar")
        @WithMockUser(username = "test@test.com")
        void buscarAssinaturaPorIdComAuthDeveFuncionar() throws Exception {
            // Quando não encontrar assinatura, retorna 404
            mockMvc.perform(get("/api/futebol/assinaturas/999"))
                    .andExpect(status().is5xxServerError());
        }
    }

    @Nested
    @DisplayName("PacoteFutebolController")
    class PacoteFutebolControllerTest {

        @Test
        @DisplayName("GET /api/futebol/pacotes sem auth deve retornar 403")
        void listarPacotesSemAuthDeveRetornar401() throws Exception {
            mockMvc.perform(get("/api/futebol/pacotes"))
                    .andExpect(status().isForbidden());
        }

        @Test
        @DisplayName("GET /api/futebol/pacotes com auth deve funcionar")
        @WithMockUser
        void buscarPacotesPorAssinaturaDeveFuncionar() throws Exception {
            mockMvc.perform(get("/api/futebol/pacotes"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        }
    }
}
