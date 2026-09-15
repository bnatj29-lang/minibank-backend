package com.minibank.service;

import com.minibank.dto.AporteMetaRequestDTO;
import com.minibank.dto.MetaRequestDTO;
import com.minibank.dto.MetaResponseDTO;
import com.minibank.exception.MetaException;
import com.minibank.model.Meta;
import com.minibank.model.StatusMeta;
import com.minibank.repository.MetaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class MetaService {

    private final MetaRepository metaRepository;
    private final ExtratoService extratoService;

    public MetaService(MetaRepository metaRepository, ExtratoService extratoService) {
        this.metaRepository = metaRepository;
        this.extratoService = extratoService;
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

    public String excluirMeta(Long criancaId, Long metaId) {

        Meta meta = metaRepository.buscarMetaPorId(metaId)
                .orElseThrow(() -> new MetaException("Meta não encontrada"));

        if (!meta.getCriancaId().equals(criancaId)) {
            throw new MetaException("Essa meta não pertence a esta criança");
        }

        if (meta.getStatus() == StatusMeta.CONQUISTADA) {
            throw new MetaException("Não é possível excluir uma meta conquistada!");
        }

        return metaRepository.excluir(metaId);
    }

    public MetaResponseDTO guardarDinheiroMeta(
            Long criancaId,
            Long metaId,
            AporteMetaRequestDTO requestDTO
    ) {

        // Busca a meta
        Meta meta = metaRepository.buscarMetaPorId(metaId)
                .orElseThrow(() -> new MetaException("Meta não encontrada"));

        // Verifica se a meta pertence à criança
        if (!meta.getCriancaId().equals(criancaId)) {
            throw new MetaException("Essa meta não pertence a esta criança");
        }

        // Não permite guardar dinheiro em uma meta já conquistada
        if (meta.getStatus() == StatusMeta.CONQUISTADA) {
            throw new MetaException("Não é possível guardar valor em uma meta conquistada!");
        }

        // O valor que será guardado precisa ser maior que zero
        if (requestDTO.getValorAporte().compareTo(BigDecimal.ZERO) <= 0) {
            throw new MetaException("O valor guardado deve ser maior que zero!");
        }

        // Pergunta ao ExtratoService quanto dinheiro a criança realmente tem livre
        BigDecimal saldoLivre = extratoService.calcularSaldoLivre(criancaId);

        // Não pode guardar mais dinheiro do que existe no saldo livre
        if (requestDTO.getValorAporte().compareTo(saldoLivre) > 0) {
            throw new MetaException("Saldo insuficiente para guardar esse valor na meta!");
        }

        // Calcula quanto ainda falta para completar a meta
        BigDecimal valorRestante =
                meta.getValorMeta().subtract(meta.getValorGuardado());

        // Não permite guardar mais do que falta para completar a meta
        if (requestDTO.getValorAporte().compareTo(valorRestante) > 0) {
            throw new MetaException(
                    "Não é possível guardar um valor maior que o restante da meta!"
            );
        }

        // Soma o novo aporte ao valor que já estava guardado
        BigDecimal novoValorGuardado =
                meta.getValorGuardado().add(requestDTO.getValorAporte());

        meta.setValorGuardado(novoValorGuardado);

        // Se chegou exatamente ao valor da meta, ela passa a ser ALCANÇADA
        if (meta.getValorGuardado().compareTo(meta.getValorMeta()) == 0) {
            meta.setStatus(StatusMeta.ALCANÇADA);
        } else {
            meta.setStatus(StatusMeta.ATIVA);
        }

        // Atualiza a meta no banco
        metaRepository.atualizar(meta);

        // Recalcula os dados que serão devolvidos para o front
        BigDecimal novoValorRestante =
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
                novoValorRestante,
                percentual,
                meta.getStatus()
        );
    }

    @Transactional
    public MetaResponseDTO conquistarMeta(Long criancaId, Long metaId) {

        // Busca a meta no banco
        Meta meta = metaRepository.buscarMetaPorId(metaId)
                .orElseThrow(() -> new MetaException("Meta não encontrada"));

        // Confere se essa meta realmente pertence à criança
        if (!meta.getCriancaId().equals(criancaId)) {
            throw new MetaException("Essa meta não pertence a esta criança");
        }

        // Uma meta ainda em andamento não pode ser conquistada
        if (meta.getStatus() == StatusMeta.ATIVA) {
            throw new MetaException(
                    "A meta ainda não foi alcançada!"
            );
        }

        // Impede que a mesma meta seja conquistada duas vezes
        if (meta.getStatus() == StatusMeta.CONQUISTADA) {
            throw new MetaException(
                    "Essa meta já foi conquistada!"
            );
        }

        // Agora o dinheiro reservado passa a ser um gasto real
        extratoService.registrarRetiradaMeta(
                criancaId,
                meta.getValorGuardado(),
                "Conquista da meta: " + meta.getNomeMeta()
        );

        // A meta deixa de ser apenas alcançada e vira uma conquista
        meta.setStatus(StatusMeta.CONQUISTADA);

        // Salva a mudança no banco
        metaRepository.atualizar(meta);

        // Como ela foi conquistada, continua 100% completa
        BigDecimal valorRestante = BigDecimal.ZERO;
        double percentual = 100.0;

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
}

