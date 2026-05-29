# Security Auth JWT System

## Overview

This project is a Spring Boot based Authentication and Authorization System using:

* Spring Security
* JWT Authentication
* Role Based Access Control (RBAC)
* Permission Based Authorization
* OAuth2 Token Support
* MySQL Database
* REST APIs

The system supports:

* User Login
* JWT Token Generation
* Admin Creation
* Role Management
* Permission Management
* Secure APIs

---

# Technologies Used

* Java
* Spring Boot
* Spring Security
* Spring Data JPA
* JWT (JJWT)
* OAuth2
* MySQL
* Maven
* Lombok

---

# Features

* JWT Authentication
* Role Based Authorization
* Permission Based Access
* Secure REST APIs
* Default Admin Creation on Startup
* Admin Management APIs
* Stateless Authentication
* Password Encryption using BCrypt

---

# Project Structure

```text
src/main/java
│
├── controller
├── entity
├── repository
├── security
├── service
├── config
└── dto
```

---

# Database Tables

* users
* roles
* permissions
* user_roles
* role_permissions

---

# Default Admin

When the application starts for the first time, a default admin user is created automatically.

## Default Credentials

```text
Username : admin
Password : admin123
```

---

# Authentication Flow

1. User logs in using username and password
2. JWT token is generated
3. Token is sent in Authorization header
4. Spring Security validates the token
5. APIs are accessed based on roles and permissions

---

# API Endpoints

## Authentication APIs

### Login

```http
POST /auth/login
```

Request:

```json
{
  "username": "admin",
  "password": "admin123"
}
```

---

## Admin APIs

### Create Admin

```http
POST /admin/create-admin
```

Headers:

```text
Authorization: Bearer <JWT_TOKEN>
```

Request:

```json
{
  "username": "admin2",
  "password": "admin123"
}
```

---

# Security Configuration

* JWT Authentication Filter
* Stateless Session Management
* BCrypt Password Encoding
* Role Based Authorization
* Secure API Access

---

# Roles and Permissions

## Roles

* ADMIN
* USER

## Example Permissions

* CREATE_USER
* READ_USER
* UPDATE_USER
* DELETE_USER

---

# Running the Project

## Clone Repository

```bash
git clone <repository-url>
```

## Configure Database

Update application.properties:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/security_db
spring.datasource.username=root
spring.datasource.password=root
```

---

## Run Application

```bash
mvn spring-boot:run
```

---

# Swagger

Swagger UI:

```text
http://localhost:8080/swagger-ui/index.html
```

---

# Author

Mahesh Kumar
