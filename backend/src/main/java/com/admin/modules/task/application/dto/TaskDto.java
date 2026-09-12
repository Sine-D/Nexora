package com.admin.modules.task.application.dto;

import com.admin.modules.task.domain.TaskPriority;
import com.admin.modules.task.domain.TaskStatus;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskDto {
    private Long id;
    private String title;
    private String description;
    private TaskStatus status;
    private TaskPriority priority;
    private LocalDate dueDate;
    private Integer estimatedPoints;
    private Long createdById;
    private Long assignedToId;
    private String assignedToName;
    private LocalDateTime createdAt;
}
