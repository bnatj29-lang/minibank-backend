package com.minibank.service;

import com.minibank.dto.BuscarEmailRequestDTO;
import com.minibank.dto.CadastroRequestDTO;
import com.minibank.dto.UsuarioResponseDTO;
import com.minibank.exception.EmailJaCadastradoException;
import com.minibank.model.Usuario;
import com.minibank.repository.UsuarioRepository;
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
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UsuarioResponseDTO cadastrar(CadastroRequestDTO dto) {

        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new EmailJaCadastradoException(dto.getEmail());
        }

        String senhaCriptografada = passwordEncoder.encode(dto.getSenha());
        String senhaPainelCriptografada = passwordEncoder.encode(dto.getSenhaPainel());

        Usuario novoUsuario = new Usuario(dto.getNome(), dto.getEmail(), senhaCriptografada, senhaPainelCriptografada);

        Usuario usuarioSalvo = usuarioRepository.salvar(novoUsuario);

        return new UsuarioResponseDTO(usuarioSalvo);
    }

    public String buscarPorEmail(@Valid BuscarEmailRequestDTO dto) {
        return usuarioRepository.findByEmail(dto.getEmail());
    }
}
