package com.minibank.model;

import java.math.BigDecimal;

public class ConfiguracaoMesada {

    private Long id;
    private Long criancaId;

    private BigDecimal valorBase;
    private BigDecimal notaMinimaIntermediaria;
    private BigDecimal notaMinimaMaxima;

    private BigDecimal valorFaixaBaixa;
    private BigDecimal valorFaixaIntermediaria;
    private BigDecimal valorFaixaMaxima;

    public ConfiguracaoMesada(){}

    public ConfiguracaoMesada(Long id, Long criancaId, BigDecimal valorBase, BigDecimal notaMinimaIntermidiaria, BigDecimal notaMinimaMaxima, BigDecimal valorFaixaBaixa, BigDecimal valorFaixaIntermediaria, BigDecimal valorFaixaMaxima) {
        this.id = id;
        this.criancaId = criancaId;
        this.valorBase = valorBase;
        this.notaMinimaIntermediaria = notaMinimaIntermidiaria;
        this.notaMinimaMaxima = notaMinimaMaxima;
        this.valorFaixaBaixa = valorFaixaBaixa;
        this.valorFaixaIntermediaria = valorFaixaIntermediaria;
        this.valorFaixaMaxima = valorFaixaMaxima;
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

    public BigDecimal getValorBase() {
        return valorBase;
    }

    public void setValorBase(BigDecimal valorBase) {
        this.valorBase = valorBase;
    }

    public BigDecimal getNotaMinimaIntermediaria() {
        return notaMinimaIntermediaria;
    }

    public void setNotaMinimaIntermediaria(BigDecimal notaMinimaIntermediaria) {
        this.notaMinimaIntermediaria = notaMinimaIntermediaria;
    }

    public BigDecimal getNotaMinimaMaxima() {
        return notaMinimaMaxima;
    }

    public void setNotaMinimaMaxima(BigDecimal notaMinimaMaxima) {
        this.notaMinimaMaxima = notaMinimaMaxima;
    }

    public BigDecimal getValorFaixaBaixa() {
        return valorFaixaBaixa;
    }

    public void setValorFaixaBaixa(BigDecimal valorFaixaBaixa) {
        this.valorFaixaBaixa = valorFaixaBaixa;
    }

    public BigDecimal getValorFaixaIntermediaria() {
        return valorFaixaIntermediaria;
    }

    public void setValorFaixaIntermediaria(BigDecimal valorFaixaIntermediaria) {
        this.valorFaixaIntermediaria = valorFaixaIntermediaria;
    }

    public BigDecimal getValorFaixaMaxima() {
        return valorFaixaMaxima;
    }

    public void setValorFaixaMaxima(BigDecimal valorFaixaMaxima) {
        this.valorFaixaMaxima = valorFaixaMaxima;
    }
}
