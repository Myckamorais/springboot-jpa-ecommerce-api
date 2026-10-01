# 🛒 Ecommerce API

API REST de e-commerce desenvolvida com **Spring Boot**, **JPA/Hibernate** e **PostgreSQL**, com deploy em produção via **Docker** no **Render**.

## Sobre o projeto

Backend de uma aplicação de e-commerce que expõe endpoints REST para gerenciar usuários, pedidos, produtos, categorias, itens de pedido e pagamentos. O projeto segue uma arquitetura em camadas (Resource → Service → Repository → Entities) e trata erros de forma padronizada com respostas JSON.

## Tecnologias

- Java 25
- Spring Boot 4.1.1
- Spring Data JPA / Hibernate
- PostgreSQL (produção) · H2 in-memory (desenvolvimento/testes)
- Maven
- Docker (multi-stage build)
- Deploy: [Render](https://render.com)

## Domínio

```
User ──< Order >── OrderItem ──> Product >──< Category
                     │
                  Payment
```

- **User** — cadastro de usuários
- **Order** — pedidos com status (`WAITING_PAYMENT`, `PAID`, `SHIPPED`, `DELIVERED`, `CANCELED`)
- **Product** / **Category** — catálogo com associação muitos-para-muitos
- **OrderItem** — item de pedido com quantidade, preço e subtotal calculado
- **Payment** — pagamento associado a um pedido (chave compartilhada com `Order`)

## Endpoints disponíveis

| Método | Rota | Descrição |
|--------|------|-----------|
| GET | `/users` | Lista todos os usuários |
| GET | `/users/{id}` | Busca usuário por ID |
| POST | `/users` | Cria um novo usuário |
| PUT | `/users/{id}` | Atualiza dados do usuário |
| DELETE | `/users/{id}` | Remove um usuário |
| GET | `/orders` | Lista todos os pedidos |
| GET | `/orders/{id}` | Busca pedido por ID |
| GET | `/products` | Lista todos os produtos |
| GET | `/products/{id}` | Busca produto por ID |
| GET | `/categories` | Lista todas as categorias |
| GET | `/categories/{id}` | Busca categoria por ID |

## Tratamento de erros

Respostas de erro seguem um formato padronizado:

```json
{
  "timestamp": "2026-09-30T00:00:00Z",
  "status": 404,
  "error": "Resource not found",
  "message": "Resource not found. Id 99",
  "path": "/users/99"
}
```

## Como rodar localmente

**Pré-requisitos:** Java 25, Maven

```bash
# Clone o repositório
git clone https://github.com/Myckamorais/springboot-jpa-ecommerce-api.git
cd springboot-jpa-ecommerce-api

# Rode com o perfil de teste (H2 in-memory — sem precisar de banco externo)
./mvnw spring-boot:run
```

A aplicação sobe em `http://localhost:8080`.  
Console do H2 disponível em `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:testdb`, usuário: `sa`, senha: vazia).

## Deploy com Docker

A aplicação está containerizada com um **multi-stage build**: a primeira etapa compila o projeto com o JDK completo; a segunda roda apenas o `.jar` com o JRE, gerando uma imagem final mais leve.

```dockerfile
# Etapa 1 — build
FROM eclipse-temurin:25-jdk AS build
WORKDIR /app
COPY . .
RUN chmod +x mvnw && ./mvnw clean package -DskipTests

# Etapa 2 — runtime
FROM eclipse-temurin:25-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

As credenciais do banco são lidas de variáveis de ambiente — nenhuma informação sensível está no código.

## API em produção

```
https://springboot-jpa-ecommerce-api.onrender.com
```

> O serviço utiliza o plano gratuito do Render e pode demorar ~1 minuto para responder após um período de inatividade.
