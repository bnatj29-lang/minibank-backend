package com.minibank.controller;

import com.minibank.dto.ConfiguracaoMesadaRequestDTO;
import com.minibank.model.ConfiguracaoMesada;
import com.minibank.service.ConfiguracaoMesadaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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

    @GetMapping("/{criancaId}/configuracao-mesada")
    public ResponseEntity<ConfiguracaoMesada> buscarConfiguracao(
            @PathVariable Long criancaId) {

        Optional<ConfiguracaoMesada> configuracao =
                service.buscar(criancaId);

        if (configuracao.isPresent()) {
            return ResponseEntity.ok(configuracao.get());
        }

        return ResponseEntity.notFound().build();
    }
}