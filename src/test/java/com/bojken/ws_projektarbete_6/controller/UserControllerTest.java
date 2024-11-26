package com.bojken.ws_projektarbete_6.controller;

import com.bojken.ws_projektarbete_6.model.CustomUser;
import com.bojken.ws_projektarbete_6.model.UserDTO;
import com.bojken.ws_projektarbete_6.repository.MovieRepository;
import com.bojken.ws_projektarbete_6.repository.UserRepository;
import com.bojken.ws_projektarbete_6.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import static org.mockito.Mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserService userService;
    @Mock
    private MovieRepository movieRepository;
    @Mock
    private Model model;
    @Mock
    private BindingResult bindingResult;

    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userController = new UserController(userRepository, passwordEncoder, userService, movieRepository);
    }

    @Test
    void testRegisterGetEndpoint() {
        // Testa om registerUser ger rätt vy och att attributet "userDTO" sätts i modellen
        String viewName = userController.registerUser((Model) model);
        assertEquals("register", viewName);                         // Assert 3
        verify(model).addAttribute(eq("userDTO"), any(UserDTO.class));  // Assert 4
    }

    @Test
    void testRegisterUserWithValidCredentials() {
        // Testa att användaren kan registreras med giltiga uppgifter
        UserDTO user = new UserDTO("testUser", "password123");

        when(bindingResult.hasErrors()).thenReturn(false); // Inget fel vid validering
        when(userRepository.findByUsername(any())).thenReturn(Optional.empty()); // Ingen användare finns med det namnet
        when(passwordEncoder.encode(any())).thenReturn("encodedPassword"); // Simulera lösenordskodning

        String viewName = userController.registerUser(user, bindingResult, (Model) model);
        assertEquals("redirect:/", viewName);      // Assert 5
        verify(userRepository).save(any(CustomUser.class));        // Assert 6
    }

    @Test
    void testRegisterUserWithExistingUsername() {
        // Testa när användarnamnet redan är taget
        UserDTO user = new UserDTO("existingUser", "password123");

        when(bindingResult.hasErrors()).thenReturn(false); // Inget fel vid validering
        when(userRepository.findByUsername("existingUser")).thenReturn(Optional.of(new CustomUser("existingUser", "password123"))); // Användare finns redan

        String viewName = userController.registerUser(user, bindingResult, (Model) model);
        assertEquals("register", viewName); // Assert 7
        verify(model).addAttribute(eq("usernameError"), eq("Username is already taken")); // Assert 8
    }
}
