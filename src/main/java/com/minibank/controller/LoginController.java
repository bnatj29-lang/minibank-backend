package com.minibank.controller;

import com.minibank.dto.LoginRequestDTO;
import com.minibank.dto.VerificarSenhaPainelRequestDTO;
import com.minibank.service.LoginService;
import com.minibank.service.PainelService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/autenticar")
@RestController
public class LoginController {

    private final PainelService painelService;
    private final LoginService loginService;
    public LoginController(PainelService painelService, LoginService loginService) {
        this.painelService = painelService;
        this.loginService = loginService;
    }

    @PostMapping("/painel")
    public ResponseEntity<Void> loginPainel(
            @Valid @RequestBody VerificarSenhaPainelRequestDTO request) {

        painelService.verificarSenhaPainel(request.getEmail(), request.getSenha());

        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(
            @Valid @RequestBody LoginRequestDTO request) {

       loginService.login(request);

        return ResponseEntity.ok().build();
    }
}