# Design Document: DDD with Spring Boot

## 1. Overview

This document outlines the design and architecture of the "DDD-with-Spring-boot" project. The application serves as a practical example of applying Domain-Driven Design (DDD) principles to a modern Spring Boot application. It manages a simple `Order` domain, exposing its functionality through a RESTful API.

## 2. Architectural Style

The project follows a classic layered architecture, heavily influenced by DDD tactical patterns. This separates concerns and creates a more maintainable and scalable system.

The layers are organized as follows:

- **Interfaces:** Exposes the application's capabilities to the outside world (e.g., via a REST controller). It is responsible for request handling, validation, and data transfer object (DTO) mapping.
- **Application:** Orchestrates the business logic. It does not contain business rules itself but directs the domain objects to perform tasks.
- **Domain:** The core of the application. It contains the business logic, entities, and value objects that represent the problem domain. This layer is completely independent of any framework.
- **Infrastructure:** Implements technical concerns like data persistence, messaging, and integration with external systems. It provides concrete implementations for interfaces defined in the domain or application layers.

## 3. Core Domain: The `Order` Aggregate

The central component of the domain model is the `Order` aggregate.

- **Aggregate Root:** `Order` is the aggregate root, ensuring the consistency of all objects within its boundary. All interactions with the aggregate must go through the root.
- **Entity:** `Order` is an entity with a unique identifier (`id`).
- **Properties:**
    - `id`: A unique identifier for the order.
    - `product`: The name of the product being ordered.
    - `quantity`: The number of items for the product.

## 4. Layer Breakdown

### 4.1. Interfaces Layer

- **`OrderController.java`**: A standard Spring `@RestController` that handles all incoming HTTP requests for the `/orders` resource.
    - It depends on the `OrderService` from the Application layer.
    - It uses `CreateOrderRequest` as a Data Transfer Object (DTO) to decouple the API contract from the internal domain model.
    - It handles request validation (`@Validated`) and maps HTTP methods to application service calls.

### 4.2. Application Layer

- **`OrderService.java`**: This service acts as a facade over the domain layer.
    - It orchestrates the creation, retrieval, and deletion of `Order` aggregates.
    - It contains the primary application logic but delegates all business rule enforcement to the `Order` domain object itself.
    - It depends on an `OrderRepository` interface (to be implemented by the Infrastructure layer) for persistence.

### 4.3. Infrastructure Layer

- **Persistence:** The project uses Spring Data JPA for data persistence.
    - An `OrderRepository` interface extends `JpaRepository`, allowing the infrastructure layer to provide a concrete implementation at runtime.
    - For simplicity and demonstration, the application is configured to use an in-memory **H2 database**.

## 5. API Endpoints

The following RESTful endpoints are exposed by the `OrderController`:

| Method   | Path          | Description        | Request Body             | Response Status |
|----------|---------------|--------------------|--------------------------|-----------------|
| `POST`   | `/orders`     | Creates a new order. | `CreateOrderRequest`     | `201 CREATED`   |
| `GET`    | `/orders/{id}`| Retrieves a single order by its ID. | -                        | `200 OK`        |
| `GET`    | `/orders`     | Retrieves a list of all orders. | -                        | `200 OK`        |
| `DELETE` | `/orders/{id}`| Deletes an order by its ID. | -                        | `204 NO CONTENT`|

## 6. Data Flow: Creating an Order

1.  A `POST` request with a JSON body hits the `OrderController` at `/orders`.
2.  The controller validates the incoming `CreateOrderRequest` DTO.
3.  The controller invokes the `create()` method on the `OrderService`, passing the product and quantity.
4.  The `OrderService` creates a new `Order` domain object.
5.  The service uses the `OrderRepository` to save the new `Order` instance.
6.  The repository (implemented by Spring Data JPA) persists the entity to the H2 database.
7.  The saved `Order` is returned up the call stack and sent back as the HTTP response body.

## 7. Tooling and Quality Assurance

- **Build Tool:** Maven is used for dependency management and building the project.
- **CI/CD:** A GitHub Actions workflow (`.github/workflows/ci.yml`) is configured to run on every push and pull request.
- **Code Style:** The **Spotless** Maven plugin automatically checks and enforces a consistent code style.
- **Test Coverage:** The **JaCoCo** Maven plugin is configured to enforce **100% code coverage**, ensuring all logic is tested. The build will fail if coverage drops below this threshold.
