package com.minibank.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class CriancaRequestDTO {
    @NotBlank(message = "O nome da criança é obrigatório")
    private String nome;

    @Min(value = 1, message = "A idade precisa ser maior que zero")
    private int idade;

    public CriancaRequestDTO() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }
}