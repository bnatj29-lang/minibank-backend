package com.minibank.controller;

import com.minibank.dto.MetaRequestDTO;
import com.minibank.dto.MetaResponseDTO;
import com.minibank.service.MetaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/criancas")
public class MetaController {

    private final MetaService service;

    public MetaController(MetaService metaService) {
        this.service = metaService;
    }

    @PostMapping("/{criancaId}/metas")
    public MetaResponseDTO criarMeta(
            @PathVariable Long criancaId,
            @RequestBody MetaRequestDTO requestDTO
    ) {
        return service.criarMeta(criancaId, requestDTO);
    }

    @GetMapping("/{criancaId}/metas")
    public List<MetaResponseDTO> listarMetas(@PathVariable Long criancaId) {
        return service.listarMetas(criancaId);
    }

    @GetMapping("/{criancaId}/metas/{metaId}")
    public MetaResponseDTO buscarMetaPorId(
            @PathVariable Long criancaId,
            @PathVariable Long metaId
    ) {
        return service.buscarMetaPorId(metaId);
    }

    @DeleteMapping("/{criancaId}/metas/{metaId}")
    public String excluirMeta(
            @PathVariable Long criancaId,
            @PathVariable Long metaId) {
        return service.excluirMeta(criancaId, metaId);

    }
}