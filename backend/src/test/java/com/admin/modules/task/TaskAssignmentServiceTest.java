package com.admin.modules.task;

import com.admin.modules.developer.application.dto.DeveloperSummaryDto;
import com.admin.modules.developer.domain.DeveloperProfile;
import com.admin.modules.developer.domain.DeveloperSkill;
import com.admin.modules.developer.domain.ExperienceLevel;
import com.admin.modules.developer.infrastructure.DeveloperProfileRepository;
import com.admin.modules.developer.infrastructure.DeveloperSkillRepository;
import com.admin.modules.task.application.dto.CreateTaskRequest;
import com.admin.modules.task.application.dto.SuggestAssigneeRequest;
import com.admin.modules.task.application.dto.SuggestAssigneeResponse;
import com.admin.modules.task.application.dto.TaskDto;
import com.admin.modules.task.application.service.TaskAssignmentService;
import com.admin.modules.task.domain.TaskItem;
import com.admin.modules.task.domain.TaskPriority;
import com.admin.modules.task.domain.TaskStatus;
import com.admin.modules.task.infrastructure.TaskRepository;
import com.admin.modules.user.domain.Role;
import com.admin.modules.user.domain.User;
import com.admin.modules.user.infrastructure.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskAssignmentServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private DeveloperProfileRepository profileRepository;

    @Mock
    private DeveloperSkillRepository skillRepository;

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskAssignmentService taskAssignmentService;

    private User devUser;
    private DeveloperProfile devProfile;

    @BeforeEach
    void setUp() {
        devUser = User.builder()
                .id(10L)
                .name("Alex Developer")
                .email("alex@nexora.com")
                .role(Role.DEVELOPER)
                .enabled(true)
                .build();

        devProfile = DeveloperProfile.builder()
                .id(100L)
                .user(devUser)
                .experienceLevel(ExperienceLevel.MID)
                .capacityPoints(20)
                .build();
    }

    @Test
    @DisplayName("Should list active developers with skill summary and workload")
    void testListDevelopers() {
        when(userRepository.findAll()).thenReturn(List.of(devUser));
        when(profileRepository.findByUserId(10L)).thenReturn(Optional.of(devProfile));
        when(taskRepository.sumActivePoints(eq(10L), eq(TaskStatus.DONE))).thenReturn(5);
        when(skillRepository.findByProfileId(100L)).thenReturn(List.of(
                DeveloperSkill.builder().name("React").level(4).build(),
                DeveloperSkill.builder().name("Spring Boot").level(4).build()
        ));

        List<DeveloperSummaryDto> developers = taskAssignmentService.listDevelopers();

        assertNotNull(developers);
        assertEquals(1, developers.size());
        assertEquals("Alex Developer", developers.get(0).getName());
        assertEquals(5, developers.get(0).getActiveWorkloadPoints());
        assertEquals(2, developers.get(0).getSkills().size());
    }

    @Test
    @DisplayName("AI Assignee Engine: Should suggest best matching developer based on skills & workload")
    void testSuggestAssignee() {
        when(userRepository.findAll()).thenReturn(List.of(devUser));
        when(profileRepository.findByUserId(10L)).thenReturn(Optional.of(devProfile));
        when(taskRepository.sumActivePoints(eq(10L), eq(TaskStatus.DONE))).thenReturn(2);
        when(skillRepository.findByProfileId(100L)).thenReturn(List.of(
                DeveloperSkill.builder().name("React").level(5).build()
        ));

        SuggestAssigneeRequest req = SuggestAssigneeRequest.builder()
                .title("Build React UI Dashboard")
                .description("Create interactive components using React and JSX.")
                .estimatedPoints(3)
                .build();

        SuggestAssigneeResponse response = taskAssignmentService.suggest(req);

        assertNotNull(response);
        assertNotNull(response.getRecommendedDeveloper());
        assertEquals("Alex Developer", response.getRecommendedDeveloper().getName());
        assertTrue(response.getConfidence() > 50, "Confidence score should be high for matching skills");
        assertTrue(response.getMatchedSkills().contains("React"));
    }

    @Test
    @DisplayName("Should successfully create a task assigned to a developer")
    void testCreateTask() {
        User manager = User.builder().id(1L).email("manager@nexora.com").name("Manager User").build();
        CreateTaskRequest req = CreateTaskRequest.builder()
                .title("New API Feature")
                .description("Build REST endpoint")
                .priority(TaskPriority.HIGH)
                .estimatedPoints(5)
                .assignedToId(10L)
                .build();

        when(userRepository.findByEmail("manager@nexora.com")).thenReturn(Optional.of(manager));
        when(userRepository.findById(10L)).thenReturn(Optional.of(devUser));
        when(taskRepository.save(any(TaskItem.class))).thenAnswer(i -> {
            TaskItem item = i.getArgument(0);
            item.setId(50L);
            return item;
        });

        TaskDto created = taskAssignmentService.createTask("manager@nexora.com", req);

        assertNotNull(created);
        assertEquals(50L, created.getId());
        assertEquals("New API Feature", created.getTitle());
        assertEquals(TaskPriority.HIGH, created.getPriority());
        assertEquals(10L, created.getAssignedToId());
    }
}
