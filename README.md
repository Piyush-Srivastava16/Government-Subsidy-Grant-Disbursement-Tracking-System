# Development of Digital Subsidy & Grant Administration Platform

## 📌 Project Overview

The **Development of Digital Subsidy & Grant Administration Platform** is a web-based application designed to streamline and manage the complete lifecycle of government subsidy and grant administration.

The platform manages the process from **beneficiary registration and subsidy application to multi-level verification, milestone-based disbursement, fund utilization, and analytics**.

The system provides role-based access for different government officials and maintains a centralized database for secure and efficient subsidy administration.

---

## 🎯 Objectives

* Digitize the subsidy and grant administration process.
* Manage beneficiary and government scheme information.
* Automate application eligibility and verification workflows.
* Support multi-level verification and approval.
* Manage staged fund disbursement based on milestones.
* Track utilization of released funds.
* Provide dashboards and analytics for monitoring.
* Maintain audit logs for important system activities.
* Implement role-based access control.

---

## 🚀 Key Features

### 1. Beneficiary Management

* Register beneficiaries.
* Update beneficiary information.
* Search and manage beneficiary records.
* Maintain category, region, and income details.

### 2. Scheme Management

* Create and manage government subsidy schemes.
* Define eligibility criteria.
* Configure grant amounts.
* Maintain regional scheme information.
* Track scheme allocation budgets.

### 3. Application Management

* Create subsidy applications.
* Link applications with beneficiaries and schemes.
* Maintain application status.
* Store eligibility scores.
* Track application lifecycle.

### 4. Document Management

* Upload beneficiary documents.
* Store document metadata.
* Associate documents with beneficiaries.
* Verify or reject uploaded documents.
* Maintain document verification status.

### 5. Multi-Level Verification

The platform supports a multi-level verification workflow:

```text
FIELD_OFFICER
      ↓
DISTRICT_OFFICER
      ↓
FINANCE_APPROVER
      ↓
APPLICATION APPROVED
```

Each verification stage can contain:

* Verification level
* Officer name
* Status
* Remarks
* Application reference

### 6. Milestone Management

* Create application milestones.
* Define due dates.
* Track milestone status.
* Record milestone remarks.
* Support milestone-based fund release.

### 7. Disbursement Management

* Create disbursement records.
* Link disbursement with applications and milestones.
* Track disbursement amount.
* Track disbursement date.
* Maintain `PENDING`, `RELEASED`, and `CANCELLED` statuses.

### 8. Fund Utilization

* Record utilized funds.
* Link utilization with disbursements.
* Track utilization date.
* Maintain utilization descriptions.
* Verify or reject utilization records.

### 9. Dashboard & Analytics

The system provides:

* Total beneficiaries
* Total applications
* Approved applications
* Pending applications
* Total disbursements
* Total fund released
* Total fund utilized
* Fund utilization percentage
* Beneficiaries by region
* Applications by status

### 10. Security & Role-Based Access

The platform provides different access levels:

| Role             | Access                                 |
| ---------------- | -------------------------------------- |
| ADMIN            | All modules                            |
| FIELD_OFFICER    | Beneficiaries, Applications, Documents |
| DISTRICT_OFFICER | Verifications, Milestones              |
| FINANCE_APPROVER | Disbursements, Fund Utilization        |

Authentication is implemented using **Spring Security with Basic Authentication and BCrypt password encoding**.

---

## 🛠️ Technology Stack

### Backend

* Java 17
* Spring Boot
* Spring MVC
* Spring Data JPA
* Hibernate
* Spring Security
* REST APIs
* Maven
* Lombok

### Database

* MySQL

### Frontend

* React.js
* Vite
* JavaScript
* HTML
* CSS
* Axios
* React Router

### Development Tools

* IntelliJ IDEA / VS Code
* MySQL Workbench
* Postman
* Git
* GitHub

---

## 🏗️ System Architecture

The project follows a layered architecture:

```text
                    React Frontend
                          │
                          ▼
                    REST API Layer
                          │
                          ▼
                    Controller Layer
                          │
                          ▼
                     Service Layer
                          │
                          ▼
                   Repository Layer
                          │
                          ▼
                     MySQL Database
```

### Backend Flow

```text
Client
  ↓
Controller
  ↓
Service
  ↓
Repository
  ↓
Database
```

This separation improves maintainability, scalability, and code organization.

---

## 🔄 Complete Business Workflow

```text
Beneficiary Registration
          ↓
Scheme Selection
          ↓
Application Creation
          ↓
Document Upload
          ↓
Field Officer Verification
          ↓
District Officer Verification
          ↓
Finance Approver
          ↓
Application Approved
          ↓
Milestone Creation
          ↓
Milestone Completion
          ↓
Fund Disbursement
          ↓
Fund Utilization
          ↓
Dashboard & Analytics
```

---

## 🔐 Security

The application uses **Spring Security** for authentication and authorization.

Role-based access is implemented using the following roles:

```text
ADMIN
FIELD_OFFICER
DISTRICT_OFFICER
FINANCE_APPROVER
```

Passwords are stored using **BCrypt password hashing**.

---

## 📂 Project Structure

```text
Government-Subsidy-Grant-System/
│
├── backend/
│   ├── src/
│   │   └── main/
│   │       ├── java/
│   │       │   └── com/
│   │       │       └── government/
│   │       │           └── subsidy/
│   │       │
│   │       └── resources/
│   │
│   ├── pom.xml
│   └── uploads/
│
├── frontend/
│   ├── src/
│   │   ├── components/
│   │   ├── pages/
│   │   ├── services/
│   │   └── App.jsx
│   │
│   ├── package.json
│   └── vite.config.js
│
├── .gitignore
└── README.md
```

---

## 🗄️ Database Entities

The major entities in the system include:

```text
User
Beneficiary
Scheme
Application
BeneficiaryDocument
Verification
Milestone
Disbursement
FundUtilization
AuditLog
```

Relationships include:

```text
Beneficiary
    ↓
Application
    ↓
Verification
    ↓
Milestone
    ↓
Disbursement
    ↓
FundUtilization
```

---

## 🔗 Important API Endpoints

### Beneficiaries

```text
GET     /api/beneficiaries
GET     /api/beneficiaries/{id}
POST    /api/beneficiaries
PUT     /api/beneficiaries/{id}
DELETE  /api/beneficiaries/{id}
```

### Schemes

```text
GET     /api/schemes
GET     /api/schemes/{id}
POST    /api/schemes
PUT     /api/schemes/{id}
DELETE  /api/schemes/{id}
```

### Applications

```text
GET     /api/applications
GET     /api/applications/{id}
POST    /api/applications
PUT     /api/applications/{id}
DELETE  /api/applications/{id}
```

### Documents

```text
GET     /api/documents
POST    /api/documents
POST    /api/documents/upload
PUT     /api/documents/{id}
PUT     /api/documents/{id}/verify
DELETE  /api/documents/{id}
```

### Verifications

```text
GET     /api/verifications
POST    /api/verifications
PUT     /api/verifications/{id}
DELETE  /api/verifications/{id}
```

### Milestones

```text
GET     /api/milestones
POST    /api/milestones
PUT     /api/milestones/{id}
DELETE  /api/milestones/{id}
```

### Disbursements

```text
GET     /api/disbursements
POST    /api/disbursements
PUT     /api/disbursements/{id}
DELETE  /api/disbursements/{id}
```

### Fund Utilization

```text
GET     /api/fund-utilizations
POST    /api/fund-utilizations
PUT     /api/fund-utilizations/{id}
DELETE  /api/fund-utilizations/{id}
GET     /api/fund-utilizations/summary
```

### Dashboard

```text
GET     /api/dashboard/summary
```

### Analytics

```text
GET     /api/analytics/beneficiaries-by-region
GET     /api/analytics/applications-by-status
GET     /api/analytics/total-applications
GET     /api/analytics/approved-applications
GET     /api/analytics/pending-applications
GET     /api/analytics/total-fund-released
GET     /api/analytics/total-fund-utilized
```

---

## ⚙️ Installation & Setup

### 1. Clone the Repository

```bash
git clone <YOUR_GITHUB_REPOSITORY_URL>
```

```bash
cd Government-Subsidy-Grant-System
```

---

### 2. Configure MySQL

Create the database:

```sql
CREATE DATABASE subsidydb;
```

Update the Spring Boot database configuration according to your local MySQL setup.

Example:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/subsidydb
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD
```

---

### 3. Run Backend

Go to backend directory:

```bash
cd backend
```

Run:

```bash
mvn spring-boot:run
```

Backend will run on:

```text
http://localhost:8080
```

---

### 4. Run Frontend

Open another terminal:

```bash
cd frontend
```

Install dependencies:

```bash
npm install
```

Run the application:

```bash
npm run dev
```

Frontend will normally run on:

```text
http://localhost:5173
```

---

## 🧪 Testing

The APIs can be tested using **Postman**.

Recommended testing order:

```text
1. User
2. Beneficiary
3. Scheme
4. Application
5. Document
6. Verification
7. Milestone
8. Disbursement
9. Fund Utilization
10. Dashboard
11. Analytics
```

---

## 📊 Dashboard

The dashboard provides a centralized overview of subsidy administration data including application statistics and fund utilization.

Example metrics:

```text
Total Beneficiaries
Total Applications
Approved Applications
Pending Applications
Total Disbursements
Total Fund Released
Total Fund Utilized
Fund Utilization %
```

---

## 🔮 Future Enhancements

Possible future improvements include:

* JWT-based authentication
* Email/SMS notifications
* Advanced eligibility scoring algorithms
* PDF/Excel report generation
* Cloud file storage
* Advanced audit trail
* Deployment using Docker
* Cloud deployment
* Automated scheduled disbursement processing
* Advanced analytics and reporting

---

## 👨‍💻 Project Status

```text
Backend CRUD                         ✅
MySQL Integration                    ✅
REST APIs                            ✅
Document Upload                      ✅
Multi-Level Verification             ✅
Milestone Management                 ✅
Disbursement Management              ✅
Fund Utilization                     ✅
Role-Based Access Control            ✅
Dashboard                            ✅
Analytics                            ✅
React Frontend                       ✅
```

---

## 📌 Conclusion

The **Development of Digital Subsidy & Grant Administration Platform** provides a structured digital solution for managing government subsidy and grant activities.

The platform integrates beneficiary management, applications, document verification, multi-level approval, milestone tracking, fund disbursement, fund utilization, security, dashboard monitoring, and analytics into a single system.


# 👨‍💻 Author

**Piyush Srivastava**
