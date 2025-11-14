package com.personal.mapis.models.entities;

import com.personal.mapis.models.enums.AgencyType;
import com.personal.mapis.models.enums.TaskStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "agency_tasks")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AgencyTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Which permit this agency task belongs to
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "permit_id", nullable = false)
    private Permit permit;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AgencyType agencyType;   // ENV, FIRE, HEALTH

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskStatus status;       // PENDING, SCHEDULED, COMPLETED

    // ID returned by the agency (inspectionId, clearanceId, approvalId)
    @Column(nullable = false)
    private String externalReferenceId;

    // Optional result: PASS / FAIL / APPROVED / REJECTED / WITH_CONDITIONS
    private String result;

    private Instant lastUpdatedAt;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;
}
