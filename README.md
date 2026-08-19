# Minibank Backend

API REST do projeto **Minibank**, desenvolvida em Java 17 com Spring Boot.

O acesso ao MySQL é feito com `JdbcTemplate` e SQL explícito, sem Hibernate/JPA. A aplicação Spring Boot e o MySQL são executados localmente.

## Funcionalidade disponível

- Cadastro de responsável e criança(s): `POST /contas/cadastro`

## Tecnologias

- Java 17
- Spring Boot 3.3
- Maven
- Spring JDBC (`JdbcTemplate`)
- Spring Security e BCrypt
- MySQL 8

## Programas necessários

### Obrigatório

| Programa | Para que serve |
| --- | --- |
| [JDK 17](https://adoptium.net/) ou superior | Compilar e executar a aplicação Java |
| [Maven](https://maven.apache.org/download.cgi) | Baixar as dependências e iniciar o Spring Boot |
| [MySQL Community Server](https://dev.mysql.com/downloads/mysql/) | Executar e armazenar os dados do sistema |
| [Git](https://git-scm.com/downloads) | Clonar e versionar o projeto |

> Este repositório ainda não possui Maven Wrapper (`mvnw`), portanto o Maven precisa estar instalado no computador e disponível no `PATH`.

### Recomendado, mas opcional

| Programa | Para que serve |
| --- | --- |
| [IntelliJ IDEA](https://www.jetbrains.com/idea/download/) | Abrir, editar e executar o projeto Java |
| [Postman](https://www.postman.com/downloads/) | Fazer requisições e testar a API |
| [MySQL Workbench](https://dev.mysql.com/downloads/workbench/) | Administrar o MySQL e visualizar bancos, tabelas e registros |

O IntelliJ, o Postman e o Workbench ajudam no desenvolvimento, mas não são necessários para iniciar o projeto pelo terminal.

## Configuração no macOS

### 1. Instale os programas

Instale o JDK 17, Maven, Git e MySQL pelos links da seção anterior. Se você já utiliza o [Homebrew](https://brew.sh/), pode instalá-los pelo Terminal:

```bash
brew install openjdk@17 maven git mysql
```

Depois, inicie o MySQL:

```bash
brew services start mysql
```

Se o MySQL foi instalado por outro método, inicie-o pelas Preferências do Sistema ou pela ferramenta fornecida pelo instalador.

### 2. Confirme as instalações

Abra o Terminal e execute:

```bash
java -version
mvn -version
mysql --version
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

### 4. Configure a senha e inicie a API

Por padrão, a aplicação tenta acessar o MySQL com usuário `root`, senha `root` e porta `3306`. Se a senha do seu MySQL for diferente, informe-a antes de iniciar a aplicação:

```bash
export DB_PASSWORD=SUA_SENHA_DO_MYSQL
mvn spring-boot:run
```

Se sua senha já for `root`, execute apenas `mvn spring-boot:run`.

Mantenha esse terminal aberto enquanto usar a API. Para encerrar a aplicação, pressione `Control + C`.

## Configuração no Windows

### 1. Instale os programas

Instale pelos links da seção **Programas necessários**:

1. JDK 17 ou superior.
2. Maven.
3. Git.
4. MySQL Community Server.

Durante a instalação, permita que Java, Maven, Git e MySQL sejam adicionados ao `PATH`. No instalador do MySQL, defina uma senha para o usuário `root` e mantenha a porta padrão `3306`.

Depois, abra o aplicativo **Services** do Windows e confirme que o serviço `MySQL80` está em execução. O nome pode variar conforme a versão instalada.

### 2. Confirme as instalações

Abra o **PowerShell** e execute:

```powershell
java -version
mvn -version
mysql --version
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

### 4. Configure a senha e inicie a API

Por padrão, a aplicação tenta acessar o MySQL com usuário `root`, senha `root` e porta `3306`. Se a senha definida na instalação for diferente, execute dentro da pasta do projeto:

```powershell
$env:DB_PASSWORD="SUA_SENHA_DO_MYSQL"
mvn spring-boot:run
```

Se sua senha já for `root`, execute apenas `mvn spring-boot:run`.

Mantenha esse PowerShell aberto enquanto usar a API. Para encerrar a aplicação, pressione `Ctrl + C`.

## Configuração do banco de dados

A aplicação utiliza estas configurações por padrão:

| Configuração | Valor |
| --- | --- |
| Host | `localhost` |
| Porta | `3306` |
| Banco | `minibank` |
| Usuário | `root` |
| Senha | `root` |

É possível substituir os valores sem editar o código usando as variáveis:

```text
DB_URL
DB_USERNAME
DB_PASSWORD
```

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
Connection Name: Minibank Local
Hostname: localhost
Port: 3306
Username: root
Password: a senha definida na instalação do MySQL
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

Encerre a API pressionando `Ctrl + C` no terminal em que o Maven está rodando.

O MySQL pode permanecer ativo como um serviço do computador. Caso queira encerrá-lo no macOS e tenha feito a instalação pelo Homebrew:

```bash
brew services stop mysql
```

Para iniciá-lo novamente:

```bash
brew services start mysql
```

No Windows, o serviço do MySQL pode ser iniciado ou interrompido pelo aplicativo **Services**. Parar o serviço não apaga os dados.

## Solução de problemas

### A API não consegue conectar ao MySQL

Confira se o MySQL está em execução e se a porta, o usuário e a senha estão corretos.

No macOS com Homebrew:

```bash
brew services list
```

No Windows com PowerShell:

```powershell
Get-Service *mysql*
```

Se a senha do usuário `root` não for `root`, defina `DB_PASSWORD` antes de executar o Maven. Se também precisar alterar o usuário ou a URL, utilize `DB_USERNAME` e `DB_URL`.

### A porta 3306 está diferente

Se o MySQL estiver configurado em outra porta, informe a URL completa pela variável `DB_URL`. Exemplo: `jdbc:mysql://localhost:3307/minibank?useSSL=false&serverTimezone=UTC&createDatabaseIfNotExist=true&allowPublicKeyRetrieval=true`.

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
export DB_PASSWORD=SUA_SENHA_DO_MYSQL
mvn spring-boot:run
```

### Windows (PowerShell)

```powershell
cd minibank-backend
$env:DB_PASSWORD="SUA_SENHA_DO_MYSQL"
mvn spring-boot:run
```

Depois, teste `POST http://localhost:8080/contas/cadastro` no Postman ou com o comando curl correspondente ao seu sistema.

## Exemplo: o que é necessário para criar uma API

Uma API normalmente é separada em camadas. Cada camada tem uma responsabilidade específica, o que deixa o código mais organizado e fácil de manter.

Neste exemplo, criaremos uma API simplificada para cadastrar uma conta de usuário:

> Este é um exemplo didático e resumido. O endpoint `/usuarios` mostrado abaixo não faz parte da implementação atual do Minibank; o cadastro real do projeto utiliza `POST /contas/cadastro`.

```text
POST /usuarios
        │
        ▼
Controller → DTO → Service → Model → Repository → Banco de dados
```

O fluxo funciona assim:

1. O cliente, como Postman ou frontend, envia uma requisição HTTP.
2. O **Controller** recebe a requisição.
3. O **DTO** representa e valida os dados recebidos.
4. O **Service** executa as regras de negócio.
5. O **Model** representa o objeto utilizado pela aplicação.
6. O **Repository** executa o SQL.
7. O **banco de dados** armazena as informações.

### 1. Banco de dados

O banco é responsável por armazenar os dados permanentemente. Primeiro, precisamos de uma tabela:

```sql
CREATE TABLE IF NOT EXISTS usuario (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    senha_hash VARCHAR(255) NOT NULL,
    criado_em TIMESTAMP NOT NULL
);
```

Neste projeto, as tabelas ficam no arquivo `src/main/resources/schema.sql`. O Spring executa esse arquivo automaticamente ao iniciar a aplicação.

### 2. DTO

DTO significa **Data Transfer Object**. Ele define o formato dos dados que entram ou saem da API.

Exemplo de DTO de entrada:

```java
public class UsuarioRequestDTO {

    @NotBlank
    private String nome;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 6)
    private String senha;

    // getters e setters
}
```

Esse DTO informa que a requisição deve possuir `nome`, `email` e `senha`. As anotações também validam os dados antes de executar o cadastro.

Também podemos criar um DTO de resposta para impedir que informações sensíveis sejam devolvidas:

```java
public class UsuarioResponseDTO {

    private String nome;
    private String email;

    // construtor, getters e setters
}
```

A senha não aparece no DTO de resposta porque nunca deve ser enviada de volta ao cliente.

### 3. Model

O Model representa uma informação do sistema dentro da aplicação:

```java
public class Usuario {

    private Long id;
    private String nome;
    private String email;
    private String senhaHash;
    private LocalDateTime criadoEm;

    // construtor, getters e setters
}
```

Enquanto o DTO representa os dados da requisição ou resposta, o Model representa o usuário completo utilizado internamente pelo sistema.

### 4. Repository

O Repository é responsável pela comunicação com o banco de dados. Nele ficam os comandos SQL:

```java
@Repository
public class UsuarioRepository {

    private final JdbcTemplate jdbcTemplate;

    public UsuarioRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void salvar(Usuario usuario) {
        String sql = """
            INSERT INTO usuario (nome, email, senha_hash, criado_em)
            VALUES (?, ?, ?, ?)
            """;

        jdbcTemplate.update(
            sql,
            usuario.getNome(),
            usuario.getEmail(),
            usuario.getSenhaHash(),
            usuario.getCriadoEm()
        );
    }

    public boolean emailJaExiste(String email) {
        String sql = "SELECT COUNT(*) FROM usuario WHERE email = ?";
        Integer quantidade = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return quantidade != null && quantidade > 0;
    }
}
```

O Repository não deve decidir se um cadastro pode ou não ser realizado. Ele apenas consulta, salva, altera ou remove dados.

### 5. Service

O Service contém as regras de negócio. Nesse exemplo, ele verifica se o e-mail já existe e criptografa a senha:

```java
@Service
public class UsuarioService {

    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(
        UsuarioRepository repository,
        PasswordEncoder passwordEncoder
    ) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public UsuarioResponseDTO cadastrar(UsuarioRequestDTO dto) {
        if (repository.emailJaExiste(dto.getEmail())) {
            throw new IllegalArgumentException("E-mail já cadastrado");
        }

        Usuario usuario = new Usuario(
            null,
            dto.getNome(),
            dto.getEmail(),
            passwordEncoder.encode(dto.getSenha()),
            LocalDateTime.now()
        );

        repository.salvar(usuario);

        return new UsuarioResponseDTO(
            usuario.getNome(),
            usuario.getEmail()
        );
    }
}
```

Exemplos de regras que normalmente ficam no Service:

- Verificar se um e-mail já foi cadastrado.
- Criptografar uma senha.
- Calcular um valor.
- Conferir saldo antes de uma transferência.
- Chamar mais de um Repository na mesma operação.

### 6. Controller

O Controller disponibiliza o endpoint HTTP e encaminha os dados para o Service:

```java
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> cadastrar(
        @Valid @RequestBody UsuarioRequestDTO dto
    ) {
        UsuarioResponseDTO resposta = service.cadastrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
    }
}
```

As principais anotações são:

- `@RestController`: informa que a classe possui endpoints REST.
- `@RequestMapping("/usuarios")`: define o início da URL.
- `@PostMapping`: define que o endpoint recebe requisições `POST`.
- `@RequestBody`: transforma o JSON recebido em um objeto Java.
- `@Valid`: executa as validações definidas no DTO.

### 7. Exemplo de requisição

Depois de iniciar o banco e a aplicação, o cliente enviaria:

```http
POST http://localhost:8080/usuarios
Content-Type: application/json
```

```json
{
  "nome": "Maria Silva",
  "email": "maria@email.com",
  "senha": "123456"
}
```

Uma resposta possível seria:

```http
HTTP/1.1 201 Created
Content-Type: application/json
```

```json
{
  "nome": "Maria Silva",
  "email": "maria@email.com"
}
```

### Resumo da responsabilidade de cada camada

| Camada | Responsabilidade | Não deve fazer |
| --- | --- | --- |
| Controller | Receber requisições e devolver respostas HTTP | Escrever SQL ou concentrar regras de negócio |
| DTO | Definir e validar dados de entrada e saída | Acessar o banco de dados |
| Service | Executar regras de negócio e organizar a operação | Tratar detalhes de HTTP |
| Model | Representar os dados utilizados pelo sistema | Receber diretamente todas as responsabilidades |
| Repository | Consultar e modificar dados no banco | Decidir regras de negócio |
| Banco de dados | Armazenar os dados permanentemente | Tratar requisições HTTP |

Essa separação permite alterar uma camada com menos impacto nas demais. Por exemplo, é possível mudar a forma de salvar os dados no Repository sem alterar o endpoint definido no Controller.
