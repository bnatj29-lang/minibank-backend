package com.minibank.service;

import com.minibank.exception.CriancaNaoPertenceException;
import com.minibank.repository.CriancaRepository;
import com.minibank.repository.ResponsavelRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AutorizacaoService {

    private final CriancaRepository criancaRepository;
    private final ResponsavelRepository responsavelRepository;

    public AutorizacaoService(
            CriancaRepository criancaRepository,
            ResponsavelRepository responsavelRepository) {
        this.criancaRepository = criancaRepository;
        this.responsavelRepository = responsavelRepository;
    }

    public Long responsavelAutenticadoId() {
        Authentication autenticacao = SecurityContextHolder.getContext().getAuthentication();
        if (autenticacao == null || !autenticacao.isAuthenticated()) {
            throw new AccessDeniedException("Acesso negado.");
        }
        try {
            return Long.valueOf(autenticacao.getName());
        } catch (NumberFormatException exception) {
            throw new AccessDeniedException("Acesso negado.");
        }
    }

    public void validarResponsavel(Long responsavelId) {
        if (responsavelId == null || !responsavelAutenticadoId().equals(responsavelId)) {
            throw new AccessDeniedException("Acesso negado.");
        }
    }

    public void validarEmailResponsavel(String email) {
        boolean pertence = email != null && responsavelRepository.buscarPorEmail(email)
                .map(responsavel -> responsavelAutenticadoId().equals(responsavel.getId()))
                .orElse(false);
        if (!pertence) {
            throw new AccessDeniedException("Acesso negado.");
        }
    }

    public void validarCrianca(Long criancaId) {
        if (criancaId == null || !criancaRepository.pertenceAoResponsavel(
                criancaId,
                responsavelAutenticadoId())) {
            throw new CriancaNaoPertenceException("Acesso negado.");
        }
    }
}
