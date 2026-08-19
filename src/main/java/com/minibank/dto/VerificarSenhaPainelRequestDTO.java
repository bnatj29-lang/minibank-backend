package com.minibank.dto;

import jakarta.validation.constraints.NotBlank;

public class VerificarSenhaPainelRequestDTO {

    @NotBlank(message = "A senha do painel é obrigatória")
    private String senha;

    public VerificarSenhaPainelRequestDTO() {
    }

    public VerificarSenhaPainelRequestDTO(String senha) {
        this.senha = senha;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }


}