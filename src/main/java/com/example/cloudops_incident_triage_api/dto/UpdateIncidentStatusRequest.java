package com.example.cloudops_incident_triage_api.dto;

import com.example.cloudops_incident_triage_api.entity.IncidentStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateIncidentStatusRequest(
        @NotNull
        IncidentStatus status
) {
}