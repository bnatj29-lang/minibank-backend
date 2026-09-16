package com.minibank.repository;
import org.springframework.jdbc.core.JdbcTemplate;
import java.math.BigDecimal;
import com.minibank.model.Missao;
import java.util.List;
import org.springframework.jdbc.core.RowMapper;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import java.sql.PreparedStatement;

@Repository
public class MissaoRepository {

    private final JdbcTemplate jdbcTemplate;


    public MissaoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long salvar(Long criancaId, String criterio, BigDecimal nota) {

        KeyHolder keyHolder = new GeneratedKeyHolder();

        String sql = """
            INSERT INTO missao (crianca_id, criterio, nota)
            VALUES (?, ?, ?)
            """;

        jdbcTemplate.update(connection -> {

            PreparedStatement ps = connection.prepareStatement(
                    sql,
                    new String[]{"id"}
            );

            ps.setLong(1, criancaId);
            ps.setString(2, criterio);
            ps.setBigDecimal(3, nota);

            return ps;

        }, keyHolder);

        Number key = keyHolder.getKey();

        if (key == null) {
            throw new IllegalStateException(
                    "Não foi possível obter o ID da missão criada."
            );
        }

        return key.longValue();
    }

    public List<Missao> listarPorCrianca(Long criancaId) {

        RowMapper<Missao> mapper = (rs, rowNum) -> new Missao(
                rs.getLong("id"),
                rs.getString("criterio"),
                rs.getLong("crianca_id"),
                rs.getBigDecimal("nota")
        );
        return jdbcTemplate.query(
                "SELECT * FROM missao WHERE crianca_id = ?",
                mapper,
                criancaId
        );
    }
    public Optional<Missao> buscarPorId(Long id) {

        RowMapper<Missao> mapper = (rs, rowNum) -> new Missao(
                rs.getLong("id"),
                rs.getString("criterio"),
                rs.getLong("crianca_id"),
                rs.getBigDecimal("nota")
        );

        try {
            Missao missao = jdbcTemplate.queryForObject(
                    "SELECT * FROM missao WHERE id = ?",
                    mapper,
                    id
            );

            return Optional.ofNullable(missao);
        }catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }


    public void atualizar(Long id, String criterio, BigDecimal nota) {
        jdbcTemplate.update(
                "UPDATE missao SET criterio = ?, nota = ? WHERE id = ?",
                criterio,
                nota,
                id
        );

    }
    public void excluir(Long id) {
        jdbcTemplate.update(
                "DELETE FROM missao WHERE id = ?",

                id

        );

    }



}
