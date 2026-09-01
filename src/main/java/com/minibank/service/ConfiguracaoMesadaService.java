package com.minibank.service;

import com.minibank.repository.ConfiguracaoMesadaRepository;
import org.springframework.stereotype.Service;

@Service
public class ConfiguracaoMesadaService {

    private final ConfiguracaoMesadaRepository repository;

    public ConfiguracaoMesadaService(ConfiguracaoMesadaRepository repository) {
        this.repository = repository;
    }
}