package com.personal.mapis.models.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EnvResponseDTO {
    private String inspectionId;
    private String status;          // e.g. PENDING
    private String requestedDate;   // optional but matches mock
    private String permitId;
}
