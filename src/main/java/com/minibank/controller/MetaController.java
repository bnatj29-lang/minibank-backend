package com.minibank.controller;

import com.minibank.dto.AporteMetaRequestDTO;
import com.minibank.dto.MetaRequestDTO;
import com.minibank.dto.MetaResponseDTO;
import com.minibank.service.MetaService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/metas")
public class MetaController {

    private final MetaService service;

    public MetaController(MetaService metaService) {

        this.service = metaService;
    }

    @PostMapping("/{criancaId}/criar")
    public MetaResponseDTO criarMeta(
            @PathVariable Long criancaId,
            @RequestBody MetaRequestDTO requestDTO
    ) {

        return service.criarMeta(criancaId, requestDTO);
    }

    @GetMapping("/{criancaId}/listar-todas")
    public List<MetaResponseDTO> listarMetas(@PathVariable Long criancaId) {
        return service.listarMetas(criancaId);
    }

    @GetMapping("/{criancaId}/buscar/{metaId}")
    public MetaResponseDTO buscarMetaPorId(
            @PathVariable Long criancaId,
            @PathVariable Long metaId
    ) {
        return service.buscarMetaPorId(criancaId, metaId);
    }

    @PutMapping("/{criancaId}/editar/{metaId}")
    public MetaResponseDTO editarMeta(
            @PathVariable Long criancaId,
            @PathVariable Long metaId,
            @Valid @RequestBody MetaRequestDTO requestDTO
    ) {
        return service.editarMeta(criancaId, metaId, requestDTO);
    }

    @DeleteMapping("/{criancaId}/excluir/{metaId}")
    public String excluirMeta(
            @PathVariable Long criancaId,
            @PathVariable Long metaId) {
        return service.excluirMeta(criancaId, metaId);

    }

    @PostMapping("/{criancaId}/metas/{metaId}/guardar")
    public MetaResponseDTO guardarDinheiroMeta(
            @PathVariable Long criancaId,
            @PathVariable Long metaId,
            @RequestBody AporteMetaRequestDTO requestDTO) {

        return service.guardarDinheiroMeta(criancaId, metaId, requestDTO);
    }

    @PostMapping("/{criancaId}/metas/{metaId}/conquistar")
    public MetaResponseDTO conquistarMeta(
            @PathVariable Long criancaId,
            @PathVariable Long metaId) {
        return service.conquistarMeta(criancaId, metaId);
    }
}