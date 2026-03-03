# Car Rental System

Backend para aluguel de carros construído com arquitetura de microsserviços, Java 21, Spring Boot e comunicação assíncrona via RabbitMQ.

---

## Arquitetura

```
                        ┌─────────────────┐
                        │   API Gateway   │
                        │  (planejado)    │
                        └────────┬────────┘
                                 │ REST
        ┌────────────────────────┼────────────────────────┐
        │                        │                        │
┌───────▼───────┐   ┌────────────▼────────┐   ┌──────────▼──────┐
│   Inventory   │   │      Booking        │   │    Customer     │
│   Service     │◄──┤      Service        ├──►│    Service      │
│  :8080        │   │      :8081          │   │   (planejado)   │
└───────────────┘   └──────────┬──────────┘   └─────────────────┘
                               │
                    ┌──────────▼──────────┐
                    │      RabbitMQ       │
                    │  booking-exchange   │
                    └──┬──────────────┬───┘
                       │              │
             ┌─────────▼───┐   ┌──────▼──────────┐
             │   Payment   │   │  Notification   │
             │   Service   │   │    Service      │
             │ (planejado) │   │  (planejado)    │
             └─────────────┘   └─────────────────┘
                    │
             ┌──────▼──────────┐
             │    Contract     │
             │    Service      │
             │  (planejado)    │
             └─────────────────┘
```

### Fluxo de uma reserva

```
1. POST /v1/bookings          → Booking Service cria a reserva
2. booking.created (event)    → Inventory marca veículo como RENTED
3. booking.created (event)    → Payment cria cobrança PENDING
4. payment.confirmed (event)  → Contract gera PDF do contrato
5. payment.confirmed (event)  → Notification envia e-mail ao cliente
```

---

## Microsserviços

| Serviço      | Porta | Status        | Responsabilidade                     |
|--------------|-------|---------------|--------------------------------------|
| inventory    | 8080  | ✅ Implementado | Cadastro e gestão de veículos        |
| booking      | 8081  | ✅ Implementado | Reservas, cancelamentos, conflitos   |
| customer     | —     | 🔲 Planejado   | Cadastro e gestão de clientes        |
| payment      | —     | 🔲 Planejado   | Processamento de pagamentos          |
| notification | —     | 🔲 Planejado   | Envio de e-mails e notificações      |
| contract     | —     | 🔲 Planejado   | Geração de contratos em PDF          |

---

## Stack

- **Java 21** + **Spring Boot 4**
- **PostgreSQL** — banco por serviço (isolamento de dados)
- **RabbitMQ** — comunicação assíncrona entre serviços
- **Spring Data JPA** + Hibernate
- **Spring AMQP** — produtores e consumidores de eventos
- **Lombok** — redução de boilerplate
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

# 2. Inventory Service
cd inventory && ./mvnw spring-boot:run

# 3. Booking Service (em outro terminal)
cd booking && ./mvnw spring-boot:run
```

---

## Endpoints disponíveis

### Inventory Service — `localhost:8080`

```
POST   /v1/vehicles            Cadastrar veículo
GET    /v1/vehicles            Listar veículos
GET    /v1/vehicles/details/{id}  Buscar veículo por ID
```

### Booking Service — `localhost:8081`

```
POST   /v1/bookings            Criar reserva
GET    /v1/bookings            Listar reservas (filtros: customerId, vehicleId)
DELETE /v1/bookings/{id}       Cancelar reserva
```

---

## Roadmap

Veja o progresso completo do projeto:

```bash
./roadmap.sh
```

---

## Estrutura do projeto

```
car-rental/
├── booking/          ✅ Reservas e eventos
├── inventory/        ✅ Veículos e listeners
├── customer/         🔲 A implementar
├── payment/          🔲 A implementar
├── notification/     🔲 A implementar
├── contract/         🔲 A implementar
├── docker-compose.yml
└── roadmap.sh
```
