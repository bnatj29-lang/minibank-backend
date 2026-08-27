package com.minibank.dto;
import java.math.BigDecimal;

public class RegistrarExtratoRequestDTO {
    private Long criancaId;
    private String tipo;
    private BigDecimal valor;   //os atributos representam os dados que o dto precisa receber
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
        //valor desse objeto -- parametro
    }

    public String getDescricao(){return descricao;}
    public void setDescricao(String descricao){
        this.descricao = descricao;
    }
}
//DTO transporta dados
//O DTO serve para o Spring pegar esses dados (corpo json) e colocar dentro de um objeto Java.
//set = coloca os dados no dto / get pega os dados do dto
//FLUXO COMPLETO:
//FRONT-END -> manda JSON -> CONTROLLER -> DTO -> SERVICE -> REPOSITORY -> JdbcTemplate -> MYSQL