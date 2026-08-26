package com.minibank.repository;

import com.minibank.model.ConfiguracaoMesada;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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
}