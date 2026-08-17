package com.minibank.model;

import java.time.LocalDateTime;

public class Crianca {

    private Long id;
    private String nome;
    private int idade;
    private Long responsavelId;
    private LocalDateTime criadoEm;

    public Crianca() {}

    public Crianca(Long responsavelId, int idade, String nome) {
        this.criadoEm = LocalDateTime.now();
        this.responsavelId = responsavelId;
        this.idade = idade;
        this.nome = nome;
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

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public Long getResponsavelId() {
        return responsavelId;
    }

    public void setResponsavelId(Long responsavelId) {
        this.responsavelId = responsavelId;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(LocalDateTime criadoEm) {
        this.criadoEm = criadoEm;
    }
}