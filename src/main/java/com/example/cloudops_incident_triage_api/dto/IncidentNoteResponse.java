package com.example.cloudops_incident_triage_api.dto;

import java.time.Instant;

public record IncidentNoteResponse(
        Long id,
        Long incidentId,
        String author,
        String note,
        Instant createdAt
) {
}