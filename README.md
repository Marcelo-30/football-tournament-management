# Football Tournament Management

A RESTful API for managing football tournaments, teams, players, and scheduled matches.

The project is built with Spring Boot and follows a layered architecture that separates the domain model, application services, web controllers, and persistence implementation.

## Features

- Create, retrieve, search, and delete tournaments
- Register teams in a tournament
- Register players in a team
- Schedule matches between registered teams
- Search matches by team name
- Retrieve teams, players, and matches by their parent resource
- PostgreSQL persistence with Spring Data JPA
- DTO/entity mapping with MapStruct
- Interactive OpenAPI documentation with Swagger UI
- Descriptive responses through the `X-Response-Message` header

## Tech Stack

- Java 21
- Spring Boot 4.1.0
- Spring Web MVC
- Spring Data JPA
- PostgreSQL
- MapStruct 1.6.3
- Springdoc OpenAPI
- Maven

## Architecture

```text
Web controllers
       |
Application services
       |
Domain repositories
       |
Persistence adapters
       |
Spring Data JPA
       |
PostgreSQL
```

The main source packages are organized as follows:

```text
src/main/java/com/footballtournament/management
├── domain
│   ├── model
│   └── repository
├── persistence
│   ├── crud
│   ├── entity
│   ├── mapper
│   └── repository
├── service
└── web
    ├── controller
    └── response
```

## Requirements

Before running the application, make sure you have:

- Java 21
- Maven 3.9 or later
- PostgreSQL
- A PostgreSQL database named `football_tournament_db`

## Database Setup

Connect to PostgreSQL and create the database:

```sql
CREATE DATABASE football_tournament_db;
```

The application reads the database credentials from the following environment variables:

- `DB_USERNAME`
- `DB_PASSWORD`

If these variables are not defined, the application uses `postgres` as both the username and password.

### PowerShell

```powershell
$env:DB_USERNAME="postgres"
$env:DB_PASSWORD="your_password"
```

### Linux or macOS

```bash
export DB_USERNAME=postgres
export DB_PASSWORD=your_password
```

The database URL is configured as:

```text
jdbc:postgresql://localhost:5432/football_tournament_db
```

Hibernate automatically creates or updates the database tables when the application starts.

## Installation

Clone the repository:

```bash
git clone https://github.com/Marcelo-30/football-tournament-management.git
cd football-tournament-management
```

Build the project:

```bash
mvn clean package -DskipTests
```

Run the application:

```bash
mvn spring-boot:run
```

The API will be available at:

```text
http://localhost:8080
```

## API Documentation

Once the application is running, open Swagger UI:

```text
http://localhost:8080/swagger-ui/index.html
```

The OpenAPI specification is available at:

```text
http://localhost:8080/v3/api-docs
```

## API Endpoints

### Tournaments

| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/tournaments` | Get all tournaments |
| `GET` | `/tournaments/{id}` | Get a tournament by ID |
| `GET` | `/tournaments/season/{season}` | Get tournaments by season |
| `GET` | `/tournaments/search?name={name}` | Search tournaments by name |
| `POST` | `/tournaments` | Create a tournament |
| `DELETE` | `/tournaments/{id}` | Delete a tournament |

### Teams

| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/api/teams` | Get all teams |
| `GET` | `/api/teams/{id}` | Get a team by ID |
| `GET` | `/api/teams/tournament/{tournamentId}` | Get teams registered in a tournament |
| `POST` | `/api/teams/tournament/{tournamentId}` | Register a team in a tournament |
| `DELETE` | `/api/teams/{id}` | Delete a team |

### Players

| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/api/players` | Get all players |
| `GET` | `/api/players/{id}` | Get a player by ID |
| `GET` | `/api/players/team/{teamId}` | Get players registered in a team |
| `POST` | `/api/players/team/{teamId}` | Register a player in a team |
| `DELETE` | `/api/players/{id}` | Delete a player |

### Matches

| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/api/matches` | Get all matches |
| `GET` | `/api/matches?team={name}` | Search matches by team name |
| `GET` | `/api/matches/{id}` | Get a match by ID |
| `GET` | `/api/matches/tournament/{tournamentId}` | Get matches from a tournament |
| `POST` | `/api/matches/tournament/{tournamentId}` | Schedule a tournament match |
| `DELETE` | `/api/matches/{id}` | Delete a match |

## Request Examples

### Create a tournament

```json
{
  "name": "Regional Football Cup",
  "season": "2026-2027"
}
```

```bash
curl -i -X POST http://localhost:8080/tournaments \
  -H "Content-Type: application/json" \
  -d '{"name":"Regional Football Cup","season":"2026-2027"}'
```

### Register a team

```json
{
  "name": "Tigres",
  "city": "Monterrey"
}
```

Send the request to:

```text
POST /api/teams/tournament/{tournamentId}
```

### Register a player

```json
{
  "name": "Carlos Rodríguez",
  "number": 8,
  "position": "Midfielder"
}
```

Send the request to:

```text
POST /api/players/team/{teamId}
```

### Schedule a match

```json
{
  "homeTeamId": 1,
  "awayTeamId": 2,
  "scheduledAt": "2026-08-03T18:00:00"
}
```

Send the request to:

```text
POST /api/matches/tournament/{tournamentId}
```

Both teams must be registered in the selected tournament.

## Response Messages

API responses include a custom header containing a human-readable operation result:

```text
X-Response-Message: Tournament created successfully
```

Use `curl -i`, Postman, or the browser developer tools to inspect this header.

## Running Tests

Make sure PostgreSQL is running and the configured test database credentials are valid. Then run:

```bash
mvn test
```

## Suggested Improvements

- Add request validation with Jakarta Bean Validation
- Add a global exception handler
- Add unit and integration tests
- Use Testcontainers or an embedded database for testing
- Add update endpoints
- Add database migrations with Flyway or Liquibase
- Add Docker Compose for PostgreSQL
- Add GitHub Actions for automated builds and tests
- Add authentication and authorization
- Add tournament standings and score management

## Author

Created by [Marcelo-30](https://github.com/Marcelo-30).
