package com.minibank.service;

import com.minibank.dto.ConfiguracaoMesadaRequestDTO;
import com.minibank.model.ConfiguracaoMesada;
import com.minibank.repository.ConfiguracaoMesadaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ConfiguracaoMesadaService {

    private final ConfiguracaoMesadaRepository repository;
    private final AutorizacaoService autorizacaoService;

    public ConfiguracaoMesadaService(ConfiguracaoMesadaRepository repository, AutorizacaoService autorizacaoService) {

        this.repository = repository;
        this.autorizacaoService = autorizacaoService;
    }

    public Optional<ConfiguracaoMesada> buscar(Long criancaId) {
        autorizacaoService.validarCrianca(criancaId);

        return repository.buscarCrianca(criancaId);
    }

    public void configurar(Long criancaId, ConfiguracaoMesadaRequestDTO dto) {
        autorizacaoService.validarCrianca(criancaId);

        if (dto.getNotaMinimaMaxima().compareTo(dto.getNotaMinimaIntermediaria()) <= 0) {
            throw new IllegalArgumentException(
                    "A nota da faixa máxima deve ser maior que a nota da faixa intermediária.");
        }

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
