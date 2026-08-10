-- Este script roda automaticamente quando a aplicacao sobe
-- (configurado em application.properties: spring.sql.init.mode=always).
-- E aqui, e nao mais no Hibernate, que a estrutura do banco fica definida.

CREATE TABLE IF NOT EXISTS usuario (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    senha_hash VARCHAR(255) NOT NULL,
    criado_em TIMESTAMP NOT NULL
);
