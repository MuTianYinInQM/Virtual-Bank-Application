package com.virtualbank.service;

import com.virtualbank.model.SavingGoal;
import com.virtualbank.repository.SavingGoalRepository;
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
public class SavingGoalServiceTest {

    @Mock
    private SavingGoalRepository savingGoalRepository;

    @InjectMocks
    private SavingGoalService savingGoalService;

    private SavingGoal goal;

    @BeforeEach
    void setUp() {
        goal = new SavingGoal("1", "1001", 500.0, 250.0, "Bike");
    }

    @Test
    void addSavingGoalTest() {
        doNothing().when(savingGoalRepository).save(any(SavingGoal.class));
        SavingGoal createdGoal = savingGoalService.addSavingGoal("1001", "Bike", 500.0);
        assertNotNull(createdGoal);
        assertEquals("Bike", createdGoal.getName());
        verify(savingGoalRepository).save(any(SavingGoal.class));
    }

    @Test
    void contributeToGoalTest() {
        when(savingGoalRepository.findById("1")).thenReturn(Optional.of(goal));
        doNothing().when(savingGoalRepository).save(any(SavingGoal.class));
        savingGoalService.contributeToGoal("1", 50.0);
        assertEquals(300.0, goal.getCurrentAmount()); // 确保currentAmount被更新
        verify(savingGoalRepository).save(goal);
    }

    @Test
    void getSavingGoalFeedbackTest() {
        assertEquals("You've saved half of your goal! Well done!", savingGoalService.getSavingGoalFeedback(goal));
        goal.setCurrentAmount(375.0);
        assertEquals("You're three-quarters there! Keep it up!", savingGoalService.getSavingGoalFeedback(goal));
        goal.setCurrentAmount(450.0);
        assertEquals("Great job! You're 90% of the way to your goal!", savingGoalService.getSavingGoalFeedback(goal));
        goal.setCurrentAmount(500.0);
        assertEquals("Congratulations! You've reached your savings goal!", savingGoalService.getSavingGoalFeedback(goal));
    }

    @Test
    void getAllSavingGoalsTest() {
        when(savingGoalRepository.findAll()).thenReturn(Arrays.asList(goal));
        List<SavingGoal> goals = savingGoalService.getAllSavingGoals();
        assertFalse(goals.isEmpty());
        assertEquals(1, goals.size());
        assertEquals("Bike", goals.get(0).getName());
        verify(savingGoalRepository).findAll();
    }

    @Test
    void deleteSavingGoalTest() {
        doNothing().when(savingGoalRepository).deleteById("1");
        savingGoalService.deleteSavingGoal("1");
        verify(savingGoalRepository).deleteById("1");
    }

    @Test
    void updateSavingGoalTest() {
        when(savingGoalRepository.findById("1")).thenReturn(Optional.of(goal));
        doNothing().when(savingGoalRepository).save(any(SavingGoal.class));
        savingGoalService.updateSavingGoal("1", 600.0);
        assertEquals(600.0, goal.getTargetAmount());
        verify(savingGoalRepository).save(goal);
    }
}
