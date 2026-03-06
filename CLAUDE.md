?# Task Management System - Backend

## Project
Spring Boot REST API backend for a Task Management System. Frontend is a React app running at `localhost:5173`.

## Tech Stack
- Java 21, Spring Boot 3.5.11
- Spring Data JPA, Spring Security, Spring Validation
- MySQL database
- Lombok, BCrypt password encoding
- Maven build tool

## Project Structure
```
src/main/java/com/TaskManagementSystem/
├── config/         # PasswordConfig (Security, CORS, BCrypt)
├── controller/     # UserController, TaskController
├── model/          # User, Task
├── repository/     # UserRepository, TaskRepository
├── service/        # UserService, TaskService
└── TaskManagementSystemApplication.java
```

## Environment Variables
Database credentials are loaded from a `.env` file (not committed):
- `DB_URL`
- `DB_USERNAME`
- `DB_PASSWORD`

## API Endpoints
### Users — `/api/users`
- `POST /create` — register a new user
- `POST /login` — authenticate (BCrypt password match)
- `GET /` — list all users
- `GET /{email}` — get user by email
- `DELETE /{email}` — delete user by email

### Tasks — `/api/task`
- `POST /user/{userEmail}` — create task assigned to a user
- `PUT /update/{id}` — update task (description + status)
- `GET /` — list all tasks
- `GET /{id}` — get task by id
- `DELETE /{id}` — delete task by id

## Key Conventions
- Passwords are always BCrypt-encoded before saving
- All endpoints are currently open (no auth enforcement via Spring Security)
- CORS is configured globally for `http://localhost:5173`
- CSRF is disabled
- JSON serialization uses `@JsonManagedReference` / `@JsonBackReference` to avoid circular references between User and Task
- Use Lombok (`@Getter`, `@Setter`, `@NoArgsConstructor`, `@AllArgsConstructor`, `@RequiredArgsConstructor`) — do not write boilerplate manually
- Validation annotations (`@NotBlank`, `@Email`) live on model fields; use `@Valid` in controllers

## Build & Run
```bash
./mvnw spring-boot:run
```

## Git
- Active branch: `backEndDev`
- Main branch: `master`
