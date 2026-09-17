package com.minibank.dto;

import java.util.List;

// Dados da família retornados somente após validar o e-mail e a senha.
public class LoginResponseDTO {

    private ResponsavelResponseDTO responsavel;
    private List<CriancaResponseDTO> criancas;
    private String token;

    public LoginResponseDTO(ResponsavelResponseDTO responsavel, List<CriancaResponseDTO> criancas, String token) {
        this.responsavel = responsavel;
        this.criancas = criancas;
        this.token = token;
    }

    public ResponsavelResponseDTO getResponsavel() {
        return responsavel;
    }

    public List<CriancaResponseDTO> getCriancas() {
        return criancas;
    }

    public String getToken() {
        return token;
    }
}
