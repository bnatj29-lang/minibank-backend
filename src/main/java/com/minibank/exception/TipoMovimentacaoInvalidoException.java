package com.minibank.exception;

public class TipoMovimentacaoInvalidoException extends RuntimeException {

    public TipoMovimentacaoInvalidoException(String mensagem) {
        super(mensagem);
    }
}
