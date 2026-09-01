package com.minibank.service;
import com.minibank.dto.RegistrarExtratoRequestDTO;
import com.minibank.model.Extrato;
import org.springframework.stereotype.Service;
import com.minibank.repository.ExtratoRepository;
import java.time.LocalDate;

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
      Extrato extrato = new Extrato();
      extrato.setCriancaId(request.getCriancaId());
      //Pegamos o criancaId que está dentro do DTO e colocamos dentro do objeto Extrato.
      extrato.setTipo(request.getTipo()); //(pegamos o tipo entrada/retirada)
      extrato.setValor(request.getValor()); //pega o valor enviado pelo front
      extrato.setDescricao(request.getDescricao()); //
      extrato.setData(LocalDate.now());

      // O Repository é quem vai executar o INSERT no MySQL.
      extratoRepository.salvar(extrato);
  }
}
