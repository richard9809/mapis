# 🚀 MAPIS — Multi-Agency Permit Integration System  
### _A Spring Boot + Apache Camel + PostgreSQL micro-integration service_

MAPIS is a backend service that orchestrates **permit approvals across multiple agencies** (Environment, Fire, Health).  
It exposes REST APIs to create permits, initiates approvals in external agencies, stores the workflow, and receives asynchronous callbacks.

This project demonstrates advanced skills in:

✔ **Spring Boot 3.5**  
✔ **Apache Camel Integration Patterns**  
✔ **PostgreSQL + Docker**  
✔ **REST API Design (Request/Response DTOs)**  
✔ **Async Callbacks & External System Orchestration**  
✔ **Clean Architecture, Service Layer, Entities, Logging**  
✔ **Java 21 + Hibernate ORM**  
✔ **Containerization & DevOps Basics**

---

# 📌 Table of Contents

1. Project Overview  
2. System Architecture  
3. Technologies Used  
4. Key Features  
5. Project Structure  
6. API Endpoints  
7. Apache Camel Flows  
8. Docker Setup  
9. Database Schema  
10. How to Run the Project  
11. Why This Project Matters (Recruiter Summary)

---

# 📘 Project Overview

MAPIS acts as a **centralized permit gateway**, allowing a county government to:

- Accept new permit applications  
- Automatically contact external government agencies  
- Track each agency approval task  
- Receive callback updates  
- Determine when the permit is fully ready for issuance  

The system follows a **real-world workflow** similar to municipal / county licensing systems.

---

# 🏗️ System Architecture

```
                     ┌──────────────────────┐
                     │     MAPIS API        │
                     │  (Spring Boot 3.5)   │
                     └──────────┬───────────┘
                                │ creates
                                ▼
                       ┌────────────────┐
                       │   Permit       │
                       └───────┬────────┘
                               │ triggers
                               ▼
                   ┌────────────────────────┐
                   │ Apache Camel Orchestration │
                   └──────┬────────┬────────┘
         calls ENV API →  │        │  ← receives callbacks
                          │        │
         calls FIRE API → │        │  ← receives callbacks
                          │        │
         calls HLTH API → │        │  ← receives callbacks
                          ▼        ▼
                      Updates agency tasks
```

---

# 🧰 Technologies Used

| Technology      | Purpose |
|-----------------|----------|
| Java 17         | Modern language features |
| Spring Boot 3.5 | Bootstrapping, DI, REST |
| Apache Camel    | Integration with external agencies |
| PostgreSQL 16   | Persistence |
| Docker Compose  | Database + pgAdmin |
| Hibernate/JPA   | ORM + schema management |
| Lombok          | Cleaner POJOs |
| SLF4J Logging   | End-to-end traceability |

---

# ✨ Key Features

### 🔹 Create a permit and trigger agency workflows  
Each new permit triggers **three agency tasks**.

### 🔹 Track each agency task  
Stored in the `agency_tasks` table.

### 🔹 Receive asynchronous callbacks  
MAPIS updates task status + result.

### 🔹 Full logging and observability  
Each step logs outbound requests + responses.

### 🔹 Clean architecture  

---

# 📁 Project Structure

```
src/main/java/com/personal/mapis
│
├── controllers
├── services
├── camel
├── models
└── repositories
```

---

# 📌 API Endpoints

```
POST /api/permits
GET /api/permits/{permitNumber}
GET /api/permits/{permitNumber}/details

POST /api/callbacks/env
POST /api/callbacks/fire
POST /api/callbacks/health
```

---

# 🐳 Docker Setup

```
docker-compose up -d
```

PostgreSQL:
- host: localhost  
- port: 5433  
- user: permit_user  
- pass: permit_pass  

---

# ▶️ How to Run

```
./mvnw spring-boot:run
```

---

# 🧑‍💼 Why This Project Matters

Demonstrates strong backend/integration engineering skills.

