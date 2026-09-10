package com.minibank.dto;

import com.minibank.model.StatusMeta;

import java.math.BigDecimal;

public class MetaResponseDTO {

    private Long id;
    private String nomeMeta;
    private BigDecimal valorGuardado;
    private BigDecimal valorMeta;
    private BigDecimal valorRestante;
    private double percentual;
    private StatusMeta status;

    public MetaResponseDTO() {
    }

    public MetaResponseDTO(Long id, String nomeMeta, BigDecimal valorGuardado, BigDecimal valorMeta, BigDecimal valorRestante, double percentual, StatusMeta status) {
        this.id = id;
        this.nomeMeta = nomeMeta;
        this.valorGuardado = valorGuardado;
        this.valorMeta = valorMeta;
        this.valorRestante = valorRestante;
        this.percentual = percentual;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeMeta() {
        return nomeMeta;
    }

    public void setNomeMeta(String nomeMeta) {
        this.nomeMeta = nomeMeta;
    }

    public BigDecimal getValorGuardado() {
        return valorGuardado;
    }

    public void setValorGuardado(BigDecimal valorGuardado) {
        this.valorGuardado = valorGuardado;
    }

    public BigDecimal getValorMeta() {
        return valorMeta;
    }

    public void setValorMeta(BigDecimal valorMeta) {
        this.valorMeta = valorMeta;
    }

    public BigDecimal getValorRestante() {
        return valorRestante;
    }

    public void setValorRestante(BigDecimal valorRestante) {
        this.valorRestante = valorRestante;
    }

    public double getPercentual() {
        return percentual;
    }

    public void setPercentual(double percentual) {
        this.percentual = percentual;
    }

    public StatusMeta getStatus() {
        return status;
    }

    public void setStatus(StatusMeta status) {
        this.status = status;
    }
}

