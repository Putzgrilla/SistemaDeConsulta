# 🏥 Sistema de Consulta

API REST em **Spring Boot** para gerenciamento de consultas médicas, com autenticação via **JWT** e controle de acesso baseado em cargos (paciente, médico, recepcionista e administrador).

---

## 📌 Sumário

- [Tecnologias](#-tecnologias)
- [Arquitetura do projeto](#-arquitetura-do-projeto)
- [Cargos e permissões](#-cargos-e-permissões)
- [Autenticação](#-autenticação)
- [Endpoints da API](#-endpoints-da-api)
- [Status de consulta](#-status-de-consulta)
- [Tratamento de erros](#-tratamento-de-erros)
- [Variáveis de ambiente](#-variáveis-de-ambiente)
- [Como rodar o projeto](#-como-rodar-o-projeto)
- [Documentação interativa (Swagger)](#-documentação-interativa-swagger)
- [Roadmap](#-roadmap)

---

## 🚀 Tecnologias

| Categoria | Tecnologia |
|---|---|
| Linguagem | Java 21 |
| Framework | Spring Boot (spring-boot-starter-parent 4.0.6) |
| Segurança | Spring Security + JWT (`java-jwt`), autenticação stateless |
| Persistência | Spring Data JPA + PostgreSQL |
| Mapeamento | MapStruct |
| Boilerplate | Lombok |
| Validação | Bean Validation (Jakarta Validation) |
| Documentação | springdoc-openapi (Swagger UI) |
| Build | Maven |

---

## 🏗 Arquitetura do projeto

O código segue uma organização em camadas típica de aplicações Spring Boot:

```
src/main/java/com/arthurMariano/SistemaDeConsulta
├── annotations/     # Anotações customizadas de autorização (@AcessoGeral, @Funcionario)
├── config/          # Configurações de segurança, JWT e CORS
├── controller/       # Camada REST (endpoints)
├── dto/              # Objetos de saída (respostas da API)
├── exception/        # Exceções customizadas + tratador global
├── forms/             # Objetos de entrada (requisições/validação)
├── mapper/            # Conversão entre entidades e DTOs/Forms (MapStruct)
├── model/              # Entidades JPA
│   └── enums/          # Enums de domínio (Cargo, Status)
├── repository/         # Interfaces Spring Data JPA
└── service/             # Regras de negócio
```

### Anotações de autorização

O projeto usa duas anotações customizadas (baseadas em `@PreAuthorize`) para simplificar o controle de acesso nos controllers:

| Anotação | Cargos permitidos |
|---|---|
| `@AcessoGeral` | `USUARIO`, `MEDICO`, `RECEPCIONISTA`, `ADMIN` (qualquer usuário autenticado) |
| `@Funcionario` | `MEDICO`, `RECEPCIONISTA`, `ADMIN` |

---

## 👥 Cargos e permissões

- `ADMIN` — administrador do sistema
- `MEDICO` — profissional de saúde
- `RECEPCIONISTA` — atendimento/funcionário
- `USUARIO` — paciente

A autenticação é **stateless** (sem sessão): a cada requisição, o token JWT enviado no header `Authorization: Bearer <token>` é validado por um filtro (`FiltroDeSeguranca`), que extrai os dados do usuário (id, e-mail e cargo) e monta o contexto de segurança do Spring com a role correspondente (`ROLE_<CARGO>`).

---

## 🔐 Autenticação

O fluxo de autenticação funciona da seguinte forma:

1. O usuário se registra (`/auth/register/...`) ou já possui cadastro.
2. Faz login em `/auth/login` enviando e-mail e senha.
3. A API retorna um token JWT (válido por 50 minutos), assinado com o algoritmo **HMAC256** usando um segredo definido em variável de ambiente.
4. O token deve ser enviado no header `Authorization: Bearer <token>` nas requisições subsequentes.

As únicas rotas públicas (não exigem token) são:
- `POST /auth/login`
- `POST /auth/register/paciente`
- Rotas do Swagger (`/v3/api-docs/**`, `/swagger-ui/**`, `/swagger-ui.html`)

Todas as demais rotas exigem autenticação.

---

## 📡 Endpoints da API

### 🔑 Autenticação — `/auth`

| Método | Rota | Descrição | Acesso |
|---|---|---|---|
| POST | `/auth/login` | Login do usuário | Público |
| POST | `/auth/register/paciente` | Cadastro de paciente | Público |
| POST | `/auth/register/medico` | Cadastro de médico | `ADMIN` |
| POST | `/auth/register/recepcionista` | Cadastro de recepcionista | `ADMIN` |

### 📅 Consultas — `/consulta`

| Método | Rota | Descrição | Acesso |
|---|---|---|---|
| GET | `/consulta/minhas` | Lista as consultas do usuário logado | Autenticado |
| POST | `/consulta/marca` | Marca uma nova consulta | Autenticado |
| GET | `/consulta/horarios/{id}` | Lista horários disponíveis de um médico | Autenticado |
| GET | `/consulta/confirma/{id}` | Confirma a presença do paciente | Funcionário |
| GET | `/consulta/cancelar/{id}` | Cancela uma consulta | Funcionário |
| GET | `/consulta/cancelarMinhas/{id}` | Cancela uma consulta do próprio usuário | Autenticado |
| GET | `/consulta/consultas/{id}` | Busca consultas pelo id do usuário | Funcionário |
| GET | `/consulta/concluir/{id}` | Marca uma consulta como concluída | Funcionário |
| GET | `/consulta/painel` | Lista consultas marcadas/confirmadas (uso em painel) | Funcionário |

### 👨‍⚕️ Médicos — `/medico`

| Método | Rota | Descrição | Acesso |
|---|---|---|---|
| GET | `/medico/especialidade/{id}` | Busca médicos por especialidade | Autenticado |
| GET | `/medico/nome/{nome}` | Busca médicos pelo nome | Autenticado |

### 🩺 Especialidades — `/especialidade`

| Método | Rota | Descrição | Acesso |
|---|---|---|---|
| POST | `/especialidade/salva` | Cadastra uma nova especialidade | `ADMIN` |
| GET | `/especialidade/buscarTodos` | Lista todas as especialidades | Autenticado |

### 🕐 Horários — `/horario`

| Método | Rota | Descrição | Acesso |
|---|---|---|---|
| POST | `/horario/salvar` | Cadastra um horário de atendimento | `ADMIN` |
| POST | `/horario/salvarlist` | Cadastra uma lista de horários de atendimento | `ADMIN` |

### 🧍 Paciente — `/paciente`

| Método | Rota | Descrição | Acesso |
|---|---|---|---|
| GET | `/paciente/pesquisaCpf/{cpf}` | Busca um paciente pelo CPF | Funcionário |

### 👤 Usuário — `/usuario`

| Método | Rota | Descrição | Acesso |
|---|---|---|---|
| GET | `/usuario/meusDados` | Retorna os dados do usuário logado | Autenticado |
| GET | `/usuario/{id}` | Retorna os dados de um usuário pelo id | `ADMIN` |

---

## 🔄 Status de consulta

Uma consulta pode assumir os seguintes status (`model/enums/Status`):

`AGENDADA` → `CONFIRMADA` → `CONCLUIDA`
`AGENDADA` → `CANCELADA`
`AGENDADA` → `FALTA`

---

## ⚠️ Tratamento de erros

A API possui um handler global (`TratadorGlobalDeExcecoes`) que padroniza as respostas de erro no formato:

```json
{
  "field": "campo relacionado ao erro",
  "mensagem": "descrição do erro",
  "status": 404,
  "timestamp": "2026-08-17T12:00:00"
}
```

| Situação | Status HTTP |
|---|---|
| Recurso não encontrado | 404 |
| Login inválido / credenciais erradas | 401 |
| Dado já cadastrado (duplicado) | 409 |
| Erro de validação de campos (`@Valid`) | 400 |
| Data inválida | 400 |

---

## ⚙️ Variáveis de ambiente

O projeto usa variáveis de ambiente para todos os dados sensíveis — nenhuma credencial fica exposta no código:

| Variável | Descrição |
|---|---|
| `SEGREDO` | Chave usada para assinar o token JWT |
| `db_url` | URL de conexão com o banco PostgreSQL |
| `db_user` | Usuário do banco |
| `db_senha` | Senha do banco |
| `font.url` *(opcional)* | URL do frontend liberada no CORS (padrão: `*`) |

Outras configurações relevantes (`application.properties`):

- `spring.profiles.active=dev` — perfil ativo por padrão
- `consulta.maximoDeConsultas=60` — limite máximo de consultas
- `consulta.minimoDeConsultas=3` — limite mínimo de consultas
- `spring.jpa.hibernate.ddl-auto=update` — atualização automática do schema

> No perfil `prod`, o Swagger UI e o `api-docs` ficam desabilitados por padrão (`application-prod.properties`).

---

## ▶️ Como rodar o projeto

### Pré-requisitos

- Java 21+
- Maven (ou use o wrapper `./mvnw`)
- PostgreSQL em execução

### Passos

```bash
# 1. Clone o repositório
git clone https://github.com/Putzgrilla/SistemaDeConsulta.git
cd SistemaDeConsulta

# 2. Defina as variáveis de ambiente necessárias
export SEGREDO=sua_chave_secreta
export db_url=jdbc:postgresql://localhost:5432/seu_banco
export db_user=seu_usuario
export db_senha=sua_senha

# 3. Rode a aplicação
./mvnw spring-boot:run
```

A API sobe por padrão em `http://localhost:8080`.

---

## 📖 Documentação interativa (Swagger)

Com o perfil `dev` ativo, a documentação interativa da API fica disponível em:

- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`

---

## 🗺 Roadmap

Projeto em desenvolvimento ativo. Próximos passos planejados:

- [ ] Logs estruturados
- [ ] Testes automatizados mais completos
- [ ] Tratamento de mais casos de erro
