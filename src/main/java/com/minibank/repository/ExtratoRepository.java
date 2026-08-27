package com.minibank.repository;
import com.minibank.model.Extrato; //repository: recebe e devolve objetos Extrato.
import org.springframework.jdbc.core.JdbcTemplate;
import java.util.List;
import org.springframework.stereotype.Repository; //primeiro: conectar o repository ao banco

@Repository //NOTA QUE DIZ QUE ESSA CLASSE É UM REPOSITORY (spring)

public class ExtratoRepository {

    private final JdbcTemplate jdbcTemplate;
    //criacao de atributo  - tipo / nome do atributo


    public ExtratoRepository(JdbcTemplate jdbcTemplate){
        //(tipo do parametro / nome do parametro)
        this.jdbcTemplate = jdbcTemplate;
        //atributo         //pareametro recebido pelo construtor

    }

    //salva a movimentação
    public void salvar(Extrato extrato){

        //funcao que executa alteracao no banco
        //preenche
        jdbcTemplate.update(
                "INSERT INTO extrato (crianca_id, tipo, valor, descricao, data) VALUES (?, ?, ?, ?, ?)",
                extrato.getCriancaId(),
                extrato.getTipo(),
                extrato.getValor(),
                extrato.getDescricao(),
                extrato.getData()
        );
    }

    // A FUNCAO BUSCARPORCRIANCA TRABALHA JUNTO COM "SELECT * FROM...."
     public List<Extrato> buscarPorCrianca(Long criancaId) {
        String sql = "SELECT * FROM extrato WHERE crianca_id = ?";
        //funcao que consulta  -- parametros

         //RowMapper é a função que pega cada linha retornada pelo MySQL e transforma essa linha em um objeto Extrato.
         return jdbcTemplate.query(
                  sql, //1. qual consulta executar
                 (rs, rowNum) ->  {

                      Extrato extrato = new Extrato(); //objeto para armazenar o que vem do banco

                     extrato.setId(rs.getLong("id")); //nome da coluna no banco.
                     extrato.setCriancaId(rs.getLong("crianca_id"));
                     extrato.setTipo(rs.getString("tipo"));
                     extrato.setValor(rs.getBigDecimal("valor"));
                     extrato.setDescricao(rs.getString("descricao"));
                     extrato.setData(rs.getDate("data").toLocalDate());

                     return extrato;
                  },
                   criancaId //esse é o valor que preenche o "?"
          );
     }


    //Frontend → Controller → Service → Repository → MySQL
    //O Repository é a parte que vai conversar diretamente com o banco.
    //ele vai precisar de duas operacoes: salvar uma movimentacao - consultar o extrato
    // JDBC é uma API do Java que permite a comunicação entre aplicações Java e bancos.


}
