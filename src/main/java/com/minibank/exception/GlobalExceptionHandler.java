package com.minibank.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

// Essa classe "escuta" as exceções lançadas em qualquer lugar da aplicação
// e transforma cada uma delas numa resposta HTTP organizada, com status
// code correto e uma mensagem clara em JSON.
//
// Sem isso, um erro no backend derrubaria uma stack trace feia direto
// para o frontend, o que é ruim tanto para o usuário quanto para debugar.
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Erro 409 (Conflict): e-mail já cadastrado
    @ExceptionHandler(EmailJaCadastradoException.class)
    public ResponseEntity<Map<String, String>> tratarEmailDuplicado(EmailJaCadastradoException ex) {
        Map<String, String> erro = new HashMap<>();
        erro.put("mensagem", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(erro);
    }

    // Erro 400 (Bad Request): campos inválidos no formulário (@NotBlank, @Email, @Size)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> tratarCamposInvalidos(MethodArgumentNotValidException ex) {
        Map<String, String> erros = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(erro ->
            erros.put(erro.getField(), erro.getDefaultMessage())
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erros);
    }
}
