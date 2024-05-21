package com.virtualbank.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TaskTest {
    private Task task;

    @BeforeEach
    void setUp() {
        task = new Task("1", "Homework", "Complete your homework on time.", 10.0, "John",
                "2024-04-01 10:00", "2024-04-02 10:00", "created");
    }

    @Test
    void testConstructorAndGetter() {
        assertEquals("1", task.getId());
        assertEquals("Homework", task.getTaskName());
        assertEquals("Complete your homework on time.", task.getDescription());
        assertEquals(10.0, task.getReward());
        assertEquals("John", task.getChildName());
        assertEquals("2024-04-01 10:00", task.getStartDate());
        assertEquals("2024-04-02 10:00", task.getEndDate());
        assertEquals("created", task.getStatus());
    }

    @Test
    void testSetter() {
        task.setId("2");
        assertEquals("2", task.getId());

        task.setTaskName("Play");
        assertEquals("Play", task.getTaskName());

        task.setDescription("Play outside.");
        assertEquals("Play outside.", task.getDescription());

        task.setReward(20.0);
        assertEquals(20.0, task.getReward());

        task.setChildName("Alice");
        assertEquals("Alice", task.getChildName());

        task.setStartDate("2024-05-01 12:00");
        assertEquals("2024-05-01 12:00", task.getStartDate());

        task.setEndDate("2024-05-02 12:00");
        assertEquals("2024-05-02 12:00", task.getEndDate());

        task.setStatus("published");
        assertEquals("published", task.getStatus());
    }

    @Test
    void testToString() {
        String expected = "Task{" +
                "id='1'" +
                ", taskName='Homework'" +
                ", description='Complete your homework on time.'" +
                ", reward=10.0" +
                ", childName='John'" +
                ", startDate=2024-04-01 10:00" +
                ", endDate=2024-04-02 10:00" +
                ", status='created'" +
                '}';
        assertEquals(expected, task.toString());
    }
}
