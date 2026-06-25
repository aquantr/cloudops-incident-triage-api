package com.example.cloudops_incident_triage_api.dto;

import com.example.cloudops_incident_triage_api.entity.IncidentSeverity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateIncidentRequest(
        @NotBlank
        @Size(max = 120)
        String title,

        @NotBlank
        String summary,

        @NotNull
        IncidentSeverity severity,

        @NotBlank
        @Size(max = 120)
        String affectedService
) {
}