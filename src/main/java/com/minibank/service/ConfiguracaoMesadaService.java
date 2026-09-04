package com.minibank.service;

import com.minibank.dto.ConfiguracaoMesadaRequestDTO;
import com.minibank.model.ConfiguracaoMesada;
import com.minibank.repository.ConfiguracaoMesadaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ConfiguracaoMesadaService {

    private final ConfiguracaoMesadaRepository repository;

    public ConfiguracaoMesadaService(ConfiguracaoMesadaRepository repository) {
        this.repository = repository;
    }

    public void configurar(Long criancaId, ConfiguracaoMesadaRequestDTO dto) {

        ConfiguracaoMesada configuracao = new ConfiguracaoMesada();

        configuracao.setCriancaId(criancaId);
        configuracao.setValorBase(dto.getValorBase());
        configuracao.setNotaMinimaIntermediaria(dto.getNotaMinimaIntermediaria());
        configuracao.setNotaMinimaMaxima(dto.getNotaMinimaMaxima());
        configuracao.setValorFaixaBaixa(dto.getValorFaixaBaixa());
        configuracao.setValorFaixaIntermediaria(dto.getValorFaixaIntermediaria());
        configuracao.setValorFaixaMaxima(dto.getValorFaixaMaxima());

        Optional<ConfiguracaoMesada> existente =
                repository.buscarCrianca(criancaId);

        if (existente.isPresent()) {
            repository.atualizar(configuracao);
        } else {
            repository.salvar(configuracao);
        }
    }
}