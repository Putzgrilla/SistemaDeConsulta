# Sistema de Consulta

API REST desenvolvida em Spring Boot para gerenciamento de consultas médicas, com autenticação e controle de acesso baseado em cargos (paciente, médico, recepcionista e admin).

## Tecnologias

- Java 21
- Spring Boot
- Spring Security + JWT (autenticação stateless)
- Spring Data JPA
- PostgreSQL
- MapStruct (mapeamento entre entidades e DTOs)
- Lombok
- Bean Validation

## Funcionalidades

### Autenticação (`/auth`)
- Login
- Registro de paciente
- Registro de médico
- Registro de recepcionista

### Consultas (`/consulta`)
- Listar minhas consultas (paciente ou médico)
- Marcar consulta
- Consultar horários disponíveis
- Confirmar consulta
- Cancelar consulta
- Buscar consulta por id

### Médicos (`/medico`)
- Buscar médicos por especialidade
- Buscar médicos por nome

### Especialidades (`/especialidade`)
- Cadastrar especialidade
- Listar especialidades

### Horários (`/horario`)
- Cadastrar horário
- Cadastrar lista de horários

## Cargos de usuário

- `ADMIN`
- `MEDICO`
- `RECEPCIONISTA`
- `USUARIO` (paciente)

## Status de consulta

`AGENDADA`, `CONFIRMADA`, `CANCELADA`, `CONCLUIDA`, `FALTA`

## Configuração

O projeto usa variáveis de ambiente para dados sensíveis, nenhuma credencial fica exposta no código:

| Variável | Descrição |
|---|---|
| `SEGREDO` | Chave usada para assinar o token JWT |
| `db_url` | URL de conexão com o banco PostgreSQL |
| `db_user` | Usuário do banco |
| `db_senha` | Senha do banco |

## Como rodar

```bash
# defina as variáveis de ambiente necessárias antes de iniciar
mvn spring-boot:run
```

## Em andamento

Este projeto está em desenvolvimento ativo. Próximos passos planejados:

- [ ] Logs estruturados
- [ ] Testes automatizados mais completos
- [ ] Documentação da API (Swagger/OpenAPI)
- [ ] Tratamento de mais casos de erro
