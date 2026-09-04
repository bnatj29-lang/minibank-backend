package com.minibank.controller;

import com.minibank.dto.ConfiguracaoMesadaRequestDTO;
import com.minibank.service.ConfiguracaoMesadaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/criancas")
public class ConfiguracaoMesadaController {

    private final ConfiguracaoMesadaService service;

    public ConfiguracaoMesadaController(ConfiguracaoMesadaService service) {
        this.service = service;
    }

    @PutMapping("/{criancaId}/configuracao-mesada")
    public ResponseEntity<Void> configurarMesada(
            @PathVariable Long criancaId,
            @Valid @RequestBody ConfiguracaoMesadaRequestDTO dto) {

        service.configurar(criancaId, dto);

        return ResponseEntity.ok().build();
    }
}