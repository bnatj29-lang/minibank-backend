package com.minibank.service;

import com.minibank.dto.LoginRequestDTO;
import com.minibank.model.Responsavel;
import com.minibank.repository.ResponsavelRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final ResponsavelRepository responsavelRepository;
    private final PasswordEncoder passwordEncoder;

    public LoginService(
            ResponsavelRepository responsavelRepository,
            PasswordEncoder passwordEncoder) {

        this.responsavelRepository = responsavelRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void login(LoginRequestDTO dto) {

        Responsavel responsavel = responsavelRepository
                .buscarPorEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("E-mail ou senha inválidos"));

        boolean senhaCorreta =
                passwordEncoder.matches(dto.getSenha(), responsavel.getSenhaHash());
    }