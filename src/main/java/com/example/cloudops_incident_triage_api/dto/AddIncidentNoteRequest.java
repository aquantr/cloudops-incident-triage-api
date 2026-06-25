package com.example.cloudops_incident_triage_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AddIncidentNoteRequest(
        @NotBlank
        @Size(max = 120)
        String author,

        @NotBlank
        String note
) {
}