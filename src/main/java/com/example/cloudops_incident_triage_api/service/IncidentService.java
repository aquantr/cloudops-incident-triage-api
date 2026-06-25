package com.example.cloudops_incident_triage_api.service;

import com.example.cloudops_incident_triage_api.dto.*;
import com.example.cloudops_incident_triage_api.entity.Incident;
import com.example.cloudops_incident_triage_api.entity.IncidentNote;
import com.example.cloudops_incident_triage_api.entity.IncidentStatus;
import com.example.cloudops_incident_triage_api.exception.ResourceNotFoundException;
import com.example.cloudops_incident_triage_api.repository.IncidentNoteRepository;
import com.example.cloudops_incident_triage_api.repository.IncidentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IncidentService {

    private final IncidentRepository incidentRepository;
    private final IncidentNoteRepository incidentNoteRepository;

    @Transactional
    public IncidentResponse createIncident(CreateIncidentRequest request) {
        Incident incident = new Incident();
        incident.setTitle(request.title());
        incident.setSummary(request.summary());
        incident.setSeverity(request.severity());
        incident.setStatus(IncidentStatus.OPEN);
        incident.setAffectedService(request.affectedService());

        Incident saved = incidentRepository.save(incident);
        return toIncidentResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<IncidentResponse> getIncidents() {
        return incidentRepository.findAll()
                .stream()
                .map(this::toIncidentResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public IncidentResponse getIncident(Long id) {
        Incident incident = findIncidentById(id);
        return toIncidentResponse(incident);
    }

    @Transactional
    public IncidentResponse updateIncidentStatus(Long id, UpdateIncidentStatusRequest request) {
        Incident incident = findIncidentById(id);
        incident.setStatus(request.status());

        Incident saved = incidentRepository.save(incident);
        return toIncidentResponse(saved);
    }

    @Transactional
    public IncidentNoteResponse addNote(Long incidentId, AddIncidentNoteRequest request) {
        Incident incident = findIncidentById(incidentId);

        IncidentNote note = new IncidentNote();
        note.setIncident(incident);
        note.setAuthor(request.author());
        note.setNote(request.note());

        IncidentNote saved = incidentNoteRepository.save(note);
        return toNoteResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<IncidentNoteResponse> getNotes(Long incidentId) {
        if (!incidentRepository.existsById(incidentId)) {
            throw new ResourceNotFoundException("Incident not found with id: " + incidentId);
        }

        return incidentNoteRepository.findByIncidentIdOrderByCreatedAtAsc(incidentId)
                .stream()
                .map(this::toNoteResponse)
                .toList();
    }

    private Incident findIncidentById(Long id) {
        return incidentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Incident not found with id: " + id));
    }

    private IncidentResponse toIncidentResponse(Incident incident) {
        return new IncidentResponse(
                incident.getId(),
                incident.getTitle(),
                incident.getSummary(),
                incident.getSeverity(),
                incident.getStatus(),
                incident.getAffectedService(),
                incident.getCreatedAt(),
                incident.getUpdatedAt()
        );
    }

    private IncidentNoteResponse toNoteResponse(IncidentNote note) {
        return new IncidentNoteResponse(
                note.getId(),
                note.getIncident().getId(),
                note.getAuthor(),
                note.getNote(),
                note.getCreatedAt()
        );
    }
}