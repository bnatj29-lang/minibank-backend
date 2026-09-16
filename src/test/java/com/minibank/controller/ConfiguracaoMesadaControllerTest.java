package com.minibank.controller;

import com.minibank.model.ConfiguracaoMesada;
import com.minibank.repository.ConfiguracaoMesadaRepository;
import com.minibank.service.ConfiguracaoMesadaService;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ConfiguracaoMesadaControllerTest {
    @Test
    void consultaValoresSalvosPorCriancaSemAlterarConfiguracao() throws Exception {
        ConfiguracaoMesadaRepository repositorio = mock(ConfiguracaoMesadaRepository.class);
        ConfiguracaoMesada configuracao = new ConfiguracaoMesada(1L, 42L,
                new BigDecimal("300.50"), new BigDecimal("7.25"), new BigDecimal("9.50"),
                new BigDecimal("200.25"), new BigDecimal("300.50"), new BigDecimal("400.75"));
        when(repositorio.buscarCrianca(42L)).thenReturn(Optional.of(configuracao));
        when(repositorio.buscarCrianca(43L)).thenReturn(Optional.empty());
        MockMvc requisicoes = MockMvcBuilders.standaloneSetup(
                new ConfiguracaoMesadaController(new ConfiguracaoMesadaService(repositorio))).build();

        requisicoes.perform(get("/criancas/42/configuracao-mesada"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.criancaId").value(42))
                .andExpect(jsonPath("$.valorBase").value(300.50))
                .andExpect(jsonPath("$.notaMinimaIntermediaria").value(7.25))
                .andExpect(jsonPath("$.notaMinimaMaxima").value(9.50))
                .andExpect(jsonPath("$.valorFaixaBaixa").value(200.25))
                .andExpect(jsonPath("$.valorFaixaIntermediaria").value(300.50))
                .andExpect(jsonPath("$.valorFaixaMaxima").value(400.75));
        requisicoes.perform(get("/criancas/43/configuracao-mesada"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensagem").value("Configuração de mesada não encontrada para a criança."));
        verify(repositorio).buscarCrianca(42L);
        verify(repositorio).buscarCrianca(43L);
        verifyNoMoreInteractions(repositorio);
    }
}
