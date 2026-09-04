package com.minibank.service;
import com.minibank.dto.RegistrarExtratoRequestDTO;
import com.minibank.model.Extrato;
import org.springframework.stereotype.Service;
import com.minibank.repository.ExtratoRepository;
import java.time.LocalDate;
import java.util.List;
import java.math.BigDecimal;

@Service //logica/regras da aplicacao
public class ExtratoService {

 private final ExtratoRepository extratoRepository; //atributo que guarda o repósitory



//construtor:
  public ExtratoService(ExtratoRepository extratoRepository) {
      this.extratoRepository = extratoRepository;
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
          BigDecimal saldo = calcularSaldo(request.getCriancaId());

          if(request.getValor().compareTo(saldo) > 0){
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
  public BigDecimal calcularSaldo(Long criancaId){
      List<Extrato> extratos = extratoRepository.buscarPorCrianca(criancaId);

      BigDecimal saldo = BigDecimal.ZERO;

      for(Extrato extrato : extratos){
          if(extrato.getTipo().equals("ENTRADA")) {
              saldo = saldo.add(extrato.getValor());
          } else if(extrato.getTipo().equals("RETIRADA")){
              saldo = saldo.subtract(extrato.getValor());
          }
      }
      return saldo; //saldo é um resultado temporario calculado a partir dos valores
  }



}
