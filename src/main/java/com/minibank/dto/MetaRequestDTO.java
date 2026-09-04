package com.minibank.dto;

import java.math.BigDecimal;

public class MetaRequestDTO {

    private String nomeMeta;
    private BigDecimal valorAlvo;

    public MetaRequestDTO() {}

    public MetaRequestDTO(String nomeMeta, BigDecimal valorAlvo) {
        this.nomeMeta = nomeMeta;
        this.valorAlvo = valorAlvo;
    }

    public String getNomeMeta() {
        return nomeMeta;
    }

    public void setNomeMeta(String nomeMeta) {
        this.nomeMeta = nomeMeta;
    }

    public BigDecimal getValorAlvo() {
        return valorAlvo;
    }

    public void setValorAlvo(BigDecimal valorAlvo) {
        this.valorAlvo = valorAlvo;
    }
}
