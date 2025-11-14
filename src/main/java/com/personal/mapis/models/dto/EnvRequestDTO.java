package com.personal.mapis.models.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnvRequestDTO {

    private String permitId;
    private String businessName;
    private String siteAddress;
    private String requestedDate;

    private String callbackUrl;
}
