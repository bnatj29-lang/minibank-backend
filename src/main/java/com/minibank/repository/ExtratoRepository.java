package com.minibank.repository;

import com.minibank.model.Extrato;
import org.springframework.jdbc.core.JdbcTemplate;
import java.util.List;
import org.springframework.stereotype.Repository; //primeiro: conectar o repository ao banco

@Repository
public class ExtratoRepository {

                  //classespring
    private final JdbcTemplate jdbcTemplate;

    public ExtratoRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
        //atributo         //parametro
        //da classe
    }

    public void salvar(Extrato extrato){
       //funcao que executa alteracao no banco
        jdbcTemplate.update(
                "INSERT INTO extrato (crianca_id, tipo, valor, descricao, data) VALUES (?, ?, ?, ?, ?)",
                extrato.getCriancaId(),
                extrato.getTipo(),
                extrato.getValor(),
                extrato.getDescricao(),
                extrato.getData()
        );
    }
     public List<Extrato> buscarPorCrianca(Long criancaId){
        String sql = "SELECT * FROM extrato WHERE crianca_id = ?";
        //funcao que consulta  -- parametros

         //rowMapper é uma funcao que pega o que vem do mysql e transforma em objeto Extrato (linhas)
         return jdbcTemplate.query(
                  sql, //1. qual consulta executar
                 (rs, rowNum) ->  {
                   Extrato extrato = new Extrato(); //objeto para armazenar o que vem do banco

                     extrato.setId(rs.getLong("id"));

                     return extrato;
                  }
          );
     }


    //Frontend → Controller → Service → Repository → MySQL
    //O Repository é a parte que vai conversar diretamente com o banco.
    //ele vai precisar de duas operacoes: salvar uma movimentacao - consultar o extrato
    // JDBC é uma API do Java que permite a comunicação entre aplicações Java e bancos.


}
