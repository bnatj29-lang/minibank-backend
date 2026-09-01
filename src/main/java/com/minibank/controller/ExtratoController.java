package com.minibank.controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import com.minibank.dto.RegistrarExtratoRequestDTO;  //importa o dto (RECEBERA OS DADOS DO FRONT)
import com.minibank.service.ExtratoService;


@RestController //essa anotacap diz ao spring que a classe é um controller - recebe requisicoes http
public class ExtratoController {

    //criamos um atributo para guardar o service
    private final ExtratoService extratoService;

    public ExtratoController(ExtratoService extratoService) {
        this.extratoService = extratoService; //guarda o service dentro do atributo
    }

    //METODO EXECUTADO QND RECEBEMOS UMA REQUISICAO HTTP POST
    @PostMapping("/extrato")
    public void registrar(@RequestBody RegistrarExtratoRequestDTO request) {
        extratoService.registrar(request); //chamando metodo do service
        //passa para ele o dto recebido
    }



}
