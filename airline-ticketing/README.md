# Airline Ticketing Application

A Spring Boot application for airline ticket booking with automatic 13-digit ticket number generation.

## Features

- **Ticket Booking**: Book airline tickets with passenger, itinerary, and fare details
- **13-Digit Ticket Number Generation**: Unique ticket numbers generated using SHA-256 hash of input parameters
- **REST API**: Complete RESTful API for ticket management
- **Validation**: Input validation with proper error handling
- **H2 Database**: In-memory database for development and testing

## Ticket Number Generation Algorithm

The application generates a unique 13-digit ticket number using:
- **Input Parameters**: Name, Age, PNR, Itinerary details, Fare details
- **SHA-256 Hash**: Creates hash from combined input data
- **Random Component**: Adds 4 random digits for additional uniqueness
- **Timestamp**: Includes timestamp-based digit for collision prevention

## API Endpoints

### Book Ticket
```
POST /api/tickets/book
```

**Request Body:**
```json
{
  "pnr": "ABC123",
  "passengerName": "John Doe",
  "passengerAge": 30,
  "origin": "NYC",
  "destination": "LAX",
  "departureTime": "2024-01-15T10:00:00",
  "arrivalTime": "2024-01-15T13:00:00",
  "flightNumber": "AA101",
  "baseFare": 299.99,
  "taxAmount": 50.00,
  "currency": "USD"
}
```

### Get Ticket by Number
```
GET /api/tickets/{ticketNumber}
```

### Get Tickets by PNR
```
GET /api/tickets/pnr/{pnr}
```

### Search Tickets by Passenger Name
```
GET /api/tickets/search?passengerName={name}
```

### Update Ticket Status
```
PUT /api/tickets/{ticketNumber}/status?status={CONFIRMED|CANCELLED|CHECKED_IN|BOARDED}
```

### Cancel Ticket
```
PUT /api/tickets/{ticketNumber}/cancel
```

### Get All Tickets
```
GET /api/tickets/all
```

## Running the Application

1. **Prerequisites**: Java 17, Maven
2. **Build**: `mvn clean install`
3. **Run**: `mvn spring-boot:run`
4. **Access**: http://localhost:8080
5. **H2 Console**: http://localhost:8080/h2-console

## Database Configuration

- **URL**: jdbc:h2:mem:testdb
- **Username**: sa
- **Password**: password

## Example Usage

```bash
# Book a ticket
curl -X POST http://localhost:8080/api/tickets/book \
  -H "Content-Type: application/json" \
  -d '{
    "pnr": "ABC123",
    "passengerName": "John Doe",
    "passengerAge": 30,
    "origin": "NYC",
    "destination": "LAX",
    "departureTime": "2024-01-15T10:00:00",
    "arrivalTime": "2024-01-15T13:00:00",
    "flightNumber": "AA101",
    "baseFare": 299.99,
    "taxAmount": 50.00
  }'

# Get ticket by number
curl http://localhost:8080/api/tickets/1234567890123
```

## Project Structure

```
src/main/java/com/airline/ticketing/
├── controller/          # REST Controllers
├── dto/                # Data Transfer Objects
├── exception/          # Exception Handling
├── model/              # JPA Entities
├── repository/         # Data Repositories
└── service/            # Business Logic
```
