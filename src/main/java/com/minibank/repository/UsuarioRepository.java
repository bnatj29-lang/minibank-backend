package com.minibank.repository;

import com.minibank.model.Usuario;
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
public class UsuarioRepository {

    private final JdbcTemplate jdbcTemplate;

    public UsuarioRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean existsByEmail(String email) {
        String sql = "SELECT COUNT(*) FROM usuario WHERE email = ?";
        Integer total = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return total != null && total > 0;
    }

    public String findByEmail(String email) {
        String sql = "SELECT COUNT(*) FROM usuario WHERE email = ?";
        Integer total = jdbcTemplate.queryForObject(sql, Integer.class, email);
        if (total > 0){
            return "Email já cadastrado";
        }
        return "Email não cadastrado";
    }

    // INSERT explícito. Usamos GeneratedKeyHolder para recuperar o "id"
    // que o MySQL gera automaticamente (AUTO_INCREMENT) após o insert.
    public Usuario salvar(Usuario usuario) {
        String sql = "INSERT INTO usuario (nome, email, senha_hash, criado_em) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, usuario.getSenhaHash());
            ps.setTimestamp(4, Timestamp.valueOf(usuario.getCriadoEm()));
            return ps;
        }, keyHolder);

        Long idGerado = keyHolder.getKey().longValue();
        usuario.setId(idGerado);
        return usuario;
    }

    // Transforma uma linha do ResultSet (retorno "cru" do banco) em um objeto Usuario.
    // Com Hibernate isso acontecia escondido; aqui fica explícito coluna por coluna.
    private Usuario mapearUsuario(ResultSet rs, int rowNum) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setId(rs.getLong("id"));
        usuario.setNome(rs.getString("nome"));
        usuario.setEmail(rs.getString("email"));
        usuario.setSenhaHash(rs.getString("senha_hash"));
        usuario.setCriadoEm(rs.getTimestamp("criado_em").toLocalDateTime());
        return usuario;
    }
}
