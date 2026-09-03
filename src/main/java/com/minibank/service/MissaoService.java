package com.minibank.service;
import com.minibank.exception.NotaMissaoInvalidaException;
import com.minibank.model.Missao;
import com.minibank.repository.ConfiguracaoMesadaRepository;
import com.minibank.repository.MissaoRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import com.minibank.model.ConfiguracaoMesada;
import java.util.Optional;
import org.springframework.stereotype.Service;


@Service
public class MissaoService {

    private final MissaoRepository missaoRepository;
    private final ConfiguracaoMesadaRepository configuracaoMesadaRepository;

    public MissaoService(
            MissaoRepository missaoRepository,
            ConfiguracaoMesadaRepository configuracaoMesadaRepository) {

        this.missaoRepository = missaoRepository;
        this.configuracaoMesadaRepository = configuracaoMesadaRepository;
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

    public BigDecimal calcularMedia(Long criancaId) {

        List<Missao> missoes = missaoRepository.listarPorCrianca(criancaId);
        BigDecimal soma = BigDecimal.ZERO;

        if (missoes.isEmpty()) {
            return BigDecimal.ZERO;
        }

        for (Missao missao : missoes) {
            soma = soma.add(missao.getNota());
        }

        BigDecimal media = soma.divide(
                BigDecimal.valueOf(missoes.size()),
                2,
                RoundingMode.HALF_UP
        );

        return media;
    }

    public BigDecimal calcularMesada(Long criancaId) {

        BigDecimal media = calcularMedia(criancaId);

        Optional<ConfiguracaoMesada> resultado =
                configuracaoMesadaRepository.buscarCrianca(criancaId);

        if (resultado.isEmpty()) {
            throw new RuntimeException("Configuração de mesada não encontrada para a criança.");
        }

        ConfiguracaoMesada configuracao = resultado.get();

        if (media.compareTo(configuracao.getNotaMinimaMaxima()) >= 0) {
            return configuracao.getValorFaixaMaxima();
        }

        if (media.compareTo(configuracao.getNotaMinimaIntermediaria()) >= 0) {
            return configuracao.getValorFaixaIntermediaria();
        }

        return configuracao.getValorFaixaBaixa();
    }

    public List<Missao> listarMissoes(Long criancaId) {
        return missaoRepository.listarPorCrianca(criancaId);
    }




    public Optional<Missao> buscarMissaoPorId(Long id) {
        return missaoRepository.buscarPorId(id);
    }

    public void atualizarMissao(Long id, String criterio, BigDecimal nota) {

        if (nota.compareTo(BigDecimal.ZERO) < 0 ||
                nota.compareTo(BigDecimal.TEN) > 0) {

            throw new NotaMissaoInvalidaException(
                    "A nota deve estar entre 0 e 10."
            );
        }

        missaoRepository.atualizar(id, criterio, nota);
    }

    public void excluirMissao(Long id) {
        missaoRepository.excluir(id);
    }
}