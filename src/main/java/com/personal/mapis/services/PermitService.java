package com.personal.mapis.services;

import com.personal.mapis.models.dto.*;
import com.personal.mapis.models.entities.AgencyTask;
import com.personal.mapis.models.entities.Permit;
import com.personal.mapis.models.enums.AgencyType;
import com.personal.mapis.models.enums.PermitStatus;
import com.personal.mapis.models.enums.TaskStatus;
import com.personal.mapis.repositories.AgencyTaskRepository;
import com.personal.mapis.repositories.PermitRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.camel.ProducerTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PermitService {
    private final PermitRepository permitRepository;
    private final AgencyTaskRepository agencyTaskRepository;
    private final ProducerTemplate producerTemplate;

    @Transactional
    public PermitResponseDTO createPermit(PermitRequestDTO request) {
        Instant now = Instant.now();

        // 1) Build Permit entity
        Permit permit = Permit.builder()
                .permitNumber(generatePermitNumber())
                .applicantName(request.getApplicantName())
                .businessName(request.getBusinessName())
                .siteAddress(request.getSiteAddress())
                .status(PermitStatus.PENDING_AGENCIES)
                .createdAt(now)
                .updatedAt(now)
                .build();

        Permit savedPermit = permitRepository.save(permit);

        // 2) Call ENV / FIRE / HEALTH via Camel and create AgencyTasks
        createEnvTask(savedPermit);
        createFireTask(savedPermit);
        createHealthTask(savedPermit);

        return toResponseDTO(savedPermit);
    }

    @Transactional
    public PermitResponseDTO getPermitByNumber(String permitNumber) {
        Permit permit = permitRepository.findByPermitNumber(permitNumber)
                .orElseThrow(() -> new IllegalArgumentException("Permit number not found: " +  permitNumber));
        return toResponseDTO(permit);
    }

    @Transactional
    public PermitDetailsDTO getPermitDetails(String permitNumber) {
        Permit permit = permitRepository.findByPermitNumber(permitNumber)
                .orElseThrow(() -> new IllegalArgumentException("Permit number not found: " +  permitNumber));

        var tasks = agencyTaskRepository.findByPermitId(permit.getId());

        return toPermitDetailsDTO(permit, tasks);
    }

    // -------- agency task creation helpers --------
    private void createEnvTask(Permit permit) {
        // Build request body for ENV
        EnvRequestDTO req = EnvRequestDTO.builder()
                .permitId(permit.getPermitNumber())
                .businessName(permit.getBusinessName())
                .siteAddress(permit.getSiteAddress())
                .requestedDate("2025-11-25")
                .callbackUrl("http://localhost:8080/callbacks/env/inspections")
                .build();

        EnvResponseDTO resp = producerTemplate.requestBody(
                "direct:env-create-inspection",
                req,
                EnvResponseDTO.class
        );

        AgencyTask task = AgencyTask.builder()
                .permit(permit)
                .agencyType(AgencyType.ENVIRONMENT)
                .status(TaskStatus.valueOf(resp.getStatus().toUpperCase())) // expect "PENDING"
                .externalReferenceId(resp.getInspectionId())
                .createdAt(Instant.now())
                .lastUpdatedAt(Instant.now())
                .build();

        agencyTaskRepository.save(task);
    }

    private void createFireTask(Permit permit) {
        FireRequestDTO request = FireRequestDTO.builder()
                .permitId(permit.getPermitNumber())
                .businessName(permit.getBusinessName())
                .siteAddress(permit.getSiteAddress())
                .callbackUrl("http://localhost:8080/callbacks/fire/clearances")
                .build();

        FireResponseDTO resp = producerTemplate.requestBody(
                "direct:fire-create-clearance",
                request,
                FireResponseDTO.class
        );

        AgencyTask task = AgencyTask.builder()
                .permit(permit)
                .agencyType(AgencyType.FIRE)
                .status(TaskStatus.valueOf(resp.getStatus().toUpperCase()))
                .externalReferenceId(resp.getClearanceId())
                .createdAt(Instant.now())
                .lastUpdatedAt(Instant.now())
                .build();

        agencyTaskRepository.save(task);
    }

    private void createHealthTask(Permit permit) {
        HealthRequestDTO req = HealthRequestDTO.builder()
                .permitId(permit.getPermitNumber())
                .businessName(permit.getBusinessName())
                .siteAddress(permit.getSiteAddress())
                .callbackUrl("http://localhost:8080/callbacks/health/approvals")
                .build();

        HealthResponseDTO resp = producerTemplate.requestBody(
                "direct:health-create-approval",
                req,
                HealthResponseDTO.class
        );

        AgencyTask task = AgencyTask.builder()
                .permit(permit)
                .agencyType(AgencyType.HEALTH)
                .status(TaskStatus.valueOf(resp.getStatus().toUpperCase()))
                .externalReferenceId(resp.getApprovalId())
                .createdAt(Instant.now())
                .lastUpdatedAt(Instant.now())
                .build();

        agencyTaskRepository.save(task);
    }

    // helpers
    private String generatePermitNumber() {
        String suffix = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        return "PERMIT-" + suffix;
    }

    // ------------- mapping helper ------------
    private PermitResponseDTO toResponseDTO(Permit permit) {
        return PermitResponseDTO.builder()
                .id(permit.getId())
                .permitNumber(permit.getPermitNumber())
                .applicantName(permit.getApplicantName())
                .businessName(permit.getBusinessName())
                .siteAddress(permit.getSiteAddress())
                .status(permit.getStatus())
                .createdAt(permit.getCreatedAt())
                .updatedAt(permit.getUpdatedAt())
                .build();
    }

    private PermitDetailsDTO toPermitDetailsDTO(Permit permit, List<AgencyTask> tasks) {
        var taskDtos = tasks.stream()
                .map(task -> AgencyTaskSummaryDTO.builder()
                        .agencyType(task.getAgencyType())
                        .status(task.getStatus())
                        .externalReferenceId(task.getExternalReferenceId())
                        .result(task.getResult())
                        .build()
                )
                .toList();

        return PermitDetailsDTO.builder()
                .id(permit.getId())
                .permitNumber(permit.getPermitNumber())
                .applicantName(permit.getApplicantName())
                .businessName(permit.getBusinessName())
                .siteAddress(permit.getSiteAddress())
                .status(permit.getStatus())
                .createdAt(permit.getCreatedAt())
                .updatedAt(permit.getUpdatedAt())
                .tasks(taskDtos)
                .build();
    }
}
