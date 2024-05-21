package com.virtualbank.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.virtualbank.model.AccountManager;
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

@ExtendWith(MockitoExtension.class)
public class SavingGoalServiceTest {

    @Mock
    private SavingGoalRepository mockSavingGoalRepository;
    @Mock
    private AccountManager mockAccountManager;

    @InjectMocks
    private SavingGoalService savingGoalService;

    @BeforeEach
    void setup() {
        lenient().when(mockAccountManager.getTotalBalance()).thenReturn(1000.0);
    }


    @Test
    void testAddSavingGoal() {
        SavingGoal newGoal = savingGoalService.addSavingGoal("Child1", "Goal1", 500);
        assertNotNull(newGoal);
        verify(mockSavingGoalRepository).save(any(SavingGoal.class));
    }

    @Test
    void testGetCurrentBalance() {
        when(mockAccountManager.getTotalBalance()).thenReturn(1000.0);
        assertEquals(1000.0, savingGoalService.getCurrentBalance(), "Current balance should be retrieved from the account manager.");
    }

    @Test
    void testGetAllSavingGoals() {
        when(mockSavingGoalRepository.findAll()).thenReturn(Arrays.asList(new SavingGoal()));
        List<SavingGoal> goals = savingGoalService.getAllSavingGoals();
        assertFalse(goals.isEmpty(), "Should retrieve all saving goals.");
    }

    // Additional tests can be implemented in a similar manner.
}
