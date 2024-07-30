package com.devbridge.learning.Apptasks.services;

import com.devbridge.learning.Apptasks.dtos.TaskDetailedDto;
import com.devbridge.learning.Apptasks.dtos.TaskDto;
import com.devbridge.learning.Apptasks.exceptions.EntityNotFoundException;
import com.devbridge.learning.Apptasks.mappers.TaskMapper;
import com.devbridge.learning.Apptasks.models.*;
import com.devbridge.learning.Apptasks.repositories.ProjectRepository;
import com.devbridge.learning.Apptasks.repositories.TaskRepository;
import com.devbridge.learning.Apptasks.repositories.CategoryRepository;
import com.devbridge.learning.Apptasks.repositories.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final CategoryRepository categoryRepository;
    private final EmployeeRepository employeeRepository;
    private final ProjectRepository projectRepository;
    private final static String TASK_NOT_FOUND = "Task with given id not found";
    private static final String CATEGORY_NOT_FOUND = "Category with given id not found";
    private static final String EMPLOYEE_NOT_FOUND = "Employee with given id not found";
    private static final String PROJECT_NOT_FOUND = "Project with given id not found";

    public List<TaskDto> getAllTasks() {
        return taskRepository.findAll().stream()
                .map(TaskMapper::toDto)
                .collect(Collectors.toList());
    }

    public TaskDto getTaskById(UUID taskId) {
        Task existingTask = validateTaskId(taskId);
        return TaskMapper.toDto(existingTask);
    }

    public TaskDetailedDto getTaskDetailedById(UUID taskId) {
        Task existingTask = validateTaskId(taskId);
        return toDetailedDtoInfo(existingTask);
    }

    public List<TaskDto> getTasksByProjectId(UUID projectId) {
        validateProjectId(projectId);
        return taskRepository.findTasksByProjectId(projectId).stream()
                .map(TaskMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<TaskDetailedDto> getTasksDetailedByProjectId(UUID projectId) {
        validateProjectId(projectId);
        return taskRepository.findTasksByProjectId(projectId).stream()
                .map(this::toDetailedDtoInfo)
                .collect(Collectors.toList());
    }

    public List<TaskDto> getTasksByEmployeeId(UUID employeeId) {
        validateEmployeeId(employeeId);
        return taskRepository.findTasksByEmployeeId(employeeId).stream()
                .map(TaskMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<TaskDto> getTasksByAssignedToId(UUID employeeId) {
        validateEmployeeId(employeeId);
        return taskRepository.findTasksByAssignedToId(employeeId).stream()
                .map(TaskMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<TaskDto> getTasksByCreatedById(UUID employeeId) {
        validateEmployeeId(employeeId);
        return taskRepository.findTasksByCreatedById(employeeId).stream()
                .map(TaskMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<TaskDetailedDto> getTasksDetailedByEmployeeId(UUID employeeId) {
        validateEmployeeId(employeeId);
        return taskRepository.findTasksByEmployeeId(employeeId).stream()
                .map(this::toDetailedDtoInfo)
                .collect(Collectors.toList());
    }

    public TaskDto createTask(TaskDto taskDto) {
        Category category = getCategory(taskDto.getCategoryId());

        validateEmployeeId(taskDto.getCreatedById());
        if (taskDto.getAssignedToId() != null) {
            validateEmployeeId(taskDto.getAssignedToId());
        }
        initializeTaskDefaults(taskDto);
        taskDto.setTaskId(UUID.randomUUID());
        taskDto.setCreatedDate(OffsetDateTime.now(ZoneOffset.UTC));

        Task task = TaskMapper.toEntity(taskDto, category);
        setTaskDatesOnCreate(task);

        taskRepository.create(task);
        return TaskMapper.toDto(task);
    }

    public TaskDto updateTask(UUID taskId, TaskDto taskDto) {
        Task existingTask = validateTaskId(taskId);
        updateExistingTask(existingTask, taskDto);
        taskRepository.update(existingTask);

        return TaskMapper.toDto(existingTask);
    }

    public TaskDto assignTaskToProject(UUID taskId, UUID projectId) {
        validateProjectId(projectId);
        Task existingTask = validateTaskId(taskId);

        existingTask.setProjectId(projectId);

        taskRepository.updateProjectId(existingTask);
        return TaskMapper.toDto(existingTask);
    }

    public void deleteTask(UUID taskId) {
        taskRepository.delete(taskId);
    }

    private void setTaskDatesOnCreate(Task task) {
        if (task.getAssignedToId() != null) {
            task.setAssignedDate(OffsetDateTime.now(ZoneOffset.UTC));
        } else {
            task.setAssignedDate(null);
        }

        if (task.getStatus() == Status.DONE) {
            task.setDoneDate(OffsetDateTime.now(ZoneOffset.UTC));
        } else {
            task.setDoneDate(null);
        }
    }

    private void setTaskDatesOnUpdate(Task existingTask, TaskDto taskDto) {
        UUID existingId = existingTask.getAssignedToId();
        UUID newId = taskDto.getAssignedToId();

        if (newId != null) {
            if (!newId.equals(existingId)) {
                existingTask.setAssignedDate(OffsetDateTime.now(ZoneOffset.UTC));
            }
            existingTask.setUnassignedDate(null);
        }

        if (newId == null && existingId != null) {
            existingTask.setAssignedDate(null);
            existingTask.setUnassignedDate(OffsetDateTime.now(ZoneOffset.UTC));
        }

        if (existingTask.getStatus() == Status.DONE) {
            existingTask.setDoneDate(OffsetDateTime.now(ZoneOffset.UTC));
        } else {
            existingTask.setDoneDate(null);
        }
    }

    private void validateEmployeeId(UUID employeeId) {
        if (employeeRepository.findById(employeeId).isEmpty()) {
            throw new EntityNotFoundException(EMPLOYEE_NOT_FOUND);
        }
    }

    private Project validateProjectId(UUID projectId) {
        return projectRepository.findById(projectId)
                .orElseThrow( () -> new EntityNotFoundException(PROJECT_NOT_FOUND) );
    }

    private Task validateTaskId(UUID taskId) {
        return taskRepository.findByTaskId(taskId)
                .orElseThrow( () -> new EntityNotFoundException(TASK_NOT_FOUND) );
    }

    private void updateExistingTask(Task existingTask, TaskDto taskDto) {
        if (taskDto.getAssignedToId() != null) {
            validateEmployeeId(taskDto.getAssignedToId());
        }

        if (taskDto.getCategoryId() != null) {
            Category category = categoryRepository.findById(taskDto.getCategoryId())
                    .orElseThrow(() -> new EntityNotFoundException(CATEGORY_NOT_FOUND));
            existingTask.setCategory(category);
        } else {
            existingTask.setCategory(null);
        }

        if (taskDto.getStatus() != null) {
            existingTask.setStatus(TaskMapper.toStatus(taskDto.getStatus()));
        }

        if (taskDto.getPriority() != null) {
            existingTask.setPriority(TaskMapper.toPriority(taskDto.getPriority()));
        }

        setTaskDatesOnUpdate(existingTask, taskDto);

        existingTask.setTitle(taskDto.getTitle());
        existingTask.setDescription(taskDto.getDescription());
        existingTask.setAssignedToId(taskDto.getAssignedToId());
        existingTask.setProjectId(taskDto.getProjectId());
    }

    private TaskDetailedDto toDetailedDtoInfo(Task task) {
        Employee createdByEmployee = employeeRepository.findById(task.getCreatedById())
                .orElseThrow(() -> new EntityNotFoundException(EMPLOYEE_NOT_FOUND));

        Employee assignedToEmployee = task.getAssignedToId() != null
                ? employeeRepository.findById(task.getAssignedToId()).orElse(null)
                : null;

        Project project = task.getProjectId() != null
                ? projectRepository.findById(task.getProjectId()).orElse(null)
                : null;

        return TaskMapper.toDetailedDto(
                task,
                createdByEmployee.getFirstName(),
                createdByEmployee.getLastName(),
                assignedToEmployee != null ? assignedToEmployee.getFirstName() : null,
                assignedToEmployee != null ? assignedToEmployee.getLastName() : null,
                project != null ? project.getProjectName() : null
        );
    }

    private void initializeTaskDefaults(TaskDto taskDto) {
        if (taskDto.getStatus() == null) {
            taskDto.setStatus(Status.NOT_STARTED.toString());
        }
        if (taskDto.getPriority() == null) {
            taskDto.setPriority(Priority.MEDIUM.toString());
        }
    }

    private Category getCategory(UUID categoryId) {
        if (categoryId == null) return null;
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException(CATEGORY_NOT_FOUND));
    }

}