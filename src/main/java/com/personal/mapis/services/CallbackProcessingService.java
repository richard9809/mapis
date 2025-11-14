package com.personal.mapis.services;

import com.personal.mapis.models.dto.EnvCallbackDTO;
import com.personal.mapis.models.dto.FireCallbackDTO;
import com.personal.mapis.models.dto.HealthCallbackDTO;
import com.personal.mapis.models.entities.AgencyTask;
import com.personal.mapis.models.entities.Permit;
import com.personal.mapis.models.enums.AgencyType;
import com.personal.mapis.models.enums.PermitStatus;
import com.personal.mapis.models.enums.TaskStatus;
import com.personal.mapis.repositories.AgencyTaskRepository;
import com.personal.mapis.repositories.PermitRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class CallbackProcessingService {

    private final AgencyTaskRepository agencyTaskRepository;
    private final PermitRepository permitRepository;

    @Transactional
    public void handleEnvCallback(EnvCallbackDTO dto) {
        AgencyTask task = agencyTaskRepository
                .findByExternalReferenceIdAndAgencyType(dto.getInspectionId(), AgencyType.ENVIRONMENT)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Environment task not found for inspectionId: " + dto.getInspectionId()
                ));
        updateTaskFromCallback(task, dto.getStatus(), dto.getResult());
        checkAndUpdatePermitStatus(task.getPermit());
    }

    @Transactional
    public void handleFireCallback(FireCallbackDTO dto) {
        AgencyTask task = agencyTaskRepository
                .findByExternalReferenceIdAndAgencyType(dto.getClearanceId(), AgencyType.FIRE)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Fire task not found for clearanceId: " + dto.getClearanceId()
                ));
        updateTaskFromCallback(task, dto.getStatus(), dto.getResult());
        checkAndUpdatePermitStatus(task.getPermit());
    }

    @Transactional
    public void handleHealthCallback(HealthCallbackDTO dto) {
        AgencyTask task = agencyTaskRepository
                .findByExternalReferenceIdAndAgencyType(dto.getApprovalId(), AgencyType.HEALTH)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Health task not found for approvalId: " + dto.getApprovalId()
                ));
        updateTaskFromCallback(task, dto.getStatus(), dto.getResult());
        checkAndUpdatePermitStatus(task.getPermit());
    }

    // --------- helpers ---------
    private void updateTaskFromCallback(AgencyTask task, String status, String result) {
        TaskStatus mappedStatus = mapStatus(status);

        task.setStatus(mappedStatus);
        task.setResult(result);
        task.setLastUpdatedAt(Instant.now());

        agencyTaskRepository.save(task);
    }

    private TaskStatus mapStatus(String status) {
        if (status == null) {
            return TaskStatus.PENDING;
        }
        // expect "PENDING", "SCHEDULED", "COMPLETED"
        return TaskStatus.valueOf(status.toUpperCase());
    }

    private void checkAndUpdatePermitStatus(Permit permit) {
        Long permitId = permit.getId();
        long completedCount = agencyTaskRepository.countByPermitIdAndStatus(permitId, TaskStatus.COMPLETED);
        int totalAgencies = AgencyType.values().length;

        if (completedCount == totalAgencies && permit.getStatus().equals(PermitStatus.PENDING_AGENCIES)) {
            permit.setStatus(PermitStatus.READY_TO_ISSUE);
            permit.setUpdatedAt(Instant.now());
            permitRepository.save(permit);
        }
    }
}
