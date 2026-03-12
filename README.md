# Investment Portfolio Manager

Backend system for creating investment portfolios, adding investments (stocks, ETFs, crypto, etc.), tracking portfolio value, and analyzing performance.

---

## Where is the code?

All backend code is in the **`backend/`** folder:

```
backend/
├── pom.xml                          # Maven build
├── src/main/java/com/portfolio/manager/
│   ├── PortfolioManagerApplication.java
│   ├── controller/                  # REST APIs (Portfolio, Investment)
│   ├── service/                     # Business logic
│   ├── repository/                  # JPA repositories
│   ├── model/                       # Entities & DTOs (Portfolio, Investment, etc.)
│   └── config/                      # Exception handler, SampleDataLoader
├── src/main/resources/
│   └── application.properties       # H2, JPA config
└── src/test/java/                   # Unit & controller tests
```

See **[backend/README.md](backend/README.md)** for full API docs, tech stack, and run instructions.

---

## Tech stack

| Technology     | Purpose              |
|----------------|----------------------|
| Java 11        | Runtime              |
| Spring Boot 2.7| REST API, JPA, etc.  |
| Spring Data JPA| Data access          |
| H2             | In-memory DB (dev)    |
| Maven          | Build                 |
| Lombok         | Boilerplate reduction |

---

## Quick start

**Prerequisites:** Java 11+, Maven 3.6+

```bash
cd backend
mvn clean install
mvn spring-boot:run
```

- **API:** http://localhost:8080  
- **H2 console:** http://localhost:8080/h2-console (JDBC URL: `jdbc:h2:mem:portfoliodb`, User: `sa`, Password: empty)

**API demo (run in a second terminal after app is up):**

```bash
cd backend
./run-api-demo.sh
```

---

## Features

- **Portfolios:** Create, list, get by id, delete
- **Investments:** Add to portfolio (symbol, type, quantity, price, date); types: STOCK, ETF, CRYPTO, MUTUAL_FUND, BOND, OTHER
- **Value & analytics:** `GET /portfolio/{id}/summary` – total cost, current value, return %, allocation by type
- **Sample data:** On startup, 4 portfolios and 12 investments are loaded for demo (see H2 console)

---

## API overview

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST   | `/portfolio` | Create portfolio |
| GET    | `/portfolio` | List all portfolios |
| GET    | `/portfolio/{id}` | Get portfolio by id |
| GET    | `/portfolio/{id}/summary` | Value & performance summary |
| DELETE | `/portfolio/{id}` | Delete portfolio |
| POST   | `/portfolio/{id}/investments` | Add investment |
| GET    | `/portfolio/{id}/investments` | List investments in portfolio |
| GET    | `/investments/{id}` | Get investment |
| PUT    | `/investments/{id}` | Update investment |
| DELETE | `/investments/{id}` | Delete investment |

Full request/response details and validation: **[backend/README.md](backend/README.md)**.

---

## Tests

```bash
cd backend
mvn test
```

---

## License

This project is for portfolio/learning purposes.
