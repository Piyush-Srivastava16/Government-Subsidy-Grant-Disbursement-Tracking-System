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






SubsidyController.java
@RestController annotation so that Spring can treat this class as a REST API controller.
@RequestMapping("/api/subsidies") as the base URL for all subsidy-related endpoints.
@PostMapping --> creating a subsidy
@GetMapping --> reading data
@PutMapping --> updating a subsidy

@RequestBody to receive the subsidy data from the client. 
@PathVariable when retrieving a specific subsidy by its ID.
ResponseEntity so that I can return both the response data and appropriate HTTP status codes.
@Qualifier to explicitly inject the bean. Dude in my case it is V2.




SubsidyService.java ---> Business logic
SubsidyService interface defines the operations required for the CRUD functionality.
It aslo Maintain a layered and loosely coupled architecture where the controller handles requests, the service handles business logic, and the repository handles database operations


SubsidyServiceImplV1 and V2.

@Service annotation to tell Spring that this class belongs to the service layer and should be managed as a Spring Bean.
Constructor-based dependency injection to inject SubsidyRepository into the service class
For creating subsidy -> save() 
Reading data -> findById() and findAll()   etc.
orElseThrow() --> exception

NOTE :) 
In V2, I added some additional business logic. When a new subsidy is created without a status, I automatically assign PENDING as the default status.
For deletion, I use existsById() to verify that the record exists before deleting it.




SubsidyRepository

SubsidyRepository for database operations related to the Subsidy entity.
It extends JpaRepository<Subsidy, Long>.
JpaRepository ---> CRUD methods such as save(), findById(), findAll(), and deleteById() without writing SQL queries manually.



Subsidy.java
Subsidy class represents a subsidy record in the database.
@Entity annotation to tell JPA that this class should be mapped to a database table.
@Id to define the primary key of the entity
@GeneratedValue with the IDENTITY strategy so that the ID can be generated automatically by MySQL.
The entity contains fields such as subsidy name, description, amount, beneficiary name and status.
