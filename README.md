****# Car Rental System

Backend para aluguel de carros construído com arquitetura de microsserviços, Java 21, Spring Boot e comunicação assíncrona via RabbitMQ.

---

## Arquitetura

```mermaid
graph TD
    Client([Cliente]) -->|REST| GW[API Gateway :8080]

    GW -->|/v1/auth, /v1/customers, /v1/employees| ID[Identity Service :8082]
    GW -->|/v1/vehicles| INV[Inventory Service :8083]
    GW -->|/v1/bookings| BK[Booking Service :8081]

    BK -->|HTTP - busca veículo| INV

    BK -->|booking.created / booking.cancelled| MQ[(RabbitMQ\nbooking-exchange)]

    MQ -->|booking.created| INV
    MQ -.->|booking.created| PAY[Payment Service]
    MQ -.->|payment.confirmed| NT[Notification Service]
    PAY -.->|payment.confirmed| CT[Contract Service]

    ID --- DB_ID[(identity_db)]
    INV --- DB_INV[(inventory_db)]
    BK --- DB_BK[(booking_db)]

    style GW fill:#4a90d9,stroke:#2c5f8a,color:#fff
    style ID fill:#50b86c,stroke:#2d8a4a,color:#fff
    style INV fill:#50b86c,stroke:#2d8a4a,color:#fff
    style BK fill:#50b86c,stroke:#2d8a4a,color:#fff
    style MQ fill:#ff9f43,stroke:#cc7a2e,color:#fff
    style PAY fill:#a0a0a0,stroke:#707070,color:#fff
    style NT fill:#a0a0a0,stroke:#707070,color:#fff
    style CT fill:#a0a0a0,stroke:#707070,color:#fff
    style DB_ID fill:#336791,stroke:#1e3d56,color:#fff
    style DB_INV fill:#336791,stroke:#1e3d56,color:#fff
    style DB_BK fill:#336791,stroke:#1e3d56,color:#fff
```

> Linhas tracejadas representam serviços **planejados**.

### Fluxo de uma reserva

```mermaid
sequenceDiagram
    actor C as Cliente
    participant GW as API Gateway
    participant BK as Booking Service
    participant INV as Inventory Service
    participant MQ as RabbitMQ
    participant PAY as Payment Service
    participant CT as Contract Service
    participant NT as Notification Service

    C->>GW: POST /v1/bookings
    GW->>GW: Valida JWT
    GW->>BK: Encaminha request
    BK->>INV: GET /v1/vehicles/details/{id}
    INV-->>BK: Dados do veículo
    BK->>BK: Verifica conflitos e calcula valor
    BK-->>GW: 201 Created
    GW-->>C: Reserva criada

    BK--)MQ: booking.created
    MQ--)INV: booking.created
    INV->>INV: Marca veículo como RENTED

    Note over PAY,NT: Serviços planejados

    MQ---)PAY: booking.created
    PAY->>PAY: Cria cobrança PENDING
    PAY--)MQ: payment.confirmed
    MQ---)CT: payment.confirmed
    CT->>CT: Gera PDF do contrato
    MQ---)NT: payment.confirmed
    NT->>NT: Envia e-mail ao cliente
```

---

## Microsserviços

| Serviço      | Porta | Status        | Responsabilidade                          |
|--------------|-------|---------------|-------------------------------------------|
| gateway      | 8080  | ✅ Implementado | Roteamento, autenticação JWT              |
| inventory    | 8083  | ✅ Implementado | Cadastro e gestão de veículos             |
| booking      | 8081  | ✅ Implementado | Reservas, cancelamentos, conflitos        |
| identity     | 8082  | ✅ Implementado | Clientes, funcionários, autenticação      |
| notification | —     | 🔲 Planejado   | Envio de e-mails e notificações           |
| payment      | —     | 🔲 Planejado   | Processamento de pagamentos               |
| contract     | —     | 🔲 Planejado   | Geração de contratos em PDF               |

---

## Stack

- **Java 21** + **Spring Boot 4**
- **PostgreSQL** — banco por serviço (isolamento de dados)
- **RabbitMQ** — comunicação assíncrona entre serviços
- **Spring Data JPA** + Hibernate
- **Spring AMQP** — produtores e consumidores de eventos
- **Lombok** — redução de boilerplate
- **Spring Cloud Gateway (MVC)** — API Gateway com roteamento e filtros
- **JWT (java-jwt)** — autenticação via token no gateway
- **Maven** — build e gerenciamento de dependências

---

## Padrões aplicados

- **Clean Architecture** — domain / application / infra
- **DDD** — entidades, value objects, eventos de domínio
- **Event-Driven Architecture** — TopicExchange com routing keys
- **Factory Pattern** — criação de tipos de veículo (EconomicCar, SuvCar)
- **Specification Pattern** — filtros dinâmicos com JPA Specifications
- **Repository Pattern** — Spring Data JPA
- **SOLID**

---

## Como executar

> Pré-requisitos: Docker, Java 21, Maven

```bash
# 1. Subir infraestrutura (PostgreSQL + RabbitMQ)
docker-compose up -d

# 2. Identity Service
cd identity && ./mvnw spring-boot:run

# 3. Inventory Service (em outro terminal)
cd inventory && ./mvnw spring-boot:run

# 4. Booking Service (em outro terminal)
cd booking && ./mvnw spring-boot:run

# 5. Gateway (em outro terminal)
cd gateway && ./mvnw spring-boot:run
```

---

## Endpoints disponíveis

> Todas as requisições passam pelo **Gateway** (`localhost:8080`).
> Rotas `/v1/auth/**` são públicas. As demais exigem header `Authorization` com JWT válido.

### Identity Service — `/v1/auth`, `/v1/customers`, `/v1/employees`

```
POST   /v1/auth                Autenticação (login)
GET    /v1/customers           Listar clientes
GET    /v1/employees           Listar funcionários
```

### Inventory Service — `/v1/vehicles`

```
POST   /v1/vehicles            Cadastrar veículo
GET    /v1/vehicles            Listar veículos
GET    /v1/vehicles/details/{id}  Buscar veículo por ID
```

### Booking Service — `/v1/bookings`

```
POST   /v1/bookings            Criar reserva
GET    /v1/bookings            Listar reservas (filtros: customerId, vehicleId)
DELETE /v1/bookings/{id}       Cancelar reserva
```

---

## Estrutura do projeto

```
car-rental/
├── gateway/          ✅ Roteamento e autenticação JWT
├── identity/         ✅ Clientes, funcionários e auth
├── inventory/        ✅ Veículos e listeners
├── booking/          ✅ Reservas e eventos
├── notification/     🔲 A implementar
├── payment/          🔲 A implementar
├── contract/         🔲 A implementar
├── docker-compose.yml
└── roadmap.sh
```
