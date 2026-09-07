package com.minibank.model;

import java.math.BigDecimal;

public class Meta {

    private Long id;
    private Long criancaId;

    private String nomeMeta;
    private BigDecimal valorMeta;
    private BigDecimal valorGuardado;
    private StatusMeta status;

    public Meta() {
    }

    public Meta(Long criancaId, String nomeMeta, BigDecimal valorMeta) {
        this.criancaId = criancaId;
        this.nomeMeta = nomeMeta;
        this.valorMeta = valorMeta;
        this.valorGuardado = BigDecimal.ZERO;
        this.status = StatusMeta.ATIVA;
    }

    public Meta(Long id, Long criancaId, String nomeMeta, BigDecimal valorMeta, BigDecimal valorGuardado, StatusMeta status) {
        this.id = id;
        this.criancaId = criancaId;
        this.nomeMeta = nomeMeta;
        this.valorMeta = valorMeta;
        this.valorGuardado = valorGuardado;
        this.status = status;
    }

    public BigDecimal getValorMeta() {
        return valorMeta;
    }

    public void setValorMeta(BigDecimal valorMeta) {
        this.valorMeta = valorMeta;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCriancaId() {
        return criancaId;
    }

    public void setCriancaId(Long criancaId) {
        this.criancaId = criancaId;
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

    public StatusMeta getStatus() {
        return status;
    }

    public void setStatus(StatusMeta status) {
        this.status = status;
    }
}