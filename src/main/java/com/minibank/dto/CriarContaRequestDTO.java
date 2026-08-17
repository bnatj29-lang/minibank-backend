package com.minibank.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class CriarContaRequestDTO {

    @NotNull(message = "Os dados do responsável são obrigatórios")
    @Valid
    private ResponsavelRequestDTO responsavel;

    @NotNull(message = "Os dados da criança são obrigatórios")
    @Valid
    private List<CriancaRequestDTO> crianca;


    public CriarContaRequestDTO() {
    }

    public List<CriancaRequestDTO> getCrianca() {
        return crianca;
    }

    public void setCrianca(List<CriancaRequestDTO> crianca) {

        this.crianca = crianca;
    }

    public ResponsavelRequestDTO getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(ResponsavelRequestDTO responsavel) {

        this.responsavel = responsavel;
    }


}