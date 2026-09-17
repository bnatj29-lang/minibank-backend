package com.minibank.service;

import com.minibank.dto.AdicionarCriancaRequestDTO;
import com.minibank.dto.CriancaResponseDTO;
import com.minibank.dto.EditarCriancaRequestDTO;
import com.minibank.exception.CriancaNaoEncontradaException;
import com.minibank.exception.CriancaNaoPertenceException;
import com.minibank.model.Crianca;
import com.minibank.repository.CriancaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CriancaService {

    private final CriancaRepository criancaRepository;

    public CriancaService(CriancaRepository criancaRepository) {
        this.criancaRepository = criancaRepository;
    }

    public Crianca adicionarCrianca(AdicionarCriancaRequestDTO dto, Long responsavelId) {

        Crianca crianca = new Crianca(
                responsavelId,
                dto.getIdade(),
                dto.getNome()
        );

        criancaRepository.salvar(crianca);

        return crianca;
    }

    public List<CriancaResponseDTO> listarPorResponsavel(Long responsavelId) {
        return criancaRepository.buscarPorResponsavel(responsavelId).stream()
                .map(CriancaResponseDTO::new)
                .toList();
    }

    public void editar(
            Long criancaId,
            Long responsavelId,
            EditarCriancaRequestDTO request
    ) {

        // 1 - Verifica se a criança existe
        if (!criancaRepository.existePorId(criancaId)) {
            throw new CriancaNaoEncontradaException("Criança não encontrada.");
        }

        // 2 - Verifica se a criança pertence ao responsável
        if (!criancaRepository.pertenceAoResponsavel(
                criancaId,
                responsavelId
        )) {
            throw new CriancaNaoPertenceException(
                    "Essa criança não pertence a este responsável."
            );
        }

        // 3 - Cria o objeto com os novos dados
        Crianca crianca = new Crianca();

        crianca.setId(criancaId);
        crianca.setNome(request.getNome());
        crianca.setIdade(request.getIdade());
        crianca.setResponsavelId(responsavelId);

        // 4 - Atualiza a criança no banco
        criancaRepository.atualizar(crianca);
    }

    @Transactional
    public void excluir(Long criancaId, Long responsavelId) {
        if (!criancaRepository.existePorId(criancaId)) {
            throw new CriancaNaoEncontradaException("Criança não encontrada.");
        }
        if (!criancaRepository.pertenceAoResponsavel(criancaId, responsavelId)) {
            throw new CriancaNaoPertenceException("Essa criança não pertence a este responsável.");
        }
        criancaRepository.excluir(criancaId);
    }
}
