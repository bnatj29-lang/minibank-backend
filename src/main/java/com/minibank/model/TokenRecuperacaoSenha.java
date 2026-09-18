package com.minibank.model;

import java.time.LocalDateTime;

public class TokenRecuperacaoSenha {

    private Long id;
    private Long responsavelId;
    private String tokenHash;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataExpiracao;
    private LocalDateTime utilizadoEm;

    public TokenRecuperacaoSenha(Long responsavelId, String tokenHash,
                                 LocalDateTime dataCriacao, LocalDateTime dataExpiracao) {
        this.responsavelId = responsavelId;
        this.tokenHash = tokenHash;
        this.dataCriacao = dataCriacao;
        this.dataExpiracao = dataExpiracao;
    }

    public TokenRecuperacaoSenha(Long id, Long responsavelId, String tokenHash,
                                 LocalDateTime dataCriacao, LocalDateTime dataExpiracao,
                                 LocalDateTime utilizadoEm) {
        this.id = id;
        this.responsavelId = responsavelId;
        this.tokenHash = tokenHash;
        this.dataCriacao = dataCriacao;
        this.dataExpiracao = dataExpiracao;
        this.utilizadoEm = utilizadoEm;
    }

    public Long getId() {
        return id;
    }

    public Long getResponsavelId() {
        return responsavelId;
    }

    public String getTokenHash() {
        return tokenHash;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public LocalDateTime getDataExpiracao() {
        return dataExpiracao;
    }

    public LocalDateTime getUtilizadoEm() {
        return utilizadoEm;
    }
}
