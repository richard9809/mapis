package com.personal.mapis.models.dto;

import com.personal.mapis.models.enums.PermitStatus;
import lombok.*;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PermitDetailsDTO {
    private Long id;
    private String permitNumber;

    private String applicantName;
    private String businessName;
    private String siteAddress;

    private PermitStatus status;

    private Instant createdAt;
    private Instant updatedAt;

    private List<AgencyTaskSummaryDTO> tasks;
}
