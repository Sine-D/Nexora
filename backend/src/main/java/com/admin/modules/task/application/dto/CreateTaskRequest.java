package com.admin.modules.task.application.dto;

import com.admin.modules.task.domain.TaskPriority;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateTaskRequest {

    @NotBlank
    private String title;

    private String description;

    private TaskPriority priority;

    private LocalDate dueDate;

    private Integer estimatedPoints;

    private Long assignedToId;
}
