package com.minibank.dto;

import java.math.BigDecimal;

public class AporteMetaRequestDTO {
    private BigDecimal valorAporte;

    public AporteMetaRequestDTO(){}

    public AporteMetaRequestDTO(BigDecimal valor) {
        this.valorAporte = valor;
    }

    public BigDecimal getValor() {
        return valorAporte;
    }

    public void setValor(BigDecimal valor) {
        this.valorAporte = valor;
    }
}
