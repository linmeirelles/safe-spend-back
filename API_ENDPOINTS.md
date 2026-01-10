# API Endpoints - Sistema de Controle de Gastos

## Base URL
```
http://localhost:8080
```

## Documentação Swagger
```
http://localhost:8080/swagger-ui.html
```

---

## 📝 Autenticação

### 1. Registrar novo usuário
```http
POST /api/auth/register
Content-Type: application/json

{
  "name": "João Silva",
  "email": "joao@email.com",
  "password": "senha123"
}
```

**Resposta (201 Created):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "type": "Bearer",
  "userId": "uuid-do-usuario",
  "name": "João Silva",
  "email": "joao@email.com"
}
```

### 2. Login
```http
POST /api/auth/login
Content-Type: application/json

{
  "email": "joao@email.com",
  "password": "senha123"
}
```

**Resposta (200 OK):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "type": "Bearer",
  "userId": "uuid-do-usuario",
  "name": "João Silva",
  "email": "joao@email.com"
}
```

---

## 💰 Contas (Accounts)

**Nota:** Todos os endpoints abaixo requerem autenticação. Adicione o header:
```
Authorization: Bearer {seu-token-jwt}
```

### 1. Criar conta
```http
POST /api/accounts
Content-Type: application/json
Authorization: Bearer {token}

{
  "name": "Conta Corrente Nubank",
  "initialBalance": 5000.00,
  "type": "CHECKING"
}
```

**Tipos disponíveis:** `CHECKING`, `SAVINGS`, `CASH`, `INVESTMENT`

**Resposta (201 Created):**
```json
{
  "id": "uuid-da-conta",
  "name": "Conta Corrente Nubank",
  "initialBalance": 5000.00,
  "currentBalance": 5000.00,
  "type": "CHECKING"
}
```

### 2. Listar todas as contas
```http
GET /api/accounts
Authorization: Bearer {token}
```

### 3. Buscar conta por ID
```http
GET /api/accounts/{id}
Authorization: Bearer {token}
```

### 4. Atualizar conta
```http
PUT /api/accounts/{id}
Content-Type: application/json
Authorization: Bearer {token}

{
  "name": "Conta Corrente Nubank - Principal",
  "initialBalance": 5000.00,
  "type": "CHECKING"
}
```

### 5. Deletar conta
```http
DELETE /api/accounts/{id}
Authorization: Bearer {token}
```

---

## 🏷️ Categorias (Categories)

### 1. Criar categoria
```http
POST /api/categories
Content-Type: application/json
Authorization: Bearer {token}

{
  "name": "Alimentação",
  "icon": "🍔",
  "type": "EXPENSE"
}
```

**Tipos disponíveis:** `INCOME`, `EXPENSE`

**Resposta (201 Created):**
```json
{
  "id": "uuid-da-categoria",
  "name": "Alimentação",
  "icon": "🍔",
  "type": "EXPENSE"
}
```

### 2. Listar todas as categorias
```http
GET /api/categories
Authorization: Bearer {token}
```

### 3. Buscar categoria por ID
```http
GET /api/categories/{id}
Authorization: Bearer {token}
```

### 4. Atualizar categoria
```http
PUT /api/categories/{id}
Content-Type: application/json
Authorization: Bearer {token}

{
  "name": "Alimentação e Restaurantes",
  "icon": "🍽️",
  "type": "EXPENSE"
}
```

### 5. Deletar categoria
```http
DELETE /api/categories/{id}
Authorization: Bearer {token}
```

---

## 💳 Cartões de Crédito (Credit Cards)

### 1. Criar cartão de crédito
```http
POST /api/credit-cards
Content-Type: application/json
Authorization: Bearer {token}

{
  "name": "Nubank Platinum",
  "closingDay": 10,
  "dueDay": 17,
  "limitValue": 10000.00
}
```

**Resposta (201 Created):**
```json
{
  "id": "uuid-do-cartao",
  "name": "Nubank Platinum",
  "closingDay": 10,
  "dueDay": 17,
  "limitValue": 10000.00,
  "usedLimit": 0.00,
  "availableLimit": 10000.00
}
```

### 2. Listar todos os cartões
```http
GET /api/credit-cards
Authorization: Bearer {token}
```

### 3. Buscar cartão por ID
```http
GET /api/credit-cards/{id}
Authorization: Bearer {token}
```

### 4. Atualizar cartão
```http
PUT /api/credit-cards/{id}
Content-Type: application/json
Authorization: Bearer {token}

{
  "name": "Nubank Platinum - Principal",
  "closingDay": 10,
  "dueDay": 17,
  "limitValue": 15000.00
}
```

### 5. Deletar cartão
```http
DELETE /api/credit-cards/{id}
Authorization: Bearer {token}
```

---

## 💸 Transações (Transactions)

### 1. Criar transação (Despesa em conta)
```http
POST /api/transactions
Content-Type: application/json
Authorization: Bearer {token}

{
  "description": "Compra supermercado",
  "amount": 350.50,
  "date": "2026-01-10",
  "paid": true,
  "type": "EXPENSE",
  "categoryId": "uuid-da-categoria",
  "accountId": "uuid-da-conta"
}
```

### 2. Criar transação (Despesa no cartão parcelada)
```http
POST /api/transactions
Content-Type: application/json
Authorization: Bearer {token}

{
  "description": "Notebook Dell",
  "amount": 500.00,
  "date": "2026-01-10",
  "paid": false,
  "type": "EXPENSE",
  "categoryId": "uuid-da-categoria",
  "creditCardId": "uuid-do-cartao",
  "installmentCurrent": 1,
  "installmentTotal": 10
}
```

### 3. Criar transação (Receita)
```http
POST /api/transactions
Content-Type: application/json
Authorization: Bearer {token}

{
  "description": "Salário Janeiro",
  "amount": 5000.00,
  "date": "2026-01-05",
  "paid": true,
  "type": "INCOME",
  "categoryId": "uuid-da-categoria",
  "accountId": "uuid-da-conta"
}
```

**Tipos disponíveis:** `INCOME`, `EXPENSE`, `TRANSFER`

**Resposta (201 Created):**
```json
{
  "id": "uuid-da-transacao",
  "description": "Compra supermercado",
  "amount": 350.50,
  "date": "2026-01-10",
  "paid": true,
  "type": "EXPENSE",
  "categoryId": "uuid-da-categoria",
  "categoryName": "Alimentação",
  "accountId": "uuid-da-conta",
  "accountName": "Conta Corrente Nubank",
  "creditCardId": null,
  "creditCardName": null,
  "installmentCurrent": null,
  "installmentTotal": null
}
```

### 4. Listar todas as transações
```http
GET /api/transactions
Authorization: Bearer {token}
```

### 5. Buscar transação por ID
```http
GET /api/transactions/{id}
Authorization: Bearer {token}
```

### 6. Buscar transações por período
```http
GET /api/transactions/period?startDate=2026-01-01&endDate=2026-01-31
Authorization: Bearer {token}
```

### 7. Listar transações pendentes (não pagas)
```http
GET /api/transactions/pending
Authorization: Bearer {token}
```

### 8. Marcar transação como paga
```http
PATCH /api/transactions/{id}/mark-as-paid
Authorization: Bearer {token}
```

### 9. Atualizar transação
```http
PUT /api/transactions/{id}
Content-Type: application/json
Authorization: Bearer {token}

{
  "description": "Compra supermercado - Atualizada",
  "amount": 380.00,
  "date": "2026-01-10",
  "paid": true,
  "type": "EXPENSE",
  "categoryId": "uuid-da-categoria",
  "accountId": "uuid-da-conta"
}
```

### 10. Deletar transação
```http
DELETE /api/transactions/{id}
Authorization: Bearer {token}
```

---

## 🔒 Segurança

- Todos os endpoints (exceto `/api/auth/**`) requerem autenticação JWT
- O token deve ser enviado no header: `Authorization: Bearer {token}`
- Cada usuário só tem acesso aos seus próprios dados
- Tokens expiram em 24 horas

---

## 📊 Validações

### Contas
- `name`: 3-255 caracteres, obrigatório
- `initialBalance`: obrigatório
- `type`: CHECKING, SAVINGS, CASH, INVESTMENT

### Categorias
- `name`: 2-255 caracteres, obrigatório
- `icon`: opcional
- `type`: INCOME, EXPENSE

### Cartões de Crédito
- `name`: 3-255 caracteres, obrigatório
- `closingDay`: 1-31, obrigatório
- `dueDay`: 1-31, obrigatório
- `limitValue`: obrigatório

### Transações
- `description`: 3-255 caracteres, obrigatório
- `amount`: valor positivo, obrigatório
- `date`: obrigatório
- `paid`: true/false, obrigatório
- `type`: INCOME, EXPENSE, TRANSFER
- `categoryId`: UUID válido, obrigatório
- Despesas devem ter `accountId` OU `creditCardId`

---

## ❌ Tratamento de Erros (RFC 7807)

Todos os erros seguem o padrão Problem Details:

```json
{
  "type": "https://api.sistema-gastos.com/errors/not-found",
  "title": "Recurso não encontrado",
  "status": 404,
  "detail": "Conta não encontrada",
  "timestamp": "2026-01-10T18:30:00Z"
}
```

### Códigos de Status HTTP
- `200 OK` - Sucesso
- `201 Created` - Recurso criado
- `204 No Content` - Deleção bem-sucedida
- `400 Bad Request` - Erro de validação
- `401 Unauthorized` - Não autenticado
- `404 Not Found` - Recurso não encontrado
- `500 Internal Server Error` - Erro no servidor
