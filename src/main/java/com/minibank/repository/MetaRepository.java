package com.minibank.repository;

import com.minibank.model.Meta;
import com.minibank.model.StatusMeta;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Repository
public class MetaRepository {

    private final JdbcTemplate jdbcTemplate;

    public MetaRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long salvar(Meta meta) {

        KeyHolder keyHolder = new GeneratedKeyHolder();

        String sql = """
            INSERT INTO meta
            (
                crianca_id,
                nome_meta,
                valor_meta,
                valor_guardado,
                status
            )
            VALUES (?, ?, ?, ?, ?)
            """;

        jdbcTemplate.update(connection -> {

            PreparedStatement ps = connection.prepareStatement(
                    sql,
                    new String[]{"id"}
            );

            ps.setLong(1, meta.getCriancaId());
            ps.setString(2, meta.getNomeMeta());
            ps.setBigDecimal(3, meta.getValorMeta());
            ps.setBigDecimal(4, meta.getValorGuardado());
            ps.setString(5, meta.getStatus().name());

            return ps;

        }, keyHolder);

        Number key = keyHolder.getKey();

        if (key == null) {
            throw new IllegalStateException(
                    "Não foi possível obter o ID da meta criada."
            );
        }

        Long idGerado = key.longValue();

        meta.setId(idGerado);

        return idGerado;
    }

    public List<Meta> buscarMetas(Long criancaId) {

        String sql = "SELECT * FROM meta WHERE crianca_id = ?";

        List<Meta> metas = jdbcTemplate.query(
                sql,
                (rs, rowNum) -> new Meta(
                        rs.getLong("id"),
                        rs.getLong("crianca_id"),
                        rs.getString("nome_meta"),
                        rs.getBigDecimal("valor_meta"),
                        rs.getBigDecimal("valor_guardado"),
                        StatusMeta.valueOf(rs.getString("status"))
                ),
                criancaId
        );

        return metas;
    }

    public Optional<Meta> buscarMetaPorId(Long id) {
        String sql = "SELECT * FROM meta WHERE id = ?";

        List<Meta> metas = jdbcTemplate.query(
                sql,
                (rs, rowNum) -> new Meta(
                        rs.getLong("id"),
                        rs.getLong("crianca_id"),
                        rs.getString("nome_meta"),
                        rs.getBigDecimal("valor_meta"),
                        rs.getBigDecimal("valor_guardado"),
                        StatusMeta.valueOf(rs.getString("status"))
                ),
                id
        );

        if (metas.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(metas.get(0));
        }
    }

    public void atualizar(Meta meta) {
        String sql = """
                
                UPDATE meta
                SET nome_meta = ?,
                valor_meta = ?,
                valor_guardado = ?,
                status = ?
                WHERE id = ?
                """;

        jdbcTemplate.update(
                sql,
                meta.getNomeMeta(),
                meta.getValorMeta(),
                meta.getValorGuardado(),
                meta.getStatus().name(),
                meta.getId()
        );
    }

    public String excluir(Long id){
        int linhasAfetadas = jdbcTemplate.update(
                "DELETE FROM meta WHERE id = ?",
                id
        );
        if (linhasAfetadas == 0) {
            return "Nenhuma meta encontrada com esse ID";
        } else {
            return "Meta excluída com sucesso";
        }
    }

}
