package com.admin.modules.task.application.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScoreBreakdownDto {
    private double skillScore;
    private double workloadScore;
    private double experienceScore;
}
