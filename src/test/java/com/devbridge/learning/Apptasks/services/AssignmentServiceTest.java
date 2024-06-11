package com.devbridge.learning.Apptasks.services;

import com.devbridge.learning.Apptasks.dtos.AssignmentDto;
import com.devbridge.learning.Apptasks.exceptions.EntityNotFoundException;
import com.devbridge.learning.Apptasks.models.*;
import com.devbridge.learning.Apptasks.repositories.AssignmentRepository;
import com.devbridge.learning.Apptasks.repositories.CategoryRepository;
import com.devbridge.learning.Apptasks.repositories.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.OffsetDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AssignmentServiceTest {

    @Mock
    private AssignmentRepository assignmentRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private AssignmentService assignmentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllAssignments() {
        UUID assignmentId = UUID.randomUUID();
        Assignment assignment = createSampleAssignment(assignmentId);
        when(assignmentRepository.findAll()).thenReturn(Collections.singletonList(assignment));

        List<AssignmentDto> result = assignmentService.getAllAssignments();
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(assignment.getAssignmentId(), result.get(0).getAssignmentId());
    }

    @Test
    void testGetAssignmentById() {
        UUID assignmentId = UUID.randomUUID();
        Assignment assignment = createSampleAssignment(assignmentId);
        when(assignmentRepository.findById(assignmentId)).thenReturn(Optional.of(assignment));

        AssignmentDto result = assignmentService.getAssignmentById(assignmentId);
        assertNotNull(result);
        assertEquals(assignmentId, result.getAssignmentId());
    }

    @Test
    void testGetAssignmentById_NotFound() {
        UUID assignmentId = UUID.randomUUID();
        when(assignmentRepository.findById(assignmentId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> assignmentService.getAssignmentById(assignmentId));
    }

    @Test
    void testCreateAssignment() {
        UUID categoryId = UUID.randomUUID();
        UUID createdById = UUID.randomUUID();
        UUID assignedToId = UUID.randomUUID();

        AssignmentDto assignmentDto = AssignmentDto.builder()
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

        assignmentService.createAssignment(assignmentDto);

        verify(assignmentRepository, times(1)).create(any(Assignment.class));
    }

    @Test
    void testCreateAssignment_CategoryNotFound() {
        UUID categoryId = UUID.randomUUID();
        UUID createdById = UUID.randomUUID();

        AssignmentDto assignmentDto = AssignmentDto.builder()
                .title("Title 1")
                .categoryId(categoryId)
                .description("Test Description 1")
                .createdById(createdById)
                .priority(Priority.MEDIUM.toString())
                .status(Status.NOT_STARTED.toString())
                .build();

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> assignmentService.createAssignment(assignmentDto));
    }

    @Test
    void testUpdateAssignment() {
        UUID assignmentId = UUID.randomUUID();
        UUID categoryId = UUID.randomUUID();
        UUID assignedToId = UUID.randomUUID();

        AssignmentDto assignmentDto = AssignmentDto.builder()
                .assignmentId(assignmentId)
                .title("Updated Title 2")
                .categoryId(categoryId)
                .assignedToId(assignedToId)
                .priority(Priority.MEDIUM.toString())
                .status(Status.NOT_STARTED.toString())
                .build();

        Assignment existingAssignment = createSampleAssignment(assignmentId);

        Category category = new Category();
        category.setCategoryId(categoryId);

        when(assignmentRepository.findById(assignmentId)).thenReturn(Optional.of(existingAssignment));
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        when(employeeRepository.findById(assignedToId)).thenReturn(Optional.of(new Employee()));

        assignmentService.updateAssignment(assignmentId, assignmentDto);

        verify(assignmentRepository, times(1)).update(existingAssignment);
    }

    @Test
    void testUpdateAssignment_NotFound() {
        UUID assignmentId = UUID.randomUUID();
        AssignmentDto assignmentDto = AssignmentDto.builder()
                .priority(Priority.MEDIUM.toString())
                .status(Status.NOT_STARTED.toString())
                .build();

        when(assignmentRepository.findById(assignmentId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> assignmentService.updateAssignment(assignmentId, assignmentDto));

    }

    @Test
    void testDeleteAssignment() {
        UUID assignmentId = UUID.randomUUID();

        assignmentService.deleteAssignment(assignmentId);

        verify(assignmentRepository, times(1)).delete(assignmentId);
    }

    private Assignment createSampleAssignment(UUID assignmentId) {
        Assignment assignment = new Assignment();
        assignment.setAssignmentId(assignmentId);
        assignment.setTitle("Title 1");
        assignment.setCategory(new Category(UUID.randomUUID(), "Category 1"));
        assignment.setDescription("Test Description 1");
        assignment.setCreatedById(UUID.randomUUID());
        assignment.setAssignedToId(UUID.randomUUID());
        assignment.setPriority(Priority.MEDIUM);
        assignment.setStatus(Status.NOT_STARTED);
        assignment.setCreatedDate(OffsetDateTime.now());
        return assignment;
    }
}