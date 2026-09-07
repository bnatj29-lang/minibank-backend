package com.minibank.dto;

import java.math.BigDecimal;

public class MetaRequestDTO {

    private String nomeMeta;
    private BigDecimal valorMeta;

    public MetaRequestDTO() {
    }

    public MetaRequestDTO(String nomeMeta, BigDecimal valorAlvo) {
        this.nomeMeta = nomeMeta;
        this.valorMeta = valorAlvo;
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

    public void setValorAlvo(BigDecimal valorAlvo) {
        this.valorMeta = valorAlvo;
    }
}
