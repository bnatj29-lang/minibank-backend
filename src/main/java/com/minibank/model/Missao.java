package com.minibank.model;
import java.math.BigDecimal;

public class Missao {

    private Long id;
    private String criterio;
    private Long criancaId;
    private BigDecimal nota;

    public Missao() {
    }

    public Missao(Long id, String criterio, Long criancaId, BigDecimal nota) {
        this.id = id;
        this.criterio = criterio;
        this.criancaId = criancaId;
        this.nota = nota;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCriterio() {
        return criterio;
    }

    public void setCriterio(String criterio) {
        this.criterio = criterio;
    }

    public Long getCriancaId() {
        return criancaId;
    }

    public void setCriancaId(Long criancaId) {
        this.criancaId = criancaId;
    }

    public BigDecimal getNota() {
        return nota;
    }

    public void setNota(BigDecimal nota) {
        this.nota = nota;
    }
}