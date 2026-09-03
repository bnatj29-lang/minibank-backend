package com.minibank.controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.minibank.dto.RegistrarExtratoRequestDTO;
import com.minibank.model.Extrato;
import com.minibank.service.ExtratoService;

import java.util.List;


@RestController
@CrossOrigin(origins = "http://localhost:5173")//essa anotacap diz ao spring que a classe é um controller - recebe requisicoes http
public class ExtratoController {

    //criamos um atributo para guardar o Service
    private final ExtratoService extratoService;

    public ExtratoController(ExtratoService extratoService) {
        this.extratoService = extratoService; //guarda o service dentro do atributo
    }


    //==========
    //ENDPOINTS//
    //==========

    //METODO EXECUTADO QND RECEBEMOS UMA REQUISICAO HTTP POST - registrar movimentacao
    @PostMapping("/extrato")
    public void registrar(@RequestBody RegistrarExtratoRequestDTO request) {
        extratoService.registrar(request); //chamando metodo do service
        //passa para ele o dto recebido
    }

  @GetMapping("/extrato/{criancaId}") //consulta o extrato
    public List<Extrato> consultar(@PathVariable Long criancaId){
        return extratoService.consultar(criancaId);
  }

}
