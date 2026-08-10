package com.minibank.exception;

// Exceção "de negócio": usamos ela quando alguém tenta se cadastrar
// com um e-mail que já existe no banco. Criar exceções customizadas
// deixa o código mais claro do que usar RuntimeException genérica.
public class EmailJaCadastradoException extends RuntimeException {

    public EmailJaCadastradoException(String email) {
        super("Já existe um cadastro com o e-mail: " + email);
    }
}
