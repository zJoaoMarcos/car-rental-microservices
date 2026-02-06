# 🚗 Car Rental System – MVP
Backend para aluguel de carros, desenvolvido em Java + Spring Boot, com foco em POO, regras de negócio e arquitetura limpa.

### 🎯 O que o sistema faz
- Cadastro de carros e clientes
- Reserva de veículos
- Início e finalização de aluguel
- Cálculo de valor e multa por atraso
- Pagamento simulado (Pix / Cartão)
- Notificações via log

### 🧠 Conceitos aplicados
- Encapsulamento, Herança, Polimorfismo e Abstração
- SOLID
- Clean Architecture (DDD light)
- Factory, Strategy e State

### 🏗️ Estrutura
domain | application | infrastructure | api

### 🧪 Testes
- Regras de negócio no domínio
- Cálculo de preço e multa
- Pagamento aprovado vs recusado

### 🛠️ Stack
- Java 17+
- Spring Boot
- JPA / Hibernate
- H2
- JUnit 5

### ▶️ Executar
./mvnw spring-boot:run

### 🚧 Fora do MVP
- Autenticação
- UI
- Relatórios
- Descontos