package com.minibank.service;

import com.minibank.dto.BuscarEmailRequestDTO;
import com.minibank.dto.ResponsavelRequestDTO;
import com.minibank.dto.ResponsavelResponseDTO;
import com.minibank.exception.EmailJaCadastradoException;
import com.minibank.model.Responsavel;
import com.minibank.repository.ResponsavelRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

// É aqui que mora a REGRA DE NEGÓCIO do cadastro. O Controller não decide nada
// sozinho, ele só recebe o pedido e delega pro Service.
//
// Passo a passo do que essa classe faz:
// 1. Confere se já existe alguém cadastrado com aquele e-mail
// 2. Criptografa a senha (nunca guardamos senha "pura" no banco)
// 3. Cria e salva o novo usuário
// 4. Devolve um DTO de resposta, sem a senha
@Service
public class ResponsavelService {

    private final ResponsavelRepository responsavelRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ResponsavelService(ResponsavelRepository responsavelRepository, PasswordEncoder passwordEncoder) {
        this.responsavelRepository = responsavelRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponsavelResponseDTO cadastrar(ResponsavelRequestDTO dto) {

        if (responsavelRepository.existsByEmail(dto.getEmail())) {
            throw new EmailJaCadastradoException(dto.getEmail());
        }

        String senhaCriptografada = passwordEncoder.encode(dto.getSenha());
        String senhaPainelCriptografada = passwordEncoder.encode(dto.getSenhaPainel());

        Responsavel novoResponsavel = new Responsavel(dto.getNome(), dto.getEmail(), senhaCriptografada, senhaPainelCriptografada);

        Responsavel responsavelSalvo = responsavelRepository.salvar(novoResponsavel);

        return new ResponsavelResponseDTO(responsavelSalvo);
    }
}
