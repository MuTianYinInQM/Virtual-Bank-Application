package com.virtualbank.model.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    private User user;

    @BeforeEach
    void setUp() {
        user = new User("testUser", "testPass", true);
    }

    @Test
    void testUserConstructor() {
        assertEquals("testUser", user.getUsername());
        assertEquals("testPass", user.getPassword());
    }

    @Test
    void testSetAndGetUsername() {
        user.setUsername("newUser");
        assertEquals("newUser", user.getUsername(), "The username should be updated to newUser.");
    }

    @Test
    void testSetAndGetPassword() {
        user.setPassword("newPass");
        assertEquals("newPass", user.getPassword(), "The password should be updated to newPass.");
    }

}
