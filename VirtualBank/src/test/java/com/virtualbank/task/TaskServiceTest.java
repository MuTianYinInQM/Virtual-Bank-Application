package com.virtualbank.task;

import com.virtualbank.model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    private Task task;

    @BeforeEach
    void setUp() {
        task = new Task("1", "Homework", "Math homework", 10.0, "2024-04-01 10:00", "2024-04-02 10:00", "created");
    }

    @Test
    void testCreateTask() {
        taskService.createTask("Homework", "Math homework", 10.0, "2024-04-01 10:00", "2024-04-02 10:00");
        verify(taskRepository).add(any(Task.class));
    }

    @Test
    void testPublishTask() {
        when(taskRepository.findById("1")).thenReturn(Optional.of(task));
        taskService.publishTask("1");
        assertEquals("published", task.getStatus());
        verify(taskRepository).update(task);
    }

    @Test
    void testRetractTask() {
        task.setStatus("published");
        when(taskRepository.findById("1")).thenReturn(Optional.of(task));
        taskService.retractTask("1");
        assertEquals("retracted", task.getStatus());
        verify(taskRepository).update(task);
    }

    @Test
    void testGetAllTasks() {
        when(taskRepository.findAll()).thenReturn(Arrays.asList(task));
        List<Task> tasks = taskService.getAllTasks();
        assertFalse(tasks.isEmpty());
        assertEquals(1, tasks.size());
        verify(taskRepository).findAll();
    }

    @Test
    void testGetTaskById() {
        when(taskRepository.findById("1")).thenReturn(Optional.of(task));
        Task foundTask = taskService.getTaskById("1");
        assertNotNull(foundTask);
        assertEquals("Homework", foundTask.getName());
        verify(taskRepository).findById("1");
    }

    @Test
    void testUpdateTask() {
        when(taskRepository.findById("1")).thenReturn(Optional.of(task));
        taskService.updateTask("1", "Homework Updated", "Math homework updated", 15.0, "2024-04-01 11:00", "2024-04-03 11:00");
        assertEquals("Homework Updated", task.getName());
        assertEquals("Math homework updated", task.getDescription());
        assertEquals(15.0, task.getReward());
        assertEquals("2024-04-01 11:00", task.getStartDate());
        assertEquals("2024-04-03 11:00", task.getEndDate());
        verify(taskRepository).update(task);
    }

    @Test
    void testDeleteTask() {
        doNothing().when(taskRepository).deleteById("1");
        taskService.deleteTask("1");
        verify(taskRepository).deleteById("1");
    }

        @Test
    void testAcceptTask() {
        when(taskRepository.findById("1")).thenReturn(Optional.of(task));
        taskService.acceptTask("1");
        assertEquals("accepted", task.getStatus());
        verify(taskRepository).update(task);
    }

    @Test
    void testSubmitTask() {
        task.setStatus("accepted");  // Set initial status to accepted to allow submission
        when(taskRepository.findById("1")).thenReturn(Optional.of(task));
        taskService.submitTask("1");
        assertEquals("submitted", task.getStatus());
        verify(taskRepository).update(task);
    }

    @Test
    void testGetTasksByStatus() {
        when(taskRepository.findAll()).thenReturn(Arrays.asList(task));
        List<Task> result = taskService.getTasksByStatus("created");
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("created", result.get(0).getStatus());
        verify(taskRepository).findAll();
    }

    @Test
    void testPublishTaskWithNonExistentId() {
        when(taskRepository.findById("non-existent-id")).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            taskService.publishTask("non-existent-id");
        });

        assertEquals("Task with id non-existent-id not found", exception.getMessage());
    }
}
