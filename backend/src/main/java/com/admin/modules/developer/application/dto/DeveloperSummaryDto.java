package com.admin.modules.developer.application.dto;

import com.admin.modules.developer.domain.ExperienceLevel;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeveloperSummaryDto {
    private Long id;
    private String name;
    private String email;
    private ExperienceLevel experienceLevel;
    private Integer capacityPoints;
    private Integer activeWorkloadPoints;
    private List<SkillDto> skills;
}
