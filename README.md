# minibank-backend

Backend do projeto minibank, feito em Java com Spring Boot.
Acesso ao banco de dados feito com **JdbcTemplate e SQL explícito** (sem Hibernate/JPA), para deixar claro exatamente o que está sendo executado no banco.

## Funcionalidade implementada nesta etapa
- Cadastro de usuário (responsável): `POST /usuarios/cadastro`

---

## 1. Verificando se Java e Maven estão instalados

Antes de rodar o projeto, confira no terminal:

```bash
java -version
```
Deve aparecer algo como `openjdk version "17..."` ou superior. Se der erro de "comando não encontrado", é preciso instalar o JDK 17+.

```bash
mvn -version
```
Deve mostrar a versão do Maven e, junto, qual Java ele está usando. Se der erro, é preciso instalar o Maven (ou usar o `./mvnw` que já vem no projeto, que não depende de instalação separada).

Se o projeto já tiver o Maven Wrapper (`mvnw` / `mvnw.cmd`), dá pra rodar os comandos abaixo trocando `mvn` por `./mvnw` (Linux/Mac) ou `mvnw.cmd` (Windows), mesmo sem o Maven instalado globalmente.

---

## 2. Pré-requisitos
- Java 17+
- Maven (ou usar o `./mvnw`)
- MySQL rodando localmente (Workbench, XAMPP, Docker, etc.)

## 3. Como rodar
1. Garanta que o MySQL está rodando localmente na porta padrão (3306)
2. Ajuste usuário/senha em `src/main/resources/application.properties` se necessário (o padrão é `root`/`root`)
3. O banco `minibank` é criado automaticamente na primeira conexão (`createDatabaseIfNotExist=true`), e a tabela `usuario` é criada pelo `schema.sql`
4. Rode:
```bash
mvn spring-boot:run
```
5. A API sobe em `http://localhost:8080`

## 4. Testando o cadastro (exemplo com curl)
```bash
curl -X POST http://localhost:8080/usuarios/cadastro \
  -H "Content-Type: application/json" \
  -d '{"nome":"Bruna Silva","email":"bruna@email.com","senha":"123456"}'
```

## 5. Estrutura de pastas
```
src/main/java/com/minibank/
├── controller/    endpoints da API
├── service/       regras de negócio
├── repository/    acesso ao banco de dados (JdbcTemplate + SQL explícito)
├── model/         classes que representam os dados (POJOs)
├── dto/           formatos de entrada/saída da API
├── exception/     tratamento de erros
└── config/        configurações (segurança, etc.)
```

---

## 6. Comandos Git — subindo este projeto pro GitHub

1. Crie um repositório vazio no GitHub chamado `minibank-backend` (sem README, sem .gitignore, pra não dar conflito)
2. Dentro desta pasta, rode:

```bash
git init
git add .
git commit -m "Cadastro de usuário: banco MySQL, backend e validações"
git branch -M main
git remote add origin https://github.com/SEU_USUARIO/minibank-backend.git
git push -u origin main
```

### Comandos do dia a dia, depois do primeiro push
```bash
git status                        # ver o que mudou
git add .                         # selecionar tudo que mudou
git commit -m "mensagem clara"    # registrar as mudanças
git push                          # enviar pro GitHub
git pull                          # trazer atualizações do GitHub (ex: se outra pessoa da equipe alterou algo)
```

### Trabalhando em equipe com branches (recomendado para o time)
```bash
git checkout -b feature/login     # cria e muda para uma nova branch
git push -u origin feature/login  # sobe essa branch pro GitHub
# depois, abrir um Pull Request no GitHub para revisar antes de juntar na main
```
