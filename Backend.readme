# Backend Explanation Plan

## 1. First: Overall Backend Architecture

I will explain:

```text
React Frontend
      ↓
Controller
      ↓
Service
      ↓
Repository
      ↓
JPA / Hibernate
      ↓
MySQL Database
```

You will understand:

* What each layer does
* Why we use layered architecture
* How a request travels through the project
* Why Controller should not contain business logic
* Why Service is important
* Why Repository is used
* How JPA and Hibernate communicate with MySQL

---

# 2. Every Annotation Explained

For example:

### `@Entity`

Why we use it:

> `@Entity` tells JPA and Hibernate that this Java class represents a database table.

Example:

```java
@Entity
public class User {
}
```

Without `@Entity`, Hibernate will not treat the class as a database entity.

---

### `@Id`

```java
@Id
private Long id;
```

This tells Hibernate:

> This field is the Primary Key of the database table.

---

### `@GeneratedValue`

```java
@GeneratedValue(strategy = GenerationType.IDENTITY)
```

This tells MySQL/Hibernate to automatically generate IDs.

For example:

```text
User 1 → ID 1
User 2 → ID 2
User 3 → ID 3
```

You do not manually enter IDs.

---

### `@Column`

```java
@Column(nullable = false)
```

This creates validation at the database level.

It means:

```text
This column cannot contain NULL.
```

---

### `@Table`

```java
@Table(name = "users")
```

This explicitly defines the database table name.

---

### `@Getter`

```java
@Getter
```

Lombok automatically generates getter methods.

Instead of manually writing:

```java
public String getUsername() {
    return username;
}
```

Lombok creates it automatically.

---

### `@Setter`

```java
@Setter
private String username;
```

Lombok automatically generates:

```java
setUsername()
```

---

# 3. Entity Relationships

I will explain all your relationships properly:

### `@ManyToOne`

Example:

```java
@ManyToOne
@JoinColumn(name = "beneficiary_id")
private Beneficiary beneficiary;
```

Meaning:

```text
Many Applications
        ↓
One Beneficiary
```

One beneficiary can create multiple applications.

---

### `@OneToOne`

Example from your Disbursement:

```java
@OneToOne
@JoinColumn(name = "milestone_id")
private Milestone milestone;
```

Meaning:

```text
One Milestone
       ↓
One Disbursement
```

Your business rule ensures that one milestone cannot have multiple disbursements.

---

### `@JoinColumn`

Example:

```java
@JoinColumn(name = "application_id")
```

This creates a foreign key column:

```text
application_id
```

inside the database table.

---

# 4. Controller Layer

I will explain every controller like:

```java
@RestController
@RequestMapping("/api/users")
public class UserController
```

You will understand:

### `@RestController`

Why it is used:

> It tells Spring Boot that this class handles HTTP requests and returns JSON responses.

---

### `@RequestMapping`

```java
@RequestMapping("/api/users")
```

Creates the base URL:

```text
/api/users
```

---

### `@GetMapping`

```java
@GetMapping
```

Handles:

```text
GET /api/users
```

Used for fetching data.

---

### `@PostMapping`

```java
@PostMapping
```

Handles:

```text
POST /api/users
```

Used for creating data.

---

### `@PutMapping`

Used for updating data.

---

### `@DeleteMapping`

Used for deleting data.

---

### `@RequestBody`

```java
@RequestBody User user
```

Converts JSON received from Postman or React into a Java object.

Example:

```json
{
  "username": "admin",
  "password": "password123",
  "role": "ADMIN"
}
```

Spring converts this JSON into:

```java
User user
```

---

### `@PathVariable`

Example:

```java
@GetMapping("/{id}")
```

```java
public User getUserById(@PathVariable Long id)
```

For URL:

```text
/api/users/5
```

Spring takes:

```text
5
```

and stores it in:

```java
Long id
```

---

# 5. Service Layer

This is one of the most important parts of your project.

For example:

```text
Controller receives request
        ↓
Service checks business rules
        ↓
Repository communicates with database
```

Your Service layer contains business logic such as:

* Username validation
* Password encryption
* Role validation
* Duplicate username checking
* Milestone validation
* Disbursement validation
* Status transition validation
* Released disbursement protection


---

# 6. Repository Layer

For example:

```java
public interface UserRepository
        extends JpaRepository<User, Long> {
}
```

Why do we use `JpaRepository`?

Because Spring automatically provides methods such as:

```java
save()
findById()
findAll()
delete()
existsById()
```

You do not need to manually write SQL queries for basic CRUD operations.

---

# 7. Custom Repository Methods

Example:

```java
Optional<User> findByUsername(String username);
```

Spring Data JPA automatically understands this.

It generates logic similar to:

```sql
SELECT *
FROM users
WHERE username = ?
```

You don't manually write the SQL.

---

# 8. Complete User Management Explanation

We will explain:

```text
User Entity
      ↓
UserRepository
      ↓
UserService
      ↓
UserServiceImplV1
      ↓
UserController
      ↓
Security
```

Including:

* Why username must be unique
* Why password is encrypted
* How roles are validated
* Why users are prevented from deletion in certain cases
* How the first admin works
* How new users are created

---

# 9. Spring Security Complete Explanation

Your project uses:

```text
Spring Security
      ↓
HTTP Basic Authentication
      ↓
CustomUserDetailsService
      ↓
UserRepository
      ↓
MySQL
      ↓
Role Validation
      ↓
API Access
```

I will explain:

### `SecurityConfig`

```java
@Configuration
@EnableWebSecurity
```

### Password Encoder

```java
@Bean
public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
}
```

### DAO Authentication Provider

```java
DaoAuthenticationProvider
```

### Custom UserDetailsService

```java
UserDetailsService
```

### HTTP Basic Authentication

```text
Username + Password
```

### Role-Based Authorization

For example:

```java
.hasRole("ADMIN")
```

Meaning only users with:

```text
ROLE_ADMIN
```

can access that API.

---

# 10. Beneficiary Module

We will cover:

```text
Beneficiary Entity
↓
Beneficiary Repository
↓
Beneficiary Service
↓
Beneficiary Controller
```

And explain how beneficiary data is connected with:

```text
User
Applications
```

---

# 11. Scheme Module

We will explain:

```text
Scheme
↓
Government subsidy scheme
↓
Grant amount
↓
Allocation budget
↓
Eligibility criteria
```

And why scheme management is restricted to:

```text
ADMIN
```

---

# 12. Application Module

This module connects:

```text
Beneficiary
       ↓
Application
       ↓
Scheme
```

We will explain:

* Application creation
* Application status
* Eligibility score
* Beneficiary relationship
* Scheme relationship
* Application lifecycle

---

# 13. Document Module

We will explain:

```text
Application
      ↓
Document
```

And how documents support the verification process.

---

# 14. Verification Module

We will explain how:

```text
Application
      ↓
Verification
      ↓
District Officer Decision
```

works.

Including:

* Verification status
* Business logic
* Application relationship
* Officer-level processing

---

# 15. Milestone Module

We will explain your staged subsidy process:

```text
Application
      ↓
Milestone
      ↓
COMPLETED
      ↓
Eligible for Disbursement
```

Including milestone examples such as:

```text
DOCUMENTATION
GROUND_VERIFICATION
UTILIZATION_PROOF
```

---

# 16. Disbursement Module — Full Explanation

This is one of the strongest modules in your project.

Your flow is:

```text
Application
      ↓
Milestone
      ↓
Milestone COMPLETED
      ↓
Disbursement Created
      ↓
PENDING
      ↓
APPROVED
      ↓
RELEASED
```

Or:

```text
PENDING
   ↓
REJECTED
```

I will explain every method from your `DisbursementServiceImplV1`.

Especially:

### `validateDisbursementRequest()`

Why we check:

```text
Amount
Application
Milestone
```

---

### `validateMilestone()`

Why we check:

```text
Does milestone belong to application?
```

and:

```text
Is milestone COMPLETED?
```

---

### One Milestone = One Disbursement

Your repository checks whether a disbursement already exists.

This prevents duplicate fund releases.

---

### Status Transition Validation

Your project prevents invalid flows.

Valid:

```text
PENDING → APPROVED
PENDING → REJECTED
APPROVED → RELEASED
```

Invalid example:

```text
PENDING → RELEASED
```

This is rejected.

---

### Released Disbursement Protection

Once:

```text
RELEASED
```

it cannot be:

```text
Updated
Deleted
```

This is an important financial business rule.

---

# 17. Audit Logging

I will explain:

```java
auditLogService.createAuditLog(...)
```

Why it is used.

Example:

```text
User performs action
        ↓
CREATE / UPDATE / DELETE
        ↓
Audit Log Saved
```

This provides:

* Accountability
* Transparency
* Activity tracking

---

# 18. Exception Handling

We will cover:

```text
DuplicateUsernameException
InvalidRoleException
UserDeletionException
UserNotFoundException
```

And your other exceptions.

You will understand:

> Why custom exceptions are better than using only RuntimeException.

We will also explain your:

```java
@RestControllerAdvice
```

and how it handles errors globally.

---

# 19. Complete Request Flow Example

For example:

```text
POST /api/disbursements
        ↓
DisbursementController
        ↓
DisbursementServiceImplV1
        ↓
Validate Request
        ↓
Find Application
        ↓
Find Milestone
        ↓
Validate Milestone
        ↓
Check Duplicate Disbursement
        ↓
Save Through Repository
        ↓
Hibernate
        ↓
MySQL Database
        ↓
Create Audit Log
        ↓
Return JSON Response
```
---



