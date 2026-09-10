package com.minibank.dto;

import java.math.BigDecimal;

public class AporteMetaRequestDTO {
    private BigDecimal valorAporte;

    public AporteMetaRequestDTO(){}

    public AporteMetaRequestDTO(BigDecimal valorAporte) {
        this.valorAporte = valorAporte;
    }

    public BigDecimal getValorAporte() {
        return valorAporte;
    }

    public void setValorAporte(BigDecimal valorAporte) {
        this.valorAporte = valorAporte;
    }
}
