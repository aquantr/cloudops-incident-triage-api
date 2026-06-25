package com.example.cloudops_incident_triage_api.controller;

import com.example.cloudops_incident_triage_api.dto.*;
import com.example.cloudops_incident_triage_api.service.IncidentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/incidents")
@RequiredArgsConstructor
public class IncidentController {

    private final IncidentService incidentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public IncidentResponse createIncident(@Valid @RequestBody CreateIncidentRequest request) {
        return incidentService.createIncident(request);
    }

    @GetMapping
    public List<IncidentResponse> getIncidents() {
        return incidentService.getIncidents();
    }

    @GetMapping("/{id}")
    public IncidentResponse getIncident(@PathVariable Long id) {
        return incidentService.getIncident(id);
    }

    @PatchMapping("/{id}/status")
    public IncidentResponse updateIncidentStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateIncidentStatusRequest request
    ) {
        return incidentService.updateIncidentStatus(id, request);
    }

    @PostMapping("/{id}/notes")
    @ResponseStatus(HttpStatus.CREATED)
    public IncidentNoteResponse addNote(
            @PathVariable Long id,
            @Valid @RequestBody AddIncidentNoteRequest request
    ) {
        return incidentService.addNote(id, request);
    }

    @GetMapping("/{id}/notes")
    public List<IncidentNoteResponse> getNotes(@PathVariable Long id) {
        return incidentService.getNotes(id);
    }
}