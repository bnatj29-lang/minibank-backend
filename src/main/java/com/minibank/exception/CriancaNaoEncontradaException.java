package com.minibank.exception;

public class CriancaNaoEncontradaException extends RuntimeException {
    public CriancaNaoEncontradaException(String mensagem) {
        super(mensagem);
    }
}
