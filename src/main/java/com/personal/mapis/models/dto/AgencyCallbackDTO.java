package com.personal.mapis.models.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AgencyCallbackDTO {
    private String referenceId;   // inspectionId OR clearanceId OR approvalId
    private String status;        // SCHEDULED or COMPLETED
    private String result;        // APPROVED, REJECTED, PASS, FAIL, etc.
}
