package com.minibank.exception;

public class EmailSenhaIncorretaException extends RuntimeException {

    public EmailSenhaIncorretaException(String mensagem) {
        super(mensagem);
    }
}