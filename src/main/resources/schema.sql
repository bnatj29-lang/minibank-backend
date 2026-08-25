-- Este script roda automaticamente quando a aplicacao sobe
-- (configurado em application.properties: spring.sql.init.mode=always).
-- E aqui, e nao mais no Hibernate, que a estrutura do banco fica definida.

CREATE TABLE IF NOT EXISTS usuario (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    senha_hash VARCHAR(255) NOT NULL,
    senha_painel_hash VARCHAR(255) NOT NULL,
    criado_em TIMESTAMP NOT NULL
    );

CREATE TABLE IF NOT EXISTS crianca (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    idade INT NOT NULL,
    usuario_id BIGINT NOT NULL,
    criado_em TIMESTAMP NOT NULL,
    FOREIGN KEY (usuario_id) REFERENCES usuario(id)
    );

CREATE TABLE  IF NOT EXISTS configuracao_mesada (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  crianca_id BIGINT NOT NULL,
  valor_base DECIMAL(10,2) NOT NULL,
  nota_minima_intermediaria DECIMAL(4,2) NOT NULL,
  nota_minima_maxima DECIMAL(4,2) NOT NULL,
  valor_faixa_baixa DECIMAL(10,2) NOT NULL,
  valor_faixa_intermediaria DECIMAL(10,2) NOT NULL,
  valor_faixa_maxima DECIMAL(10,2) NOT NULL,

   FOREIGN KEY (crianca_id) REFERENCES crianca(id)
);

 CREATE TABLE IF NOT EXISTS extrato (
     id BIGINT AUTO_INCREMENT PRIMARY KEY,
     crianca_id BIGINT NOT NULL,
     tipo VARCHAR(20) NOT NULL,
     valor DECIMAL(10,2) NOT NULL,
     descricao VARCHAR(255) NOT NULL,
     data DATE NOT NULL,
     FOREIGN KEY (crianca_id) REFERENCES crianca(id)
 );