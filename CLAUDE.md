# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Commands

```bash
# Run the application (local profile, default)
./mvnw spring-boot:run

# Run with a specific profile
./mvnw spring-boot:run -Dspring-boot.run.profiles=prod

# Build (skip tests)
./mvnw clean package -DskipTests

# Run all tests
./mvnw test

# Run a single test class
./mvnw test -Dtest=TaskManagementSystemApplicationTests
```

## Environment Variables
Requires a `.env` file at the project root:

**Local profile** (`application-local.properties`):
- `DB_URL` — full JDBC URL (e.g. `jdbc:mysql://localhost:3306/taskdb`)
- `DB_USERNAME`, `DB_PASSWORD`
- `CORS_ALLOWED_ORIGINS` — comma-separated origins (e.g. `http://localhost:5173`)
- `PORT` (optional, defaults to `8080`)

**Prod profile** (`application-prod.properties`):
- `DB_HOST`, `DB_PORT`, `DB_NAME`, `DB_USER`, `DB_PASS`
- `CORS_ALLOWED_ORIGINS`, `PORT`

Active profile is set in `application.properties` via `spring.profiles.active`.

## Architecture

Spring Boot 3.5.11 / Java 21 REST API with MySQL via JPA. All endpoints are currently open (no auth enforced).

### Package Structure (`com.TaskManagementSystem`)
```
Config/       — SecurityConfig (stateless, all-permit), CORS (reads CORS_ALLOWED_ORIGINS env var)
Controller/   — UserController, TaskController
DTO/
  Request/    — UserRequest, LoginRequest
  Response/   — LoginResponse { message, UserResponse }, UserResponse { name, email }
Model/        — User (OneToMany → Task), Task (ManyToOne → User), Project (empty placeholder)
ModelMapper/  — UserMapper (MapStruct interface, componentModel="spring")
Repository/   — UserRepo, TaskRepo
Service/      — UserService, TaskService
```

### Data Model
- **User**: `id`, `name`, `email` (unique), `password` (BCrypt), `tasks` (`@OneToMany`, `@JsonManagedReference`)
- **Task**: `id`, `title`, `description`, `status`, `priority`, `user` (`@ManyToOne`)
- `ddl-auto=update` — schema is auto-managed by Hibernate

### API Endpoints
| Method | Path | Description |
|--------|------|-------------|
| POST | `/api/users/SignUp` | Register — returns 201 or 409 if email exists |
| POST | `/api/users/Login` | Login — returns 200, 401, or 404 |
| POST | `/api/tasks/user/{userId}` | Create task for user |
| POST | `/api/tasks/update/{id}` | Update task fields |
| GET  | `/api/tasks/user/{email}` | Get all tasks for a user |

`LoginResponse` uses string message codes: `"User already exists"`, `"User created successfully"`, `"USER_NOT_FOUND"`, `"INVALID_CREDENTIALS"`, `"SUCCESS"`.

### Key Patterns
- **MapStruct** (`UserMapper`) handles `UserRequest → User` and `User → UserResponse` mapping. Add new mappers as `@Mapper(componentModel = "spring")` interfaces in `ModelMapper/`.
- **CORS** is configured in `Config/CORS.java` via `WebMvcConfigurer`, not in `SecurityConfig`. The allowed origins come from the `CORS_ALLOWED_ORIGINS` env var.
- **Password encoding**: `PasswordEncoder` bean is defined in `SecurityConfig`. `UserService` injects it to encode passwords on sign-up and verify on login.
- **Circular dependency risk**: if JWT/filter auth is added, inject `JwtFilter` with `@Lazy` in `SecurityConfig` to avoid circular dependency between `SecurityConfig` → `UserDetailsService` → `PasswordEncoder`.

## Security

- **Do NOT read or access the `.env` file** under any circumstances. It contains sensitive credentials and secrets that should remain private.

## Frontend Reference

- For any frontend-related checks, questions, or context, refer to the file repository:
  **D:/Project File/Task_Management_System/Task-Management-SystemFE**