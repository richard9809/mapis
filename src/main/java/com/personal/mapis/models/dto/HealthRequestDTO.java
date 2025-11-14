package com.personal.mapis.models.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HealthRequestDTO {
    private String permitId;
    private String businessName;
    private String siteAddress;

    private String callbackUrl;
}
