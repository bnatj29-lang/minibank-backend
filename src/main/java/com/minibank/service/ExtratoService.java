package com.minibank.service;
import org.springframework.stereotype.Service;
import com.minibank.repository.ExtratoRepository;

@Service //logica/regras da aplicacao
public class ExtratoService {

 private final ExtratoRepository extratoRepository;

  public ExtratoService(ExtratoRepository extratoRepository) {
      this.extratoRepository = extratoRepository;
  }
}
