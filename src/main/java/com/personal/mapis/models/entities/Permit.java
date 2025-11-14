package com.personal.mapis.models.entities;

import com.personal.mapis.models.enums.PermitStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "permits")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Permit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // External-facing ID (can be shown to applicants)
    @Column(nullable = false, unique = true)
    private String permitNumber;

    @Column(nullable = false)
    private String applicantName;

    @Column(nullable = false)
    private String businessName;

    @Column(nullable = false)
    private String siteAddress;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PermitStatus status;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant updatedAt;

    @OneToMany(mappedBy = "permit", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AgencyTask> agencyTasks;
}
