# CloudOps Incident Triage API

A small backend/cloud portfolio project for tracking production incidents and triage notes.

This project models a simplified incident management workflow used by backend, platform, cloud operations, and SRE teams. It provides REST endpoints for creating incidents, tracking severity/status, and adding investigation notes during incident response.

## Why this project exists

Production systems fail. Backend and cloud engineers need tools to track incidents, understand affected services, document investigation steps, and move issues from detection to resolution.

This MVP focuses on a clean, interview-explainable backend implementation:

* REST API with Spring Boot
* PostgreSQL persistence
* Flyway database migrations
* Layered architecture
* Request validation
* Practical error handling
* Docker Compose local environment

## Tech stack

* Java 21
* Spring Boot
* Spring Web
* Spring Data JPA
* PostgreSQL
* Flyway
* Docker Compose
* Bean Validation
* Maven

## Architecture

The application uses a simple layered backend structure:

```text
Controller -> Service -> Repository -> PostgreSQL
```

Package layout:

```text
controller/   REST endpoints
service/      Business logic and mapping
repository/   Spring Data JPA repositories
entity/       JPA entities and enums
dto/          Request and response DTOs
exception/    API error handling
```

## Core domain

### Incident

Represents a production issue affecting a service.

Fields:

* `id`
* `title`
* `summary`
* `severity`
* `status`
* `affectedService`
* `createdAt`
* `updatedAt`

Severity values:

```text
LOW, MEDIUM, HIGH, CRITICAL
```

Status values:

```text
OPEN, INVESTIGATING, MITIGATED, RESOLVED
```

### IncidentNote

Represents investigation or mitigation notes attached to an incident.

Fields:

* `id`
* `incidentId`
* `author`
* `note`
* `createdAt`

## API endpoints

| Method | Endpoint                     | Description                |
| ------ | ---------------------------- | -------------------------- |
| POST   | `/api/incidents`             | Create a new incident      |
| GET    | `/api/incidents`             | List all incidents         |
| GET    | `/api/incidents/{id}`        | Get one incident           |
| PATCH  | `/api/incidents/{id}/status` | Update incident status     |
| POST   | `/api/incidents/{id}/notes`  | Add a note to an incident  |
| GET    | `/api/incidents/{id}/notes`  | List notes for an incident |

## Run locally

Start PostgreSQL:

```bash
docker compose up -d
```

Run the API:

```bash
./mvnw spring-boot:run
```

Check health:

```bash
curl http://localhost:8080/actuator/health
```

## Example requests

### Create an incident

```bash
curl -X POST http://localhost:8080/api/incidents \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Payment API latency spike",
    "summary": "Checkout requests are timing out for some users.",
    "severity": "HIGH",
    "affectedService": "payment-service"
  }'
```

### List incidents

```bash
curl http://localhost:8080/api/incidents
```

### Get an incident

```bash
curl http://localhost:8080/api/incidents/1
```

### Update incident status

```bash
curl -X PATCH http://localhost:8080/api/incidents/1/status \
  -H "Content-Type: application/json" \
  -d '{
    "status": "INVESTIGATING"
  }'
```

### Add an incident note

```bash
curl -X POST http://localhost:8080/api/incidents/1/notes \
  -H "Content-Type: application/json" \
  -d '{
    "author": "on-call-engineer",
    "note": "Checked logs. Elevated 5xx errors started after latest deployment."
  }'
```

### List incident notes

```bash
curl http://localhost:8080/api/incidents/1/notes
```

## Database schema

The schema is managed with Flyway.

Tables:

```text
incidents
incident_notes
```

Indexes:

```text
idx_incidents_status
idx_incidents_severity
idx_incident_notes_incident_id
```

## Error handling

The API returns structured errors for:

* missing resources
* validation failures

Example validation error:

```json
{
  "timestamp": "2026-06-25T20:00:00Z",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "validationErrors": {
    "title": "must not be blank"
  }
}
```

## Future improvements

These are intentionally not part of the first MVP:

* Add Testcontainers integration tests
* Add OpenAPI/Swagger documentation
* Add filtering by status, severity, and affected service
* Add pagination
* Add authentication
* Add Kafka for incident event publishing
* Add Redis caching for incident dashboards
* Add Prometheus/Grafana metrics
* Add AWS deployment with Terraform
* Add RAG-based incident summary generation
* Add Kubernetes deployment manifests

## Interview explanation

This project demonstrates a clean backend API for a realistic cloud operations use case. I kept the first version intentionally small: a production incident can be created, tracked through its lifecycle, and documented through notes. The API uses Spring Boot, PostgreSQL, Flyway migrations, validation, structured errors, and a layered architecture. Future extensions could turn this into a broader incident response platform with event streaming, observability, cloud deployment, and AI-assisted triage.
