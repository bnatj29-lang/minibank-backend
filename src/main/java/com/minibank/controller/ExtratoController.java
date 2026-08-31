package com.minibank.controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import com.minibank.dto.RegistrarExtratoRequestDTO;

@RestController
public class ExtratoController {

    @PostMapping
    public void registrar(@RequestBody RegistrarExtratoRequestDTO request){

    }
}
