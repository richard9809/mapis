package com.personal.mapis.models.dto;

import com.personal.mapis.models.enums.AgencyType;
import com.personal.mapis.models.enums.TaskStatus;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AgencyTaskSummaryDTO {

    private AgencyType agencyType;
    private TaskStatus status;
    private String externalReferenceId;
    private String result;
}
