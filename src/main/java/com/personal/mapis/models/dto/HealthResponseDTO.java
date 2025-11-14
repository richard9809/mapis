package com.personal.mapis.models.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HealthResponseDTO {
    private String approvalId;
    private String status;    // e.g. PENDING
    private String permitId;
}
