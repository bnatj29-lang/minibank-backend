package com.minibank.controller;

import com.minibank.service.MissaoService;
import org.springframework.web.bind.annotation.*;
import com.minibank.dto.MissaoRequestDTO;
import com.minibank.model.Missao;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import java.math.BigDecimal;

@RestController
@RequestMapping("/missoes")
public class MissaoController {

    private final MissaoService missaoService;

    public MissaoController(MissaoService missaoService) {
        this.missaoService = missaoService;
    }

    @PostMapping("/{criancaId}")
    public void criarMissao(
            @PathVariable Long criancaId,
            @RequestBody MissaoRequestDTO dto) {

        missaoService.criarMissao(
                criancaId,
                dto.getCriterio(),
                dto.getNota()
        );
    }
    @GetMapping("/crianca/{criancaId}")
    public List<Missao> listarMissoes(
            @PathVariable Long criancaId) {

        return missaoService.listarMissoes(criancaId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Missao> buscarMissaoPorId(
            @PathVariable Long id) {

        Optional<Missao> resultado = missaoService.buscarMissaoPorId(id);

        if (resultado.isPresent()) {
            return ResponseEntity.ok(resultado.get());
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public void atualizarMissao(
            @PathVariable Long id,
            @RequestBody MissaoRequestDTO dto) {

        missaoService.atualizarMissao(
                id,
                dto.getCriterio(),
                dto.getNota()
        );
    }

    @DeleteMapping("/{id}")
    public void excluirMissao(@PathVariable Long id) {
        missaoService.excluirMissao(id);
    }

    @GetMapping("/{criancaId}/media")
    public BigDecimal calcularMedia(
            @PathVariable Long criancaId) {

        return missaoService.calcularMedia(criancaId);
    }

    @GetMapping("/{criancaId}/mesada")
    public BigDecimal calcularMesada(
            @PathVariable Long criancaId) {

        return missaoService.calcularMesada(criancaId);
    }

    @PostMapping("/{criancaId}/mesada/registrar")
    public BigDecimal registrarMesada(
            @PathVariable Long criancaId) {

        return missaoService.registrarMesada(criancaId);
    }




}
