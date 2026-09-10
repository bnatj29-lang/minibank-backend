package com.minibank.controller;

import com.minibank.exception.GlobalExceptionHandler;
import com.minibank.model.Crianca;
import com.minibank.model.Responsavel;
import com.minibank.repository.CriancaRepository;
import com.minibank.repository.ResponsavelRepository;
import com.minibank.service.LoginService;
import com.minibank.service.PainelService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class LoginControllerTest {

    private ResponsavelRepository responsavelRepository;
    private CriancaRepository criancaRepository;
    private PasswordEncoder passwordEncoder;
    private MockMvc requisicoes;

    @BeforeEach
    void preparar() {
        responsavelRepository = mock(ResponsavelRepository.class);
        criancaRepository = mock(CriancaRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        LoginService service = new LoginService(responsavelRepository, passwordEncoder, criancaRepository);
        requisicoes = MockMvcBuilders.standaloneSetup(new LoginController(mock(PainelService.class), service))
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    private void prepararResponsavel(boolean senhaCorreta) {
        Responsavel responsavel = new Responsavel("Maria", "familia@example.com", "hash-login", "hash-painel");
        responsavel.setId(10L);
        when(responsavelRepository.buscarPorEmail("familia@example.com")).thenReturn(Optional.of(responsavel));
        when(passwordEncoder.matches("senha123", "hash-login")).thenReturn(senhaCorreta);
    }

    @Test
    void retornaResponsavelEDuasCriancasSemSenhas() throws Exception {
        prepararResponsavel(true);
        Crianca primeira = new Crianca(10L, 8, "Ana");
        primeira.setId(20L);
        Crianca segunda = new Crianca(10L, 12, "Bia");
        segunda.setId(21L);
        when(criancaRepository.buscarPorResponsavel(10L)).thenReturn(List.of(primeira, segunda));

        requisicoes.perform(post("/autenticar/login").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"familia@example.com\",\"senha\":\"senha123\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.responsavel.id").value(10))
                .andExpect(jsonPath("$.responsavel.nome").value("Maria"))
                .andExpect(jsonPath("$.responsavel.email").value("familia@example.com"))
                .andExpect(jsonPath("$.responsavel.senha").doesNotExist())
                .andExpect(jsonPath("$.responsavel.senhaHash").doesNotExist())
                .andExpect(jsonPath("$.responsavel.senhaPainelHash").doesNotExist())
                .andExpect(jsonPath("$.criancas.length()").value(2))
                .andExpect(jsonPath("$.criancas[0].id").value(20))
                .andExpect(jsonPath("$.criancas[0].nome").value("Ana"))
                .andExpect(jsonPath("$.criancas[0].idade").value(8))
                .andExpect(jsonPath("$.criancas[1].id").value(21));
        verify(criancaRepository).buscarPorResponsavel(10L);
    }

    @Test
    void retornaListaVaziaQuandoResponsavelNaoTemCriancas() throws Exception {
        prepararResponsavel(true);
        when(criancaRepository.buscarPorResponsavel(10L)).thenReturn(List.of());
        requisicoes.perform(post("/autenticar/login").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"familia@example.com\",\"senha\":\"senha123\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.criancas").isArray())
                .andExpect(jsonPath("$.criancas").isEmpty());
    }

    @Test
    void senhaIncorretaNaoConsultaCriancas() throws Exception {
        prepararResponsavel(false);
        requisicoes.perform(post("/autenticar/login").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"familia@example.com\",\"senha\":\"senha123\"}"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.responsavel").doesNotExist())
                .andExpect(jsonPath("$.criancas").doesNotExist());
        verifyNoInteractions(criancaRepository);
    }

    @Test
    void emailInexistenteNaoConsultaCriancas() throws Exception {
        when(responsavelRepository.buscarPorEmail("familia@example.com")).thenReturn(Optional.empty());
        requisicoes.perform(post("/autenticar/login").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"familia@example.com\",\"senha\":\"senha123\"}"))
                .andExpect(status().isUnauthorized());
        verifyNoInteractions(criancaRepository, passwordEncoder);
    }

    @Test
    void dadosInvalidosRetornamErroDeValidacao() throws Exception {
        requisicoes.perform(post("/autenticar/login").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"invalido\",\"senha\":\"123\"}"))
                .andExpect(status().isBadRequest());
        verifyNoInteractions(responsavelRepository, criancaRepository, passwordEncoder);
    }
}
