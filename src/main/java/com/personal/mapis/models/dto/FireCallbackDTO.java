package com.personal.mapis.models.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FireCallbackDTO {
    // matches: { "clearanceId": "...", "status": "...", "result": "..." }
    private String clearanceId;
    private String status;   // e.g. "COMPLETED"
    private String result;   // "APPROVED" / "REJECTED"
}
