
# Employee Manager

A Spring Boot employee management application using Java,
Spring Data JPA, MySQL, Thymeleaf, and Lombok.

## Features
- Create, view, update, and delete employees
- Store employee records in MySQL
- Search employees by name

## Requirements
- Java 21
- Maven
- MySQL Server
- Postman (for REST API testing)

## Run the project
1. Create a MySQL database named `employee_db`.
2. Configure the database URL, username, and password
   in your local application configuration.
3. Run the application using your IDE or Maven.
4. Open http://localhost:8080.

## REST API
- GET /api/employees
- GET /api/employees/{id}
- POST /api/employees
- PUT /api/employees/{id}
- DELETE /api/employees/{id}
