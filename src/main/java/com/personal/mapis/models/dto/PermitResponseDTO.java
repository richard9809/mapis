package com.personal.mapis.models.dto;

import com.personal.mapis.models.enums.PermitStatus;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PermitResponseDTO {
    private Long id;
    private String permitNumber;

    private String applicantName;
    private String businessName;
    private String siteAddress;

    private PermitStatus status;

    private Instant createdAt;
    private Instant updatedAt;
}
