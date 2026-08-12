package com.minibank.model;

import java.time.LocalDateTime;

// Essa classe representa um registro da tabela "usuario" no banco de dados.
// Repare que aqui NÃO tem nenhuma anotação de JPA/Hibernate (@Entity, @Table, @Id).
// É uma classe Java "comum" (chamada de POJO). Quem sabe transformar isso em
// linhas do banco é o UsuarioRepository, escrevendo SQL na mão com JdbcTemplate.
//
// Isso é mais verboso que usar Hibernate, mas deixa bem claro o que está
// acontecendo em cada consulta, o que ajuda bastante quem está aprendendo.
public class Usuario {

    private Long id;
    private String nome;
    private String email;

    // Aqui NUNCA guardamos a senha em texto puro.
    // Guardamos o "hash" gerado pelo BCrypt (ver UsuarioService).
    private String senhaHash;

    private String senhaPainelHash;

    private LocalDateTime criadoEm;

    public Usuario() {
    }

    public Usuario(String nome, String email, String senhaHash, String senhaPainelHash) {
        this.nome = nome;
        this.email = email;
        this.senhaHash = senhaHash;
        this.senhaPainelHash = senhaPainelHash;
        this.criadoEm = LocalDateTime.now();
    }

    public Usuario(String nome, String email, String senhaHash, String senhaPainelHash, LocalDateTime criadoEm) {
        this.nome = nome;
        this.email = email;
        this.senhaHash = senhaHash;
        this.senhaPainelHash = senhaPainelHash;
        this.criadoEm = criadoEm;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenhaHash() {
        return senhaHash;
    }

    public void setSenhaHash(String senhaHash) {
        this.senhaHash = senhaHash;
    }

    public void setSenhaPainelHash (String senhaPainelHash) {
        this.senhaPainelHash = senhaPainelHash;
    }

    public String getSenhaPainelHash() {
        return senhaPainelHash;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(LocalDateTime criadoEm) {
        this.criadoEm = criadoEm;
    }
}
