# 🏦 API de Contas Bancárias

Base URL: `http://localhost:8080`

> ⚠️ **Aviso:** Esta API foi desenvolvida com **Java 21** e **Spring Boot**. Os exemplos de requisição foram testados utilizando o **Postman**.


---

## Endpoints

### Criar Conta
**POST** `/contas`

Cria uma nova conta bancária.

**Request Body:**
```json
{
  "titular": "João Silva",
  "numeroConta": "12345",
  "saldo": 1000.0
}
```

---

### Buscar Conta por Número
**POST** `/contas/buscar`

Busca uma conta pelo número da conta.

**Request Body:**
```json
{
  "numeroConta": "12345"
}
```

---

### Buscar Conta por ID
**POST** `/contas/buscar-por-id`

Busca uma conta pelo ID interno.

**Request Body:**
```json
{
  "id": 1
}
```

---

### Transferir Dinheiro
**POST** `/contas/transferir`

Realiza uma transferência entre duas contas pelo número.

**Request Body:**
```json
{
  "origem": "12345",
  "destino": "67890",
  "valor": 250.0
}
```

---

### Sacar Dinheiro
**POST** `/contas/sacar`

Realiza um saque em uma conta pelo número.

**Request Body:**
```json
{
  "numero": "12345",
  "valor": 50.0
}
```

---

### Depositar Dinheiro
**POST** `/contas/depositar`

Realiza um depósito em uma conta pelo número.

**Request Body:**
```json
{
  "numero": "12345",
  "valor": 100.0
}
```