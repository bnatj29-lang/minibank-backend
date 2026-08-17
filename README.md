# Minibank Backend

API REST do projeto **Minibank**, desenvolvida em Java 17 com Spring Boot.

O acesso ao MySQL é feito com `JdbcTemplate` e SQL explícito, sem Hibernate/JPA. O Docker é usado para executar o banco de dados; a aplicação Spring Boot é executada localmente com Maven.

## Funcionalidade disponível

- Cadastro de responsável e criança(s): `POST /contas/cadastro`

## Tecnologias

- Java 17
- Spring Boot 3.3
- Maven
- Spring JDBC (`JdbcTemplate`)
- Spring Security e BCrypt
- MySQL 8
- Docker Compose

## O que instalar

### Obrigatório

| Programa | Para que serve |
| --- | --- |
| [JDK 17](https://adoptium.net/) ou superior | Compilar e executar a aplicação Java |
| [Maven](https://maven.apache.org/download.cgi) | Baixar as dependências e iniciar o Spring Boot |
| [Docker Desktop](https://www.docker.com/products/docker-desktop/) | Executar o MySQL pelo Docker Compose |
| [Git](https://git-scm.com/downloads) | Clonar e versionar o projeto |

> Este repositório ainda não possui Maven Wrapper (`mvnw`), portanto o Maven precisa estar instalado no computador.

### Recomendado, mas opcional

| Programa | Para que serve |
| --- | --- |
| [IntelliJ IDEA](https://www.jetbrains.com/idea/download/) | Abrir, editar e executar o projeto Java |
| [Postman](https://www.postman.com/downloads/) | Fazer requisições e testar a API |
| [MySQL Workbench](https://dev.mysql.com/downloads/workbench/) | Visualizar o banco, as tabelas e os registros |

O IntelliJ, o Postman e o Workbench ajudam no desenvolvimento, mas não são necessários para iniciar o projeto pelo terminal.

## Como rodar o projeto

### 1. Confirme as instalações

Abra um terminal e execute, nesta ordem:

```bash
java -version
mvn -version
docker --version
docker compose version
git --version
```

O Java deve indicar a versão 17 ou superior. Se algum comando não for encontrado, instale o programa correspondente antes de continuar.

### 2. Obtenha o projeto

Se você ainda não clonou o repositório:

```bash
git clone URL_DO_REPOSITORIO
cd minibank-backend
```

Se o projeto já estiver no computador, apenas abra o terminal dentro da pasta `minibank-backend`.

### 3. Inicie o Docker Desktop

Abra o Docker Desktop e aguarde até ele indicar que o Docker está em execução.

### 4. Suba o banco MySQL

Dentro da pasta do projeto, execute:

```bash
docker compose up -d
docker compose ps
```

O container criado terá estas configurações:

| Configuração | Valor |
| --- | --- |
| Container | `minibank-mysql` |
| Host | `localhost` |
| Porta no computador | `3308` |
| Banco | `minibank` |
| Usuário | `root` |
| Senha | `mini2026` |

O MySQL pode levar alguns segundos para ficar pronto na primeira execução. Para acompanhar a inicialização:

```bash
docker compose logs -f mysql
```

Quando aparecer a mensagem de que o servidor está pronto para conexões, pressione `Ctrl + C` para sair dos logs. Isso não encerra o container.

### 5. Inicie a API

Com o MySQL em execução, rode:

```bash
mvn spring-boot:run
```

Quando a inicialização terminar, a API estará disponível em:

```text
http://localhost:8080
```

Na primeira inicialização, o Spring executa automaticamente o arquivo `src/main/resources/schema.sql` e cria as tabelas `usuario` e `crianca`.

Mantenha esse terminal aberto enquanto estiver usando a API. Para encerrá-la, pressione `Ctrl + C`.

## Testando o cadastro

### Com Postman

1. Crie uma requisição do tipo `POST`.
2. Use a URL `http://localhost:8080/contas/cadastro`.
3. Na aba **Body**, escolha **raw** e selecione **JSON**.
4. Envie o conteúdo abaixo:

```json
{
  "responsavel": {
    "nome": "Bruna Silva",
    "email": "bruna@email.com",
    "senha": "123456",
    "senhaPainel": "1234"
  },
  "crianca": [
    {
      "nome": "Ana",
      "idade": 10
    }
  ]
}
```

Um cadastro realizado com sucesso retorna o status HTTP `201 Created`.

### Com curl

```bash
curl -i -X POST http://localhost:8080/contas/cadastro \
  -H "Content-Type: application/json" \
  -d '{
    "responsavel": {
      "nome": "Bruna Silva",
      "email": "bruna@email.com",
      "senha": "123456",
      "senhaPainel": "1234"
    },
    "crianca": [
      {
        "nome": "Ana",
        "idade": 10
      }
    ]
  }'
```

## Acessando o banco pelo MySQL Workbench

Crie uma nova conexão usando:

```text
Connection Name: Minibank Docker
Hostname: localhost
Port: 3308
Username: root
Password: mini2026
Default Schema: minibank
```

Depois de conectar, você pode conferir os dados cadastrados:

```sql
USE minibank;
SELECT * FROM usuario;
SELECT * FROM crianca;
```

Não é necessário criar o banco ou as tabelas manualmente.

## Encerrando o projeto

Primeiro encerre a API com `Ctrl + C`. Depois, para parar o MySQL sem apagar os dados:

```bash
docker compose down
```

Os dados permanecem no volume Docker `minibank-mysql-data` e estarão disponíveis na próxima execução.

Para iniciar novamente em outro momento:

```bash
docker compose up -d
mvn spring-boot:run
```

## Comandos úteis do Docker

```bash
docker compose ps
docker compose logs -f mysql
docker compose stop
docker compose start
docker compose down
```

> Atenção: `docker compose down -v` também remove o volume e apaga os dados do banco. Use esse comando somente quando quiser recriar o banco do zero.

## Solução de problemas

### A API não consegue conectar ao MySQL

Confirme que o container está ativo:

```bash
docker compose ps
```

Se ele não estiver em execução:

```bash
docker compose up -d
docker compose logs mysql
```

### A porta 3308 já está sendo usada

Verifique se já existe outro container do projeto:

```bash
docker ps
```

Se necessário, altere a porta à esquerda em `docker-compose.yml` e use a mesma porta na URL de conexão em `src/main/resources/application.properties`.

### A porta 8080 já está sendo usada

Encerre a aplicação que está ocupando a porta ou altere `server.port` em `src/main/resources/application.properties`.

## Estrutura principal

```text
src/main/java/com/minibank/
├── config/       configurações da aplicação e segurança
├── controller/   endpoints da API
├── dto/          formatos de entrada e saída da API
├── exception/    tratamento de erros
├── model/        classes que representam os dados
├── repository/   acesso ao MySQL com JdbcTemplate e SQL
└── service/      regras de negócio

src/main/resources/
├── application.properties  porta e conexão com o banco
└── schema.sql               criação das tabelas
```

## Resumo rápido dos comandos

Para quem já instalou os pré-requisitos e clonou o projeto:

```bash
cd minibank-backend
docker compose up -d
docker compose ps
mvn spring-boot:run
```

Depois, teste `POST http://localhost:8080/contas/cadastro` no Postman ou com curl.
