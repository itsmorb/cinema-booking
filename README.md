# Cinema Booking API

REST API for cinema seat reservation built with Spring Boot.

## Tech Stack

- Java 21
- Spring Boot 3.x
- Spring Security + JWT
- Spring Data JPA
- PostgreSQL
- Docker
- Lombok
- Maven

## Requirements

- Java 21
- Docker Desktop

## Getting Started

### 1. Clone the repository

```bash
git clone https://github.com/itsmorb/cinema-booking.git
cd cinema-booking
```

### 2. Start the database

```bash
docker compose up -d
```

### 3. Run the application

```bash
./mvnw spring-boot:run
```

Application will start on `http://localhost:8080`

## API Endpoints

### Auth (no token required)

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/auth/register` | Register new user |
| POST | `/api/auth/login` | Login, returns JWT token |

### Movies (token required)

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/movies` | Get all movies |
| GET | `/api/movies?genre=Sci-Fi` | Filter by genre |
| GET | `/api/movies?title=inception` | Search by title |
| GET | `/api/movies/{id}` | Get movie by ID |
| POST | `/api/movies` | Add movie (ADMIN only) |
| DELETE | `/api/movies/{id}` | Delete movie (ADMIN only) |

### Screenings (token required)

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/screenings` | Get all screenings |
| GET | `/api/screenings?movieId=1` | Get screenings by movie |
| GET | `/api/screenings/upcoming` | Get upcoming screenings |
| GET | `/api/screenings/{id}` | Get screening by ID |
| GET | `/api/screenings/{id}/seats` | Get seat map for screening |
| POST | `/api/screenings` | Add screening (ADMIN only) |
| DELETE | `/api/screenings/{id}` | Delete screening (ADMIN only) |

### Reservations (token required)

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/reservations` | Create reservation |
| GET | `/api/reservations/my` | Get my reservations |
| PUT | `/api/reservations/{id}/confirm` | Confirm reservation |
| DELETE | `/api/reservations/{id}/delete` | Cancel reservation |

## Usage Example

### 1. Register

```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username": "john", "email": "john@example.com", "password": "password123"}'
```

### 2. Login

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "john", "password": "password123"}'
```

### 3. Use token

```bash
curl http://localhost:8080/api/movies \
  -H "Authorization: Bearer <your_token>"
```

### 4. Reserve a seat

```bash
curl -X POST http://localhost:8080/api/reservations \
  -H "Authorization: Bearer <your_token>" \
  -H "Content-Type: application/json" \
  -d '{"screeningId": 1, "seatId": 5}'
```

## Security

- JWT authentication for all endpoints except register/login
- BCrypt password hashing
- SQL Injection prevention via JPA prepared statements
- XSS prevention via Spring Security headers
- Role-based access control (USER, ADMIN)

## Default Configuration

Database runs on `localhost:5432` with:
- Database: `cinema`
- Username: `cinema_user`
- Password: `cinema_pass`
