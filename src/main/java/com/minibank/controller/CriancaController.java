package com.minibank.controller;

import com.minibank.dto.EditarCriancaRequestDTO;
import com.minibank.service.CriancaService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class CriancaController {

    // Criamos um atributo para guardar o Service
    private final CriancaService criancaService;

    public CriancaController(CriancaService criancaService) {
        this.criancaService = criancaService;
    }

    // ==========
    // ENDPOINTS//
    // ==========


    @PutMapping("/criancas/{criancaId}/responsavel/{responsavelId}")
    public void editar(
            @PathVariable Long criancaId,
            @PathVariable Long responsavelId,
            @Valid @RequestBody EditarCriancaRequestDTO request
    ) {
        criancaService.editar(
                criancaId,
                responsavelId,
                request
        );
    }
}