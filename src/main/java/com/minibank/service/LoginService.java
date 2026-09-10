package com.minibank.service;

import com.minibank.dto.LoginRequestDTO;
import com.minibank.dto.LoginResponseDTO;
import com.minibank.dto.CriancaResponseDTO;
import com.minibank.dto.ResponsavelResponseDTO;
import com.minibank.model.Crianca;
import com.minibank.repository.CriancaRepository;
import com.minibank.exception.EmailSenhaIncorretaException;
import com.minibank.model.Responsavel;
import com.minibank.repository.ResponsavelRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LoginService {

    private final ResponsavelRepository responsavelRepository;
    private final PasswordEncoder passwordEncoder;
    private final CriancaRepository criancaRepository;

    public LoginService(
            ResponsavelRepository responsavelRepository,
            PasswordEncoder passwordEncoder,
            CriancaRepository criancaRepository) {

        this.responsavelRepository = responsavelRepository;
        this.passwordEncoder = passwordEncoder;
        this.criancaRepository = criancaRepository;
    }

    public LoginResponseDTO login(LoginRequestDTO dto) {

        Responsavel responsavel = responsavelRepository
                .buscarPorEmail(dto.getEmail())
                .orElseThrow(() -> new EmailSenhaIncorretaException("E-mail ou senha inválidos"));

        boolean senhaCorreta =
                passwordEncoder.matches(dto.getSenha(), responsavel.getSenhaHash());
        if (!senhaCorreta) {
            throw new EmailSenhaIncorretaException("E-mail ou senha inválidos!");
        }

        List<Crianca> criancas = criancaRepository.buscarPorResponsavel(responsavel.getId());
        List<CriancaResponseDTO> respostasCriancas = new ArrayList<>();

        for (Crianca crianca : criancas) {
            respostasCriancas.add(new CriancaResponseDTO(crianca));
        }

        return new LoginResponseDTO(new ResponsavelResponseDTO(responsavel), respostasCriancas);
    }
}