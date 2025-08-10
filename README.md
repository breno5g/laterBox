# laterBox

A simple, educational "read it later" backend inspired by Pocket. This repository exists solely for study and experimentation purposes — it is not intended for production use.

## Why this project?
The goal is to explore how to build a modern Spring Boot REST API that supports:
- User registration and authentication
- JWT-based stateless sessions
- PostgreSQL persistence with migrations
- A clean modular structure ready to evolve into a bookmark/link-saving service

The API currently exposes authentication, tag creation, and link creation endpoints, serving as a foundation for the rest of the features.

## Key Features (Current)
- Authentication API:
  - POST /auth/register — create a user account
  - POST /auth/login — obtain a JWT token
- Tag API (JWT required):
  - POST /tags — create a tag for the authenticated user
- Link API (JWT required):
  - POST /links — create a link with optional tags; missing tags are auto-created for the user
- OpenAPI/Swagger UI available at /swagger-ui/index.html
- Stateless security with JWT (HMAC256)
- PostgreSQL with Flyway database migrations
- Docker Compose for local database provisioning
- Validation and exception handling

## Tech Stack
- Language: Java 24 (as configured in this project)
- Framework: Spring Boot 3 (Web, Security, Data JPA, Validation)
- Security: Spring Security + JWT (auth0/java-jwt)
- Database: PostgreSQL
- Migrations: Flyway
- Build: Maven
- DevOps: Docker Compose
- Testing: JUnit 5, Testcontainers (PostgreSQL)

## Getting Started
### Prerequisites
- Java 24 (matching the project configuration)
- Maven 3.9+
- Docker and Docker Compose

### Environment Setup
Create a .env file in the project root to configure the database and JWT secret (values are examples):

```
POSTGRES_DB=laterbox
POSTGRES_USER=postgres
POSTGRES_PASSWORD=postgres
POSTGRES_PORT=5432
JWT_SECRET=replace-with-a-strong-secret
```

Ensure the same variables are available to the Spring application (application.yaml reads from POSTGRES_* and JWT_SECRET).

### Start the Database
Run PostgreSQL using Docker Compose:

```
docker compose up -d
```

This starts a local Postgres container and a named volume for data persistence.

### Run the Application
Use Maven to start the Spring Boot application:

```
mvn spring-boot:run
```

The app will connect to the database using the variables configured above and apply Flyway migrations automatically.

## API Overview
Base path: /

- POST /auth/register
  - Body: { "username": "string", "password": "string" }
  - Response: 200 OK, "User created successfully" (or error if user exists)

- POST /auth/login
  - Body: { "username": "string", "password": "string" }
  - Response: 200 OK, { "token": "<JWT>" }

- POST /tags (JWT required)
  - Body: { "name": "string", "color": "#RRGGBB" }
  - Response: 200 OK, Tag JSON

- POST /links (JWT required)
  - Body: {
      "url": "https://example.com",
      "title": "string",
      "description": "string",
      "tags": [ { "name": "reading", "color": "#RRGGBB" } ]
    }
  - Behavior: any provided tag not found for the user will be automatically created
  - Response: 200 OK, Link JSON with metadata and tags

Use the returned JWT in the Authorization header for protected endpoints:

```
Authorization: Bearer <JWT>
```

OpenAPI/Swagger UI: /swagger-ui/index.html

## Project Structure (Highlights)
- src/main/java/dev/breno5g/laterbox
  - config/ — Security, JWT token service, filters, configuration
  - user/ — User domain, service, controller for auth
  - link/ — Link domain, service, controller (create)
  - tag/ — Tag domain, service, controller (create)
- src/main/resources
  - application.yaml — application configuration
  - db/migration — Flyway SQL migrations
- compose.yaml — local PostgreSQL service

## Development Notes
- Passwords are stored hashed using BCrypt.
- JWT includes userId and username claims and a default expiration of 24 hours.
- Flyway baseline and migrations run automatically on startup.

## Project Status
- Current: MVP with authentication (register/login) and initial content features: create tags and create links (with auto-creation of missing user tags). All protected with JWT and backed by PostgreSQL.
- Final (envisioned): A personal, private "read it later" API where authenticated users can:
  - Save links with optional title/description metadata
  - Organize links with tags (user-scoped)
  - Mark links as read/unread and favorite/unfavorite
  - Filter/search links by tag, read/favorite, and text
  - Paginate and sort link listings
  - Optionally fetch basic preview metadata (title/OG tags) server-side
  - Manage their own tags
  - Have complete data isolation per user

This project remains educational and non-production, but the final vision guides the next development steps.

## Domain Model Overview
- User (table: users)
  - id: UUID (PK)
  - username: String (unique)
  - password: String (BCrypt hash)
  - createdAt: LocalDateTime (auto)

- Tag (table: tags)
  - id: UUID (PK)
  - name: String
  - color: String
  - user: ManyToOne User (each tag owned by a user)

- Link (table: links)
  - id: UUID (PK)
  - url: String
  - title: String
  - description: String
  - isRead: Boolean
  - isFavorite: Boolean
  - createdAt: LocalDateTime (auto)
  - readAt: LocalDateTime (nullable)
  - userId: UUID (owner reference)
  - tags: ManyToMany Tag via link_tag (join table)

## Acknowledgments
- Inspired by Pocket’s read-it-later concept.
- Spring ecosystem and community for libraries and documentation.