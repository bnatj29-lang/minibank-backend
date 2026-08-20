package com.minibank.exception;

public class SenhaPainelIncorretaException extends RuntimeException {

    public SenhaPainelIncorretaException() {
        super("Senha do painel incorreta");
    }
}