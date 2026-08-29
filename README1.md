FLOW OF APPLICATION 

```
                 ┌─────────────────────┐
                 │       CLIENT        │
                 │   Postman / User    │
                 └──────────┬──────────┘
                            │
                       HTTP Request
                            │
                            ▼
                 ┌─────────────────────┐
                 │     CONTROLLER      │
                 │ SubsidyController   │
                 │                     │
                 │ POST / GET / PUT    │
                 │ DELETE              │
                 └──────────┬──────────┘
                            │
                  Calls Service Method
                            │
                            ▼
                 ┌─────────────────────┐
                 │       SERVICE       │
                 │   SubsidyService    │
                 │                     │
                 │ V1 / V2             │
                 │                     │
                 │ V2 = Active         │
                 └──────────┬──────────┘
                            │
                    Business Logic
                            │
                            ▼
                 ┌─────────────────────┐
                 │     REPOSITORY      │
                 │ SubsidyRepository   │
                 │                     │
                 │ save()              │
                 │ findById()          │
                 │ findAll()           │
                 │ deleteById()        │
                 └──────────┬──────────┘
                            │
                       Spring Data JPA
                            │
                            ▼
                 ┌─────────────────────┐
                 │   JPA / HIBERNATE   │
                 │                     │
                 │ Object ↔ SQL        │
                 └──────────┬──────────┘
                            │
                            ▼
                 ┌─────────────────────┐
                 │        MYSQL        │
                 │     subsidydb       │
                 │                     │
                 │   Subsidy Table     │
                 └─────────────────────┘


              EXCEPTION FLOW
                     │
                     ▼
        ┌─────────────────────────┐
        │ SubsidyNotFoundException│
        └────────────┬────────────┘
                     │
                     ▼
        ┌─────────────────────────┐
        │ GlobalExceptionHandler  │
        └────────────┬────────────┘
                     │
                     ▼
               Error Response
                  (404)

 ```






## 📁 Project Components

### 1. SubsidyController.java

The `SubsidyController` handles HTTP requests and responses related to subsidy operations.

- `@RestController` marks the class as a REST controller that handles HTTP requests and returns data, usually in JSON format.
- `@RequestMapping("/api/subsidies")` defines the base URL for all subsidy-related endpoints.
- `@PostMapping` is used to create a new subsidy.
- `@GetMapping` is used to retrieve subsidy data.
- `@PutMapping` is used to update an existing subsidy.
- `@DeleteMapping` is used to delete a subsidy.
- `@RequestBody` converts JSON request data into a Java object.
- `@PathVariable` extracts values such as the subsidy ID from the URL.
- `ResponseEntity` is used to return both response data and appropriate HTTP status codes.
- `@Qualifier` is used to explicitly select a specific service implementation. In this project, `subsidyServiceV2` is selected.

---

### 2. SubsidyService.java

`SubsidyService` is an interface that defines the business operations required for subsidy management.

It provides methods for:

- Creating a subsidy
- Retrieving a subsidy by ID
- Retrieving all subsidies
- Updating a subsidy
- Deleting a subsidy

The service interface helps maintain a **layered and loosely coupled architecture**, where:

```text
Controller  →  Handles HTTP requests
Service     →  Handles business logic
Repository  →  Handles database operations
```

### 3. SubsidyServiceImplV1.java and SubsidyServiceImplV2.java

These classes provide implementations of the `SubsidyService` interface.

- `@Service` marks the class as a Spring-managed service component. 
- Constructor-based dependency injection is used to inject `SubsidyRepository`. 
- `save()` is used to create a subsidy. 
- `findById()` is used to retrieve a subsidy by its ID. 
- `findAll()` is used to retrieve all subsidies. 
- `deleteById()` is used to delete a subsidy. 
- `orElseThrow()` is used to throw a custom exception when a requested subsidy does not exist. 

#### Additional Business Logic in V2

`SubsidyServiceImplV2` contains additional business logic compared to V1.

When a new subsidy is created without a status, the system automatically assigns:

```text
PENDING
```



## SubsidyRepository

* SubsidyRepository for database operations related to the Subsidy entity.
* It extends JpaRepository<Subsidy, Long>.
* JpaRepository ---> CRUD methods such as save(), findById(), findAll(), and deleteById() without writing SQL queries manually.



## Subsidy.java

* Subsidy class represents a subsidy record in the database.
* @Entity annotation to tell JPA that this class should be mapped to a database table.
* @Id to define the primary key of the entity
* @GeneratedValue with the IDENTITY strategy so that the ID can be generated automatically by MySQL.
* The entity contains fields such as subsidy name, description, amount, beneficiary name and status.
