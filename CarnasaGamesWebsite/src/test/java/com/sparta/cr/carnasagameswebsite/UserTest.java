package com.sparta.cr.carnasagameswebsite;

import com.sparta.cr.carnasagameswebsite.models.User;
import com.sparta.cr.carnasagameswebsite.repositories.UserRepository;
import com.sparta.cr.carnasagameswebsite.service.SiteUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private SiteUserService siteUserService;

    @Test
    @DisplayName("Test creating a user")
    void testCreateUser() {
        User user = new User();
        user.setUsername("test");
        user.setPassword("Test@1234");
        user.setEmail("test@test.com");

        when(userRepository.save(user)).thenReturn(user);

        User createdUser = siteUserService.createUser(user);

        assertNotNull(createdUser);
        assertEquals("test", createdUser.getUsername());
        assertEquals("test@test.com", createdUser.getEmail());
    }
}
