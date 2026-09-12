package com.admin.modules.task.web;

import com.admin.modules.task.application.dto.TaskDto;
import com.admin.modules.task.application.service.DeveloperTaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/developer/tasks")
@RequiredArgsConstructor
public class DeveloperTaskController {

    private final DeveloperTaskService developerTaskService;

    @GetMapping
    public ResponseEntity<List<TaskDto>> myAssigned(Authentication authentication) {
        return ResponseEntity.ok(developerTaskService.listAssignedToMe(authentication.getName()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> myTask(Authentication authentication, @PathVariable Long id) {
        return ResponseEntity.ok(developerTaskService.getAssignedToMe(authentication.getName(), id));
    }
}
