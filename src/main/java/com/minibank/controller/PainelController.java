package com.minibank.controller;

import com.minibank.dto.VerificarSenhaPainelRequestDTO;
import com.minibank.service.PainelService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PainelController {

    private final PainelService painelService;

    public PainelController(PainelService painelService) {
        this.painelService = painelService;
    }

    @PostMapping("/painel/verificar")
    public ResponseEntity<Void> verificarSenhaPainel(
            @Valid @RequestBody VerificarSenhaPainelRequestDTO request,
            Authentication authentication) {

        String emailLogado = authentication.getName();

        painelService.verificarSenhaPainel(emailLogado, request.getSenha());

        return ResponseEntity.ok().build();
    }
}