package com.minibank.repository;

import com.minibank.model.Crianca;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

@Repository
public class CriancaRepository {

    private final JdbcTemplate jdbcTemplate;

    public CriancaRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void salvar(Crianca crianca) {
        String sql = "INSERT INTO crianca (nome, idade, usuario_id, criado_em) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, crianca.getNome());
            ps.setInt(2, crianca.getIdade());
            ps.setLong(3, crianca.getResponsavelId());
            ps.setTimestamp(4, Timestamp.valueOf(crianca.getCriadoEm()));
            return ps;
        }, keyHolder);

        Long idGerado = keyHolder.getKey().longValue();
        crianca.setId(idGerado);
    }

    public List<Crianca> buscarPorResponsavel(Long responsavelId) {
        String sql = "SELECT id, nome, idade, usuario_id, criado_em FROM crianca WHERE usuario_id = ? ORDER BY id";

        return jdbcTemplate.query(sql, (rs, numeroLinha) -> {
            Crianca crianca = new Crianca();
            crianca.setId(rs.getLong("id"));
            crianca.setNome(rs.getString("nome"));
            crianca.setIdade(rs.getInt("idade"));
            crianca.setResponsavelId(rs.getLong("usuario_id"));
            crianca.setCriadoEm(rs.getTimestamp("criado_em").toLocalDateTime());
            return crianca;
        }, responsavelId);
    }
}