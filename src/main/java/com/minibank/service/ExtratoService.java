package com.minibank.service;

import com.minibank.dto.RegistrarExtratoRequestDTO;
import com.minibank.exception.SaldoInsuficienteException;
import com.minibank.exception.TipoMovimentacaoInvalidoException;
import com.minibank.exception.ValorMovimentacaoInvalidoException;
import com.minibank.model.Extrato;
import com.minibank.model.Meta;
import com.minibank.model.StatusMeta;
import com.minibank.repository.ExtratoRepository;
import com.minibank.repository.MetaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class ExtratoService {

    private final ExtratoRepository extratoRepository;
    private final MetaRepository metaRepository;

    public ExtratoService(
            ExtratoRepository extratoRepository,
            MetaRepository metaRepository
    ) {
        this.extratoRepository = extratoRepository;
        this.metaRepository = metaRepository;
    }

    public void registrar(RegistrarExtratoRequestDTO request) {

        // 1 - O valor precisa ser maior que zero
        if (request.getValor().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValorMovimentacaoInvalidoException(
                    "O valor deve ser maior que zero."
            );
        }

        // 2 - O tipo precisa ser ENTRADA ou RETIRADA
        if (!request.getTipo().equals("ENTRADA") &&
                !request.getTipo().equals("RETIRADA")) {

            throw new TipoMovimentacaoInvalidoException(
                    "Tipo de movimentação inválido."
            );
        }

        // 3 - Em uma retirada comum, só pode usar o saldo livre
        if (request.getTipo().equals("RETIRADA")) {

            BigDecimal saldoLivre =
                    calcularSaldoLivre(request.getCriancaId());

            if (request.getValor().compareTo(saldoLivre) > 0) {
                throw new SaldoInsuficienteException(
                        "Saldo insuficiente."
                );
            }
        }

        // 4 - Depois das validações, cria a movimentação
        Extrato extrato = new Extrato();

        extrato.setCriancaId(request.getCriancaId());
        extrato.setTipo(request.getTipo());
        extrato.setValor(request.getValor());
        extrato.setDescricao(request.getDescricao());
        extrato.setData(LocalDate.now());

        extratoRepository.salvar(extrato);
    }

    public List<Extrato> consultar(Long criancaId) {
        return extratoRepository.buscarPorCrianca(criancaId);
    }

    // Calcula todo o dinheiro da criança:
    // ENTRADAS - RETIRADAS
    public BigDecimal calcularSaldoTotal(Long criancaId) {

        List<Extrato> extratos =
                extratoRepository.buscarPorCrianca(criancaId);

        BigDecimal saldoTotal = BigDecimal.ZERO;

        for (int i = 0; i < extratos.size(); i++) {

            Extrato extrato = extratos.get(i);

            if (extrato.getTipo().equals("ENTRADA")) {

                saldoTotal = saldoTotal.add(extrato.getValor());

            } else if (extrato.getTipo().equals("RETIRADA")) {

                saldoTotal = saldoTotal.subtract(extrato.getValor());
            }
        }

        return saldoTotal;
    }

    // Calcula quanto do dinheiro está reservado
    // em metas ATIVAS ou ALCANÇADAS
    public BigDecimal calcularValorEmMetas(Long criancaId) {

        List<Meta> metas =
                metaRepository.buscarMetas(criancaId);

        BigDecimal valorEmMetas = BigDecimal.ZERO;

        for (int i = 0; i < metas.size(); i++) {

            Meta meta = metas.get(i);

            if (meta.getStatus() == StatusMeta.ATIVA ||
                    meta.getStatus() == StatusMeta.ALCANÇADA) {

                valorEmMetas =
                        valorEmMetas.add(meta.getValorGuardado());
            }
        }

        return valorEmMetas;
    }

    // Saldo livre = saldo total - dinheiro reservado nas metas
    public BigDecimal calcularSaldoLivre(Long criancaId) {

        BigDecimal saldoTotal =
                calcularSaldoTotal(criancaId);

        BigDecimal valorEmMetas =
                calcularValorEmMetas(criancaId);

        return saldoTotal.subtract(valorEmMetas);
    }

    // Usado quando uma meta é conquistada.
    // Nesse caso o dinheiro reservado vira uma retirada real.
    public void registrarRetiradaMeta(
            Long criancaId,
            BigDecimal valor,
            String descricao
    ) {

        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValorMovimentacaoInvalidoException(
                    "O valor deve ser maior que zero."
            );
        }

        BigDecimal saldoTotal =
                calcularSaldoTotal(criancaId);

        if (valor.compareTo(saldoTotal) > 0) {
            throw new SaldoInsuficienteException(
                    "Saldo insuficiente."
            );
        }

        Extrato extrato = new Extrato();

        extrato.setCriancaId(criancaId);
        extrato.setTipo("RETIRADA");
        extrato.setValor(valor);
        extrato.setDescricao(descricao);
        extrato.setData(LocalDate.now());

        extratoRepository.salvar(extrato);
    }
}