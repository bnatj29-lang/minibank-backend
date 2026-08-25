package com.minibank.service;

import com.minibank.exception.EmailSenhaIncorretaException;
import com.minibank.model.Responsavel;
import com.minibank.repository.ResponsavelRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PainelService {

    private final ResponsavelRepository responsavelRepository;
    private final PasswordEncoder passwordEncoder;

    public PainelService(ResponsavelRepository responsavelRepository, PasswordEncoder passwordEncoder) {
        this.responsavelRepository = responsavelRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void verificarSenhaPainel(String emailLogado, String senhaDigitada) {
        Optional<Responsavel> responsavelOpt = responsavelRepository.buscarPorEmail(emailLogado);

        Responsavel responsavel = responsavelOpt.orElseThrow(() -> new EmailSenhaIncorretaException("E-mail ou senha inválidos"));

        boolean senhaCorreta = passwordEncoder.matches(senhaDigitada, responsavel.getSenhaPainelHash());

        if (!senhaCorreta) {
            throw new EmailSenhaIncorretaException("Senha do painel de pais está incorreta!");
        }
    }
}