# Sistema de Controle de Gastos

Sistema backend para controle de gastos pessoais desenvolvido com Kotlin e Spring Boot.

## Tecnologias

- **Kotlin** 1.9.20
- **Spring Boot** 3.2.0
- **Spring Security** com JWT
- **Spring Data JPA**
- **PostgreSQL**
- **Flyway** (Migrations)
- **SpringDoc OpenAPI** (Swagger)

## Estrutura do Projeto

### Entidades

1. **User** - Usuários do sistema
2. **Account** - Contas/Carteiras (CHECKING, SAVINGS, CASH, INVESTMENT)
3. **Category** - Categorias de transações (INCOME, EXPENSE)
4. **CreditCard** - Cartões de crédito
5. **Transaction** - Transações financeiras (INCOME, EXPENSE, TRANSFER)

### Segurança

- Autenticação via JWT
- Endpoints protegidos por autenticação
- Validação de propriedade de dados (usuário só acessa seus próprios dados)

## Configuração

### Pré-requisitos

- Java 17+
- PostgreSQL
- Gradle

### Configuração do Banco de Dados

1. Crie o banco de dados PostgreSQL:
```sql
CREATE DATABASE sistema_gastos;
```

2. Configure as credenciais no `application.yml`:
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/sistema_gastos
    username: postgres
    password: postgres
```

### Variáveis de Ambiente

Configure a variável `JWT_SECRET` com uma chave secreta de pelo menos 32 caracteres:

```bash
export JWT_SECRET="sua-chave-secreta-aqui-minimo-32-caracteres"
```

Ou configure diretamente no `application.yml`.

## Executando o Projeto

```bash
./gradlew bootRun
```

O servidor estará disponível em `http://localhost:8080`.

## Documentação da API

A documentação Swagger estará disponível em:
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`

## Estrutura de Pastas

```
src/main/kotlin/com/sistema/gastos/
├── config/              # Configurações (Security, JWT)
├── domain/
│   ├── entity/         # Entidades JPA
│   ├── enum/           # Enums
│   └── repository/     # Repositories JPA
├── security/           # Classes de segurança
└── SistemaGastosApplication.kt
```

## Regras de Negócio

- Um usuário **nunca** pode acessar dados de outro usuário
- Todas as consultas nos repositories incluem validação por `userId`
- Transações podem estar vinculadas a uma conta ou cartão de crédito (opcional)
- Transações podem ser parceladas (installment_current, installment_total)
