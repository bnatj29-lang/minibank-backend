package com.minibank.controller;

import com.minibank.dto.CriancaRequestDTO;
import com.minibank.service.CriancaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.minibank.model.Crianca;

@RestController
@RequestMapping("/criancas")
public class CriancaController {

    private final CriancaService criancaService;

    public CriancaController(CriancaService criancaService) {
        this.criancaService = criancaService;
    }

    @PostMapping
    public ResponseEntity<Crianca> adicionarCrianca(
            @Valid @RequestBody CriancaRequestDTO dto,
            @RequestParam Long responsavelId) {

        Crianca crianca = criancaService.adicionarCrianca(dto, responsavelId);

        return ResponseEntity.status(HttpStatus.CREATED).body(crianca);
    }
}