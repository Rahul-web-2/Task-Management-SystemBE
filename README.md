# 📋 Task Management System – Backend

A **secure and scalable RESTful backend API** for a Task Management System built using **Spring Boot, JWT Authentication, and MySQL**.

This project provides APIs for **user authentication and task management**, including creating, updating, deleting, and tracking tasks.

The backend is designed with **clean architecture, security best practices, and RESTful API principles** to simulate a real-world production backend system.

---

# 🚀 Features

## 🔐 Authentication & Security
- User registration and login
- JWT-based authentication
- Password encryption using BCrypt
- Protected API routes using Spring Security
- Token validation for secure requests

## 📌 Task Management
- Create new tasks
- Update existing tasks
- Delete tasks
- Retrieve all tasks for a user
- Mark tasks as completed
- Assign due dates and descriptions

## ⚙️ Backend Architecture
- RESTful API design
- Layered architecture (Controller → Service → Repository)
- DTO-based data transfer
- Global exception handling
- Input validation

---

# 🏗️ Project Structure

```
src
 └── main
     └── java
         └── com.example.taskmanagement
             ├── controller      # REST Controllers
             ├── service         # Business logic
             ├── repository      # Database access layer
             ├── entity          # JPA Entities
             ├── dto             # Request/Response DTOs
             ├── security        # JWT & Spring Security
             └── config          # Configuration classes
```

---

# 🛠️ Tech Stack

## Backend
- Java 21
- Spring Boot
- Spring Security
- JWT Authentication

## Database
- MySQL

## Tools
- Maven
- Postman
- Git & GitHub
- MySQL Workbench

---

# 📦 API Endpoints

## Authentication APIs

| Method | Endpoint | Description |
|------|------|-------------|
| POST | `/api/auth/register` | Register a new user |
| POST | `/api/auth/login` | Authenticate user and return|

---

## Task APIs

| Method | Endpoint | Description |
|------|------|-------------|
| GET | `/api/tasks` | Get all tasks |
| POST | `/api/tasks` | Create a new task |
| PUT | `/api/tasks/{id}` | Update a task |
| DELETE | `/api/tasks/{id}` | Delete a task |
| PATCH | `/api/tasks/{id}/complete` | Mark task as completed |

---

# 🔑 Authentication Flow

1. User registers
2. User logs in
3. Server validates credentials


---

# ⚙️ Environment Variables

Configure these in `application.properties` or environment variables.

```
spring.datasource.url=jdbc:mysql://${DB_HOST}:${DB_PORT}/${DB_NAME}
spring.datasource.username=${DB_USER}
spring.datasource.password=${DB_PASS}

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

server.port=${PORT:8080}

jwt.secret=${JWT_SECRET}
```

---

# 🗄️ Database Schema

## Users Table

| Column | Type |
|------|------|
| id | Long |
| name | String |
| email | String |
| password | String |

## Tasks Table

| Column | Type |
|------|------|
| id | Long |
| title | String |
| description | String |
| status | Boolean |
| dueDate | Date |
| userId | Long |

---

# ▶️ Running the Project

## 1️⃣ Clone Repository

```bash
git clone https://github.com/Rahul-web-2/Task-Management-SystemBE.git
```

## 2️⃣ Navigate to Project

```bash
cd Task-Management-SystemBE
```

## 3️⃣ Configure Database

Create a MySQL database:

```
task_management
```

Update the `application.properties` file with your database credentials.

---

## 4️⃣ Run Application

Using Maven:

```bash
mvn spring-boot:run
```

Or run the **main Spring Boot application class** from your IDE.

---

# 🧪 Testing APIs

Use **Postman** or **Insomnia** to test APIs.

Example login request:

```
POST /api/auth/login
Content-Type: application/json
```

```json
{
  "email": "user@example.com",
  "password": "password123"
}
```

---

# 🔒 Security Best Practices Implemented


- Password hashing using BCrypt


---

# 📈 Future Improvements

- JWT authentication
- Protected API endpoints
- Secure environment variable configuration
- JWT token is generated
- Client stores token
- Client sends token in Authorization header
- Server validates token for protected APIs
- Role-based access control (RBAC)
- Refresh tokens
- Email verification
- Task categories and labels
- Pagination and filtering
- Docker support
- Unit and integration testing

---

# 🤝 Contributing

Contributions are welcome!

1. Fork the repository

2. Create a new branch

```
git checkout -b feature/new-feature
```

3. Commit your changes

```
git commit -m "Add new feature"
```

4. Push to your branch

```
git push origin feature/new-feature
```

5. Open a Pull Request

---

# 📜 License

This project is licensed under the **MIT License**.

---

# 👨‍💻 Author

**Rahul**

Backend Developer | Java | Spring Boot | REST APIs

GitHub:  
https://github.com/Rahul-web-2
