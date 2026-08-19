package com.minibank.repository;

import com.minibank.model.Responsavel;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Optional;

// Diferente da versão com Hibernate/JPA, aqui NÃO existe geração automática
// de SQL. Cada metodo escreve explicitamente a query que será executada.
// O JdbcTemplate (do Spring) só cuida da parte "chata" (abrir conexão,
// tratar exceções, fechar conexão); a query em si é toda nossa.
@Repository
public class ResponsavelRepository {

    private final JdbcTemplate jdbcTemplate;

    public ResponsavelRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean existsByEmail(String email) {
        String sql = "SELECT COUNT(*) FROM usuario WHERE email = ?";
        Integer total = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return total != null && total > 0;
    }

    public Optional<Responsavel> buscarPorEmail(String email){
        String sql = "SELECT COUNT(*) FROM usuario WHERE email = ?";
    }

    // INSERT explícito. Usamos GeneratedKeyHolder para recuperar o "id"
    // que o MySQL gera automaticamente (AUTO_INCREMENT) após o insert.
    public Responsavel salvar(Responsavel responsavel) {
        String sql = "INSERT INTO usuario (nome, email, senha_hash, senha_painel_hash, criado_em) VALUES (?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {

            PreparedStatement ps =
                    connection.prepareStatement(
                            sql,
                            Statement.RETURN_GENERATED_KEYS
                    );

            ps.setString(1, responsavel.getNome());
            ps.setString(2, responsavel.getEmail());
            ps.setString(3, responsavel.getSenhaHash());
            ps.setString(4, responsavel.getSenhaPainelHash());
            ps.setTimestamp(
                    5,
                    Timestamp.valueOf(responsavel.getCriadoEm())
            );

            return ps;

        }, keyHolder);

        Long idGerado = keyHolder.getKey().longValue();

        responsavel.setId(idGerado);

        return responsavel;
    }

    // Transforma uma linha do ResultSet
    // em um objeto Responsavel.
    private Responsavel mapearUsuario(ResultSet rs, int rowNum)
            throws SQLException {

        Responsavel responsavel = new Responsavel();

        responsavel.setId(rs.getLong("id"));
        responsavel.setNome(rs.getString("nome"));
        responsavel.setEmail(rs.getString("email"));
        responsavel.setSenhaHash(rs.getString("senha_hash"));
        responsavel.setSenhaPainelHash(
                rs.getString("senha_painel_hash")
        );
        responsavel.setCriadoEm(
                rs.getTimestamp("criado_em").toLocalDateTime()
        );

        return responsavel;
    }
}