package com.minibank.service;

import com.minibank.dto.EditarCriancaRequestDTO;
import com.minibank.model.Crianca;
import com.minibank.repository.CriancaRepository;
import org.springframework.stereotype.Service;

@Service
public class CriancaService {

    private final CriancaRepository criancaRepository;

    public CriancaService(CriancaRepository criancaRepository) {
        this.criancaRepository = criancaRepository;
    }

    public void editar(
            Long criancaId,
            Long responsavelId,
            EditarCriancaRequestDTO request
    ) {

        // 1 - Verifica se a criança existe
        if (!criancaRepository.existePorId(criancaId)) {
            throw new RuntimeException("Criança não encontrada.");
        }

        // 2 - Verifica se a criança pertence ao responsável
        if (!criancaRepository.pertenceAoResponsavel(
                criancaId,
                responsavelId
        )) {
            throw new RuntimeException(
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
}