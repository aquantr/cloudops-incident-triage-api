package com.example.cloudops_incident_triage_api.dto;

import com.example.cloudops_incident_triage_api.entity.IncidentSeverity;
import com.example.cloudops_incident_triage_api.entity.IncidentStatus;

import java.time.Instant;

public record IncidentResponse(
        Long id,
        String title,
        String summary,
        IncidentSeverity severity,
        IncidentStatus status,
        String affectedService,
        Instant createdAt,
        Instant updatedAt
) {
}