package com.minibank.model;
import java.time.LocalDateTime;
import java.util.List;

public class Responsavel {

    private Long id;
    private String nome;
    private String email;
    private String senhaHash;
    private String senhaPainelHash;
    private LocalDateTime criadoEm;

    public Responsavel() {}

    public Responsavel(String nome, String email, String senhaHash, String senhaPainelHash) {
        this.nome = nome;
        this.email = email;
        this.senhaHash = senhaHash;
        this.senhaPainelHash = senhaPainelHash;
        this.criadoEm = LocalDateTime.now();
    }

    public Responsavel(String nome, String email, String senhaHash, String senhaPainelHash, LocalDateTime criadoEm) {
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
