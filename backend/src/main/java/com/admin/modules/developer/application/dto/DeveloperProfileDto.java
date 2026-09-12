package com.admin.modules.developer.application.dto;

import com.admin.modules.developer.domain.ExperienceLevel;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeveloperProfileDto {
    private Long userId;
    private String name;
    private String email;
    private String phone;
    private String location;
    private String bio;
    private ExperienceLevel experienceLevel;
    private Integer capacityPoints;
    private List<SkillDto> skills;
}
