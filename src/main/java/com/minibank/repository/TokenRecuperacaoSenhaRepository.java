package com.minibank.repository;

import com.minibank.model.TokenRecuperacaoSenha;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class TokenRecuperacaoSenhaRepository {

    private final JdbcTemplate jdbcTemplate;

    public TokenRecuperacaoSenhaRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void invalidarAtivos(Long responsavelId) {
        jdbcTemplate.update(
                "UPDATE token_recuperacao_senha SET utilizado_em = UTC_TIMESTAMP() " +
                        "WHERE responsavel_id = ? AND utilizado_em IS NULL AND data_expiracao > UTC_TIMESTAMP()",
                responsavelId);
    }

    public void salvar(TokenRecuperacaoSenha token) {
        jdbcTemplate.update(
                "INSERT INTO token_recuperacao_senha " +
                        "(responsavel_id, token_hash, data_criacao, data_expiracao) " +
                        "VALUES (?, ?, UTC_TIMESTAMP(), DATE_ADD(UTC_TIMESTAMP(), INTERVAL 15 MINUTE))",
                token.getResponsavelId(), token.getTokenHash());
    }

    public Optional<TokenRecuperacaoSenha> buscarValido(String tokenHash) {
        List<TokenRecuperacaoSenha> resultado = jdbcTemplate.query(
                "SELECT id, responsavel_id, token_hash, data_criacao, data_expiracao, utilizado_em " +
                        "FROM token_recuperacao_senha " +
                        "WHERE token_hash = ? AND utilizado_em IS NULL AND data_expiracao > UTC_TIMESTAMP()",
                (rs, rowNum) -> new TokenRecuperacaoSenha(
                        rs.getLong("id"),
                        rs.getLong("responsavel_id"),
                        rs.getString("token_hash"),
                        rs.getTimestamp("data_criacao").toLocalDateTime(),
                        rs.getTimestamp("data_expiracao").toLocalDateTime(),
                        rs.getTimestamp("utilizado_em") == null
                                ? null : rs.getTimestamp("utilizado_em").toLocalDateTime()),
                tokenHash);
        return resultado.stream().findFirst();
    }

    public boolean marcarComoUtilizado(Long id) {
        return jdbcTemplate.update(
                "UPDATE token_recuperacao_senha SET utilizado_em = UTC_TIMESTAMP() " +
                        "WHERE id = ? AND utilizado_em IS NULL AND data_expiracao > UTC_TIMESTAMP()",
                id) == 1;
    }
}
