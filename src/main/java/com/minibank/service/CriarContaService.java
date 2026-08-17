package com.minibank.service;

import com.minibank.dto.CriancaRequestDTO;
import com.minibank.dto.CriarContaRequestDTO;
import com.minibank.dto.ResponsavelResponseDTO;
import com.minibank.model.Crianca;
import com.minibank.repository.CriancaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CriarContaService {

    private final ResponsavelService responsavelService;
    private final CriancaRepository criancaRepository;

    @Autowired
    public CriarContaService(ResponsavelService responsavelService, CriancaRepository criancaRepository) {
        this.responsavelService = responsavelService;
        this.criancaRepository = criancaRepository;
    }

    public void criarConta(CriarContaRequestDTO dto){
        ResponsavelResponseDTO responsavel = responsavelService.cadastrar(dto.getResponsavel());

        Long responsavelId = responsavel.getId();
        for (CriancaRequestDTO criancaDto : dto.getCrianca()) {

            Crianca novaCrianca = new Crianca(responsavelId, criancaDto.getIdade(), criancaDto.getNome());
            criancaRepository.salvar(novaCrianca);
        }
    }
}