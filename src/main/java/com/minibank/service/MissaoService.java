package com.minibank.service;
import com.minibank.exception.NotaMissaoInvalidaException;
import com.minibank.repository.MissaoRepository;
import java.math.BigDecimal;
import com.minibank.model.Missao;
import java.util.List;
import java.math.RoundingMode;

public class MissaoService {
    private final MissaoRepository missaoRepository;

    public MissaoService(MissaoRepository missaoRepository) {
        this.missaoRepository = missaoRepository;
    }

    public void criarMissao(Long criancaId, String criterio, BigDecimal nota) {

        if (nota.compareTo(BigDecimal.ZERO) < 0) {
            throw new NotaMissaoInvalidaException("A nota deve estar entre 0 e 10.");
        }

        if (nota.compareTo(BigDecimal.TEN) > 0) {
            throw new NotaMissaoInvalidaException("A nota deve estar entre 0 e 10.");
        }
        missaoRepository.salvar(criancaId, criterio, nota);
    }

    public BigDecimal calcularMedia(Long criancaId){

        List<Missao> missoes = missaoRepository.listarPorCrianca(criancaId);
        BigDecimal soma = BigDecimal.ZERO;

        if (missoes.isEmpty()) {
            return BigDecimal.ZERO;
        }

        for (Missao missao : missoes){
            soma = soma.add(missao.getNota());

        }

        BigDecimal media = soma.divide(
                BigDecimal.valueOf(missoes.size()),
                2,
                RoundingMode.HALF_UP
        );

        return media;

    }
}
