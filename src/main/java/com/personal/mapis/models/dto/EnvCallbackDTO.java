package com.personal.mapis.models.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnvCallbackDTO {
    // matches: { "inspectionId": "...", "status": "...", "result": "..." }
    private String inspectionId;
    private String status;   // "SCHEDULED" or "COMPLETED"
    private String result;   // e.g. "PASS", "FAIL" (optional for SCHEDULED)
}
