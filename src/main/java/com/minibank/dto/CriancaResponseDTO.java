package com.minibank.dto;

import com.minibank.model.Crianca;

public class CriancaResponseDTO {

    private Long id;
    private String nome;
    private int idade;

    public CriancaResponseDTO(Crianca crianca) {
        this.id = crianca.getId();
        this.nome = crianca.getNome();
        this.idade = crianca.getIdade();
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public int getIdade() {
        return idade;
    }
}
