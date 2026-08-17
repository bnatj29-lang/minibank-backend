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

## Programas necessários

### Obrigatório

| Programa | Para que serve |
| --- | --- |
| [JDK 17](https://adoptium.net/) ou superior | Compilar e executar a aplicação Java |
| [Maven](https://maven.apache.org/download.cgi) | Baixar as dependências e iniciar o Spring Boot |
| [Docker Desktop](https://www.docker.com/products/docker-desktop/) | Executar o MySQL pelo Docker Compose |
| [Git](https://git-scm.com/downloads) | Clonar e versionar o projeto |

> Este repositório ainda não possui Maven Wrapper (`mvnw`), portanto o Maven precisa estar instalado no computador e disponível no `PATH`.

### Recomendado, mas opcional

| Programa | Para que serve |
| --- | --- |
| [IntelliJ IDEA](https://www.jetbrains.com/idea/download/) | Abrir, editar e executar o projeto Java |
| [Postman](https://www.postman.com/downloads/) | Fazer requisições e testar a API |
| [MySQL Workbench](https://dev.mysql.com/downloads/workbench/) | Visualizar o banco, as tabelas e os registros |

O IntelliJ, o Postman e o Workbench ajudam no desenvolvimento, mas não são necessários para iniciar o projeto pelo terminal.

## Configuração no macOS

### 1. Instale os programas

Instale o JDK 17, Maven, Git e Docker Desktop pelos links da seção anterior. Se você já utiliza o [Homebrew](https://brew.sh/), pode instalar Java, Maven e Git pelo Terminal:

```bash
brew install openjdk@17 maven git
```

O Docker Desktop deve ser instalado separadamente. Depois da instalação, abra o aplicativo e aguarde até o Docker ficar disponível.

### 2. Confirme as instalações

Abra o Terminal e execute:

```bash
java -version
mvn -version
docker --version
docker compose version
git --version
```

O Java deve indicar a versão 17 ou superior. Se algum comando não for encontrado, instale o programa correspondente antes de continuar.

### 3. Clone e acesse o projeto

Se você ainda não clonou o repositório:

```bash
git clone URL_DO_REPOSITORIO
cd minibank-backend
```

Se o projeto já estiver no computador, use `cd` para acessar a pasta onde ele foi salvo.

### 4. Inicie o banco e a API

Com o Docker Desktop aberto, execute dentro da pasta do projeto:

```bash
docker compose up -d
docker compose ps
mvn spring-boot:run
```

Mantenha esse terminal aberto enquanto usar a API. Para encerrar a aplicação, pressione `Control + C`.

## Configuração no Windows

### 1. Instale os programas

Instale pelos links da seção **Programas necessários**:

1. JDK 17 ou superior.
2. Maven.
3. Git.
4. Docker Desktop.

Durante a instalação, permita que Java, Maven e Git sejam adicionados ao `PATH`. O Docker Desktop pode solicitar a instalação ou atualização do WSL 2; nesse caso, siga as instruções exibidas pelo instalador e reinicie o computador quando solicitado.

Depois, abra o Docker Desktop e aguarde até o Docker ficar disponível.

### 2. Confirme as instalações

Abra o **PowerShell** e execute:

```powershell
java -version
mvn -version
docker --version
docker compose version
git --version
```

O Java deve indicar a versão 17 ou superior. Se algum comando não for reconhecido, feche e abra o PowerShell novamente. Se o problema continuar, confira a instalação e o `PATH` do programa.

### 3. Clone e acesse o projeto

```powershell
git clone URL_DO_REPOSITORIO
cd minibank-backend
```

Se o projeto já estiver no computador, use `cd` para acessar a pasta. Exemplo:

```powershell
cd C:\Users\SEU_USUARIO\Downloads\minibank-backend
```

### 4. Inicie o banco e a API

Com o Docker Desktop aberto, execute dentro da pasta do projeto:

```powershell
docker compose up -d
docker compose ps
mvn spring-boot:run
```

Mantenha esse PowerShell aberto enquanto usar a API. Para encerrar a aplicação, pressione `Ctrl + C`.

## O que acontece durante a inicialização

O comando `docker compose up -d` cria e inicia o container do MySQL com estas configurações:

| Configuração | Valor |
| --- | --- |
| Container | `minibank-mysql` |
| Host | `localhost` |
| Porta no computador | `3308` |
| Banco | `minibank` |
| Usuário | `root` |
| Senha | `mini2026` |

O MySQL pode levar alguns segundos para ficar pronto na primeira execução. No macOS ou Windows, acompanhe a inicialização com:

```bash
docker compose logs -f mysql
```

Quando aparecer a mensagem de que o servidor está pronto para conexões, pressione `Ctrl + C` para sair dos logs. Isso não encerra o container.

Quando a inicialização terminar, a API estará disponível em:

```text
http://localhost:8080
```

Na primeira inicialização, o Spring executa automaticamente o arquivo `src/main/resources/schema.sql` e cria as tabelas `usuario` e `crianca`.


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

### Com curl no macOS

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

### Com curl no Windows (PowerShell)

No PowerShell, use `curl.exe` e grave o JSON em uma variável:

```powershell
$body = @'
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
'@

curl.exe -i -X POST http://localhost:8080/contas/cadastro `
  -H "Content-Type: application/json" `
  -d $body
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

### macOS

```bash
cd minibank-backend
docker compose up -d
docker compose ps
mvn spring-boot:run
```

### Windows (PowerShell)

```powershell
cd minibank-backend
docker compose up -d
docker compose ps
mvn spring-boot:run
```

Depois, teste `POST http://localhost:8080/contas/cadastro` no Postman ou com o comando curl correspondente ao seu sistema.
