package com.personal.mapis.models.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PermitRequestDTO {
    private String applicantName;
    private String businessName;
    private String siteAddress;
}
