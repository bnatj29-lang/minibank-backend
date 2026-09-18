package com.minibank.controller;

import com.minibank.dto.LoginRequestDTO;
import com.minibank.dto.LoginResponseDTO;
import com.minibank.dto.VerificarSenhaPainelRequestDTO;
import com.minibank.dto.BuscarEmailRequestDTO;
import com.minibank.dto.RedefinirSenhaRequestDTO;
import com.minibank.service.LoginService;
import com.minibank.service.PainelService;
import com.minibank.service.RecuperacaoSenhaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/autenticar")
@RestController
public class LoginController {

    private final PainelService painelService;
    private final LoginService loginService;
    private final RecuperacaoSenhaService recuperacaoSenhaService;
    public LoginController(PainelService painelService, LoginService loginService,
                           RecuperacaoSenhaService recuperacaoSenhaService) {
        this.painelService = painelService;
        this.loginService = loginService;
        this.recuperacaoSenhaService = recuperacaoSenhaService;
    }

    @PostMapping("/painel")
    public ResponseEntity<Void> loginPainel(
            @Valid @RequestBody VerificarSenhaPainelRequestDTO request) {

        painelService.verificarSenhaPainel(request.getEmail(), request.getSenha());

        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
            @Valid @RequestBody LoginRequestDTO request) {

        LoginResponseDTO resposta = loginService.login(request);
        return ResponseEntity.ok(resposta);
    }

    @PostMapping("/esqueci-senha")
    public ResponseEntity<?> esqueciSenha(@Valid @RequestBody BuscarEmailRequestDTO request) {
        recuperacaoSenhaService.solicitar(request.getEmail());
        return ResponseEntity.ok(java.util.Map.of(
                "mensagem", "Se existir uma conta cadastrada com esse e-mail, enviaremos as instruções para redefinir sua senha."));
    }

    @PostMapping("/redefinir-senha")
    public ResponseEntity<Void> redefinirSenha(@Valid @RequestBody RedefinirSenhaRequestDTO request) {
        recuperacaoSenhaService.redefinir(request);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/redefinir-senha/validar")
    public ResponseEntity<Void> validarTokenRecuperacao(@RequestParam String token) {
        recuperacaoSenhaService.validarToken(token);
        return ResponseEntity.noContent().build();
    }
}
