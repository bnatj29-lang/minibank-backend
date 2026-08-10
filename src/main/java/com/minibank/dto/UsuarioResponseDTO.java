package com.minibank.dto;

import com.minibank.model.Usuario;
import java.time.LocalDateTime;

// Esse é o formato que a API devolve para o frontend depois do cadastro.
// Regra de ouro: a senha (nem o hash dela) NUNCA deve sair da API. É por isso
// que criamos esse DTO separado, ao invés de simplesmente devolver o objeto
// Usuario inteiro na resposta.
public class UsuarioResponseDTO {

    private Long id;
    private String nome;
    private String email;
    private LocalDateTime criadoEm;

    public UsuarioResponseDTO(Usuario usuario) {
        this.id = usuario.getId();
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
        this.criadoEm = usuario.getCriadoEm();
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }
}
