package com.minibank.service;

import com.minibank.exception.EmailRecuperacaoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class EmailRecuperacaoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailRecuperacaoService.class);
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final String apiKey;
    private final String remetente;
    private final String urlFrontend;

    public EmailRecuperacaoService(
            @Value("${minibank.resend.api-key:}") String apiKey,
            @Value("${minibank.resend.from:MiniBank <onboarding@resend.dev>}") String remetente,
            @Value("${minibank.frontend.url:http://localhost:5173}") String urlFrontend) {
        this.apiKey = apiKey;
        this.remetente = remetente;
        this.urlFrontend = urlFrontend;
    }

    public void enviar(String destinatario, String token) {
        if (apiKey == null || apiKey.isBlank()) {
            LOGGER.error("MINIBANK_RESEND_API_KEY não está configurada.");
            throw new EmailRecuperacaoException();
        }

        String link = urlFrontend.replaceAll("/$", "") + "/redefinir-senha?token=" + token;
        String corpo = "{" +
                "\"from\":\"" + escaparJson(remetente) + "\"," +
                "\"to\":[\"" + escaparJson(destinatario) + "\"]," +
                "\"subject\":\"Redefinição de senha | MiniBank\"," +
                "\"html\":\"" + escaparJson(html(link)) + "\"" +
                "}";

        HttpRequest requisicao = HttpRequest.newBuilder()
                .uri(URI.create("https://api.resend.com/emails"))
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(corpo))
                .build();

        try {
            HttpResponse<String> resposta = httpClient.send(requisicao, HttpResponse.BodyHandlers.ofString());
            if (resposta.statusCode() < 200 || resposta.statusCode() >= 300) {
                LOGGER.error("Resend recusou o envio de recuperação. Status HTTP: {}", resposta.statusCode());
                throw new EmailRecuperacaoException();
            }
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            throw new EmailRecuperacaoException();
        } catch (IOException exception) {
            LOGGER.error("Falha de comunicação com o Resend.");
            throw new EmailRecuperacaoException();
        }
    }

    private String html(String link) {
        return "<div style='font-family:Arial,sans-serif;max-width:560px;margin:0 auto;color:#1f2937'>" +
                "<h1 style='color:#059669'>MiniBank</h1>" +
                "<h2>Redefinição de senha</h2>" +
                "<p>Recebemos uma solicitação para redefinir a senha da sua conta MiniBank.</p>" +
                "<p><a href='" + link + "' style='display:inline-block;background:#10b981;color:#fff;padding:12px 20px;border-radius:10px;text-decoration:none;font-weight:bold'>Redefinir minha senha</a></p>" +
                "<p>Este link é válido por 15 minutos e só pode ser utilizado uma vez.</p>" +
                "<p>Se você não solicitou a alteração, ignore este e-mail.</p>" +
                "</div>";
    }

    private String escaparJson(String valor) {
        return valor.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\r", "\\r")
                .replace("\n", "\\n");
    }
}
