package com.minibank.service;

import com.minibank.dto.RedefinirSenhaRequestDTO;
import com.minibank.exception.EmailRecuperacaoException;
import com.minibank.exception.TokenRecuperacaoInvalidoException;
import com.minibank.model.Responsavel;
import com.minibank.model.TokenRecuperacaoSenha;
import com.minibank.repository.ResponsavelRepository;
import com.minibank.repository.TokenRecuperacaoSenhaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Base64;

@Service
public class RecuperacaoSenhaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RecuperacaoSenhaService.class);
    private static final int VALIDADE_MINUTOS = 15;
    private final ResponsavelRepository responsavelRepository;
    private final TokenRecuperacaoSenhaRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailRecuperacaoService emailService;
    private final SecureRandom secureRandom = new SecureRandom();

    public RecuperacaoSenhaService(
            ResponsavelRepository responsavelRepository,
            TokenRecuperacaoSenhaRepository tokenRepository,
            PasswordEncoder passwordEncoder,
            EmailRecuperacaoService emailService) {
        this.responsavelRepository = responsavelRepository;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    @Transactional
    public void solicitar(String email) {
        Responsavel responsavel = responsavelRepository.buscarPorEmail(email.trim()).orElse(null);
        if (responsavel == null) {
            return;
        }

        byte[] bytes = new byte[32];
        secureRandom.nextBytes(bytes);
        String token = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
        LocalDateTime agora = LocalDateTime.now(ZoneOffset.UTC);
        TokenRecuperacaoSenha registro = new TokenRecuperacaoSenha(
                responsavel.getId(), gerarHash(token), agora, agora.plusMinutes(VALIDADE_MINUTOS));

        tokenRepository.invalidarAtivos(responsavel.getId());
        tokenRepository.salvar(registro);
        try {
            emailService.enviar(responsavel.getEmail(), token);
        } catch (EmailRecuperacaoException exception) {
            // A resposta pública continua genérica para não permitir enumeração de contas.
            LOGGER.error("Não foi possível enviar o e-mail de recuperação.");
        }
    }

    @Transactional
    public void redefinir(RedefinirSenhaRequestDTO dto) {
        if (!dto.getNovaSenha().equals(dto.getConfirmacaoSenha())) {
            throw new IllegalArgumentException("A nova senha e a confirmação precisam ser iguais.");
        }

        TokenRecuperacaoSenha token = tokenRepository.buscarValido(gerarHash(dto.getToken()))
                .orElseThrow(TokenRecuperacaoInvalidoException::new);

        if (!tokenRepository.marcarComoUtilizado(token.getId())) {
            throw new TokenRecuperacaoInvalidoException();
        }

        responsavelRepository.atualizarSenha(
                token.getResponsavelId(), passwordEncoder.encode(dto.getNovaSenha()));
    }

    @Transactional(readOnly = true)
    public void validarToken(String token) {
        if (token == null || token.isBlank()) {
            throw new TokenRecuperacaoInvalidoException();
        }
        tokenRepository.buscarValido(gerarHash(token))
                .orElseThrow(TokenRecuperacaoInvalidoException::new);
    }

    private String gerarHash(String token) {
        try {
            byte[] hash = MessageDigest.getInstance("SHA-256")
                    .digest(token.getBytes(StandardCharsets.UTF_8));
            StringBuilder resultado = new StringBuilder();
            for (byte valor : hash) {
                resultado.append(String.format("%02x", valor & 0xff));
            }
            return resultado.toString();
        } catch (NoSuchAlgorithmException exception) {
            throw new IllegalStateException("Não foi possível proteger o token de recuperação.", exception);
        }
    }
}
