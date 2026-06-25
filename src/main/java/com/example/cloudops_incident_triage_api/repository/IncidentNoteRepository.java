package com.example.cloudops_incident_triage_api.repository;

import com.example.cloudops_incident_triage_api.entity.IncidentNote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IncidentNoteRepository extends JpaRepository<IncidentNote, Long> {

    List<IncidentNote> findByIncidentIdOrderByCreatedAtAsc(Long incidentId);
}