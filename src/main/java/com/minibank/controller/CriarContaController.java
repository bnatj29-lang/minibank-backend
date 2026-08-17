package com.minibank.controller;

import com.minibank.dto.CriarContaRequestDTO;
import com.minibank.dto.ResponsavelResponseDTO;
import com.minibank.service.CriarContaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contas")
@CrossOrigin(origins = "*")

public class CriarContaController {

    private final  CriarContaService criarContaService;

    @Autowired
    public CriarContaController(CriarContaService criarContaService) {
        this.criarContaService = criarContaService;
    }

    @PostMapping("/cadastro")
    public ResponseEntity<ResponsavelResponseDTO> cadastrar(@Valid @RequestBody CriarContaRequestDTO dto) {
        criarContaService.criarConta(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


}
