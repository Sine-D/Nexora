package com.admin.modules.task.application.dto;

import com.admin.modules.developer.application.dto.DeveloperSummaryDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SuggestAssigneeResponse {
    private DeveloperSummaryDto recommendedDeveloper;
    private Integer confidence;
    private String explanation;

    private List<String> requiredSkills;
    private List<String> matchedSkills;
    private List<String> missingSkills;

    private ScoreBreakdownDto breakdown;
}
