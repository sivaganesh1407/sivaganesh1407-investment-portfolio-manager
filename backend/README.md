# Investment Portfolio Manager – Backend

Backend API for creating portfolios, adding investments (stocks, ETFs, crypto, etc.), tracking portfolio value, and analyzing performance.

---

## Tech stack

| Technology        | Purpose                    |
|-------------------|----------------------------|
| Java 11           | Runtime                    |
| Spring Boot 2.7   | Application framework      |
| Spring Web        | REST APIs                  |
| Spring Data JPA   | Data access                |
| H2                | In-memory DB (development) |
| Lombok            | Boilerplate reduction      |
| Validation        | Request validation         |

---

## Build and run

**Prerequisites:** Java 11+, Maven 3.6+

```bash
cd backend
mvn clean install
mvn spring-boot:run
```

Server: **http://localhost:8080**  
H2 console: **http://localhost:8080/h2-console**  
- JDBC URL: `jdbc:h2:mem:portfoliodb`  
- User: `sa`  
- Password: (empty)

---

## API overview

Base URL: `http://localhost:8080`

### Portfolios

| Method | Endpoint              | Description                    |
|--------|------------------------|--------------------------------|
| POST   | `/portfolio`          | Create portfolio (body: name, owner, optional createdDate) |
| GET    | `/portfolio`          | List all portfolios           |
| GET    | `/portfolio/{id}`     | Get portfolio by id           |
| GET    | `/portfolio/{id}/summary` | Portfolio value & performance summary |
| DELETE | `/portfolio/{id}`     | Delete portfolio               |

### Investments

| Method | Endpoint                                | Description                          |
|--------|-----------------------------------------|--------------------------------------|
| POST   | `/portfolio/{portfolioId}/investments`  | Add investment to portfolio          |
| GET    | `/portfolio/{portfolioId}/investments`  | List investments in portfolio       |
| GET    | `/investments/{id}`                     | Get investment by id                 |
| PUT    | `/investments/{id}`                     | Update investment                     |
| DELETE | `/investments/{id}`                     | Delete investment                     |

### Portfolio summary (value & performance)

**GET** `/portfolio/{id}/summary`

Returns:

- `portfolioId`, `portfolioName`, `investmentCount`
- `totalCost` – cost basis (sum of quantity × purchase price)
- `totalCurrentValue` – sum of quantity × current price (or cost if no current price)
- `returnPercentage` – (current value − cost) / cost × 100
- `allocationByType` – breakdown by type (STOCK, ETF, CRYPTO, etc.) with value and percentage

---

## Request bodies

**Create portfolio (POST /portfolio)**

```json
{
  "name": "Retirement",
  "owner": "john"
}
```

Optional: `"createdDate": "2024-01-15"` (default: today).

**Create/update investment**

```json
{
  "symbol": "AAPL",
  "type": "STOCK",
  "quantity": 10,
  "purchasePrice": 150.00,
  "purchaseDate": "2024-01-15",
  "currentPrice": 175.50,
  "notes": "Optional notes"
}
```

- `type`: one of `STOCK`, `ETF`, `CRYPTO`, `MUTUAL_FUND`, `BOND`, `OTHER`
- `currentPrice`: optional; when set, used for current value and return %.

---

## Validation and errors

- Invalid request bodies (e.g. missing required fields, negative numbers) return **400** with a JSON body containing `status`, `error`, and `message` (validation details).
- Not found (e.g. portfolio or investment id) returns **404** (no body for DELETE).
- Other errors return **500** with a generic error body.

---

## Project structure

```
com.portfolio.manager
├── controller    – REST endpoints (Portfolio, Investment)
├── service       – Business logic (Portfolio, Investment, PortfolioAnalytics)
├── repository    – JPA repositories
├── model         – Entities (Portfolio, Investment, InvestmentType) and DTOs
└── config        – Global exception handler (and other config)
```

---

## Tests

```bash
mvn test
```

Covers portfolio service (unit) and portfolio controller (MockMvc).
