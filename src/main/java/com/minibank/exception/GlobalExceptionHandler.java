package com.minibank.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Erro 409 (Conflict): e-mail já cadastrado
    @ExceptionHandler(EmailJaCadastradoException.class)
    public ResponseEntity<Map<String, String>> tratarEmailDuplicado(
            EmailJaCadastradoException ex) {

        Map<String, String> erro = new HashMap<>();
        erro.put("mensagem", ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(erro);
    }

    // Erro 401 (Unauthorized): senha do painel incorreta
    @ExceptionHandler(EmailSenhaIncorretaException.class)
    public ResponseEntity<Map<String, String>> tratarSenhaPainelIncorreta(
            EmailSenhaIncorretaException ex) {

        Map<String, String> erro = new HashMap<>();
        erro.put("mensagem", ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(erro);
    }

    // Erro 400 (Bad Request): campos inválidos no formulário
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> tratarCamposInvalidos(
            MethodArgumentNotValidException ex) {

        Map<String, String> erros = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(erro ->
                erros.put(erro.getField(), erro.getDefaultMessage())
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(erros);
    }

    @ExceptionHandler(CriancaNaoEncontradaException.class)
    public ResponseEntity<Map<String, String>> tratarCriancaNaoEncontrada(CriancaNaoEncontradaException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("mensagem", exception.getMessage()));
    }

    @ExceptionHandler(CriancaNaoPertenceException.class)
    public ResponseEntity<Map<String, String>> tratarCriancaNaoPertence(CriancaNaoPertenceException exception) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(Map.of("mensagem", exception.getMessage()));
    }

    // Erro de saldo insuficiente
    @ExceptionHandler(SaldoInsuficienteException.class)
    public ResponseEntity<Map<String, String>> tratarSaldoInsuficiente(
            SaldoInsuficienteException ex) {

        Map<String, String> erro = new HashMap<>();
        erro.put("mensagem", ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(erro);
    }

    // Erro 400: valor da movimentação inválido
    @ExceptionHandler(ValorMovimentacaoInvalidoException.class)
    public ResponseEntity<Map<String, String>> tratarValorInvalido(
            ValorMovimentacaoInvalidoException ex) {

        Map<String, String> erro = new HashMap<>();
        erro.put("mensagem", ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(erro);
    }

    // Erro 400: tipo da movimentação inválido
    @ExceptionHandler(TipoMovimentacaoInvalidoException.class)
    public ResponseEntity<Map<String, String>> tratarTipoInvalido(
            TipoMovimentacaoInvalidoException ex) {

        Map<String, String> erro = new HashMap<>();
        erro.put("mensagem", ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(erro);
    }

    // Erros relacionados às metas
    @ExceptionHandler(MetaException.class)
    public ResponseEntity<Map<String, String>> tratarMetaNaoEncontrada(
            MetaException ex) {

        Map<String, String> erro = new HashMap<>();
        erro.put("mensagem", ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(erro);
    }

    // Erros relacionados às missões
    @ExceptionHandler(NotaMissaoInvalidaException.class)
    public ResponseEntity<Map<String, String>> tratarMissaoInvalida(
            NotaMissaoInvalidaException ex) {

        return ResponseEntity
                .badRequest()
                .body(Map.of("mensagem", ex.getMessage()));
    }

    // Tratamento genérico para outros IllegalArgumentException
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> tratarIllegalArgumentException(
            IllegalArgumentException exception) {

        Map<String, String> erro = new HashMap<>();
        erro.put("mensagem", exception.getMessage());

        return ResponseEntity
                .badRequest()
                .body(erro);
    }
}
