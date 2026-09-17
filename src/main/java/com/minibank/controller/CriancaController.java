package com.minibank.controller;

import com.minibank.dto.AdicionarCriancaRequestDTO;
import com.minibank.dto.CriancaResponseDTO;
import com.minibank.dto.EditarCriancaRequestDTO;
import com.minibank.service.CriancaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.minibank.model.Crianca;

import java.util.List;

@RestController
@RequestMapping("/criancas")
@CrossOrigin(origins = "http://localhost:5173")
public class CriancaController {

    private final CriancaService criancaService;

    public CriancaController(CriancaService criancaService) {
        this.criancaService = criancaService;
    }

    // =================
    // ADICIONAR CRIANÇA
    // =================

    @PostMapping
    public ResponseEntity<Crianca> adicionarCrianca(
            @Valid @RequestBody AdicionarCriancaRequestDTO dto,
            @RequestParam Long responsavelId
    ) {

        Crianca crianca =
                criancaService.adicionarCrianca(dto, responsavelId);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(crianca);
    }

    @GetMapping("/responsavel/{responsavelId}")
    public List<CriancaResponseDTO> listarPorResponsavel(@PathVariable Long responsavelId) {
        return criancaService.listarPorResponsavel(responsavelId);
    }

    // ================
    // EDITAR CRIANÇA
    // ================

    @PutMapping("/{criancaId}/responsavel/{responsavelId}")
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
