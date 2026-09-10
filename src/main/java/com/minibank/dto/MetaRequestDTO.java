package com.minibank.dto;

import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public class MetaRequestDTO {

    @NotBlank(message = "O nome da meta não pode estar vazio!")
    private String nomeMeta;
    private BigDecimal valorMeta;

    public MetaRequestDTO() {
    }

    public MetaRequestDTO(String nomeMeta, BigDecimal valorMeta) {
        this.nomeMeta = nomeMeta;
        this.valorMeta = valorMeta;
    }

    public String getNomeMeta() {

        return nomeMeta;
    }

    public void setNomeMeta(String nomeMeta) {
        this.nomeMeta = nomeMeta;
    }

    public BigDecimal getValorMeta() {

        return valorMeta;
    }

    public void setValorMeta(BigDecimal valorMeta) {
        this.valorMeta = valorMeta;
    }
}
