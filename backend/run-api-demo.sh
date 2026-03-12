#!/bin/bash
# Run this AFTER starting the app (mvn spring-boot:run) to demo all APIs in one go.
# Use the output for LinkedIn screenshots.

BASE="http://localhost:8080"
echo "=========================================="
echo "Investment Portfolio Manager - API Demo"
echo "=========================================="
echo ""

echo "1. POST /portfolio - Create portfolio"
curl -s -X POST $BASE/portfolio -H "Content-Type: application/json" \
  -d '{"name":"My Growth Portfolio","owner":"John Doe"}'
echo -e "\n"

echo "2. POST /portfolio/1/investments - Add STOCK"
curl -s -X POST $BASE/portfolio/1/investments -H "Content-Type: application/json" \
  -d '{"symbol":"AAPL","type":"STOCK","quantity":10,"purchasePrice":150,"purchaseDate":"2024-01-15","currentPrice":175.5}'
echo -e "\n"

echo "3. POST /portfolio/1/investments - Add CRYPTO"
curl -s -X POST $BASE/portfolio/1/investments -H "Content-Type: application/json" \
  -d '{"symbol":"BTC","type":"CRYPTO","quantity":0.5,"purchasePrice":40000,"purchaseDate":"2024-06-01","currentPrice":45000}'
echo -e "\n"

echo "4. GET /portfolio - List all portfolios"
curl -s $BASE/portfolio
echo -e "\n"

echo "5. GET /portfolio/1/summary - Value & performance"
curl -s $BASE/portfolio/1/summary
echo -e "\n"

echo "Done. Use this output for LinkedIn screenshots."
