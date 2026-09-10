package com.minibank.dto;

import java.util.List;

// Dados da família retornados somente após validar o e-mail e a senha.
public class LoginResponseDTO {

    private ResponsavelResponseDTO responsavel;
    private List<CriancaResponseDTO> criancas;

    public LoginResponseDTO(ResponsavelResponseDTO responsavel, List<CriancaResponseDTO> criancas) {
        this.responsavel = responsavel;
        this.criancas = criancas;
    }

    public ResponsavelResponseDTO getResponsavel() {
        return responsavel;
    }

    public List<CriancaResponseDTO> getCriancas() {
        return criancas;
    }
}
