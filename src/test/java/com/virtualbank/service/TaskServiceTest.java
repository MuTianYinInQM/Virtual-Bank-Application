package com.virtualbank.service;

import com.virtualbank.model.Task;
import com.virtualbank.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskServiceTest {

    @Mock
    private TaskRepository mockTaskRepository;

    @InjectMocks
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateTask() {
        Task task = new Task(UUID.randomUUID().toString(), "Homework", "Complete your homework", 10.0, "Child1", "2021-01-01", "2021-01-02", "not_accepted");
        doNothing().when(mockTaskRepository).add(task);

        taskService.createTask("Homework", "Complete your homework", 10.0, "Child1", "2021-01-01", "2021-01-02");

        verify(mockTaskRepository).add(any(Task.class));
    }

    @Test
    void testPublishTask() {
        Task task = new Task(UUID.randomUUID().toString(), "Task1", "Description", 10.0, "Child1", "2021-01-01", "2021-01-02", "created");
        when(mockTaskRepository.findById(task.getId())).thenReturn(Optional.of(task));

        taskService.publishTask(task.getId());

        assertEquals("published", task.getStatus());
        verify(mockTaskRepository).update(task);
    }

    @Test
    void testRetractTask() {
        Task task = new Task(UUID.randomUUID().toString(), "Task2", "Description", 20.0, "Child2", "2021-01-01", "2021-01-02", "published");
        when(mockTaskRepository.findById(task.getId())).thenReturn(Optional.of(task));

        taskService.retractTask(task.getId());

        assertEquals("retracted", task.getStatus());
        verify(mockTaskRepository).update(task);
    }

    @Test
    void testGetAllTasks() {
        List<Task> tasks = Arrays.asList(new Task(UUID.randomUUID().toString(), "Task3", "Description", 30.0, "Child3", "2021-01-01", "2021-01-02", "created"));
        when(mockTaskRepository.findAll()).thenReturn(tasks);

        List<Task> retrievedTasks = taskService.getAllTasks();

        assertFalse(retrievedTasks.isEmpty());
        verify(mockTaskRepository).findAll();
    }

    @Test
    void testUpdateTask() {
        Task task = new Task(UUID.randomUUID().toString(), "Task4", "Description", 40.0, "Child4", "2021-01-01", "2021-01-02", "created");
        when(mockTaskRepository.findById(task.getId())).thenReturn(Optional.of(task));

        taskService.updateTask(task.getId(), "Updated Task", "Updated Description", 50.0, "Child4", "2021-01-01", "2021-01-02");

        assertEquals(50.0, task.getReward());
        verify(mockTaskRepository).update(task);
    }

    @Test
    void testDeleteTask() {
        Task task = new Task(UUID.randomUUID().toString(), "Task5", "Description", 50.0, "Child5", "2021-01-01", "2021-01-02", "created");
        doNothing().when(mockTaskRepository).deleteById(task.getId());

        taskService.deleteTask(task.getId());

        verify(mockTaskRepository).deleteById(task.getId());
    }

    @Test
    void testAcceptTask() {
        Task task = new Task(UUID.randomUUID().toString(), "Task6", "Description", 60.0, "Child6", "2021-01-01", "2021-01-02", "not_accepted");
        when(mockTaskRepository.findById(task.getId())).thenReturn(Optional.of(task));

        taskService.acceptTask(task.getId());

        assertEquals("ongoing", task.getStatus());
        verify(mockTaskRepository).update(task);
    }
}
