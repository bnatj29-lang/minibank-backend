package com.minibank.exception;

public class EmailRecuperacaoException extends RuntimeException {

    public EmailRecuperacaoException() {
        super("Não foi possível enviar o e-mail de recuperação. Tente novamente.");
    }
}
