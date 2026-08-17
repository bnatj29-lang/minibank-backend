package com.minibank.dto;

import com.minibank.model.Responsavel;
import java.time.LocalDateTime;

// Esse é o formato que a API devolve para o frontend depois do cadastro.
// Regra de ouro: a senha (nem o hash dela) NUNCA deve sair da API. É por isso
// que criamos esse DTO separado, ao invés de simplesmente devolver o objeto
// Responsavel inteiro na resposta.
public class ResponsavelResponseDTO {

    private Long id;
    private String nome;
    private String email;
    private LocalDateTime criadoEm;

    public ResponsavelResponseDTO(Responsavel responsavel) {
        this.id = responsavel.getId();
        this.nome = responsavel.getNome();
        this.email = responsavel.getEmail();
        this.criadoEm = responsavel.getCriadoEm();
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
