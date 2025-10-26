@echo off
echo ========================================
echo Testing Airline Ticketing API Endpoints
echo ========================================
echo.

set BASE_URL=http://localhost:8080

echo 1. Testing Application Status...
curl -s %BASE_URL%/api/test/generate-ticket-number
if %errorlevel% neq 0 (
    echo ❌ Application not running on port 8080
    echo Please start the application first: .\mvnw.cmd spring-boot:run
    pause
    exit /b 1
) else (
    echo ✅ Application is running
)
echo.

echo 2. Testing Ticket Generation...
curl -s %BASE_URL%/api/test/generate-ticket-number
echo.
echo.

echo 3. Testing Multiple Ticket Generation...
curl -s %BASE_URL%/api/test/generate-multiple
echo.
echo.

echo 4. Booking Test Ticket (PNR: ABC123)...
curl -X POST %BASE_URL%/api/tickets/book ^
  -H "Content-Type: application/json" ^
  -d "{\"pnr\":\"ABC123\",\"passengerName\":\"John Doe\",\"passengerAge\":30,\"origin\":\"NYC\",\"destination\":\"LAX\",\"departureTime\":\"2024-01-15T10:00:00\",\"arrivalTime\":\"2024-01-15T13:00:00\",\"flightNumber\":\"AA101\",\"baseFare\":299.99,\"taxAmount\":50.00}"
echo.
echo.

echo 5. Booking Another Test Ticket (PNR: XYZ789)...
curl -X POST %BASE_URL%/api/tickets/book ^
  -H "Content-Type: application/json" ^
  -d "{\"pnr\":\"XYZ789\",\"passengerName\":\"Jane Smith\",\"passengerAge\":25,\"origin\":\"BOS\",\"destination\":\"SFO\",\"departureTime\":\"2024-02-20T14:30:00\",\"arrivalTime\":\"2024-02-20T17:45:00\",\"flightNumber\":\"DL205\",\"baseFare\":450.00,\"taxAmount\":75.50}"
echo.
echo.

echo 6. Getting All Tickets...
curl -s %BASE_URL%/api/tickets/all
echo.
echo.

echo 7. Testing Get Tickets by PNR (ABC123)...
curl -s %BASE_URL%/api/tickets/pnr/ABC123
echo.
echo.

echo 8. Testing Get Tickets by PNR (XYZ789)...
curl -s %BASE_URL%/api/tickets/pnr/XYZ789
echo.
echo.

echo 9. Testing Search by Passenger Name (John)...
curl -s "%BASE_URL%/api/tickets/search?passengerName=John"
echo.
echo.

echo 10. Testing Search by Passenger Name (Jane)...
curl -s "%BASE_URL%/api/tickets/search?passengerName=Jane"
echo.
echo.

echo 11. Testing H2 Database Console Access...
echo Visit: %BASE_URL%/h2-console
echo URL: jdbc:h2:mem:testdb
echo Username: sa
echo Password: password
echo.

echo ========================================
echo All endpoint tests completed!
echo ========================================
pause
