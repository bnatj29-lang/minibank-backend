package com.minibank.service;
import com.minibank.dto.RegistrarExtratoRequestDTO;
import com.minibank.model.Extrato;
import com.minibank.model.Meta;
import com.minibank.model.StatusMeta;
import org.springframework.stereotype.Service;
import com.minibank.repository.ExtratoRepository;
import com.minibank.repository.MetaRepository;
import java.time.LocalDate;
import java.util.List;
import java.math.BigDecimal;

@Service //logica/regras da aplicacao
public class ExtratoService {

 private final ExtratoRepository extratoRepository; //atributo que guarda o repósitory
 private final MetaRepository metaRepository;



//construtor:
  public ExtratoService(ExtratoRepository extratoRepository, MetaRepository metaRepository) {
      this.extratoRepository = extratoRepository;
      this.metaRepository = metaRepository;
      //atributo - parametro
  }


  //METODO QUE REGISTRA A MOVIMENTACAO
    //recebe objeto dto contendo os dados enviados pelo front
  public void registrar(RegistrarExtratoRequestDTO request){

      //1- valor maior que zero?

      if(request.getValor().compareTo(BigDecimal.ZERO) <= 0){
          throw new IllegalArgumentException("o valor deve ser maior que zero.");
      }

      //2- o tipo ENTRADA OU RETIRADA

      if(!request.getTipo().equals("ENTRADA") &&
         !request.getTipo().equals("RETIRADA")){
          throw new IllegalArgumentException("Tipo de movimentação inválido.");
      }

      //3- verificar saldo se for retirada
      if(request.getTipo().equals("RETIRADA")) {
          BigDecimal saldoLivre = calcularSaldoLivre(request.getCriancaId());

          if(request.getValor().compareTo(saldoLivre) > 0){
              throw new IllegalArgumentException("Saldo insuficiente."); //interropcao do metodo
          }
      }

      //4- depois de todas as validacoes, criacao do objeto Extrato
      Extrato extrato = new Extrato();
      extrato.setCriancaId(request.getCriancaId());
      //Pegamos o criancaId que está dentro do DTO e colocamos dentro do objeto Extrato.
      extrato.setTipo(request.getTipo()); //(pegamos o tipo entrada/retirada)
      extrato.setValor(request.getValor()); //pega o valor enviado pelo front
      extrato.setDescricao(request.getDescricao()); //
      extrato.setData(LocalDate.now());


      // O Repository é quem vai executar o INSERT no MySQL. - SALVA NO BANCO
      extratoRepository.salvar(extrato);
  }

  public List<Extrato> consultar(Long criancaId){
      return extratoRepository.buscarPorCrianca(criancaId);
  }

  //METODO DE CALCULO
  public BigDecimal calcularSaldoTotal(Long criancaId){
      List<Extrato> extratos = extratoRepository.buscarPorCrianca(criancaId);

      BigDecimal saldoTotal = BigDecimal.ZERO;

      for(int i = 0; i < extratos.size(); i++){
          Extrato extrato = extratos.get(i);
          if(extrato.getTipo().equals("ENTRADA")) {
              saldoTotal = saldoTotal.add(extrato.getValor());
          } else if(extrato.getTipo().equals("RETIRADA")){
              saldoTotal = saldoTotal.subtract(extrato.getValor());
          }
      }
      return saldoTotal; //saldo é um resultado temporario calculado a partir dos valores
  }

  public BigDecimal calcularValorEmMetas(Long criancaId){
      List<Meta> metas = metaRepository.buscarMetas(criancaId);

      BigDecimal valorEmMetas = BigDecimal.ZERO;

      for(int i = 0; i < metas.size(); i++){
          Meta meta = metas.get(i);
          if(meta.getStatus() == StatusMeta.ATIVA || meta.getStatus() == StatusMeta.ALCANÇADA){
              valorEmMetas = valorEmMetas.add(meta.getValorGuardado());
          }
      }
      return valorEmMetas;
  }

  public BigDecimal calcularSaldoLivre(Long criancaId){
      BigDecimal saldoTotal = calcularSaldoTotal(criancaId);
      BigDecimal valorEmMetas = calcularValorEmMetas(criancaId);
      return saldoTotal.subtract(valorEmMetas);
  }

  public void registrarRetiradaMeta(Long criancaId, BigDecimal valor, String descricao){
      if(valor.compareTo(BigDecimal.ZERO) <= 0){
          throw new IllegalArgumentException("o valor deve ser maior que zero.");
      }

      BigDecimal saldoTotal = calcularSaldoTotal(criancaId);

      if(valor.compareTo(saldoTotal) > 0){
          throw new IllegalArgumentException("Saldo insuficiente.");
      }

      Extrato extrato = new Extrato();
      extrato.setCriancaId(criancaId);
      extrato.setTipo("RETIRADA");
      extrato.setValor(valor);
      extrato.setDescricao(descricao);
      extrato.setData(LocalDate.now());

      extratoRepository.salvar(extrato);
  }


}
