package com.minibank.controller;

import com.minibank.dto.CadastroRequestDTO;
import com.minibank.dto.UsuarioResponseDTO;
import com.minibank.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Essa é a "porta de entrada" da funcionalidade de cadastro.
// O Controller SÓ recebe a requisição e devolve a resposta.
// Toda a lógica de verdade está no Service (UsuarioService).
@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*") // em produção, trocar "*" pela URL real do frontend
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // Endpoint: POST /usuarios/cadastro
    // @Valid ativa as validações que colocamos no CadastroRequestDTO
    @PostMapping("/cadastro")
    public ResponseEntity<UsuarioResponseDTO> cadastrar(@Valid @RequestBody CadastroRequestDTO dto) {
        UsuarioResponseDTO usuarioCriado = usuarioService.cadastrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCriado);
    }

}
