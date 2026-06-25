package com.example.cloudops_incident_triage_api.repository;

import com.example.cloudops_incident_triage_api.entity.Incident;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IncidentRepository extends JpaRepository<Incident, Long> {
}