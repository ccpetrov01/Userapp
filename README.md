# UserApp

## Project Overview

UserApp is a Spring Boot REST API application for user management. It supports user registration, login with JWT-based authentication, and secured access to user data and operations. The app uses a PostgreSQL database and implements security best practices, including password hashing and role-based access control.

---

## Features

- User registration with validation and hashed passwords
- User login with JWT token generation
- JWT token validation for secured endpoints
- Role-based authorization (e.g., USER, ADMIN)
- User data CRUD operations secured via JWT
- Pageable user search with filtering by name/email
- Database migrations with Flyway

---

## Technologies Used

- Java 17
- Spring Boot 3.x
- Spring Security with JWT
- PostgreSQL
- Flyway (Database migrations)
- Lombok
- Hibernate (JPA)
- Maven

---

## Prerequisites

Before running the application, ensure you have the following installed:

- Java 17 or higher
- Maven 3.6+
- PostgreSQL 14+
- Git

---

## Database Setup

1. Install and start PostgreSQL server.
2. Create a database for the app (e.g., `userappdb`).
3. Configure connection properties in `src/main/resources/application.properties`:

   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/userappdb
   spring.datasource.username=your_db_username
   spring.datasource.password=your_db_password
   spring.jpa.hibernate.ddl-auto=validate
   spring.flyway.enabled=true
   spring.flyway.locations=classpath:db/migration


## Running the Application

Clone the repo:
bash
git clone https://github.com/ccpetrov01/Userapp.git
cd Userapp
Build the project with Maven:

bash
mvn clean install
Run the application:

bash
mvn spring-boot:run
The API will be accessible at http://localhost:8080.

## API Endpoints

POST /register – Register a new user.
POST /login – Login and receive JWT token.
GET /users – Get users list (JWT protected).
GET /users/searchUsers?term=... – Search users by name or email (JWT protected).
Other CRUD endpoints secured with JWT.

## Authentication

Use the /login endpoint to obtain a JWT token by providing valid email and password.
Include the token in the Authorization header as Bearer <token> for secured endpoints.

## Security Details

Passwords are stored hashed using BCrypt.
JWT tokens are signed with a secret key defined in application.properties.
Stateless session management; no HTTP sessions.
Roles are used for authorization; default is USER.

##Notes 
Update application.properties with your JWT secret and expiration.
Customize roles and authorization as needed.
Make sure the database is running before starting the app.

