package com.minibank.service;

import com.minibank.dto.MetaRequestDTO;
import com.minibank.dto.MetaResponseDTO;
import com.minibank.exception.MetaException;
import com.minibank.model.Meta;
import com.minibank.repository.MetaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class MetaService {

    private final MetaRepository metaRepository;

    public MetaService(MetaRepository metaRepository) {
        this.metaRepository = metaRepository;
    }

    public MetaResponseDTO criarMeta(Long criancaId, MetaRequestDTO requestDTO) {
        Meta novaMeta = new Meta(criancaId, requestDTO.getNomeMeta(), requestDTO.getValorMeta());
        metaRepository.salvar(novaMeta);

        BigDecimal valorRestante =
                novaMeta.getValorMeta().subtract(novaMeta.getValorGuardado());

        double percentual = novaMeta.getValorGuardado()
                .divide(novaMeta.getValorMeta(), 4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100)).doubleValue();

        MetaResponseDTO response = new MetaResponseDTO(
                novaMeta.getId(),
                novaMeta.getNomeMeta(),
                novaMeta.getValorGuardado(),
                novaMeta.getValorMeta(),
                valorRestante,
                percentual,
                novaMeta.getStatus()
        );
        return response;
    }

    public List<MetaResponseDTO> listarMetas(Long criancaId) {

        List<Meta> metas = metaRepository.buscarMetas(criancaId);

        List<MetaResponseDTO> respostas = new ArrayList<>();

        for (Meta meta : metas) {

            BigDecimal valorRestante =
                    meta.getValorMeta().subtract(meta.getValorGuardado());

            double percentual = meta.getValorGuardado()
                    .divide(meta.getValorMeta(), 4, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100))
                    .doubleValue();

            MetaResponseDTO response = new MetaResponseDTO(
                    meta.getId(),
                    meta.getNomeMeta(),
                    meta.getValorGuardado(),
                    meta.getValorMeta(),
                    valorRestante,
                    percentual,
                    meta.getStatus()
            );
            respostas.add(response);
        }
        return respostas;
    }

    public MetaResponseDTO buscarMetaPorId(Long id) {

        Meta meta = metaRepository.buscarMetaPorId(id)
                .orElseThrow(() -> new MetaException("Meta não encontrada"));

        BigDecimal valorRestante =
                meta.getValorMeta().subtract(meta.getValorGuardado());

        double percentual = meta.getValorGuardado()
                .divide(meta.getValorMeta(), 4, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100))
                .doubleValue();

        return new MetaResponseDTO(
                meta.getId(),
                meta.getNomeMeta(),
                meta.getValorGuardado(),
                meta.getValorMeta(),
                valorRestante,
                percentual,
                meta.getStatus()
        );

        }

    public String excluirMeta(Long id){
      return metaRepository.excluir(id);

    }

}