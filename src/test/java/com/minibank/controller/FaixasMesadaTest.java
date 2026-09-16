package com.minibank.controller;

import com.minibank.exception.GlobalExceptionHandler;
import com.minibank.model.ConfiguracaoMesada;
import com.minibank.repository.ConfiguracaoMesadaRepository;
import com.minibank.service.ConfiguracaoMesadaService;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class FaixasMesadaTest {
    private MockMvc preparar(ConfiguracaoMesadaRepository repositorio) {
        return MockMvcBuilders.standaloneSetup(
                new ConfiguracaoMesadaController(new ConfiguracaoMesadaService(repositorio)))
                .setControllerAdvice(new GlobalExceptionHandler()).build();
    }

    private String dados(String intermediaria, String maxima) {
        return "{\"valorBase\":300,\"notaMinimaIntermediaria\":" + intermediaria
                + ",\"notaMinimaMaxima\":" + maxima
                + ",\"valorFaixaBaixa\":200,\"valorFaixaIntermediaria\":300,\"valorFaixaMaxima\":400}";
    }

    @ParameterizedTest
    @CsvSource({"7,7", "7,6", "7.25,7.250", "9.50,9.49"})
    void rejeitaFaixasIguaisOuInvertidasAntesDeAcessarBanco(String intermediaria, String maxima) throws Exception {
        ConfiguracaoMesadaRepository repositorio = mock(ConfiguracaoMesadaRepository.class);
        preparar(repositorio).perform(put("/criancas/42/configuracao-mesada")
                        .contentType(MediaType.APPLICATION_JSON).content(dados(intermediaria, maxima)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensagem").value("A nota da faixa máxima deve ser maior que a nota da faixa intermediária."));
        verifyNoInteractions(repositorio);
    }

    @ParameterizedTest
    @CsvSource({"7,9,false", "7.25,7.26,false", "9.99,10,true"})
    void permiteCriarEAtualizarFaixasValidas(String intermediaria, String maxima, boolean existente) throws Exception {
        ConfiguracaoMesadaRepository repositorio = mock(ConfiguracaoMesadaRepository.class);
        when(repositorio.buscarCrianca(42L)).thenReturn(existente ? Optional.of(new ConfiguracaoMesada()) : Optional.empty());
        preparar(repositorio).perform(put("/criancas/42/configuracao-mesada")
                        .contentType(MediaType.APPLICATION_JSON).content(dados(intermediaria, maxima)))
                .andExpect(status().isOk());
        if (existente) {
            verify(repositorio).atualizar(argThat(configuracao -> configuracao.getCriancaId().equals(42L)
                    && configuracao.getNotaMinimaMaxima().compareTo(new BigDecimal(maxima)) == 0));
            verify(repositorio, never()).salvar(any());
        } else {
            verify(repositorio).salvar(argThat(configuracao -> configuracao.getCriancaId().equals(42L)
                    && configuracao.getNotaMinimaIntermediaria().compareTo(new BigDecimal(intermediaria)) == 0));
            verify(repositorio, never()).atualizar(any());
        }
    }

    @ParameterizedTest
    @CsvSource({"null,9", "7,null", "-1,9", "7,11"})
    void mantemValidacaoDeCamposObrigatoriosELimites(String intermediaria, String maxima) throws Exception {
        ConfiguracaoMesadaRepository repositorio = mock(ConfiguracaoMesadaRepository.class);
        preparar(repositorio).perform(put("/criancas/42/configuracao-mesada")
                        .contentType(MediaType.APPLICATION_JSON).content(dados(intermediaria, maxima)))
                .andExpect(status().isBadRequest());
        verifyNoInteractions(repositorio);
    }
}
