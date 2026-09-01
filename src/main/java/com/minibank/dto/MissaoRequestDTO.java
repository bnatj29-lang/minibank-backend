package com.minibank.dto;
import java.math.BigDecimal;

public class MissaoRequestDTO {

    private String criterio;
    private BigDecimal nota;

    public MissaoRequestDTO() {
    }

    public MissaoRequestDTO(String criterio, BigDecimal nota) {
        this.criterio = criterio;
        this.nota = nota;
    }

    public String getCriterio() {
        return criterio;
    }

    public void setCriterio(String criterio) {
        this.criterio = criterio;
    }

    public BigDecimal getNota() {
        return nota;
    }

    public void setNota(BigDecimal nota) {
        this.nota = nota;
    }

}
