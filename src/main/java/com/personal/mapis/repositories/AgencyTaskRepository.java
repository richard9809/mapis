package com.personal.mapis.repositories;

import com.personal.mapis.models.entities.AgencyTask;
import com.personal.mapis.models.enums.AgencyType;
import com.personal.mapis.models.enums.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AgencyTaskRepository extends JpaRepository<AgencyTask, Long> {
    List<AgencyTask> findByPermitId(Long permitId);
    Optional<AgencyTask> findByPermitIdAndAgencyType(Long permitId, AgencyType agencyType);
    long countByPermitIdAndStatus(Long permitId, TaskStatus status);
    Optional<AgencyTask> findByExternalReferenceIdAndAgencyType(String externalReferenceId, AgencyType agencyType);
}
