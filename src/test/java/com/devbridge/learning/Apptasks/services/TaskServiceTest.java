package com.devbridge.learning.Apptasks.services;

import com.devbridge.learning.Apptasks.dtos.TaskDto;
import com.devbridge.learning.Apptasks.exceptions.EntityNotFoundException;
import com.devbridge.learning.Apptasks.models.*;
import com.devbridge.learning.Apptasks.repositories.TaskRepository;
import com.devbridge.learning.Apptasks.repositories.CategoryRepository;
import com.devbridge.learning.Apptasks.repositories.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.OffsetDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllTasks() {
        UUID taskId = UUID.randomUUID();
        Task task = createSampleTask(taskId);
        when(taskRepository.findAll()).thenReturn(Collections.singletonList(task));

        List<TaskDto> result = taskService.getAllTasks();
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(task.getTaskId(), result.get(0).getTaskId());
    }

    @Test
    void testGetTaskById() {
        UUID taskId = UUID.randomUUID();
        Task task = createSampleTask(taskId);
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        TaskDto result = taskService.getTaskById(taskId);
        assertNotNull(result);
        assertEquals(taskId, result.getTaskId());
    }

    @Test
    void testGetTaskById_NotFound() {
        UUID taskId = UUID.randomUUID();
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> taskService.getTaskById(taskId));
    }

    @Test
    void testCreateTask() {
        UUID categoryId = UUID.randomUUID();
        UUID createdById = UUID.randomUUID();
        UUID assignedToId = UUID.randomUUID();

        TaskDto taskDto = TaskDto.builder()
                .title("Title 1")
                .categoryId(categoryId)
                .description("Test Description 1")
                .createdById(createdById)
                .assignedToId(assignedToId)
                .priority(Priority.MEDIUM.toString())
                .status(Status.NOT_STARTED.toString())
                .build();

        Category category = new Category();
        category.setCategoryId(categoryId);
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        when(employeeRepository.findById(createdById)).thenReturn(Optional.of(new Employee()));
        when(employeeRepository.findById(assignedToId)).thenReturn(Optional.of(new Employee()));

        taskService.createTask(taskDto);

        verify(taskRepository, times(1)).create(any(Task.class));
    }

    @Test
    void testCreateTask_CategoryNotFound() {
        UUID categoryId = UUID.randomUUID();
        UUID createdById = UUID.randomUUID();

        TaskDto taskDto = TaskDto.builder()
                .title("Title 1")
                .categoryId(categoryId)
                .description("Test Description 1")
                .createdById(createdById)
                .priority(Priority.MEDIUM.toString())
                .status(Status.NOT_STARTED.toString())
                .build();

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> taskService.createTask(taskDto));
    }

    @Test
    void testUpdateTask() {
        UUID taskId = UUID.randomUUID();
        UUID categoryId = UUID.randomUUID();
        UUID assignedToId = UUID.randomUUID();

        TaskDto taskDto = TaskDto.builder()
                .taskId(taskId)
                .title("Updated Title 2")
                .categoryId(categoryId)
                .assignedToId(assignedToId)
                .priority(Priority.MEDIUM.toString())
                .status(Status.NOT_STARTED.toString())
                .build();

        Task existingTask = createSampleTask(taskId);

        Category category = new Category();
        category.setCategoryId(categoryId);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(existingTask));
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        when(employeeRepository.findById(assignedToId)).thenReturn(Optional.of(new Employee()));

        taskService.updateTask(taskId, taskDto);

        verify(taskRepository, times(1)).update(existingTask);
    }

    @Test
    void testUpdateTask_NotFound() {
        UUID taskId = UUID.randomUUID();
        TaskDto taskDto = TaskDto.builder()
                .priority(Priority.MEDIUM.toString())
                .status(Status.NOT_STARTED.toString())
                .build();

        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> taskService.updateTask(taskId, taskDto));

    }

    @Test
    void testDeleteTask() {
        UUID taskId = UUID.randomUUID();

        taskService.deleteTask(taskId);

        verify(taskRepository, times(1)).delete(taskId);
    }

    private Task createSampleTask(UUID taskId) {
        Task task = new Task();
        task.setTaskId(taskId);
        task.setTitle("Title 1");
        task.setCategory(new Category(UUID.randomUUID(), "Category 1"));
        task.setDescription("Test Description 1");
        task.setCreatedById(UUID.randomUUID());
        task.setAssignedToId(UUID.randomUUID());
        task.setPriority(Priority.MEDIUM);
        task.setStatus(Status.NOT_STARTED);
        task.setCreatedDate(OffsetDateTime.now());
        return task;
    }
}