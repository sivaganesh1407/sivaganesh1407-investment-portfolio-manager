# Investment Portfolio Manager – Run & Screenshot Guide (LinkedIn)

Use this guide to run the project, see the outcome, and capture screenshots for LinkedIn and to push the code to Git.

---

## What This Project Is (for your post)

**One-liner:** Backend REST API built with **Java 11, Spring Boot, JPA, H2** to create portfolios, add investments (stocks, ETFs, crypto), track portfolio value, and analyze performance (returns, allocation).

**Tech:** Java 11 · Spring Boot · Spring Web · Spring Data JPA · H2 · Lombok · Maven · Validation · Layered architecture (Controller → Service → Repository → Model).

---

## What the Code Does (brief)

- **Portfolios:** Create, list, get by id, delete. Each portfolio has name, owner, created date.
- **Investments:** Add to a portfolio (symbol, type, quantity, purchase price, date; optional current price). Types: STOCK, ETF, CRYPTO, MUTUAL_FUND, BOND, OTHER.
- **Value & analytics:** `GET /portfolio/{id}/summary` returns total cost, current value, return %, and allocation by type.

All data is stored in an in-memory H2 database; you can inspect it in the H2 web console.

---

## Step 1: Run the Project

Open a terminal in the project root and run:

```bash
cd backend
mvn spring-boot:run
```

Wait until you see:

```
Started PortfolioManagerApplication in X.XXX seconds
```

**Screenshot 1 – Terminal:** Full terminal window showing the Spring Boot banner and “Started PortfolioManagerApplication”. Good for “Running the application” or “Backend server” in your post.

---

## Step 2: Call the APIs (to get output to screenshot)

Keep the app running. Open a **new terminal** and either:

- **Option A – One script (easiest for one big screenshot):**  
  `cd backend` then run:  
  `chmod +x run-api-demo.sh && ./run-api-demo.sh`  
  You’ll see all API requests and responses in one terminal. Great for a single “API demo” screenshot.

- **Option B – Commands below:** Run each curl yourself and screenshot the ones you want.

### 2a. Create a portfolio

```bash
curl -X POST http://localhost:8080/portfolio \
  -H "Content-Type: application/json" \
  -d '{"name":"My Growth Portfolio","owner":"John Doe"}'
```

You should see JSON like:

```json
{"id":1,"name":"My Growth Portfolio","owner":"John Doe","createdDate":"2025-03-12"}
```

**Screenshot 2 – Create portfolio:** Terminal with the `curl` command and the JSON response (201 Created). Good for “REST API – Create resource”.

### 2b. Add two investments

```bash
curl -X POST http://localhost:8080/portfolio/1/investments \
  -H "Content-Type: application/json" \
  -d '{"symbol":"AAPL","type":"STOCK","quantity":10,"purchasePrice":150,"purchaseDate":"2024-01-15","currentPrice":175.5,"notes":"Tech stock"}'
```

```bash
curl -X POST http://localhost:8080/portfolio/1/investments \
  -H "Content-Type: application/json" \
  -d '{"symbol":"BTC","type":"CRYPTO","quantity":0.5,"purchasePrice":40000,"purchaseDate":"2024-06-01","currentPrice":45000}'
```

**Screenshot 3 – Add investment:** One of the `curl` commands and its JSON response (e.g. id, symbol, type, quantity, portfolioId). Good for “Adding investments to portfolio”.

### 2c. Get all portfolios

```bash
curl http://localhost:8080/portfolio
```

**Screenshot 4 – List portfolios:** Response showing the portfolio list (e.g. one portfolio with id, name, owner, createdDate).

### 2d. Get portfolio summary (value & performance)

```bash
curl http://localhost:8080/portfolio/1/summary
```

You should see something like:

```json
{
  "portfolioId": 1,
  "portfolioName": "My Growth Portfolio",
  "investmentCount": 2,
  "totalCost": 2150000.0000,
  "totalCurrentValue": 2407500.0000,
  "returnPercentage": 11.9767,
  "allocationByType": [
    {"type": "STOCK", "value": 1755.0000, "percentage": 7.28...},
    {"type": "CRYPTO", "value": 22500.0000, "percentage": 92.71...}
  ]
}
```

**Screenshot 5 – Analytics:** Terminal or Postman showing this summary (total cost, current value, return %, allocation). Best one for “Portfolio value & performance analytics”.

---

## Step 3: H2 Database Console (optional but great for LinkedIn)

1. In your browser go to: **http://localhost:8080/h2-console**
2. Use:
   - **JDBC URL:** `jdbc:h2:mem:portfoliodb`
   - **User:** `sa`
   - **Password:** (leave empty)
3. Click **Connect**.
4. Run: `SELECT * FROM PORTFOLIO;` and `SELECT * FROM INVESTMENT;`

**Screenshot 6 – H2 console:** Browser showing the H2 console with the `PORTFOLIO` and/or `INVESTMENT` table results. Good for “Data persistence with H2 / JPA”.

---

## Suggested LinkedIn Captions (short)

- **Screenshot 1 (app running):** “Investment Portfolio Manager backend – Spring Boot app running locally.”
- **Screenshot 2 (create portfolio):** “REST API – creating a portfolio via POST.”
- **Screenshot 3 (add investment):** “Adding investments (stocks, crypto) to a portfolio.”
- **Screenshot 4 (list portfolios):** “GET all portfolios – JSON response.”
- **Screenshot 5 (summary):** “Portfolio analytics – total value, return %, and allocation by type.”
- **Screenshot 6 (H2):** “In-memory H2 database – JPA entities and data at runtime.”

---

## Pushing to Git

From the **repository root** (not inside `backend`):

```bash
cd /Users/sivagolla/IdeaProjects/sivaganesh1407-investment-portfolio-manager

# See what will be committed
git status

# Add all project files (backend code, README, this guide; .gitignore skips target/, .idea/)
git add .
git add backend/
git add LINKEDIN_SCREENSHOT_GUIDE.md

# Commit with a clear message
git commit -m "Investment Portfolio Manager backend: Spring Boot REST API, portfolios, investments, value tracking, analytics, validation, H2"

# Push to your remote (use your branch name if not main)
git push origin main
```

If your default branch is `master`:

```bash
git push origin master
```

If the remote doesn’t exist yet:

```bash
git remote add origin https://github.com/YOUR_USERNAME/sivaganesh1407-investment-portfolio-manager.git
git push -u origin main
```

Replace `YOUR_USERNAME` with your GitHub username.

---

## Quick Checklist for LinkedIn + Git

- [ ] Run `mvn spring-boot:run` and screenshot “Started PortfolioManagerApplication”.
- [ ] Run the curl commands (or Postman) and screenshot: create portfolio, add investment, list portfolios, get summary.
- [ ] Open H2 console, run SQL, screenshot tables.
- [ ] Run `git add .` and `git commit` and `git push` from repo root.

After this you’ll have clear run outcomes to show and the project pushed to Git for your LinkedIn post.
