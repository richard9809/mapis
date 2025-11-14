package com.personal.mapis.models.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FireResponseDTO {
    private String clearanceId;
    private String status;      // e.g. PENDING
    private String permitId;
}
