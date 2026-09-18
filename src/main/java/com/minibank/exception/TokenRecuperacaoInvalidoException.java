package com.minibank.exception;

public class TokenRecuperacaoInvalidoException extends RuntimeException {

    public TokenRecuperacaoInvalidoException() {
        super("Este link de recuperação não é mais válido. Solicite um novo.");
    }
}
