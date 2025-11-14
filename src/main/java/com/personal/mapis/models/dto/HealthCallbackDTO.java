package com.personal.mapis.models.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HealthCallbackDTO {
    // matches: { "approvalId": "...", "status": "...", "result": "..." }
    private String approvalId;
    private String status;   // e.g. "COMPLETED"
    private String result;   // "APPROVED" / "APPROVED_WITH_CONDITIONS" / "REJECTED"
}
