package com.minibank.service;

import com.minibank.dto.RegistrarExtratoRequestDTO;
import com.minibank.exception.SaldoInsuficienteException;
import com.minibank.exception.TipoMovimentacaoInvalidoException;
import com.minibank.exception.ValorMovimentacaoInvalidoException;
import com.minibank.model.Extrato;
import com.minibank.repository.ExtratoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service // lógica/regras da aplicação
public class ExtratoService {

    private final ExtratoRepository extratoRepository; // atributo que guarda o repository

    // construtor
    public ExtratoService(ExtratoRepository extratoRepository) {
        this.extratoRepository = extratoRepository;
    }


    // recebe objeto DTO contendo os dados enviados pelo front
    public void registrar(RegistrarExtratoRequestDTO request) {

        // 1 - valor maior que zero?
        if (request.getValor().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValorMovimentacaoInvalidoException(
                    "O valor deve ser maior que zero."
            );
        }

        // 2 - o tipo é ENTRADA ou RETIRADA?
        if (!request.getTipo().equals("ENTRADA") &&
                !request.getTipo().equals("RETIRADA")) {

            throw new TipoMovimentacaoInvalidoException(
                    "Tipo de movimentação inválido."
            );
        }

        // 3 - verificar saldo se for retirada
        if (request.getTipo().equals("RETIRADA")) {

            BigDecimal saldo = calcularSaldo(request.getCriancaId());

            if (request.getValor().compareTo(saldo) > 0) {
                throw new SaldoInsuficienteException(
                        "Saldo insuficiente."
                );
            }
        }

        // 4 - depois de todas as validações, criação do objeto Extrato
        Extrato extrato = new Extrato();

        extrato.setCriancaId(request.getCriancaId());
        extrato.setTipo(request.getTipo());
        extrato.setValor(request.getValor());
        extrato.setDescricao(request.getDescricao());
        extrato.setData(LocalDate.now());

        // O Repository executa o INSERT no MySQL
        extratoRepository.salvar(extrato);
    }

    // Consulta o extrato de uma criança
    public List<Extrato> consultar(Long criancaId) {
        return extratoRepository.buscarPorCrianca(criancaId);
    }


    public BigDecimal calcularSaldo(Long criancaId) {

        List<Extrato> extratos =
                extratoRepository.buscarPorCrianca(criancaId);

        BigDecimal saldo = BigDecimal.ZERO;

        for (Extrato extrato : extratos) {

            if (extrato.getTipo().equals("ENTRADA")) {

                saldo = saldo.add(extrato.getValor());

            } else if (extrato.getTipo().equals("RETIRADA")) {

                saldo = saldo.subtract(extrato.getValor());
            }
        }

        return saldo;
    }
}