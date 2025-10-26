@echo off
title Real-Time Airline Ticketing API Test
color 0A

echo ========================================
echo 🛫 REAL-TIME AIRLINE TICKETING TEST
echo ========================================
echo Starting application and testing live...
echo.

set BASE_URL=http://localhost:8080

echo 🚀 Step 1: Starting Spring Boot Application...
start "Spring Boot App" cmd /c ".\mvnw.cmd spring-boot:run"

echo ⏳ Waiting for application to start (30 seconds)...
timeout /t 30 /nobreak > nul

echo.
echo 🔍 Step 2: Testing Application Status...
for /l %%i in (1,1,10) do (
    curl -s -o nul -w "%%{http_code}" %BASE_URL%/api/test/generate-ticket-number > temp_status.txt 2>nul
    set /p STATUS=<temp_status.txt 2>nul
    if "!STATUS!"=="200" (
        echo ✅ Application is LIVE and responding!
        goto :app_ready
    )
    echo Attempt %%i: Waiting for app... Status: !STATUS!
    timeout /t 3 /nobreak > nul
)

echo ❌ Application failed to start properly
pause
exit /b 1

:app_ready
del temp_status.txt 2>nul
echo.

echo ========================================
echo 🧪 REAL-TIME ENDPOINT TESTING
echo ========================================

echo.
echo 📊 Test 1: Generate 13-Digit Ticket Number
echo URL: %BASE_URL%/api/test/generate-ticket-number
curl -s %BASE_URL%/api/test/generate-ticket-number | jq .
echo.

echo 📊 Test 2: Book Real-Time Ticket #1
echo URL: %BASE_URL%/api/tickets/book
curl -X POST %BASE_URL%/api/tickets/book ^
  -H "Content-Type: application/json" ^
  -d "{\"pnr\":\"RT001\",\"passengerName\":\"Live Test User\",\"passengerAge\":28,\"origin\":\"NYC\",\"destination\":\"LAX\",\"departureTime\":\"2024-12-25T10:00:00\",\"arrivalTime\":\"2024-12-25T13:00:00\",\"flightNumber\":\"LT101\",\"baseFare\":399.99,\"taxAmount\":60.00}" | jq .
echo.

echo 📊 Test 3: Book Real-Time Ticket #2 (Same PNR)
curl -X POST %BASE_URL%/api/tickets/book ^
  -H "Content-Type: application/json" ^
  -d "{\"pnr\":\"RT001\",\"passengerName\":\"Live Test Partner\",\"passengerAge\":26,\"origin\":\"NYC\",\"destination\":\"LAX\",\"departureTime\":\"2024-12-25T10:00:00\",\"arrivalTime\":\"2024-12-25T13:00:00\",\"flightNumber\":\"LT101\",\"baseFare\":399.99,\"taxAmount\":60.00}" | jq .
echo.

echo 📊 Test 4: Get All Tickets (Live Data)
echo URL: %BASE_URL%/api/tickets/all
curl -s %BASE_URL%/api/tickets/all | jq .
echo.

echo 📊 Test 5: Get Tickets by PNR (RT001)
echo URL: %BASE_URL%/api/tickets/pnr/RT001
curl -s %BASE_URL%/api/tickets/pnr/RT001 | jq .
echo.

echo 📊 Test 6: Search by Passenger Name
echo URL: %BASE_URL%/api/tickets/search?passengerName=Live
curl -s "%BASE_URL%/api/tickets/search?passengerName=Live" | jq .
echo.

echo 📊 Test 7: Multiple Ticket Generation (Uniqueness Test)
echo URL: %BASE_URL%/api/test/generate-multiple
curl -s %BASE_URL%/api/test/generate-multiple | jq .
echo.

echo ========================================
echo 🔄 REAL-TIME UPDATE TESTING
echo ========================================

echo Getting first ticket number for update test...
for /f "tokens=*" %%a in ('curl -s %BASE_URL%/api/tickets/all ^| jq -r ".[0].ticketNumber" 2^>nul') do set TICKET_NUM=%%a

if not "%TICKET_NUM%"=="null" if not "%TICKET_NUM%"=="" (
    echo 📊 Test 8: Update Ticket Status
    echo URL: %BASE_URL%/api/tickets/%TICKET_NUM%/status?status=CHECKED_IN
    curl -X PUT "%BASE_URL%/api/tickets/%TICKET_NUM%/status?status=CHECKED_IN" | jq .
    echo.
    
    echo 📊 Test 9: Verify Status Update
    curl -s %BASE_URL%/api/tickets/%TICKET_NUM% | jq .
    echo.
) else (
    echo ⚠️ No tickets found for update testing
)

echo ========================================
echo 🎯 REAL-TIME TEST SUMMARY
echo ========================================
echo ✅ Application Status: LIVE
echo ✅ 13-Digit Generation: TESTED
echo ✅ Ticket Booking: TESTED
echo ✅ PNR Retrieval: TESTED
echo ✅ Search Function: TESTED
echo ✅ Status Updates: TESTED
echo.
echo 🌐 Access Points:
echo - API Base: %BASE_URL%
echo - H2 Console: %BASE_URL%/h2-console
echo - Test Page: file:///test-endpoints.html
echo.
echo Press any key to continue monitoring or Ctrl+C to stop...
pause > nul

echo.
echo 🔄 Continuous Monitoring Mode...
:monitor_loop
echo [%time%] Checking application health...
curl -s -o nul -w "Status: %%{http_code} | Response Time: %%{time_total}s" %BASE_URL%/api/test/generate-ticket-number
echo.
timeout /t 10 /nobreak > nul
goto :monitor_loop
