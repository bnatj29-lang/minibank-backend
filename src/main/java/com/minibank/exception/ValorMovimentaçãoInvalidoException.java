package com.minibank.exception;

public class ValorMovimentacaoInvalidoException extends RuntimeException {

    public ValorMovimentacaoInvalidoException(String mensagem) {
        super(mensagem);
    }
}