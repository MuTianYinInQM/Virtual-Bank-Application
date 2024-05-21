package com.virtualbank.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SavingGoalTest {
    private SavingGoal savingGoal;

    @BeforeEach
    void setUp() {
        savingGoal = new SavingGoal("goal1", "child1", 1000.0, 500.0, "Vacation");
    }

    @Test
    void testConstructorAndGetter() {
        assertEquals("goal1", savingGoal.getGoalId());
        assertEquals("child1", savingGoal.getChildName());
        assertEquals(1000.0, savingGoal.getTargetAmount());
        assertEquals(500.0, savingGoal.getCurrentAmount());
        assertEquals("Vacation", savingGoal.getGoalName());
    }

    @Test
    void testSetter() {
        savingGoal.setGoalId("goal2");
        assertEquals("goal2", savingGoal.getGoalId());


        savingGoal.setTargetAmount(1500.0);
        assertEquals(1500.0, savingGoal.getTargetAmount());

        savingGoal.setCurrentAmount(750.0);
        assertEquals(750.0, savingGoal.getCurrentAmount());

        savingGoal.setGoalName("Education");
        assertEquals("Education", savingGoal.getGoalName());
    }

    @Test
    void testToString() {
        String expected = "SavingGoal{" +
                "goalId='goal1'" +
                ", childName='child1'" +
                ", targetAmount=1000.0" +
                ", currentAmount=500.0" +
                ", goalName='Vacation'" +
                '}';
        assertEquals(expected, savingGoal.toString());
    }
}
