package com.virtualbank.service;

import static org.mockito.Mockito.*;

import com.virtualbank.model.user.ParentUser;
import com.virtualbank.repository.AccountManagerSerializer;
import com.virtualbank.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AccountManagerSerializer accountManagerSerializer;

    @InjectMocks
    private UserService userService;

    @Test
    void testRegisterUserParentSuccess() throws IOException {
        when(userRepository.existsByUsername("parentUser")).thenReturn(false);
        userService.registerUser("parentUser", "password123", true);
        verify(userRepository).save(any(ParentUser.class));
    }

}
