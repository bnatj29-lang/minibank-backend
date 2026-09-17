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

    public boolean existePorId(Long id) {
        Integer quantidade = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM crianca WHERE id = ?", Integer.class, id);
        return quantidade != null && quantidade > 0;
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

    public boolean pertenceAoResponsavel(Long criancaId, Long responsavelId) {
        Integer quantidade = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM crianca WHERE id = ? AND usuario_id = ?",
                Integer.class,
                criancaId,
                responsavelId
        );

        return quantidade != null && quantidade > 0;
    }

    public void atualizar(Crianca crianca) {
        String sql = "UPDATE crianca SET nome = ?, idade = ? WHERE id = ?";

        jdbcTemplate.update(
                sql,
                crianca.getNome(),
                crianca.getIdade(),
                crianca.getId()
        );
    }

    public void excluir(Long criancaId) {
        jdbcTemplate.update("DELETE FROM configuracao_mesada WHERE crianca_id = ?", criancaId);
        jdbcTemplate.update("DELETE FROM extrato WHERE crianca_id = ?", criancaId);
        jdbcTemplate.update("DELETE FROM missao WHERE crianca_id = ?", criancaId);
        jdbcTemplate.update("DELETE FROM meta WHERE crianca_id = ?", criancaId);
        jdbcTemplate.update("DELETE FROM crianca WHERE id = ?", criancaId);
    }
}
