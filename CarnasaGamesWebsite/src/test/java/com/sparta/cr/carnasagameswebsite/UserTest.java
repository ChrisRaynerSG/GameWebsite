package com.sparta.cr.carnasagameswebsite;

import com.sparta.cr.carnasagameswebsite.models.User;
import com.sparta.cr.carnasagameswebsite.repositories.UserRepository;
import com.sparta.cr.carnasagameswebsite.service.SiteUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserTest {

    @Mock
    private UserRepository userRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private SiteUserService siteUserService;

    @BeforeEach
    void setUp() {
        bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    @Test
    @DisplayName("Test creating a user and adding to the system")
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
    @Test
    @DisplayName("Test deleting a user from the system")
    void testDeleteUser() {
        User user = new User();
        user.setId(1L);
        doNothing().when(userRepository).deleteById(user.getId());
        siteUserService.deleteUser(user);
        verify(userRepository, times(1)).deleteById(1L);
    }
    @Test
    @DisplayName("Test updating user password brings back new password")
    void testUpdateUserPassword() {
        boolean expected = true;
        User user = new User();
        user.setId(1L);
        user.setPassword("Test@1234");
        when(userRepository.save(user)).thenReturn(user);
        siteUserService.updatePassword(user, "P@55w0rd");
        verify(userRepository, times(1)).save(user);
        boolean actual = bCryptPasswordEncoder.matches("P@55w0rd",user.getPassword());
        assertEquals(expected, actual);
    }
    @Test
    @DisplayName("Test success if username and password are valid combination")
    void validateUsernameAndPassword() {
        boolean expected = true;
        User user = new User();
        user.setUsername("test");
        user.setPassword(bCryptPasswordEncoder.encode("Test@1234"));
        when(userRepository.findByUsername("test")).thenReturn(Optional.of(user));
        boolean actual = siteUserService.validateUserPasswordCombination("test", "Test@1234");
        assertEquals(expected, actual);
    }
    @Test
    @DisplayName("Test updating user email will return new email when called")
    void testUpdateUserEmail() {
        boolean expected = true;
        User user = new User();
        user.setId(1L);
        user.setEmail("test@test.com");
        when(userRepository.save(user)).thenReturn(user);
        siteUserService.updateEmail(user, "test2@test2.com");
        verify(userRepository, times(1)).save(user);
        boolean actual = user.getEmail().equals("test2@test2.com");
        assertEquals(expected, actual);
    }
    @Test
    @DisplayName("Test updating User Roles will return new Role when get Role called")
    void testUpdateUserRoles() {
        boolean expected = true;
        User user = new User();
        user.setId(1L);
        user.setRoles("ROLE_USER");
        when(userRepository.save(user)).thenReturn(user);
        siteUserService.updateRole(user, "ADMIN");
        verify(userRepository, times(1)).save(user);
        boolean actual = user.getRoles().equals("ROLE_ADMIN");
        assertEquals(expected, actual);
    }
    @Test
    @DisplayName("Test updating user description will return nerw description when getDescription called")
    void testUpdateUserDescription() {
        boolean expected = true;
        User user = new User();
        user.setId(1L);
        user.setDescription("This is a test description");
        when(userRepository.save(user)).thenReturn(user);
        siteUserService.updateDescription(user, "This is a new test description");
        verify(userRepository, times(1)).save(user);
        boolean actual = user.getDescription().equals("This is a new test description");
        assertEquals(expected, actual);
    }
    @Test
    @DisplayName("Test updating user profileImg will return new profile image when getProfileImage is called")
    void testUpdateUserProfileImg() {
        boolean expected = true;
        User user = new User();
        user.setId(1L);
        user.setProfileimage("This is a test profile image");
        when(userRepository.save(user)).thenReturn(user);
        siteUserService.updateProfileImage(user, "This is a test profile image2");
        verify(userRepository, times(1)).save(user);
        boolean actual = user.getProfileimage().equals("This is a test profile image2");
        assertEquals(expected, actual);
    }
    @Test
    @DisplayName("test that password entry must match password format")
    void testPasswordFormatWrongReturnFalse(){
        boolean expected = false;
        String password = "Password";
        boolean actual = siteUserService.validatePasswordFormat(password);
        assertEquals(expected, actual);
    }
    @Test
    @DisplayName("test that password entry must match password format")
    void testPasswordFormatCorrectReturnTrue(){
        boolean expected = true;
        String password = "P@55w0rd";
        boolean actual = siteUserService.validatePasswordFormat(password);
        assertEquals(expected, actual);
    }
    @ParameterizedTest
    @MethodSource()
    @DisplayName("test that email entry must match email format")
    void testEmailFormat(String email, boolean expected){
        boolean actual = siteUserService.validateEmailFormat(email);
        assertEquals(expected, actual);
    }

    public static Stream<Arguments> testEmailFormat(){
        return Stream.of(
                Arguments.of("test@test.com", true),
                Arguments.of("test@test.org.uk",true),
                Arguments.of("test@test", false),
                Arguments.of("test.test.com", false),
                Arguments.of("test@hotmail.co.uk", true),
                Arguments.of("something.something@gmail.com", true),
                Arguments.of("otheremail@something.fr", true),
                Arguments.of("TEST@TEST.COM", true),
                Arguments.of("TEST@TEST.C", false)
        );
    }



}
