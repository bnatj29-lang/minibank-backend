package com.minibank.exception;

public class NotaMissaoInvalidaException extends RuntimeException {

    public NotaMissaoInvalidaException(String mensagem) {
        super(mensagem);
    }
}