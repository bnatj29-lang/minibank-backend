package com.minibank.dto;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class ConfiguracaoMesadaRequestDTO {
    @NotNull  (message = "O valor base é obrigatório")
    @Positive (message = "O valor base deve ser maior que zero")
    private BigDecimal valorBase;

    @NotNull(message = "A nota mínima intermediária é obrigatória")
    @DecimalMin(value = "0.0", inclusive = true,
            message = "A nota mínima intermediária não pode ser menor que 0")
    @DecimalMax(value = "10.0", inclusive = true,
            message = "A nota mínima intermediária não pode ser maior que 10")
    private BigDecimal notaMinimaIntermediaria;

    @NotNull(message = "A nota mínima máxima é obrigatória")
    @DecimalMin(value = "0.0", inclusive = true,
            message = "A nota mínima máxima não pode ser menor que 0")
    @DecimalMax(value = "10.0", inclusive = true,
            message = "A nota mínima máxima não pode ser maior que 10")
    private BigDecimal notaMinimaMaxima;

    @NotNull(message = "O valor da faixa baixa é obrigatório")
    @Positive(message = "O valor da faixa baixa deve ser maior que zero")
    private BigDecimal valorFaixaBaixa;

    @NotNull(message = "O valor da faixa intermediária é obrigatório")
    @Positive(message = "O valor da faixa intermediária deve ser maior que zero")
    private BigDecimal valorFaixaIntermediaria;

    @NotNull(message = "O valor da faixa máxima é obrigatório")
    @Positive(message = "O valor da faixa máxima deve ser maior que zero")
    private BigDecimal valorFaixaMaxima;

    public ConfiguracaoMesadaRequestDTO(){}

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
