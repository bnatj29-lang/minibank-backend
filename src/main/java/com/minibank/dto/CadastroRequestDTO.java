package com.minibank.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

// Esse é o formato de dados que o FRONTEND envia quando alguém se cadastra.
// Repare que aqui NÃO existe id nem data de criação: isso é responsabilidade
// do backend, o front só manda o que o usuário digitou no formulário.
//
// As anotações (@NotBlank, @Email, @Size) fazem o Spring validar os dados
// AUTOMATICAMENTE antes mesmo de chegar no Controller. Se algo estiver errado,
// ele já devolve um erro 400 explicando o que falhou.
public class CadastroRequestDTO {

    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @NotBlank(message = "O e-mail é obrigatório")
    @Email(message = "E-mail em formato inválido")
    private String email;

    @NotBlank(message = "A senha é obrigatória")
    @Size(min = 6, message = "A senha precisa ter no mínimo 6 caracteres")
    private String senha;

    @NotBlank(message = "A senha do painel é obrigatória")
    @Size(min = 4, message = "A senha do painel precisa ter no mínimo 4 caracteres")
    private String senhaPainel;

    public CadastroRequestDTO() {
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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getSenhaPainel() {
        return senhaPainel;
    }

    public void setSenhaPainel(String senhaPainel) {
        this.senhaPainel = senhaPainel;
    }
}
