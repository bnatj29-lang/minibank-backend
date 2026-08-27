package com.minibank.dto;
import java.math.BigDecimal;

public class RegistrarExtratoRequestDTO {
    private Long criancaId;
    private String tipo;
    private BigDecimal valor;
    private String descricao;

    public Long getCriancaId() { return criancaId;}
    public void setCriancaId(Long criancaId) {
        this.criancaId = criancaId;
    }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo){
        this.tipo = tipo;
    }

    public BigDecimal getValor() {return valor;}
    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getDescricao(){return descricao;}
    public void setDescricao(String descricao){
        this.descricao = descricao;
    }
}
