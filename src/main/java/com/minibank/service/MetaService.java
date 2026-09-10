package com.minibank.service;

import com.minibank.dto.MetaRequestDTO;
import com.minibank.dto.MetaResponseDTO;
import com.minibank.exception.MetaException;
import com.minibank.model.Meta;
import com.minibank.model.StatusMeta;
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

    public MetaResponseDTO buscarMetaPorId(Long criancaId, Long id) {

        Meta meta = metaRepository.buscarMetaPorId(id)
                .orElseThrow(() -> new MetaException("Meta não encontrada"));

        if (!meta.getCriancaId().equals(criancaId)) {
            throw new MetaException("Essa meta não pertence a esta criança");
        }

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

    public MetaResponseDTO editarMeta(
            Long criancaId,
            Long metaId,
            MetaRequestDTO requestDTO
    ) {

        Meta meta = metaRepository.buscarMetaPorId(metaId)
                .orElseThrow(() -> new MetaException("Meta não encontrada"));

        if (!meta.getCriancaId().equals(criancaId)) {
            throw new MetaException("Essa meta não pertence a esta criança");
        }

        if (meta.getStatus() == StatusMeta.CONQUISTADA) {
            throw new MetaException("Não é possível editar uma meta conquistada!");
        }

        if (requestDTO.getValorMeta().compareTo(BigDecimal.ZERO) <= 0) {
            throw new MetaException("O valor da meta deve ser maior que zero!");
        }

        if (requestDTO.getValorMeta().compareTo(meta.getValorGuardado()) < 0) {
            throw new MetaException("O valor da meta não pode ser menor que o valor guardado!");
        }

        meta.setNomeMeta(requestDTO.getNomeMeta());
        meta.setValorMeta(requestDTO.getValorMeta());

        if (meta.getValorGuardado().compareTo(meta.getValorMeta()) == 0) {
            meta.setStatus(StatusMeta.ALCANÇADA);
        } else {
            meta.setStatus(StatusMeta.ATIVA);
        }

        metaRepository.atualizar(meta);

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
    public String excluirMeta( Long criancaId, Long metaId ){

        Meta meta = metaRepository.buscarMetaPorId(metaId)
                .orElseThrow(() -> new MetaException("Meta não encontrada"));

        if (!meta.getCriancaId().equals(criancaId)) {
            throw new MetaException("Essa meta não pertence a esta criança");
        }

        if(meta.getStatus() == StatusMeta.CONQUISTADA){
            throw new MetaException("Não é possível excluir uma meta conquistada!");
        }

      return metaRepository.excluir(metaId);
    }
}