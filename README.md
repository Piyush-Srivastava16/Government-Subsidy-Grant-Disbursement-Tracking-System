# Government Subsidy & Grant Disbursement Tracking System

A Spring Boot REST API project designed to manage and track government subsidies and grants, including beneficiary information, subsidy amounts, application status, and disbursement-related records.

## 📌 Project Overview

The **Government Subsidy & Grant Disbursement Tracking System** is a backend application developed using **Java, Spring Boot, Spring Data JPA, Hibernate, and MySQL**.

The system provides REST APIs to perform CRUD operations on subsidy records.

It follows a layered architecture where responsibilities are separated into:

* Controller Layer
* Service Layer
* Repository Layer
* Entity Layer
* Exception Handling Layer

The project is designed to make subsidy management easier, more structured, and scalable.

---

## 🚀 Features

* Create a new subsidy record
* Retrieve a subsidy by ID
* Retrieve all subsidies
* Update an existing subsidy
* Delete a subsidy
* Automatic default status as `PENDING`
* Custom exception handling
* Global exception handling
* RESTful API architecture
* MySQL database integration
* JPA/Hibernate based database operations
* Constructor-based dependency injection
* Clean layered architecture

---

## 🛠️ Technologies Used

| Technology         | Purpose                  |
| ------------------ | ------------------------ |
| Java               | Programming Language     |
| Spring Boot        | Backend Framework        |
| Spring Web         | REST API Development     |
| Spring Data JPA    | Database Access          |
| Hibernate          | ORM                      |
| MySQL              | Relational Database      |
| Maven              | Dependency Management    |
| Lombok             | Reduces Boilerplate Code |
| Postman            | API Testing              |
---

## 📂 Project Structure

```text
src
└── main
    └── java
        └── com.government.subsidy
            │
            ├── controller
            │   └── SubsidyController.java
            │
            ├── entity
            │   └── Subsidy.java
            │
            ├── exception
            │   ├── SubsidyNotFoundException.java
            │   └── GlobalExceptionHandler.java
            │
            ├── repository
            │   └── SubsidyRepository.java
            │
            └── service
                ├── SubsidyService.java
                ├── SubsidyServiceImplV1.java
                └── SubsidyServiceImplV2.java
```

---

#  System Architecture

The application follows a layered architecture.

```text
                    Client / Postman
                           │
                           │ HTTP Request
                           ▼
                 ┌─────────────────────┐
                 │  SubsidyController  │
                 │    REST APIs         │
                 └──────────┬──────────┘
                            │
                            ▼
                 ┌─────────────────────┐
                 │    SubsidyService   │
                 │     Interface       │
                 └──────────┬──────────┘
                            │
                            ▼
                 ┌─────────────────────┐
                 │ SubsidyServiceImpl  │
                 │   Business Logic    │
                 └──────────┬──────────┘
                            │
                            ▼
                 ┌─────────────────────┐
                 │  SubsidyRepository  │
                 │    Spring Data JPA  │
                 └──────────┬──────────┘
                            │
                            ▼
                 ┌─────────────────────┐
                 │    JPA / Hibernate  │
                 └──────────┬──────────┘
                            │
                            ▼
                 ┌─────────────────────┐
                 │   MySQL Database    │
                 └─────────────────────┘
```

---

# 🔄 Application Flow

For example, when creating a subsidy:

```text
Client / Postman
       ↓
POST /api/subsidies
       ↓
SubsidyController
       ↓
SubsidyService
       ↓
SubsidyServiceImplV2
       ↓
Business Logic
       ↓
SubsidyRepository
       ↓
JPA / Hibernate
       ↓
MySQL
       ↓
Response
```

---

# 🗃️ Entity Structure

The main entity of the application is `Subsidy`.

```text
Subsidy
│
├── id
├── name
├── description
├── amount
├── beneficiaryName
└── status
```

### Primary Key

The `id` field is the primary key.

`GenerationType.IDENTITY` allows MySQL to automatically generate the ID.

---

# 🔌 REST API Endpoints

Base URL:

```text
/api/subsidies
```

## 1. Create Subsidy

### Request

```http
POST /api/subsidies
```

### Example Request Body

```json
{
    "name": "Agriculture Subsidy",
    "description": "Financial support for farmers",
    "amount": 50000,
    "beneficiaryName": "Rahul Sharma",
    "status": "PENDING"
}
```

### Response

```json
{
    "id": 1,
    "name": "Agriculture Subsidy",
    "description": "Financial support for farmers",
    "amount": 50000.0,
    "beneficiaryName": "Rahul Sharma",
    "status": "PENDING"
}
```

### HTTP Status

```text
201 Created
```

---

## 2. Get Subsidy By ID

### Request

```http
GET /api/subsidies/{id}
```

### Example

```http
GET /api/subsidies/1
```

### HTTP Status

```text
200 OK
```

---

## 3. Get All Subsidies

### Request

```http
GET /api/subsidies
```

### HTTP Status

```text
200 OK
```

### Example Response

```json
[
    {
        "id": 1,
        "name": "Agriculture Subsidy",
        "description": "Financial support for farmers",
        "amount": 50000.0,
        "beneficiaryName": "Rahul Sharma",
        "status": "PENDING"
    },
    {
        "id": 2,
        "name": "Education Grant",
        "description": "Financial assistance for students",
        "amount": 30000.0,
        "beneficiaryName": "Aman Kumar",
        "status": "APPROVED"
    }
]
```

---

## 4. Update Subsidy

### Request

```http
PUT /api/subsidies/{id}
```

### Example

```http
PUT /api/subsidies/1
```

### Request Body

```json
{
    "amount": 75000,
    "status": "APPROVED"
}
```

The application updates only the fields provided in the request.

### HTTP Status

```text
200 OK
```

---

## 5. Delete Subsidy

### Request

```http
DELETE /api/subsidies/{id}
```

### Example

```http
DELETE /api/subsidies/1
```

### HTTP Status

```text
204 No Content
```

---

# ⚙️ Business Logic

The Service Implementation contains the main business logic.

One important feature is the default subsidy status.

If the client does not provide a status:

```java
if (subsidy.getStatus() == null ||
        subsidy.getStatus().isBlank()) {

    subsidy.setStatus("PENDING");
}
```

The application automatically sets:

```text
PENDING
```

This ensures that every newly created subsidy has an initial status.

---

# 🛡️ Exception Handling

The project uses custom exception handling.

## Custom Exception

```java
public class SubsidyNotFoundException
        extends RuntimeException {
}
```

This exception is thrown when a requested subsidy does not exist.

For example:

```text
GET /api/subsidies/100
```

If ID `100` does not exist, the application throws:

```text
SubsidyNotFoundException
```

---

## Global Exception Handler

The project uses:

```java
@RestControllerAdvice
```

to handle exceptions globally.

The application returns a structured response such as:

```json
{
    "timestamp": "2026-08-13T10:30:00",
    "status": 404,
    "error": "Not Found",
    "message": "Subsidy not found with id: 100"
}
```

This provides a clean and consistent error response to the client.


# 💾 Database

The application uses **MySQL** as the relational database.

Example database configuration:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/subsidydb
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

> Replace `YOUR_PASSWORD` with your local MySQL password.

The application uses Hibernate/JPA to communicate with MySQL.

---

# 📦 Maven Dependencies

The project uses dependencies for:

```text
Spring Web
Spring Data JPA
Spring Boot Validation
MySQL Driver
Lombok
Spring Boot DevTools
```

These dependencies provide REST API development, database integration, validation, reduced boilerplate code, and development-time support.

---

# 🧪 Testing With Postman

The REST APIs can be tested using Postman.

### Create

```text
POST http://localhost:8080/api/subsidies
```

### Get All

```text
GET http://localhost:8080/api/subsidies
```

### Get By ID

```text
GET http://localhost:8080/api/subsidies/1
```

### Update

```text
PUT http://localhost:8080/api/subsidies/1
```

### Delete

```text
DELETE http://localhost:8080/api/subsidies/1
```

---

# ▶️ How to Run the Project

## Step 1: Clone the Repository

```bash
git clone <YOUR_GITHUB_REPOSITORY_URL>
```

## Step 2: Open the Project

Open the project in:

* IntelliJ IDEA
* Eclipse
* VS Code
* Spring Tool Suite

## Step 3: Configure MySQL

Create the database:

```sql
CREATE DATABASE subsidydb;
```

Then update the database credentials in:

```text
src/main/resources/application.properties
```

## Step 4: Build the Project

Using Maven:

```bash
mvn clean install
```

## Step 5: Run the Application

```bash
mvn spring-boot:run
```

Or run the main Spring Boot application class directly from the IDE.

The application will start on:

```text
http://localhost:8080
```

---

# 📊 CRUD Operations

| Operation | HTTP Method | Endpoint              |
| --------- | ----------- | --------------------- |
| Create    | POST        | `/api/subsidies`      |
| Read One  | GET         | `/api/subsidies/{id}` |
| Read All  | GET         | `/api/subsidies`      |
| Update    | PUT         | `/api/subsidies/{id}` |
| Delete    | DELETE      | `/api/subsidies/{id}` |

---

# 🎯 Learning Objectives

Through this project, I practiced and implemented:

* Core Java concepts
* Spring Boot
* REST API development
* Dependency Injection
* Constructor Injection
* `@Qualifier`
* Spring Data JPA
* Hibernate
* MySQL integration
* CRUD operations
* Entity mapping
* Exception handling
* Global exception handling
* Request validation
* HTTP status codes
* Postman API testing
* Maven
* Git and GitHub
* Layered architecture

---


# 👨‍💻 Author

**Piyush Srivastava**
