package com.minibank.model;
import java.math.BigDecimal;
import java.time.LocalDate;

public class Extrato {
    private Long id;
    private Long criancaId;
    private String tipo;
    private BigDecimal valor;
    private String descricao;
    private LocalDate data;

    public Long getId() {
        return id; //DEVOLVE O VALOR GUARDADO NO ATRIBUTO ID PARA A CLASSE PEDINDO
    }

    public void setId(Long id) {
        this.id = id;
    } //this.id É O ATRIBUTO DA CLASSE e id É O PARAMETRO RECEBIDO PELO METODO
      //entao o atributo id recebe o id;

    public Long getCriancaId() {
        return criancaId;
    }

    public void setCriancaId(Long criancaId) {
        this.criancaId = criancaId;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo){
        this.tipo = tipo;
    }

    public BigDecimal getValor(){
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getDescricao(){
        return descricao;
    }
    public void setDescricao(String descricao){
        this.descricao = descricao;
    }

    public LocalDate getData(){
        return data;
    }
    public void setData(LocalDate data){
        this.data = data;
    }

    public Extrato(Long criancaId, String tipo, BigDecimal valor, String descricao, LocalDate data){
        this.criancaId = criancaId;
        this.tipo = tipo;
        this.valor = valor;  //QUANDO UMA MOVIMENTACAO ACONTECE SAO PREENCHIDOS
        this.descricao = descricao;
        this.data = data;
    }
}

//Java cria o Extrato
//      ↓
//id = null
//       ↓
//Repository envia para MySQL
//      ↓
//MySQL salva
//      ↓
//MySQL gera o ID