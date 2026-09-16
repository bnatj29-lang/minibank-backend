package com.minibank.service;

import com.minibank.dto.CriancaRequestDTO;
import com.minibank.model.Crianca;
import com.minibank.repository.CriancaRepository;
import org.springframework.stereotype.Service;


@Service
public class CriancaService {

    private final CriancaRepository criancaRepository;

    public CriancaService(CriancaRepository criancaRepository) {
        this.criancaRepository = criancaRepository;
    }

    public Crianca adicionarCrianca(CriancaRequestDTO dto, Long responsavelId) {

        Crianca crianca = new Crianca(
                responsavelId,
                dto.getIdade(),
                dto.getNome()
        );

        criancaRepository.salvar(crianca);

        return crianca;
    }
}