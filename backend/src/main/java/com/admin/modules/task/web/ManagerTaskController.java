package com.admin.modules.task.web;

import com.admin.modules.developer.application.dto.DeveloperSummaryDto;
import com.admin.modules.task.application.dto.*;
import com.admin.modules.task.application.service.TaskAssignmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/manager")
@RequiredArgsConstructor
public class ManagerTaskController {

    private final TaskAssignmentService taskAssignmentService;

    @GetMapping("/developers")
    public ResponseEntity<List<DeveloperSummaryDto>> developers() {
        return ResponseEntity.ok(taskAssignmentService.listDevelopers());
    }

    @PostMapping("/tasks/suggest")
    public ResponseEntity<SuggestAssigneeResponse> suggest(@Valid @RequestBody SuggestAssigneeRequest request) {
        return ResponseEntity.ok(taskAssignmentService.suggest(request));
    }

    @PostMapping("/tasks")
    public ResponseEntity<TaskDto> createTask(Authentication authentication, @Valid @RequestBody CreateTaskRequest request) {
        return ResponseEntity.ok(taskAssignmentService.createTask(authentication.getName(), request));
    }

    @GetMapping("/tasks")
    public ResponseEntity<List<TaskDto>> listMyTasks(Authentication authentication) {
        return ResponseEntity.ok(taskAssignmentService.listManagerTasks(authentication.getName()));
    }
}
