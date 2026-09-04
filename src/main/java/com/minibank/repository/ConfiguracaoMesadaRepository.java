package com.minibank.repository;

import com.minibank.model.ConfiguracaoMesada;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ConfiguracaoMesadaRepository {

    private final JdbcTemplate jdbcTemplate;

    public ConfiguracaoMesadaRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void salvar(ConfiguracaoMesada configuracao) {

        String sql = """
                INSERT INTO configuracao_mesada
                (
                    crianca_id,
                    valor_base,
                    nota_minima_intermediaria,
                    nota_minima_maxima,
                    valor_faixa_baixa,
                    valor_faixa_intermediaria,
                    valor_faixa_maxima
                )
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        jdbcTemplate.update(
                sql,
                configuracao.getCriancaId(),
                configuracao.getValorBase(),
                configuracao.getNotaMinimaIntermediaria(),
                configuracao.getNotaMinimaMaxima(),
                configuracao.getValorFaixaBaixa(),
                configuracao.getValorFaixaIntermediaria(),
                configuracao.getValorFaixaMaxima()
        );
    }

    public Optional<ConfiguracaoMesada> buscarCrianca(Long criancaId) {

        String sql = "SELECT * FROM configuracao_mesada WHERE crianca_id = ?";

        List<ConfiguracaoMesada> resultado =
                jdbcTemplate.query(
                        sql,
                        (rs, rowNum) -> new ConfiguracaoMesada(
                                rs.getLong("id"),
                                rs.getLong("crianca_id"),
                                rs.getBigDecimal("valor_base"),
                                rs.getBigDecimal("nota_minima_intermediaria"),
                                rs.getBigDecimal("nota_minima_maxima"),
                                rs.getBigDecimal("valor_faixa_baixa"),
                                rs.getBigDecimal("valor_faixa_intermediaria"),
                                rs.getBigDecimal("valor_faixa_maxima")
                        ),
                        criancaId
                );

        return resultado.stream().findFirst();
    }

    public void atualizar(ConfiguracaoMesada configuracao) {

        String sql = """
            UPDATE configuracao_mesada
            SET valor_base = ?,
                nota_minima_intermediaria = ?,
                nota_minima_maxima = ?,
                valor_faixa_baixa = ?,
                valor_faixa_intermediaria = ?,
                valor_faixa_maxima = ?
            WHERE crianca_id = ?
            """;

        jdbcTemplate.update(
                sql,
                configuracao.getValorBase(),
                configuracao.getNotaMinimaIntermediaria(),
                configuracao.getNotaMinimaMaxima(),
                configuracao.getValorFaixaBaixa(),
                configuracao.getValorFaixaIntermediaria(),
                configuracao.getValorFaixaMaxima(),
                configuracao.getCriancaId()
        );
    }
}